package server.games;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import server.client.GameServer;
import server.client.IServerCommunicator;
import server.client.ServerCommunicator;
import server.db.IPersistenceProvider;
import server.models.ServerAction;
import server.models.ServerModel;
import server.plugin.IPlugin;
import server.plugin.IPluginLoader;
import server.plugin.PluginConfig;
import server.plugin.PluginLoader;
import shared.IServer;
import shared.models.GameAction;
import shared.models.ICommandAction;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static server.plugin.PluginConfig.PluginType.PERSISTENCE;

public class ServerManager implements IServerManager {
    private Map<Integer, IServer> runningServers = new HashMap<>();
    private IServerCommunicator communicator;
    private ServerModel model;
    private IPluginLoader pluginLoader;
    private List<IPlugin> plugins = new ArrayList<>();
    private IPersistenceProvider persistenceProvider;
    private int N;
    private int commandsAdded;

    public ServerManager(String persistence, int N) throws IOException {
        communicator = new ServerCommunicator(this);
        pluginLoader = new PluginLoader();
        this.N = N;
        commandsAdded = 0;
        //TODO:: fix args for real
        String fs = File.separator;
        String pluginConfigFile = "java" + fs + "plugins" + fs + "config.yaml";
        File pluginDir = new File("java" + fs + "plugins");

        List<PluginConfig> pc = pluginLoader.parseConfig(pluginConfigFile);
        List<IPlugin> lc = pluginLoader.loadConfig(pc, pluginDir);
        persistenceProvider = getPersistenceProvider(lc, persistence);
        plugins = Collections.singletonList(persistenceProvider);
        if (persistenceProvider == null || persistenceProvider.start() == null) {
            throw new IOException("Error starting persistence provider " + persistence);
        }
    }

    @Nullable
    @Override
    public IServer getGameServer(int gameId) {
        // TODO: Check if game exists in server model
        return runningServers.computeIfAbsent(gameId, id -> new GameServer(this, id));
    }

    public void setGameServer(int gameId, IServer server) {
        runningServers.put(gameId, server);
    }

    @NotNull
    @Override
    public ServerModel getServerModel() {
        if (model == null) {
            model = new ServerModel();
            updateFromDatabase();
        }
        return model;
    }

    @Override
    public void startServer(@NotNull String hostname, int port) throws IOException {
        communicator.bind(hostname, port);
        communicator.start();
    }

    @Override
    public void stopServer() {
        communicator.stop();
    }

    /**
     * Takes a list of available running plugins and checks if there is one of type PERSISTENCE and
     * returns that to be the servers current database
     *
     * @param plugins
     * @param name
     * @return PersistenceProvider for server
     */
    @Override
    public IPersistenceProvider getPersistenceProvider(List<IPlugin> plugins, String name) {
        return (IPersistenceProvider) plugins.stream()
                .filter(x -> x.getType() == PERSISTENCE && name.equals(x.getName()))
                .findFirst().orElse(null);
    }

    @Override
    public IPersistenceProvider getPersistenceProvider() {
        return persistenceProvider;
    }

    public void storeCommand(ICommandAction command, int GameID) {
        commandsAdded++;
        if (commandsAdded >= N) {
            if (persistenceProvider.getGameDAO().clearCommands()) {
                commandsAdded = 0;
                getServerModel().updateGamesInDatabase(persistenceProvider);
            }
        } else {
            persistenceProvider.getGameDAO().insertCommand(command, GameID);
        }
    }

    public void updateFromDatabase() {
        getServerModel().updateFromDatabase(persistenceProvider);
        for (ICommandAction command : persistenceProvider.getGameDAO().findAllCommands()) {
            if (command.getGameId() == -1) {
                ServerAction action = (ServerAction) command;
                ((ServerAction) command).setServerModel(getServerModel());
                action.execute();
            } else {
                GameAction action = (GameAction) command;
                action.setFacades(((GameServer) getGameServer(command.getGameId())).getFacades());
                action.execute();
            }
            persistenceProvider.getGameDAO().clearCommands();
            getServerModel().updateGamesInDatabase(persistenceProvider);
        }
    }
}

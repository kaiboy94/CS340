package server.models;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.CredentialNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class ServerModel {

    // Possibly consider a Map<String, User> instead
    private Map<Integer, User> users;
    private Map<Integer, GameModel> gameModels;

    public ServerModel() {
        users = new HashMap<>();
        gameModels = new HashMap<>();
    }

    /**
     * Register a new user with a given username and password.
     *
     * @return the ID of the new user
     */
    public int registerUser(@NotNull String username, @NotNull String password) throws CredentialNotFoundException {
        if (users.values().stream().anyMatch(u -> username.equals(u.getUsername()))) {
            throw new CredentialNotFoundException("The user " + username + " already exists!");
        }
        int id = users.keySet().stream().max(Integer::compareTo).orElseGet(() -> 0) + 1;
        User user = new User();
        user.setId(id);
        assert !users.containsKey(id);
        users.put(id, user);
        return id;
    }

    /**
     * Determine whether a username and password combination are already registered
     *
     * @param username the username, not null
     * @param password the password, not null
     * @return true if the user is authenticated, else false
     */
    public boolean authenticateUser(@NotNull String username, @NotNull String password) {
        User user = users.values().stream().filter(u -> username.equals(u.getUsername())).findFirst().orElse(null);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Called when a new game is created.  This method creates a new {@link GameModel}.
     *
     * @return the new {@link GameModel#id} created
     * @pre {@link #getGameModel(int)} returns a valid {@link GameModel} for some {@code id}
     * @post A new {@link GameModel} is placed in {@link #gameModels}
     */
    public int startNewGame() {
        return 0;
    }

    /**
     * Returns a {@link User} object or null
     *
     * @param ID An integer that is used to determine which {@link User} to return
     * @return a {@link User} if {@code ID} is contained in {@link #users},
     * null otherwise
     * @pre None
     * @post A {@link User} object or null is returned
     */
    @Nullable
    public User getUser(int ID) {
        return users.get(ID);
    }

    /**
     * Returns a {@link GameModel} object or null
     *
     * @param ID An integer that is used to determine which {@link GameModel} to return
     * @return a {@link GameModel} if {@code ID} is contained in {@link #gameModels},
     * null otherwise
     * @pre None
     * @post A value {@link GameModel} object or null is returned
     */
    @Nullable
    public GameModel getGameModel(int ID) {
        return gameModels.get(ID);
    }
}

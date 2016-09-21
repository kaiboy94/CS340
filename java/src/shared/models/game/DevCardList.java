package shared.models.game;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import shared.definitions.DevCardType;

import javax.annotation.Generated;

@Generated("net.kupiakos")
public class DevCardList {

    @SerializedName("yearOfPlenty")
    @Expose
    private int yearOfPlenty;

    @SerializedName("roadBuilding")
    @Expose
    private int roadBuilding;

    @SerializedName("monument")
    @Expose
    private int monument;

    @SerializedName("soldier")
    @Expose
    private int soldier;

    @SerializedName("monopoly")
    @Expose
    private int monopoly;


    /**
     * No args constructor for use in serialization
     */
    public DevCardList() {
    }

    /**
     * @param yearOfPlenty The Year of Plenty Cards
     * @param roadBuilding The Road Building Cards
     * @param monument     The Monument (Victory Point) Cards
     * @param soldier      The Knight/Soldier Cards
     * @param monopoly     The Monopoly Cards
     */
    public DevCardList(int yearOfPlenty, int roadBuilding, int monument, int soldier, int monopoly) {
        this.yearOfPlenty = yearOfPlenty;
        this.roadBuilding = roadBuilding;
        this.monument = monument;
        this.soldier = soldier;
        this.monopoly = monopoly;
    }

    // CUSTOM CODE

    /**
     * Get the number of cards in this DevCardList of a specific type
     * @param type the type to check against
     * @return the number of cards, less than 0
     */
    public int getOfType(DevCardType type) {
        return 0;
    }

    // END CUSTOM CODE

    /**
     * @return The Year of Plenty Cards
     */
    public int getYearOfPlenty() {
        return yearOfPlenty;
    }

    /**
     * @param yearOfPlenty The Year of Plenty Cards
     */
    public void setYearOfPlenty(int yearOfPlenty) {
        if (yearOfPlenty < 0 || yearOfPlenty > 2) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 2");
        }
        this.yearOfPlenty = yearOfPlenty;
    }

    public DevCardList withYearOfPlenty(int yearOfPlenty) {
        setYearOfPlenty(yearOfPlenty);
        return this;
    }

    /**
     * @return The Road Building Cards
     */
    public int getRoadBuilding() {
        return roadBuilding;
    }

    /**
     * @param roadBuilding The Road Building Cards
     */
    public void setRoadBuilding(int roadBuilding) {
        if (roadBuilding < 0 || roadBuilding > 2) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 2");
        }
        this.roadBuilding = roadBuilding;
    }

    public DevCardList withRoadBuilding(int roadBuilding) {
        setRoadBuilding(roadBuilding);
        return this;
    }

    /**
     * @return The Monument (Victory Point) Cards
     */
    public int getMonument() {
        return monument;
    }

    /**
     * @param monument The Monument (Victory Point) Cards
     */
    public void setMonument(int monument) {
        if (monument < 0 || monument > 5) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 5");
        }
        this.monument = monument;
    }

    public DevCardList withMonument(int monument) {
        setMonument(monument);
        return this;
    }

    /**
     * @return The Knight/Soldier Cards
     */
    public int getSoldier() {
        return soldier;
    }

    /**
     * @param soldier The Knight/Soldier Cards
     */
    public void setSoldier(int soldier) {
        if (soldier < 0 || soldier > 14) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 14");
        }
        this.soldier = soldier;
    }

    public DevCardList withSoldier(int soldier) {
        setSoldier(soldier);
        return this;
    }

    /**
     * @return The Monopoly Cards
     */
    public int getMonopoly() {
        return monopoly;
    }

    /**
     * @param monopoly The Monopoly Cards
     */
    public void setMonopoly(int monopoly) {
        if (monopoly < 0 || monopoly > 2) {
            throw new IllegalArgumentException("value cannot be less than 0 or more than 2");
        }
        this.monopoly = monopoly;
    }

    public DevCardList withMonopoly(int monopoly) {
        setMonopoly(monopoly);
        return this;
    }

    @Override
    public String toString() {
        return "DevCardList [" +
                "yearOfPlenty=" + yearOfPlenty +
                ", roadBuilding=" + roadBuilding +
                ", monument=" + monument +
                ", soldier=" + soldier +
                ", monopoly=" + monopoly +
                "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof DevCardList) {
            return equals((DevCardList) other);
        }
        return false;
    }

    public boolean equals(DevCardList other) {
        return (
                yearOfPlenty == other.yearOfPlenty &&
                        roadBuilding == other.roadBuilding &&
                        monument == other.monument &&
                        soldier == other.soldier &&
                        monopoly == other.monopoly
        );
    }
}
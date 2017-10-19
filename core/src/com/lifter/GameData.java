package com.lifter;

import java.util.ArrayList;

/**
 * Created by Cameron on 19/06/2017.
 */
public class GameData {
    private Lift lift;
    private ArrayList<Floor> floors = new ArrayList();
    private boolean paused;

    int score;

    public GameData(Lift lift, ArrayList<Floor> floors, int score) {
        this.lift = lift;
        this.floors = floors;
        this.score = score;
        this.paused = true;
    }

    public Lift getLift() {
        return lift;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public int getScore() {
        return score;
    }

    public void setLift(Lift lift) {
        this.lift = lift;
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

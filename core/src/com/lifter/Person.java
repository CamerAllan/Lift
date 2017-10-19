package com.lifter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.Random;

/**
 * Created by Cameron on 19/06/2017.
 */
public class Person {
    private PersonState state;
    private int currentFloor;
    private int desiredFloor;
    private int liftPos;
    private Profession profession;
    private double distanceFromLift;

    //For drawing
    Sprite stoodSprite;
    Sprite walkingSprite;

    public Person(int currentFloor, int desiredFloor, Profession profession, TextureAtlas atlas) {
        Random rand = new Random();
        this.state = PersonState.ARRIVING;
        this.currentFloor = currentFloor;
        this.desiredFloor = desiredFloor;
        this.liftPos = rand.nextInt(18) - 12;
        this.distanceFromLift = 136 - Lift.shaftWidth;
        this.profession = profession;

        String code = getCode(profession);

        this.stoodSprite = atlas.createSprite(code + "Stood");
        this.walkingSprite = atlas.createSprite(code + "Walk");
    }

    public String getCode(Profession profession) {
        Random rand = new Random();

        //Profession part
        switch (profession) {
            case MAN: return "man" + rand.nextInt(5);
            case WOMAN: return "woman" + rand.nextInt(5);
            case DOCTOR: return "doctor" + rand.nextInt(3);
            case BUILDER: return "builder"  + rand.nextInt(4);
            default:
                System.err.println("You messed up in getCode() in the person class");
                return "doctor";
        }
    }

    public void board(Lift lift, Floor floor) {
        this.state = PersonState.ONLIFT;
        floor.getPeople().remove(this);
        lift.addPerson(this);
    }

    public void alight(Lift lift, Floor floor) {
        this.state = PersonState.DEPARTING;
        lift.removePerson(this);
        floor.getPeople().add(this);
        this.setDistanceFromLift(35);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDesiredFloor() {
        return desiredFloor;
    }

    public void setDesiredFloor(int desiredFloor) {
        this.desiredFloor = desiredFloor;
    }

    public PersonState getState() {
        return state;
    }

    public void setState(PersonState state) {
        this.state = state;
    }

    public Sprite getStoodSprite() {
        return stoodSprite;
    }

    public void setStoodSprite(Sprite stoodSprite) {
        this.stoodSprite = stoodSprite;
    }

    public Sprite getWalkingSprite() {
        return walkingSprite;
    }

    public void setWalkingSprite(Sprite walkingSprite) {
        this.walkingSprite = walkingSprite;
    }

    public int getLiftPos() {
        return liftPos;
    }

    public void setLiftPos(int liftPos) {
        this.liftPos = liftPos;
    }

    public double getDistanceFromLift() {
        return distanceFromLift;
    }

    public void setDistanceFromLift(double distanceFromLift) {
        this.distanceFromLift = distanceFromLift;
    }
}

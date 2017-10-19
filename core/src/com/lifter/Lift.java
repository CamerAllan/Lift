package com.lifter;

import java.util.ArrayList;

/**
 * Created by Cameron on 19/06/2017.
 */
public class Lift {
    public static int shaftWidth = 40;

    private double speed;
    private double maxSpeed;
    private double height;
    private int capacity;
    private int holding;
    private int currentFloor;
    private int destination;
    private double brakes;
    private double accel;
    private boolean braking;
    private LiftState state;
    private ArrayList<Person> people = new ArrayList();

    public Lift() {
        this.speed = 0;
        this.maxSpeed = 5;
        this.capacity = 3;
        this.brakes = 0.2;
        this.accel = 0.4;

        this.braking = false;
        this.height = 0;
        this.holding = 0;
        this.currentFloor = 0;
        this.destination = 0;
        this.state = LiftState.IDLE;
    }

    public void move() {
        this.setCurrentFloor((int) (this.getHeight() + Floor.height / 2) / Floor.height);

        double distanceToTarget = this.getDestination() * Floor.height - this.getHeight();

        double t = this.getSpeed() / this.getBrakes();

        //If we need to start braking
        if (Math.abs(this.getSpeed() * t / 2) >= Math.abs(distanceToTarget)) {
            //Stop completely
            if ((this.getState() == LiftState.UP && distanceToTarget <= 0)
                    || (this.getState() == LiftState.DOWN && distanceToTarget >= 0)) {
                this.setSpeed(0);
                this.setState(LiftState.IDLE);
            }
            //Brake
            else {
                this.setBraking(true);
                if (this.getState() == LiftState.UP)
                    this.decreaseSpeed();
                else
                    this.increaseSpeed();
            }

        }
        //We keep moving
        else {
            if (Math.abs(this.getSpeed()) < maxSpeed) {
                //Accelerate only if below max speed
                if (this.getState() == LiftState.UP) {
                    this.increaseSpeed();
                }
                if (this.getState() == LiftState.DOWN) {
                    this.decreaseSpeed();
                }
            }
        }
    }

    public void addPerson(Person person) {
        this.people.add(person);
    }

    public void removePerson(Person person) {
        this.people.remove(person);
    }

    public boolean isFull() {
        return this.capacity <= this.people.size();
    }

    public void increaseSpeed() {
        this.speed += this.accel;
    }

    public void decreaseSpeed() {
        this.speed -= this.accel;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getHolding() {
        return holding;
    }

    public void setHolding(int holding) {
        this.holding = holding;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public double getBrakes() {
        return brakes;
    }

    public void setBrakes(double brakes) {
        this.brakes = brakes;
    }

    public double getAccel() {
        return accel;
    }

    public void setAccel(double accel) {
        this.accel = accel;
    }

    public boolean isBraking() {
        return braking;
    }

    public void setBraking(boolean braking) {
        this.braking = braking;
    }

    public LiftState getState() {
        return state;
    }

    public void setState(LiftState state) {
        this.state = state;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

}

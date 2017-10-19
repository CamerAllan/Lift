package com.lifter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Cameron on 19/06/2017.
 */
public class Floor {

    static int height;
    double peopleRate;
    static ArrayList<String> floorTypes;
    int floorNo;
    LinkedList<Person> people = new LinkedList<Person>();
    private Random rand = new Random();
    String spriteName;

    //For drawing
    Sprite sprite;

    public Floor(LinkedList<Person> people, int floorNo, TextureAtlas atlas) {

        height = 44;
        this.people = people;
        this.floorNo = floorNo;
        this.peopleRate = 500;

        if (this.floorNo == 0) {
            this.peopleRate = 200;
            spriteName = "floor0";
            sprite = atlas.createSprite(spriteName);
        }
        else {
            Random rand = new Random();
            int randomInt = rand.nextInt(floorTypes.size());

            spriteName = floorTypes.get(randomInt);
            sprite = atlas.createSprite(spriteName);

            floorTypes.remove(randomInt);
        }
    }

    public void addPerson(TextureAtlas atlas, ArrayList<Floor> floors) {
        Random rand = new Random();
        int desired;
        Profession profession = getProfession(this.spriteName);

        if (this.floorNo == 0) {
            switch (profession) {
                case BUILDER:
                    desired = getIndexByProperty("construction0", floors);
                    break;
                case DOCTOR:
                    desired = getIndexByProperty("hospital0", floors);
                    break;
                default :
                    desired = rand.nextInt(5);
                    break;
            }
        } else {
            do {
                desired = rand.nextInt(5);
            } while (desired == this.floorNo);
        }
        if (rand.nextInt((int) peopleRate) == 1)
            this.people.add(new Person(this.floorNo, desired, profession, atlas));
    }

    private static int getIndexByProperty(String yourString, ArrayList<Floor> floors) {
        for (int i = 0; i < floors.size(); i++) {
            if (floors.get(i).getSpriteName().equals(yourString)) {
                return i;
            }
        }
        return 0;
    }

    public Profession getProfession(String floor) {
        Random rand = new Random();

        if (floor.equals("floor0")) {
            int randInt = rand.nextInt(5);
            switch (randInt) {
                case 0 : return Profession.DOCTOR;
                case 1 : return Profession.BUILDER;
                default:
                    return returnHuman();
            }
        } else if (floor.equals("hospital0")) {
            if (rand.nextBoolean()) {
                return Profession.DOCTOR;
            } else {
                return returnHuman();
            }
        } else if (floor.equals("construction0")) {
            if (rand.nextBoolean()) {
                return Profession.BUILDER;
            } else {
                return returnHuman();
            }
        } else {
            return returnHuman();
        }
    }

    public Profession returnHuman() {
        return rand.nextBoolean() ? Profession.MAN : Profession.WOMAN;
    }

    public int getFloorNo() {
        return floorNo;
    }

    public LinkedList<Person> getPeople() {
        return people;
    }

    public void setPeople(LinkedList<Person> people) {
        this.people = people;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Floor.height = height;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public double getPeopleRate() {
        return peopleRate;
    }

    public void setPeopleRate(double peopleRate) {
        this.peopleRate = peopleRate;
    }

    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String spriteName) {
        this.spriteName = spriteName;
    }
}




package com.lifter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.LinkedList;

/**
 * Created by Cameron on 22/06/2017.
 */
public class Drawer {

    private Sprite shaftSprite;
    private Sprite liftOpen;
    private Sprite liftUp;
    private Sprite liftDown;
    private Sprite infobar;
    private Sprite lightOff;
    private Sprite lightOn;
    private Pixmap pixmap;
    private TextureRegion cableTexture;
    private BitmapFont font;

    private int pixelRatio;

    private TextureAtlas atlas;

    public Drawer(TextureAtlas atlas) {
        font = new BitmapFont();

        this.atlas = atlas;
        this.liftOpen = atlas.createSprite("liftOpen");
        this.liftDown = atlas.createSprite("liftDown");
        this.liftUp = atlas.createSprite("liftUp");
        this.shaftSprite = atlas.createSprite("shaft");
        this.infobar = atlas.createSprite("infobar");
        this.lightOn = atlas.createSprite("lightOn");
        this.lightOff = atlas.createSprite("lightOff");

        this.pixmap = new Pixmap(10,10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        cableTexture = new TextureRegion();
        cableTexture.setTexture(new Texture(pixmap));

        this.pixelRatio = Gdx.graphics.getHeight() / 240;
    }

    public void draw(Batch batch, GameData gameData) {
        batch.begin();
        Lift lift = gameData.getLift();

        //Draw shaft
        batch.draw(shaftSprite, 0, 0);
        //Draw lift
        if (lift.getState() == LiftState.IDLE)
            batch.draw(liftOpen, 3, (int) gameData.getLift().getHeight());
        else {
            if (lift.getState() == LiftState.DOWN)
                batch.draw(liftDown, 3, (int) gameData.getLift().getHeight());
            else
                batch.draw(liftUp, 3, (int) gameData.getLift().getHeight());
        }

        if (lift.getState() == LiftState.IDLE) {
            for (Person person : lift.getPeople()) {
                batch.draw(person.getStoodSprite(), 20 + person.getLiftPos(),
                        (int) lift.getHeight() + 4);
            }
        }

        //Draw floors and people on floors
        for (Floor floor : gameData.getFloors()) {
            int floorHeight = floor.getFloorNo() * Floor.getHeight();
            batch.draw(floor.getSprite(), 40, floorHeight);
            LinkedList people = floor.getPeople();
            for (int i = 0 ; i < people.size() ; i++) {
                Person person = floor.getPeople().get(i);
                if (person.getState() == PersonState.ARRIVING) {
                    if ((int) person.getDistanceFromLift() % 2 == 0)
                        batch.draw(floor.getPeople().get(i).getWalkingSprite(), 40 + i * person.getWalkingSprite().getWidth() * 2 + (int) person.getDistanceFromLift(), floorHeight);
                    else
                        batch.draw(floor.getPeople().get(i).getStoodSprite(), 40 + i * person.getStoodSprite().getWidth() * 2 + (int) person.getDistanceFromLift(), floorHeight);
                }
                if (person.getState() == PersonState.WAITING)
                    batch.draw(floor.getPeople().get(i).getStoodSprite(), 40 + i * person.getStoodSprite().getWidth() * 2 + (int) person.getDistanceFromLift(), floorHeight);

                if (person.getState() == PersonState.DEPARTING) {
                    person.getStoodSprite().flip(true, false);
                    person.getWalkingSprite().flip(true, false);
                    if ((int) person.getDistanceFromLift() % 2 == 0)
                        batch.draw(floor.getPeople().get(i).getWalkingSprite(), 10 + (int) person.getDistanceFromLift(), floorHeight);
                    else
                        batch.draw(floor.getPeople().get(i).getStoodSprite(), 10 + (int) person.getDistanceFromLift(), floorHeight);
                    person.getStoodSprite().flip(true, false);
                    person.getWalkingSprite().flip(true, false);
                }
            }
        }



        //Draw info bar
        batch.draw(infobar, 40, 220);

        for (int i = 0 ; i < 5 ; i++) {
            int lightOnDistance = 40 + i * 19;
            batch.draw(lightOff, lightOnDistance, 220);
        }


        for (Person person : lift.getPeople()) {
            int lightOnDistance = 40 + person.getDesiredFloor() * 19;
            batch.draw(lightOn, lightOnDistance, 220);
        }

        font.draw(batch, "" + gameData.score, 3, 235);

        batch.draw(cableTexture, 13, (int)lift.getHeight() + Floor.getHeight() - 6, 1, 210 - (int) lift.getHeight() - Floor.getHeight() + 8);
        batch.draw(cableTexture, 26, (int)lift.getHeight() + Floor.getHeight() - 6, 1, 210 - (int) lift.getHeight() - Floor.getHeight() + 8);

        batch.end();
    }
}

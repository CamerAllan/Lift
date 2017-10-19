package com.lifter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Cameron on 22/06/2017.
 */
public class GameScreen implements Screen{
    final MainClass game;

    //Camera
    private OrthographicCamera camera;

    //Sprites and bitmaps
    private Drawer drawer;
    private TextureAtlas atlas;
    private SpriteBatch batch;

    //Constants
    final int startingNumberOfFloors = 5;

    //Game variables
    boolean paused;
    GameData gameData;

    public GameScreen(final MainClass game) {
        this.game = game;
        drawer = game.drawer;
        batch = game.batch;
        atlas = game.atlas;

        //Start stuff, like music

        //Setup camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 136, 240);

        //Sprites
        atlas = new TextureAtlas(Gdx.files.internal("atlas"));

        restart();
    }

    public void restart() {

        Floor.floorTypes = new ArrayList<String>();

        Floor.floorTypes.add("hospital0");
        Floor.floorTypes.add("apartments0");
        Floor.floorTypes.add("clothes0");
        Floor.floorTypes.add("construction0");

        Lift newLift = new Lift();
        ArrayList<Floor> newFloors = new ArrayList();
        for (int i = 0 ; i < startingNumberOfFloors ; i++)
            newFloors.add(new Floor(new LinkedList<Person>(), i, atlas));
        gameData = new GameData(newLift, newFloors, 0);
        Gdx.input.setInputProcessor(new GameScreenInput(gameData, camera, paused));
        paused = false;
    }

    @Override
    public void render(float delta) {
        draw();

        if (!paused)
            update();
    }

    public void update() {
        Lift lift = gameData.getLift();
        ArrayList<Floor> floors = gameData.getFloors();

        //If lift is moving, move
        if (lift.getState() != LiftState.IDLE) {
            lift.move();
            lift.setHeight(lift.getHeight() + lift.getSpeed());
        }

        //Add and update people people
        for (int i = 0 ; i < lift.getPeople().size() ; i++) {
            Person person = lift.getPeople().get(i);
            if (person.getDesiredFloor() == lift.getCurrentFloor() && lift.getState() == LiftState.IDLE) {
                person.alight(lift, floors.get(lift.getCurrentFloor()));
                gameData.score++;
            }
        }

        //For all floors
        for (int i = 0 ; i < startingNumberOfFloors ; i++) {
            Floor floor = floors.get(i);

            //Increase people spawn rate
            if (floor.getPeopleRate() > 2)
                floor.setPeopleRate(floor.getPeopleRate() - 0.02);

            floor.addPerson(atlas, floors);
            int queueSize = 0;
                for (int j = 0; j < floor.getPeople().size() ; j++) {
                    Person person = floor.getPeople().get(j);

                    if (person.getState() == PersonState.ARRIVING)
                        person.setDistanceFromLift(person.getDistanceFromLift() - 0.2);

                    if (person.getState() == PersonState.DEPARTING)
                        person.setDistanceFromLift(person.getDistanceFromLift() + 0.2);

                    if (person.getDistanceFromLift() < 5) {
                        person.setState(PersonState.WAITING);
                        queueSize++;
                    }

                    if (person.getDistanceFromLift() > 136)
                        floor.getPeople().remove(person);

                    if (person.getState() == PersonState.WAITING) {
                        if (lift.getState() == LiftState.IDLE && person.getCurrentFloor() == lift.getCurrentFloor() && !lift.isFull()) {
                            person.board(lift, floor);
                        }
                    }
                }

            if (queueSize > 5) {
                gameOver();
            }



        }
    }

    public void gameOver() {
        restart();
    }

    public void draw() {

        //Clear canvas
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Begin Sprite batch
        drawer.draw(batch, gameData);
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
    }


    @Override
    public void hide() {
    }

    @Override
    public void resize(int width, int height) {
    }


    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        //TODO: dispose of memory stuff sounds images etc
    }
}

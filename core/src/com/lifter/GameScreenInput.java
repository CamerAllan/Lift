package com.lifter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;

/**
 * Created by Cameron on 22/06/2017.
 */
public class GameScreenInput implements InputProcessor {

    private GameData gameData;
    private Camera camera;
    private int pixelRatio;
    private int infoBarHeight;

    public GameScreenInput(GameData gameData, Camera camera, boolean paused) {
        this.pixelRatio = Gdx.graphics.getHeight() / 240;
        this.infoBarHeight = 20 * pixelRatio;
        this.gameData = gameData;
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Lift lift = gameData.getLift();

        if (gameData.getLift().getState() == LiftState.IDLE) {
            lift.setDestination((Gdx.graphics.getHeight() - screenY) / ((Floor.getHeight()) * pixelRatio + infoBarHeight / 5));

            if (lift.getDestination() < 5 && lift.getDestination() != lift.getCurrentFloor()) {

                if (lift.getDestination() > lift.getCurrentFloor()) {
                    if (lift.getState() == LiftState.UP)
                        lift.setBraking(false);
                    if (lift.getState() == LiftState.DOWN)
                        lift.setBraking(true);

                    lift.setState(LiftState.UP);

                }
                if (lift.getDestination() < lift.getCurrentFloor()) {
                    if (lift.getState() == LiftState.UP)
                        lift.setBraking(true);
                    if (lift.getState() == LiftState.DOWN)
                        lift.setBraking(false);

                    lift.setState(LiftState.DOWN);
                }
                if (lift.getDestination() == lift.getCurrentFloor()) {
                    if (lift.getState() == LiftState.UP)
                        lift.setBraking(true);
                    if (lift.getState() == LiftState.DOWN)
                        lift.setBraking(false);

                    lift.setState(LiftState.DOWN);
                }
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }
}

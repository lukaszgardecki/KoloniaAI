package com.kolonia;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kolonia.rendering.WorldRenderer;
import com.kolonia.world.World;

import static com.kolonia.rendering.WorldRenderer.TILE_SIZE;

public class WorldScreen implements Screen, InputProcessor {
    private OrthographicCamera camera;
    private World world;
    private WorldRenderer renderer;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        world = new World();
        camera = new OrthographicCamera();
        renderer = new WorldRenderer(world, camera);
        centerCameraOnMap();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.update(delta);
        renderer.render();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        centerCameraOnMap();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    private void handleInput() {
        // wychodzenie excapem
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            Gdx.app.exit();
//        }

        handleMouseClick();
        handleCamera(Gdx.graphics.getDeltaTime());
    }

    private void handleCamera(float delta) {
//        float speed = 300 * delta;
//        if (Gdx.input.isKeyPressed(Input.Keys.W)) camera.position.y += speed;
//        if (Gdx.input.isKeyPressed(Input.Keys.S)) camera.position.y -= speed;
//        if (Gdx.input.isKeyPressed(Input.Keys.A)) camera.position.x -= speed;
//        if (Gdx.input.isKeyPressed(Input.Keys.D)) camera.position.x += speed;
    }

    private void handleMouseClick() {
        if (Gdx.input.justTouched()) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.input.getY();

//            Vector3 pos = camera.unproject(new Vector3(mouseX, mouseY, 0));
//            int tileX = (int)(pos.x / TILE_SIZE);
//            int tileY = (int)(pos.y / TILE_SIZE);

//            world.onTileClicked(tileX, tileY);
        }
    }

    private void centerCameraOnMap() {
        int mapWidth = world.getWidth() * TILE_SIZE;
        int mapHeight = world.getHeight() * TILE_SIZE;

        float camX = mapWidth / 2f;
        float camY = mapHeight / 2f;

        camera.position.set(camX, camY, 0);
        camera.update();
    }



    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int i, int i1, int i2, int i3) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }

    @Override
    public boolean scrolled(float amountX, float amountY) {
//        camera.zoom += amountY * 0.1f;
//        camera.zoom = MathUtils.clamp(camera.zoom, 0.3f, 3f);
        return true;
    }


}

package com.kolonia;

import com.badlogic.gdx.Game;

public class KoloniaGame extends Game {
    @Override
    public void create() {
        setScreen(new WorldScreen());
    }
}

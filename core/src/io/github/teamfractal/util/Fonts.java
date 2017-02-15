package io.github.teamfractal.util;

import com.badlogic.gdx.Gdx;

/**
 * Created by Joseph on 15/02/2017.
 */
public class Fonts {

    public TTFont montserratRegular;
    public TTFont montserratLight;

    public Fonts() {
        montserratRegular = new TTFont(Gdx.files.internal("font/MontserratRegular.ttf"));
        montserratLight = new TTFont(Gdx.files.internal("font/MontserratLight.ttf"));
    }

}

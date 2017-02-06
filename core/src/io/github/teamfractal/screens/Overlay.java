package io.github.teamfractal.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class Overlay extends Stage {

    /**
     * Stores current game-state, granting direct render access to the class
     */
    private Game game;

    /**
     * Table providing spatial framework for the overlay
     */
    private Table table;

    /**
     * Width of the overlay region
     */
    private float regionWidth;

    /**
     * Height of the overlay region
     */
    private float regionHeight;

    /**
     * Thickness of the overlay region's border
     */
    private int lineThickness;

    /**
     * The colour of the overlay's core
     */
    private Color fillColor;

    /**
     * The colour of the overlay's border
     */
    private Color lineColor;

    /**
     * Creates a stage that itself places a table of the specified parameters in the centre of the screen
     * The overlay's [draw()] method is unlike that of the standard Stage class as it also draws a bordered
     * rectangle behind the overlay region, hence rendering it a true overlay
     *
     * @param game Variable storing the game's state for rendering purposes
     * @param fillColor The colour of the overlay's background
     * @param lineColour The colour of the overlay's border
     * @param regionWidth The width of the overlay
     * @param regionHeight The height of the overlay
     * @param lineThickness The thickness of the overlay's border
     */
    public Overlay(Game game, Color fillColor, Color lineColour, float regionWidth, float regionHeight, int lineThickness) {
        super();
        //Construct the core stage

        this.game = game;
        //Import current game-state to access the game's renderer

        this.fillColor = fillColor;
        this.lineColor = lineColour;
        this.regionWidth = regionWidth;
        this.regionHeight = regionHeight;
        this.lineThickness = lineThickness;
        //Import overlay size/colour variables

        table = new Table();
        table.setBounds(((Gdx.graphics.getWidth() - this.regionWidth) / 2) + lineThickness, ((Gdx.graphics.getHeight() - this.regionHeight) / 2) + lineThickness, regionWidth - (lineThickness * 2), regionHeight - (lineThickness * 2));
        //Instantiate and prepare the table which will provide the overlay's spatial framework
        //This table will always inhabit the centre of the screen when this object is being drawn

        this.addActor(table);
        //Bind the overlay's spatial framework to the core stage
    }

    /**
     * Draws every actor on the core stage (along with a complementary bordered background) on to the next frame in
     * the game's rendering pipeline
     */
    @Override
    public void draw() {
        ShapeRenderer renderer = new ShapeRenderer();
        //Establish shape-renderer

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        //Set the renderer up to draw the overlay region's background block

        renderer.setColor(fillColor);
        //Set the colour of the overlay region's background

        renderer.rect((int) ((Gdx.graphics.getWidth() - this.regionWidth) / 2), (int) ((Gdx.graphics.getHeight() - this.regionHeight) / 2), regionWidth, regionHeight);
        //Draw the overlay region on the screen

        renderer.set(ShapeRenderer.ShapeType.Line);
        //Set the renderer up to draw the overlay region's border

        renderer.setColor(lineColor);
        //Set the colour of the overlay region's border

        for (int i = 0; i < lineThickness; i++) {
            renderer.rect((int) ((Gdx.graphics.getWidth() - this.regionWidth) / 2) + i, (int) ((Gdx.graphics.getHeight() - this.regionHeight) / 2) + i, regionWidth - (i * 2), regionHeight - (i * 2));
        }
        //Draw the overlay region's border on the screen (by executing one draw-call for each line of pixels comprising the border)

        renderer.end();
        //Shut the renderer down after the overlay region has been drawn

        renderer.dispose();
        //Get rid of the renderer now that it isn't useful anymore

        super.draw();
        //Draw the overlay's internal contents to the screen
    }

    /**
     * Returns the table that serves as the overlay's spatial framework
     *
     * @return Table The overlay's internal table
     */
    public Table table(){
        return table;
    }
}
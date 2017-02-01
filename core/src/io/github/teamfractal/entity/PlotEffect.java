package io.github.teamfractal.entity;

import com.badlogic.gdx.utils.Array;

/**
 * Created by Joseph on 31/01/2017.
 */
public class PlotEffect extends Array<Integer[]> {

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Constructor that assigns a name and a description to the effect
     *
     * @param name The name of the effect
     * @param description A description of the effect
     */
    public PlotEffect(String name, String description) {
        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference
    }

    /**
     * Imposes the effect's modifiers on the provided plot
     * If you ever want to restore a tile's original modifiers after using this method to change them, call this
     * again on the same tile and specify mode 2
     *
     * @param plot The plot to be affected
     * @param mode The mode of effect [0: ADD | 1: MULTIPLY | 2: OVERWRITE]
     * @param save Keeps the current modifiers at the top of the stack if true
     */
    public void impose(LandPlot plot, int mode, boolean save) {
        Integer[] originalModifiers = new Integer[3];
        Integer[] newModifiers;
        //Declare temporary arrays to handle modifier modifications

        newModifiers = super.pop();
        //Assume that the modifiers on the top of the stack are the modifiers to be imposed

        for (int i = 0; i < 3; i++) {
            originalModifiers[i] = plot.productionModifiers[i];
            //Save each of the specified tile's original modifiers

            switch (mode) {
                case (0):
                    plot.productionModifiers[i] += newModifiers[i];
                    //MODE 0: Add/subtract to/from the original modifiers
                    break;
                case (1):
                    plot.productionModifiers[i] *= newModifiers[i];
                    //MODE 1: Multiply the original modifiers
                    break;
                case (2):
                    plot.productionModifiers[i] = newModifiers[i];
                    //MODE 2: Replace the original modifiers
                    break;
            }
        }

        super.add(originalModifiers);
        //Add the tile's original modifiers to the stack for later access...

        if (save == true) {
            super.add(newModifiers);
        }
        //...and return the imposed modifiers to the top of the stack if needs be
    }

    public void swapTop() {
        if (super.size > 1) {
            Integer[] i = super.pop();
            Integer[] j = super.pop();

            super.add(i);
            super.add(j);
        }
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }
}

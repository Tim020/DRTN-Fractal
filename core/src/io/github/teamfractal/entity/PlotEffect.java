package io.github.teamfractal.entity;

/**
 * Created by Joseph on 31/01/2017.
 */
public class PlotEffect {

    /**
     * The plot that the object affects
     */
    private LandPlot plot;

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Array holding the production modifiers that the effect imposes
     */
    private int[] newModifiers;

    /**
     * Used as temporary storage for plot-modifiers that this class replaces
     */
    private int[] originalModifiers;

    /**
     * Determines whether the modifiers stored in this class are to be applied to those already affecting the bound
     * plot, or if they're meant to replace said plot's original modifiers entirely
     */
    private boolean overwrite;

    /**
     * Determines whether or not the effect is active
     */
    private boolean active;

    public PlotEffect(LandPlot plot, String name, String description, int[] productionModifiers, boolean overwrite) {
        this.plot = plot;
        //Assigns the effect to a plot

        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference

        this.newModifiers = productionModifiers;

        this.overwrite = overwrite;

        active = false;
    }

    public void switchActive() {
        if (active == false) {
            originalModifiers = plot.productionModifiers;

            if (overwrite == true) {
                plot.productionModifiers = newModifiers;
            } else {
                for (int i = 0; i < plot.productionModifiers.length; i++) {
                    plot.productionModifiers[i] *= newModifiers[i];
                }
            }
        } else {
            plot.productionModifiers = originalModifiers;
        }

        active = !active;
    }

    public Boolean active() {
        return active;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }
}

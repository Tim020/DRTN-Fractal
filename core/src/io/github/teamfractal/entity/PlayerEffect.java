package io.github.teamfractal.entity;

import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Joseph on 01/02/2017.
 */
public class PlayerEffect {

    /**
     * The name of the effect
     */
    private String name;

    /**
     * A description of the effect
     */
    private String description;

    /**
     * Array holding the inventory modifiers that the effect imposes
     * These are either to be added/subtracted to/from the player's current resource counts or multiplied by them,
     * depending on the value of the [multiply] variable declared below
     */
    private int[] modifiers;

    /**
     * Determines whether the effect is to add to (or subtract from) the player's resources or to multiply them by set
     * factors
     */
    private boolean multiply;

    public PlayerEffect(String name, String description, int oreModifier, int energyModifier, int foodModifier, int moneyModifier, int roboticonModifier, boolean multiply) {
        this.name = name;
        this.description = description;
        //Stores the effect's name and description for future reference

        this.modifiers = new int[4];
        this.modifiers[0] = oreModifier;
        this.modifiers[1] = energyModifier;
        this.modifiers[2] = foodModifier;
        this.modifiers[3] = moneyModifier;

        this.multiply = multiply;
    }

    /**
     * Imposes the effect on the player by changing the resources that they have. Their resources can either be
     * multiplied or divided
     * @param player The player that is to be effected
     */
    public void impose(Player player) {
        if (multiply == true) {
            player.setResource(ResourceType.ORE, player.getOre() * modifiers[0]);
            player.setResource(ResourceType.ENERGY, player.getEnergy() * modifiers[1]);
            player.setResource(ResourceType.FOOD, player.getFood() * modifiers[2]);
            player.setMoney(player.getMoney() * modifiers[3]);
        } else {
            player.setResource(ResourceType.ORE, player.getOre() + modifiers[0]);
            player.setResource(ResourceType.ENERGY, player.getEnergy() + modifiers[1]);
            player.setResource(ResourceType.FOOD, player.getFood() + modifiers[2]);
            player.setMoney(player.getMoney() + modifiers[3]);
        }
    }

    /**
     * Getter for the name of the effect
     * @return The name of the effect
     */
    public String name() {
        return name;
    }

    /**
     * Getter for the description of the effect
     * @return The description of the effect
     */
    public String description() {
        return description;
    }
}

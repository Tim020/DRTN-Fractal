package io.github.teamfractal.util;

import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Tim on 20/03/2017.
 */
public class ResourceGroupInteger extends ResourceGroup<Integer> implements Cloneable {

    public ResourceGroupInteger() {
        super(Integer.class);
        this.food = 0;
        this.energy = 0;
        this.ore = 0;
    }

    public ResourceGroupInteger(int food, int energy, int ore) {
        super(Integer.class);
        this.food = food;
        this.energy = energy;
        this.ore = ore;
    }

    public static ResourceGroupInteger add(ResourceGroupInteger r1, ResourceGroupInteger r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupInteger(r1.getFood() + r2.getFood(), r1.getEnergy() + r2.getEnergy(), r1.getOre() + r2.getOre());
    }

    public static ResourceGroupInteger sub(ResourceGroupInteger r1, ResourceGroupInteger r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupInteger(r1.getFood() - r2.getFood(), r1.getEnergy() - r2.getEnergy(), r1.getOre() - r2.getOre());
    }

    public static ResourceGroupInteger mult(ResourceGroupInteger r1, ResourceGroupInteger r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupInteger(r1.getFood() * r2.getFood(), r1.getEnergy() * r2.getEnergy(), r1.getOre() * r2.getOre());
    }

    public static ResourceGroupInteger div(ResourceGroupInteger r1, ResourceGroupInteger r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupInteger(r1.getFood() / r2.getFood(), r1.getEnergy() / r2.getEnergy(), r1.getOre() / r2.getOre());
    }

    public static ResourceGroupInteger sub(ResourceGroupInteger r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupInteger(r.getFood() - c, r.getEnergy() - c, r.getOre() - c);
    }

    public static ResourceGroupInteger mult(ResourceGroupInteger r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupInteger(r.getFood() * c, r.getEnergy() * c, r.getOre() * c);
    }

    public static ResourceGroupInteger mult(int c, ResourceGroupInteger r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupInteger(r.getFood() * c, r.getEnergy() * c, r.getOre() * c);
    }

    public static ResourceGroupInteger mult(ResourceGroupInteger r, float c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupInteger((int) (r.getFood() * c), (int) (r.getEnergy() * c), (int) (r.getOre() * c));
    }

    public static ResourceGroupInteger mult(float c, ResourceGroupInteger r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupInteger((int) (r.getFood() * c), (int) (r.getEnergy() * c), (int) (r.getOre() * c));
    }

    public static ResourceGroupInteger div(ResourceGroupInteger r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupInteger(r.getFood() / c, r.getEnergy() / c, r.getOre() / c);
    }

    /**
     * Returns the resource type with the highest integer amount
     * @return The resource type of the max value
     */
    public ResourceType getMaxResource() {
        if (food >= energy && food >= ore) {
            return ResourceType.FOOD;
        } else if (energy >= food && energy >= ore) {
            return ResourceType.ENERGY;
        } else {
            return ResourceType.ORE;
        }
    }

    @Override
    public int hashCode() {
        return new Integer(ore + energy + food).hashCode();
    }

    public Integer sum() {
        return food + energy + ore;
    }

    public Integer getFood() {
        return food;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Integer getOre() {
        return ore;
    }

    public ResourceGroupInteger clone() {
        return new ResourceGroupInteger(this.food, this.energy, this.ore);
    }

}

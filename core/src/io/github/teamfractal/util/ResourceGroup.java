package io.github.teamfractal.util;

import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Tim on 20/03/2017.
 */
public class ResourceGroup implements Cloneable {

    public float food;
    public float energy;
    public float ore;

    public ResourceGroup() {
        this.food = 0;
        this.energy = 0;
        this.ore = 0;
    }

    public ResourceGroup(float food, float energy, float ore) {
        this.food = food;
        this.energy = energy;
        this.ore = ore;
    }

    public static ResourceGroup add(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.getFood() + r2.getFood(), r1.getEnergy() + r2.getEnergy(), r1.getOre() + r2.getOre());
    }

    public static ResourceGroup sub(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.getFood() - r2.getFood(), r1.getEnergy() - r2.getEnergy(), r1.getOre() - r2.getOre());
    }

    public static ResourceGroup mult(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.getFood() * r2.getFood(), r1.getEnergy() * r2.getEnergy(), r1.getOre() * r2.getOre());
    }

    public static ResourceGroup div(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.getFood() / r2.getFood(), r1.getEnergy() / r2.getEnergy(), r1.getOre() / r2.getOre());
    }

    public static ResourceGroup sub(ResourceGroup r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.getFood() - c, r.getEnergy() - c, r.getOre() - c);
    }

    public static ResourceGroup mult(ResourceGroup r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.getFood() * c, r.getEnergy() * c, r.getOre() * c);
    }

    public static ResourceGroup mult(int c, ResourceGroup r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.getFood() * c, r.getEnergy() * c, r.getOre() * c);
    }

    public static ResourceGroup mult(ResourceGroup r, float c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup((int) (r.getFood() * c), (int) (r.getEnergy() * c), (int) (r.getOre() * c));
    }

    public static ResourceGroup mult(float c, ResourceGroup r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup((int) (r.getFood() * c), (int) (r.getEnergy() * c), (int) (r.getOre() * c));
    }

    public static ResourceGroup div(ResourceGroup r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.getFood() / c, r.getEnergy() / c, r.getOre() / c);
    }

    public String toString() {
        return "ResourceGroup(" + food + ", " + energy + ", " + ore + ")";
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ResourceGroup)) {
            return false;
        }
        ResourceGroup resourcesToCompare = (ResourceGroup) obj;
        return food == resourcesToCompare.food && energy == resourcesToCompare.energy && ore == resourcesToCompare.ore;
    }

    @Override
    public int hashCode() {
        return new Float(ore + energy + food).hashCode();
    }

    public float getResource(ResourceType resource) {
        switch (resource) {
            case ENERGY:
                return energy;
            case FOOD:
                return food;
            case ORE:
                return ore;
            default:
                throw new IllegalArgumentException("Illegal resource type");
        }
    }

    public void setResource(ResourceType resource, float value) {
        switch (resource) {
            case ENERGY:
                energy = value;
                break;
            case FOOD:
                food = value;
                break;
            case ORE:
                ore = value;
                break;
            default:
                throw new IllegalArgumentException("Illegal resource type");
        }
    }

    public float sum() {
        return food + energy + ore;
    }

    public float getFood() {
        return food;
    }

    public float getEnergy() {
        return energy;
    }

    public float getOre() {
        return ore;
    }

    public ResourceGroup clone() {
        return new ResourceGroup(this.food, this.energy, this.ore);
    }

}

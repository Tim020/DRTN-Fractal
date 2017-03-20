package io.github.teamfractal.util;

import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Tim on 20/03/2017.
 */
public class ResourceGroup {

    public int food;
    public int energy;
    public int ore;

    public ResourceGroup() {
        this.food = 0;
        this.energy = 0;
        this.ore = 0;
    }

    public ResourceGroup(int food, int energy, int ore) {
        this.food = food;
        this.energy = energy;
        this.ore = ore;
    }

    public static ResourceGroup add(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.GetFood() + r2.GetFood(), r1.GetEnergy() + r2.GetEnergy(), r1.GetOre() + r2.GetOre());
    }

    public static ResourceGroup sub(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.GetFood() - r2.GetFood(), r1.GetEnergy() - r2.GetEnergy(), r1.GetOre() - r2.GetOre());
    }

    public static ResourceGroup mult(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.GetFood() * r2.GetFood(), r1.GetEnergy() * r2.GetEnergy(), r1.GetOre() * r2.GetOre());
    }

    public static ResourceGroup div(ResourceGroup r1, ResourceGroup r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroup(r1.GetFood() / r2.GetFood(), r1.GetEnergy() / r2.GetEnergy(), r1.GetOre() / r2.GetOre());
    }

    public static ResourceGroup sub(ResourceGroup r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.GetFood() - c, r.GetEnergy() - c, r.GetOre() - c);
    }

    public static ResourceGroup mult(ResourceGroup r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.GetFood() * c, r.GetEnergy() * c, r.GetOre() * c);
    }

    public static ResourceGroup mult(int c, ResourceGroup r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.GetFood() * c, r.GetEnergy() * c, r.GetOre() * c);
    }

    public static ResourceGroup mult(ResourceGroup r, float c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup((int) (r.GetFood() * c), (int) (r.GetEnergy() * c), (int) (r.GetOre() * c));
    }

    public static ResourceGroup mult(float c, ResourceGroup r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup((int) (r.GetFood() * c), (int) (r.GetEnergy() * c), (int) (r.GetOre() * c));
    }

    public static ResourceGroup div(ResourceGroup r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroup argument cannot be null");
        }
        return new ResourceGroup(r.GetFood() / c, r.GetEnergy() / c, r.GetOre() / c);
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

    public int hashCode() {
        return food ^ energy << 2 ^ ore >> 2;
    }

    public int GetResource(ResourceType resource) {
        switch (resource) {
            case ENERGY:
                return energy;
            case FOOD:
                return food;
            case ORE:
                return ore;
            default:
                throw new IllegalArgumentException("Illeagal resource type");
        }
    }

    public void SetResource(ResourceType resource, int value) {
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
                throw new IllegalArgumentException("Illeagal resource type");
        }
    }

    public int Sum() {
        return food + energy + ore;
    }

    public int GetFood() {
        return food;
    }

    public int GetEnergy() {
        return energy;
    }

    public int GetOre() {
        return ore;
    }

    public ResourceGroup Clone() {
        return new ResourceGroup(this.food, this.energy, this.ore);
    }

}

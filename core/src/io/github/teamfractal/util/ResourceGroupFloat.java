package io.github.teamfractal.util;

/**
 * Created by Tim on 20/03/2017.
 */
public class ResourceGroupFloat extends ResourceGroup<Float> implements Cloneable {

    public ResourceGroupFloat() {
        super(Float.class);
        this.food = 0f;
        this.energy = 0f;
        this.ore = 0f;
    }

    public ResourceGroupFloat(float food, float energy, float ore) {
        super(Float.class);
        this.food = food;
        this.energy = energy;
        this.ore = ore;
    }

    public static ResourceGroupFloat add(ResourceGroupFloat r1, ResourceGroupFloat r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupFloat(r1.getFood() + r2.getFood(), r1.getEnergy() + r2.getEnergy(), r1.getOre() + r2.getOre());
    }

    public static ResourceGroupFloat sub(ResourceGroupFloat r1, ResourceGroupFloat r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupFloat(r1.getFood() - r2.getFood(), r1.getEnergy() - r2.getEnergy(), r1.getOre() - r2.getOre());
    }

    public static ResourceGroupFloat mult(ResourceGroupFloat r1, ResourceGroupFloat r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupFloat(r1.getFood() * r2.getFood(), r1.getEnergy() * r2.getEnergy(), r1.getOre() * r2.getOre());
    }

    public static ResourceGroupFloat div(ResourceGroupFloat r1, ResourceGroupFloat r2) {
        if (r1 == null || r2 == null) {
            throw new NullPointerException("Arguments cannot be null");
        }
        return new ResourceGroupFloat(r1.getFood() / r2.getFood(), r1.getEnergy() / r2.getEnergy(), r1.getOre() / r2.getOre());
    }

    public static ResourceGroupFloat sub(ResourceGroupFloat r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupFloat(r.getFood() - c, r.getEnergy() - c, r.getOre() - c);
    }

    public static ResourceGroupFloat mult(ResourceGroupFloat r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupFloat(r.getFood() * c, r.getEnergy() * c, r.getOre() * c);
    }

    public static ResourceGroupFloat mult(int c, ResourceGroupFloat r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupFloat(r.getFood() * c, r.getEnergy() * c, r.getOre() * c);
    }

    public static ResourceGroupFloat mult(ResourceGroupFloat r, float c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupFloat((r.getFood() * c), (r.getEnergy() * c), (r.getOre() * c));
    }

    public static ResourceGroupFloat mult(float c, ResourceGroupFloat r) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupFloat((r.getFood() * c), (r.getEnergy() * c), (r.getOre() * c));
    }

    public static ResourceGroupFloat div(ResourceGroupFloat r, int c) {
        if (r == null) {
            throw new NullPointerException("ResourceGroupInteger argument cannot be null");
        }
        return new ResourceGroupFloat(r.getFood() / c, r.getEnergy() / c, r.getOre() / c);
    }

    @Override
    public int hashCode() {
        return new Float(ore + energy + food).hashCode();
    }

    public Float sum() {
        return food + energy + ore;
    }

    public Float getFood() {
        return food;
    }

    public Float getEnergy() {
        return energy;
    }

    public Float getOre() {
        return ore;
    }

    public ResourceGroupFloat clone() {
        return new ResourceGroupFloat(this.food, this.energy, this.ore);
    }

}

package io.github.teamfractal.util;

import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Tim on 21/03/2017.
 */
public abstract class ResourceGroup<V extends Number> {

    protected ResourceGroup(Class<V> typeClass) {
        this.type = typeClass;
    }

    protected V food, ore, energy;

    public abstract V sum();

    public V getResource(ResourceType resource) {
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

    public void setResource(ResourceType resource, V value) {
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

    public V getFood() {
        return food;
    }

    public V getEnergy() {
        return energy;
    }

    public V getOre() {
        return ore;
    }

    private final Class<V> type;

    public Class<V> getTypeOfResourceGroup() {
        return this.type;
    }

    public String toString() {
        return "ResourceGroup<" + getTypeOfResourceGroup().getSimpleName() + ">(" + food + ", " + energy + ", " + ore + ")";
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ResourceGroup)) {
            return false;
        }
        ResourceGroup resourcesToCompare = (ResourceGroup) obj;
        return food == resourcesToCompare.food && energy == resourcesToCompare.energy && ore == resourcesToCompare.ore;
    }

}

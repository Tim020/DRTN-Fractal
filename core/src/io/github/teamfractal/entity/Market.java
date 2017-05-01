/**
 * @author DRTN
 * Team Website with download:
 * https://misterseph.github.io/DuckRelatedFractalProject/
 * <p>
 * This Class contains either modifications or is entirely new in Assessment 3
 * <p>
 * If you are in any doubt a complete changelog can be found here:
 * https://github.com/NotKieran/DRTN-Fractal/compare/Fractal_Initial...development
 * <p>
 * And a more concise report can be found in our Change3 document.
 **/

package io.github.teamfractal.entity;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.exception.InvalidResourceTypeException;
import io.github.teamfractal.exception.NotCommonResourceException;
import io.github.teamfractal.util.ResourceGroupInteger;
import io.github.teamfractal.util.Tuple;

import java.util.HashMap;
import java.util.Random;

public class Market {

    private int roboticons;
    private int cacheID = -1;

    private ResourceGroupInteger resources;
    private ResourceGroupInteger resourceSellingPrices;
    private ResourceGroupInteger resourceBuyingPrices;
    private ResourceGroupInteger resourceProductionTotals = new ResourceGroupInteger();
    private HashMap<Integer, Tuple<ResourceGroupInteger>> resourcePriceHistory = new HashMap<Integer, Tuple<ResourceGroupInteger>>();
    private ResourceGroupInteger runningTotal = new ResourceGroupInteger();

    private final int STARTING_FOOD_SELL_PRICE = 10;
    private final int STARTING_ENERGY_SELL_PRICE = 10;
    private final int STARTING_ORE_SELL_PRICE = 10;

    private final int STARTING_FOOD_BUY_PRICE = 10;
    private final int STARTING_ENERGY_BUY_PRICE = 10;
    private final int STARTING_ORE_BUY_PRICE = 10;

    /**
     * Initialise the market
     */
    public Market() {
        resources = new ResourceGroupInteger(16, 16, 0);
        resourceSellingPrices = new ResourceGroupInteger(STARTING_FOOD_SELL_PRICE, STARTING_ENERGY_SELL_PRICE, STARTING_ORE_SELL_PRICE);
        resourceBuyingPrices = new ResourceGroupInteger(STARTING_FOOD_BUY_PRICE, STARTING_ENERGY_BUY_PRICE, STARTING_ORE_BUY_PRICE);
        cachePrices();
        setRoboticons(12);
    }

    public void cachePrices() {
        resourcePriceHistory.put(cacheID, new Tuple<ResourceGroupInteger>(resourceBuyingPrices.clone(), resourceSellingPrices.clone()));
        cacheID++;
    }

    public void updateResourceSellPrices() {
        float elasticity = 0.7f;
        float upgradeTotalSum = (float) resourceProductionTotals.sum();
        float foodTotal = (float) resourceProductionTotals.getFood();
        float energyTotal = (float) resourceProductionTotals.getEnergy();
        float oreTotal = (float) resourceProductionTotals.getOre();

        if (upgradeTotalSum > 0) {
            float newFood = (((1 - (foodTotal / upgradeTotalSum)) / elasticity) * STARTING_FOOD_SELL_PRICE) + STARTING_FOOD_SELL_PRICE;
            float newEnergy = (((1 - (energyTotal / upgradeTotalSum) / elasticity)) * STARTING_ENERGY_SELL_PRICE) + STARTING_ENERGY_SELL_PRICE;
            float newOre = (((1 - (oreTotal / upgradeTotalSum) / elasticity)) * STARTING_ORE_SELL_PRICE) + STARTING_ORE_SELL_PRICE;
            ResourceGroupInteger newPrices = new ResourceGroupInteger((int) newFood, (int) newEnergy, (int) newOre);
            resourceSellingPrices = newPrices;
        }
    }

    public void updateResourceBuyPrices() {
        Random random = new Random();
        ResourceGroupInteger.sub(resourceSellingPrices.clone(), ResourceGroupInteger.mult(resourceSellingPrices, ((float) 1 / (2 + random.nextInt(4)))));
    }

    public void updateMarketSupplyOnBuy(ResourceGroupInteger resourcesToBuy) {
        runningTotal = ResourceGroupInteger.sub(runningTotal, resourcesToBuy);
    }

    public void updateMarketSupplyOnSell(ResourceGroupInteger resourcesToSell) {
        runningTotal = ResourceGroupInteger.add(runningTotal, resourcesToSell);
    }

    public void updateMarketSupply(ResourceGroupInteger r) {
        runningTotal = ResourceGroupInteger.add(runningTotal, r);
    }

    public void calculatePlayerResourceUpgrades() {
        resourceProductionTotals = new ResourceGroupInteger();
        for (Player player : RoboticonQuest.getInstance().getPlayerList()) {
            for (Roboticon r : player.getRoboticons()) {
                ResourceGroupInteger t = new ResourceGroupInteger();
                t.setResource(r.getCustomisation(), 1);
                resourceProductionTotals = ResourceGroupInteger.add(resourceProductionTotals, t);
            }
            for (LandPlot p : player.getLandList()) {
                resourceProductionTotals = ResourceGroupInteger.add(resourceProductionTotals, p.produceResources());
            }
        }
    }

    public ResourceGroupInteger getResourceBuyingPrices() {
        return resourceBuyingPrices;
    }

    public ResourceGroupInteger getResourceSellingPrices() {
        return resourceSellingPrices;
    }

    public HashMap<Integer, Tuple<ResourceGroupInteger>> getHistoricTradingData() {
        return resourcePriceHistory;
    }
    
    /**
     * Get the amount of food in the market
     *
     * @return The amount of food in the market.
     */
    int getFood() {
        return resources.getFood();
    }

    /**
     * Set the amount of food in the market
     *
     * @param amount The amount of new food amount.
     * @throws IllegalArgumentException If the new amount if negative, this exception will be thrown.
     */
    synchronized void setFood(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Error: Food can't be negative.");
        }
        this.resources.setResource(ResourceType.FOOD, amount);
    }

    /**
     * Get the amount of energy in the market
     *
     * @return The amount of energy in the market.
     */
    int getEnergy() {
        return resources.getEnergy();
    }

    /**
     * Set the amount of energy in the market
     *
     * @param amount The amount of new energy count.
     * @throws IllegalArgumentException If the new amount if negative, this exception will be thrown.
     */
    synchronized void setEnergy(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Error: Energy can't be negative.");
        }
        this.resources.setResource(ResourceType.ENERGY, amount);
    }

    /**
     * Get the amount of ore in the market
     *
     * @return The amount of ore in the market.
     */
    int getOre() {
        return resources.getOre();
    }

    /**
     * Set the amount of ore in the market
     *
     * @param amount The amount of new ore count.
     * @throws IllegalArgumentException If the new amount if negative, this exception will be thrown.
     */
    synchronized void setOre(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Error: Ore can't be negative.");
        }
        this.resources.setResource(ResourceType.ORE, amount);
    }

    /**
     * Get the amount of roboticons in the market
     *
     * @return The amount of roboticons in the market.
     */
    int getRoboticons() {
        return roboticons;
    }

    /**
     * Set the amount of roboticons in the market
     *
     * @param amount The amount of new roboticons count.
     * @throws IllegalArgumentException If the new amount if negative, this exception will be thrown.
     */
    void setRoboticons(int amount) throws IllegalArgumentException {
        if (amount < 0) {
            throw new IllegalArgumentException("Error: Roboticon can't be negative.");
        }
        roboticons = amount;
    }

    /**
     * Get the total amount of all available resources added together.
     *
     * @return The total amount.
     */
    private synchronized int getTotalResourceCount() {
        return getFood() + getEnergy() + getOre() + roboticons;
    }

    /**
     * Get the amount of specific resource.
     *
     * @param type The {@link ResourceType}.
     * @return The amount.
     */
    public int getResource(ResourceType type) {
        switch (type) {
            case ORE:
                return getOre();
            case ENERGY:
                return getEnergy();
            case ROBOTICON:
                return getRoboticons();
            case FOOD:
                return getFood();
            case CUSTOMISATION:
                return 1000;
            default:
                throw new NotCommonResourceException(type);
        }
    }


    /**
     * Set the amount of specific resource.
     *
     * @param type   The {@link ResourceType}.
     * @param amount The new resource amount.
     * @throws IllegalArgumentException     Will be thrown if the new amount is negative.
     * @throws InvalidResourceTypeException Will be thrown if the resource specified is invalid.
     */
    void setResource(ResourceType type, int amount) throws IllegalArgumentException, InvalidResourceTypeException {
        switch (type) {
            case ORE:
                setOre(amount);
                break;
            case ENERGY:
                setEnergy(amount);
                break;
            case ROBOTICON:
                setRoboticons(amount);
                break;
            case FOOD:
                setFood(amount);
                break;
            case CUSTOMISATION:
                break;
            default:
                throw new NotCommonResourceException(type);
        }
    }

    /**
     * Method to ensure the market have enough resources for user to purchase.
     *
     * @param type   The {@link ResourceType}.
     * @param amount the amount of resource to check.
     * @return If there are enough resources.
     * @throws InvalidResourceTypeException Will be thrown if the resource specified is invalid.
     */
    boolean hasEnoughResources(ResourceType type, int amount) throws InvalidResourceTypeException {
        int resource = getResource(type);
        return amount <= resource;
    }

    /**
     * Get the single price for a resource type.
     *
     * @param resource The {@link ResourceType}.
     * @return The buy in price.
     */
    public int getBuyPrice(ResourceType resource) {
        switch (resource) {
            case ORE:
                return resourceBuyingPrices.getOre();
            case ENERGY:
                return resourceBuyingPrices.getEnergy();
            case FOOD:
                return resourceBuyingPrices.getFood();
            case ROBOTICON:
                return 9;
            default:
                throw new IllegalArgumentException("Error: Resource type is incorrect.");
        }
    }

    /**
     * Get the single price for a resource type.
     *
     * @param resource The {@link ResourceType}.
     * @return The sell price.
     */

    public int getSellPrice(ResourceType resource) {
        switch (resource) {
            case ORE:
                return resourceSellingPrices.getOre();
            case ENERGY:
                return resourceSellingPrices.getEnergy();
            case FOOD:
                return resourceSellingPrices.getFood();
            case ROBOTICON:
                return 10;
            case CUSTOMISATION:
                return 10;
            default:
                throw new IllegalArgumentException("Error: Resource type is incorrect.");
        }
    }

    /**
     * Sell Resource to the market, caller <i>must</i> be doing all the checks.
     * <p>
     * This method will only increase the amount of specified resource.
     *
     * @param resource The {@link ResourceType}
     * @param amount   The amount of resource to buy in.
     */
    public synchronized void sellResourceToMarket(ResourceType resource, int amount) {
        if (resource == ResourceType.ORE || resource == ResourceType.ENERGY || resource == ResourceType.FOOD) {
            ResourceGroupInteger t = new ResourceGroupInteger();
            t.setResource(resource, amount);
            updateMarketSupplyOnSell(t);
        }
        setResource(resource, getResource(resource) + amount);
    }

    /**
     * Buy Resource from the market, caller <i>must</i> be doing all the checks.
     * <p>
     * This method will only decrease the amount of specified resource.
     *
     * @param resource The {@link ResourceType}
     * @param amount   The amount of resource to sell out.
     */
    public synchronized void buyResourceFromMarket(ResourceType resource, int amount) {
        if (resource == ResourceType.ORE || resource == ResourceType.ENERGY || resource == ResourceType.FOOD) {
            ResourceGroupInteger t = new ResourceGroupInteger();
            t.setResource(resource, amount);
            updateMarketSupplyOnBuy(t);
        }
        setResource(resource, getResource(resource) - amount);
    }

    /**
     * Generates a random amount of roboticons within a given range if the market contains ore.
     */
    public void generateRoboticon() {
        Random rand = new Random();
        int roboticonsToGenerate = rand.nextInt(3) + 0;
        while (this.getOre() >= 2 && roboticonsToGenerate > 0) {
            this.setResource(ResourceType.ORE, getOre() - 2);
            this.roboticons += 1;
            roboticonsToGenerate -= 1;
        }
    }
}









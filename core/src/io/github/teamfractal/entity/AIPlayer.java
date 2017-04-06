/**
 * @author DRTN
 * Team Website with download:
 * https://misterseph.github.io/DuckRelatedFractalProject/
 *
 * This Class contains either modifications or is entirely new in Assessment 3
 *
 * If you are in any doubt a complete changelog can be found here:
 * https://github.com/NotKieran/DRTN-Fractal/compare/Fractal_Initial...development
 *
 * And a more concise report can be found in our Change3 document.
 **/

package io.github.teamfractal.entity;

import com.sun.org.apache.regexp.internal.RE;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.GamePhase;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.util.ResourceGroup;
import io.github.teamfractal.util.ResourceGroupInteger;
import io.github.teamfractal.util.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * @author Duck Related Team Name
 * @version Assessment 3
 * @since Assessment 3
 */
public class AIPlayer extends Player {

    private ArrayList<Tuple<ResourceGroupInteger>> priceChanges;

    public AIPlayer(RoboticonQuest game) {
        super(game);
        /*
        Money set to a value higher than ever necessary to allow the AI to make visible progress every turn.
        Also accounts for the AI's bold market moves and allows it to keep the market changing.
         */
        this.setMoney(20000);
        priceChanges = new ArrayList<Tuple<ResourceGroupInteger>>();
    }

    /**
     * Function calling the AIPlayer to take action.
     * UPDATED: Use Enum
     */
    public void takeTurn(GamePhase phase) {
        switch (phase) {
            case TILE_ACQUISITION:
                //"Buy Land Plot
                System.out.println("AI: Phase 1 in progress");
                tileAcquisition();
                break;
            case ROBOTICON_PURCHASE:
                //"Purchase Roboticons
                System.out.println("AI: Phase 2 in progress");
                roboticonPurchase();
                break;
            case ROBOTICON_CUSTOMISATION:
                //Install Roboticons
                System.out.println("AI: Phase 3 in progress");
                roboticonCustomisation();
                break;
            case CHANCELLOR:
                chancellorPhaseRandomChance();
                game.nextPhase();
                break;
            case MARKET:
                //Resource Auction
                System.out.println("AI: Phase 5 in progress");
                tradeWithMarket();
                break;
            default:
                // Unknown phase
        }
    }

    /**
     * Function simulating the Player interaction during Phase 1.
     * <p>
     * Plots cost 10 money.
     * Only one plot can be purchased.
     * </p>
     * UPDATE: REFACTORED "phase1"
     */
    //buing from the market use market sell
    private void tileAcquisition() {

        ArrayList<LandPlot> plots = getAvailableLandPlots();
        if (plots.size() != 0 && getMoney() > 10) {

            ResourceType focus = game.market.getResourceBuyingPrices().getMaxResource();
            LandPlot best = plots.get(0);

            for (int i = 0; i < plots.size(); i++) {
                if (best.getResource(focus) < plots.get(i).getResource(focus)) {
                    best = plots.get(i);
                }
            }

            game.gameScreen.setSelectedPlot(best);
            game.gameScreen.getActors().tileClicked(best, (float) best.x, (float) best.y);
            game.gameScreen.getActors().buyLandPlotFunction();
        }
        game.nextPhase();
    }

    /**
     * NEW
     * Gets all available land plots i.e. those with no owner
     * @return available land plots
     */
    private ArrayList<LandPlot> getAvailableLandPlots() {
        ArrayList<LandPlot> available = new ArrayList<LandPlot>();

        for (int i = 0; i < game.plotManager.x; i++) {
            for (int j = 0; j < game.plotManager.y; j++) {
                if (game.plotManager.getPlot(i, j).getOwner()==null) {
                    available.add(game.plotManager.getPlot(i, j));
                }
            }
        }
        return available;
    }

    /**
     * Function simulating the Player interaction during Phase 2.
     * UPDATED: REFACTORED "phase2"
     */
    private void roboticonPurchase() {

        for (LandPlot plot : this.landList) {
            ResourceType focus = plot.getAllResources().getMaxResource();

            if (!plot.hasRoboticon()) {

                //enough money to buy a roboticon and needing to buy one
                if (this.getMoney() > 10 && game.roboticonMarket.actors().roboticons.size() == 0) {
                    game.roboticonMarket.actors().purchaseRoboticonFunction();

                    //enough money to customise
                    if (this.getMoney() > game.market.getSellPrice(focus)) {
                        game.roboticonMarket.actors().purchaseCustomisationFunction(focus, 0);
                    }
                //got a roboticon and enough to customise
                } else if (game.roboticonMarket.actors().roboticons.size() > 0 &&
                        this.getMoney() > game.market.getSellPrice(focus)) {
                    game.roboticonMarket.actors().purchaseCustomisationFunction(focus, 0);
                }
            }
        }
        game.nextPhase();
    }

    /**
     * Function simulating the Player interaction during Phase 3.
     * UPDATE: REFACTORED "phase3"
     */
    private void roboticonCustomisation() {
        ArrayList<LandPlot> plots = getUnmannedPlots();
        ArrayList<Roboticon> roboticons = getUnplacedRoboticons();

        if (roboticons.size() == 0 || plots.size() == 0) {
            game.nextPhase();
        }

        LandPlot best = plots.get(0);
        for (Roboticon roboticon : roboticons) {
            ResourceType focus = roboticon.getCustomisation();

            for (LandPlot plot : plots) {
                if (plot.getResource(focus) > best.getResource(focus)) {
                    best = plot;
                }
            }
            game.gameScreen.getActors().installRoboticonFunction(best, roboticon);
            plots.remove(best);

            if (plots.size() != 0) {
                best = plots.get(0);
            } else {
                break;
            }
        }

        game.nextPhase();
    }

    /**
     * NEW
     * Gets all the unplaced roboticons
     * @return an array list of unplaced roboticons
     */
    private ArrayList<Roboticon> getUnplacedRoboticons() {
        ArrayList<Roboticon> unplacedRoboticons = new ArrayList<Roboticon>();
        for (Roboticon roboticon : this.roboticonList) {
            if (!roboticon.isInstalled()) {
                unplacedRoboticons.add(roboticon);
            }
        }
        return unplacedRoboticons;
    }

    /**
     * NEW
     * Gets all the unmanned plots
     * @return an array list of unmanned tiles
     */
    private ArrayList<LandPlot> getUnmannedPlots() {
        ArrayList<LandPlot> unmannedPlots = new ArrayList<LandPlot>();

        for (LandPlot plot : this.landList) {
            if (!plot.hasRoboticon()) {
                unmannedPlots.add(plot);
            }
        }

        return unmannedPlots;
    }
    

    /**
     * Function simulating the Player interaction during Phase 5.
     * UPDATE: REFACTOR "phase5"
     */
    private void tradeWithMarket() {
        ArrayList<Tuple<ResourceGroupInteger>> marketHistory = getMarketHistory();

        if (marketHistory.size() > 2) {

            //update priceChanges
            for (int i = priceChanges.size() + 1; i < marketHistory.size() - 1; i++) {
                Tuple<ResourceGroupInteger> entry;
                entry = new Tuple<ResourceGroupInteger>(ResourceGroupInteger.sub(marketHistory.get(i).getHead(),
                        marketHistory.get(i - 1).getHead()), ResourceGroupInteger.sub(marketHistory.get(i - 1).getTail(),
                        marketHistory.get(i).getTail()));
                priceChanges.add(entry);
            }

            Random rand = new Random();

            boolean sold = false;
            for (ResourceType focus : new ResourceType[] {ResourceType.ENERGY, ResourceType.FOOD, ResourceType.ORE}) {

                ResourceGroupInteger[] sellingChanges = getSellingPriceChanges();
                int sellingStreak = getStreak(sellingChanges, focus);

                if (sellingStreak == 0) continue;

                float prob = getProbStreakEnd(sellingStreak, focus, sellingChanges);
                if (prob >= 0.75) {
                    sellResources(focus, this.getResource(focus) / 3);
                    sold = true;
                } else if (rand.nextFloat() >= prob) {
                    sellResources(focus, this.getResource(focus) / 3);
                    sold = true;
                }
            }

            //buy
            for (ResourceType focus : new ResourceType[] {ResourceType.ENERGY, ResourceType.FOOD, ResourceType.ORE}) {

                ResourceGroupInteger[] buyingChanges = getBuyingPriceChanges();
                int buyingStreak = getStreak(buyingChanges, focus);

                if (buyingStreak == 0) continue;

                float prob = getProbStreakEnd(buyingStreak, focus, buyingChanges);
                if (prob >= 0.75) {
                    buyResources(focus);
                } else if (rand.nextFloat() >= prob) {
                    buyResources(focus);
                }
            }

            if (!sold) {
                if (this.getMoney() > 100 && rand.nextFloat() > 0.5f) {
                    game.resourceMarket.actors().setGambleField("100");
                    game.resourceMarket.gamble();
                    System.out.println("Gambling");
                }
            }
        }
        game.nextPhase();
    }

    /**
     * NEW
     * Gets the current positive streak of price changes.
     * @param priceChanges the changes in price
     * @param focus the resource type of interest
     * @return the streak
     */
    private int getStreak(ResourceGroupInteger[] priceChanges, ResourceType focus) {
        int count = 0;
        for (int i = priceChanges.length - 1; i > -1; i--) {
            if (priceChanges[i].getResource(focus) >= 0) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }

    /**
     * NEW
     * Gets the change in selling prices (players perspective, meaning market buying price).
     * @return an array of selling price changes
     */
    private ResourceGroupInteger[] getSellingPriceChanges() {
        //market buying, player selling history...
        ResourceGroupInteger[] changes = new ResourceGroupInteger[priceChanges.size()];
        for (int i = 0; i < priceChanges.size(); i++) {
            changes[i] = priceChanges.get(i).getHead();
        }
        return changes;
    }

    /**
     * NEW
     * Gets the change in buying prices (players perspective, meaning market selling price).
     * @return an array of buying prices
     */
    private ResourceGroupInteger[] getBuyingPriceChanges() {
        //market buying, player selling history...
        ResourceGroupInteger[] changes = new ResourceGroupInteger[priceChanges.size()];
        for (int i = 0; i < priceChanges.size(); i++) {
            changes[i] = priceChanges.get(i).getTail();
        }
        return changes;
    }

    /**
     * NEW
     * Gets the ordered market price history
     * @return the market history
     */
    private ArrayList<Tuple<ResourceGroupInteger>> getMarketHistory() {
        HashMap<Integer, Tuple<ResourceGroupInteger>> history = game.market.getHistoricTradingData();
        ArrayList<Tuple<ResourceGroupInteger>> marketPrices = new ArrayList<Tuple<ResourceGroupInteger>>();

        for (int i = 0; i < history.size(); i++) {
            marketPrices.add(history.get(i));
        }

        return marketPrices;
    }

    /**
     * NEW
     * Gives the probability of a streak ending.
     * NOTE: This is not the actual probability, but an attempt to mimic a players perception of it
     * @param streak an int representing the current positive streak
     * @param focus the resource type of interest
     * @param history the selling/buying history
     * @return probability of ending the current positive streak
     */
    private float getProbStreakEnd(int streak, ResourceType focus, ResourceGroupInteger[] history) {
        ResourceGroupInteger[] current = new ResourceGroupInteger[streak];
        float total = 0;
        float count = 0;

        for (int i = 0; i < history.length - streak; i++) {
            System.arraycopy(history, i, current, 0, streak);
            if (isStreak(current, focus)) {
                if (i + 1 < history.length && history[i+1].getResource(focus) < 0) count++;
                total++;
            }
        }

        if (count == 0f && total == 0f) {
            return 0.5f;
        } else {
            return count/total;
        }
    }

    /**
     * NEW
     * Returns whether the provided history is a streak for a specific type
     * @param history selling/buying history
     * @param focus resource type of interest
     * @return true : history is of a positive streak, false : otherwise
     */
    private boolean isStreak(ResourceGroupInteger[] history, ResourceType focus) {
        for (ResourceGroupInteger entry : history) {
            if (entry.getResource(focus) < 0) {
                return false;
            }
        }
        return true;
    }

    /***
     * Utility function for AI to sell resources to market
     * @param type The resource to sell
     * @param amount The amount of resources to be sold
     */
    private void sellResources(ResourceType type, int amount) {
        this.sellResourceToMarket(amount, game.market, type);
        System.out.println("Selling: " + amount + " " + type);
    }

    /**
     * Utility function for AI to buy resources from market
     *
     * @param type The resource to buy
     */
    private void buyResources(ResourceType type) {
        this.purchaseResourceFromMarket(5, game.market, type);
        System.out.println("Buying: 5 " + type);
    }

    private void chancellorPhaseRandomChance() {
        //TODO: Give the player some reward by a random chance.
    }
}

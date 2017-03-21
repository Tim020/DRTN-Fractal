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

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.GamePhase;
import io.github.teamfractal.entity.enums.ResourceType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Duck Related Team Name
 * @version Assessment 3
 * @since Assessment 3
 */
public class AIPlayer extends Player {

    public AIPlayer(RoboticonQuest game) {
        super(game);
        /*
        Money set to a value higher than ever necessary to allow the AI to make visible progress every turn.
        Also accounts for the AI's bold market moves and allows it to keep the market changing.
         */
        this.setMoney(20000);
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
            //Phase 4 not included, no interaction required
            case MARKET:
                //Resource Auction
                System.out.println("AI: Phase 5 in progress");
                phase5();
                break;
            default:
                // Unknown phase
        }
    }

    /**
     * Utility function to return a random number between 0 and max.
     * @param max an integer value representing the largest number requested
     * @return integer random value between 0 and max.
     */
    private int random(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
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
     */
    private void phase5() {
        if (game.getTurnNumber() % 2 == 0) {
            if (this.getEnergy() > 1) {
                sellResources(ResourceType.ENERGY, this.getEnergy() / 2);
            }
            if (this.getFood() > 1) {
                sellResources(ResourceType.FOOD, this.getFood() / 2);
            }
            if (this.getOre() > 1) {
                sellResources(ResourceType.ORE, this.getOre() / 2);
            }
        } else {
            if (game.market.getEnergy() > 5) {
                buyResources(ResourceType.ENERGY);
            }
            if (game.market.getOre() > 5) {
                buyResources(ResourceType.ORE);
            }
            if (game.market.getFood() > 5) {
                buyResources(ResourceType.FOOD);
            }
        }


        game.nextPhase();
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

}

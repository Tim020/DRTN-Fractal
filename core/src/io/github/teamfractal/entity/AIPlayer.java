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
import io.github.teamfractal.util.ResourceGroupInteger;

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
                phase2();
                break;
            case ROBOTICON_CUSTOMISATION:
                //Install Roboticons
                System.out.println("AI: Phase 3 in progress");
                phase3();
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
        if (plots.size() != 0) {


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
        game.gameScreen.getActors().nextButtonFunction();
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
     */
    private void phase2() {
        boolean purchased = false;
        int robotIndex = 0;

        try {
            for (LandPlot aLandList : this.landList) {
                if (!aLandList.hasRoboticon()) {
                    float[] resources = {aLandList.getResource(ResourceType.ORE), aLandList.getResource(ResourceType.FOOD), aLandList.getResource(ResourceType.ENERGY)};
                    float max = 0;
                    int max_index = -1; //Initialise to index not used to not return false positives
                    for (int j = 0; j < resources.length; j++) {
                        if (resources[j] > max) {
                            max = resources[j];
                            max_index = j;
                        }
                    }
                    System.out.println(max_index);
                    switch (max_index) {

                        case 0:
                            //ORE
                            game.roboticonMarket.actors().purchaseRoboticonFunction();
                            try {
                                game.roboticonMarket.actors().purchaseCustomisationFunction(ResourceType.ORE, robotIndex);
                            } catch (IndexOutOfBoundsException e) {
                                break;
                            }
                            break;
                        case 1:
                            //FOOD
                            game.roboticonMarket.actors().purchaseRoboticonFunction();
                            try {
                                game.roboticonMarket.actors().purchaseCustomisationFunction(ResourceType.FOOD, robotIndex);
                            } catch (IndexOutOfBoundsException e) {
                                break;
                            }
                            break;
                        case 2:
                            //ENERGY
                            game.roboticonMarket.actors().purchaseRoboticonFunction();
                            try {
                                game.roboticonMarket.actors().purchaseCustomisationFunction(ResourceType.ENERGY, robotIndex);
                            } catch (IndexOutOfBoundsException e) {
                                break;
                            }
                            break;
                        default:

                    }

                }
            }
            game.nextPhase();

        } finally {
            game.nextPhase();
        }


    }

    /**
     * Function simulating the Player interaction during Phase 3.
     */
    private void phase3() {
        for (LandPlot aLandPlot : this.landList) {
            if (!aLandPlot.hasRoboticon()) {
                for (Roboticon aRoboticon: this.roboticonList
                     ) { if (!aRoboticon.isInstalled()){
                        game.gameScreen.getActors().installRoboticonFunction(aLandPlot,aRoboticon);
                }

                }
            }

            }
        game.gameScreen.getActors().nextButtonFunction();
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

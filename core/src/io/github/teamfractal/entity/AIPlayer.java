package io.github.teamfractal.entity;

import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.ResourceType;


import java.util.Random;

/**
 * @author Duck Related Team Name
 * @version Assessment 3
 * @since Assessment 3
 */
public class AIPlayer extends Player {

    public AIPlayer(RoboticonQuest game) {
        super(game);
        this.setMoney(20000);
    }

    /**
     * Function calling the AIPlayer to take action.
     *
     */
    public void takeTurn(int phase) {
        //TODO get gamephase being used
        int gamephase = game.getPhase();
        switch (phase) {
            case 1:
                //"Buy Land Plot
                System.out.println("AI: Phase 1 in progress");
                phase1();
                break;
            case 2:
                //"Purchase Roboticons
                System.out.println("AI: Phase 2 in progress");
                phase2();
                break;
            case 3:
                //Install Roboticons
                System.out.println("AI: Phase 3 in progress");
                phase3();
                break;
            //Phase 4 not included, no interaction required
            case 5:
                //Resource Auction
                System.out.println("AI: Phase 5 in progress");
                phase5();
                break;
            default:
                // Unknown phase
        }
    }

    private int random(int max) {
        Random rand = new Random();
        return rand.nextInt(max);
    }

    /**
     * Function simulating the Player interaction during Phase 1.
     * <p>
     * <p>
     * Plots cost 10 money.
     * Only one plot can be purchased.
     * </p>
     */
    private void phase1() {

        boolean selected = false;
        int x = game.plotManager.x;
        int y = game.plotManager.y;
        if (this.getMoney() >= 10) while (!selected) {
            int i = random(x);
            int j = random(y);
            if (!game.plotManager.getPlot(i, j).hasOwner()) {
                selected = true;
                game.gameScreen.setSelectedPlot(game.plotManager.getPlot(i, j));
                game.gameScreen.getActors().tileClicked(game.plotManager.getPlot(i, j), (float) i, (float) j);
                game.gameScreen.getActors().buyLandPlotFunction();
            }

            game.gameScreen.getActors().nextButtonFunction();
        }
    }

    /**
     * Function simulating the Player interaction during Phase 2.
     */
    private void phase2() {

        for (LandPlot aLandList : this.landList) {
            if (!aLandList.hasRoboticon()) {
                int[] resources = {aLandList.getResource(ResourceType.ORE), aLandList.getResource(ResourceType.FOOD), aLandList.getResource(ResourceType.ENERGY)};
                int max = 0;
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

                        game.roboticonMarket.actors().addRoboticonFunction();
                        game.roboticonMarket.actors().purchaseRoboticonFunction();
                        game.roboticonMarket.actors().purchaseCustomisationFunction(ResourceType.ORE, game.roboticonMarket.actors().selectedRoboticonIndex());
                        break;
                        case 1:
                        //FOOD
                        game.roboticonMarket.actors().addRoboticonFunction();
                        game.roboticonMarket.actors().purchaseRoboticonFunction();
                        game.roboticonMarket.actors().purchaseCustomisationFunction(ResourceType.FOOD,  game.roboticonMarket.actors().selectedRoboticonIndex());
                        break;
                    case 2:
                        //ENERGY
                        game.roboticonMarket.actors().addRoboticonFunction();
                        game.roboticonMarket.actors().purchaseRoboticonFunction();
                        game.roboticonMarket.actors().purchaseCustomisationFunction(ResourceType.ENERGY,  game.roboticonMarket.actors().selectedRoboticonIndex());
                        break;
                        default:
                        //Uh oh! You friccin moron. You just got BEANED!!! Tag your friends to totally BEAN! them
                }

            }
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

        game.nextPhase();
    }

}

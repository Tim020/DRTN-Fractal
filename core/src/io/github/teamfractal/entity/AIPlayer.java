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
    private int money = 100000;


    public AIPlayer(RoboticonQuest game) {
        super(game);
    }

    /**
     * Function calling the AIPlayer to take action.
     *
     */
    public void takeTurn() {
        switch (game.getPhase()) {
            case 1:
                //"Buy Land Plot
                System.out.println("AI: Phase 1 in progress");
                phase1();
            case 2:
                //"Purchase Roboticons
                System.out.println("AI: Phase 2 in progress");
                phase2();
            case 3:
                //Install Roboticons
                System.out.println("AI: Phase 3 in progress");
                phase3();
            case 4:
                //Resource Generation
                System.out.println("AI: Phase 4 in progress");
                phase4();
            case 5:
                //Resource Auction
                System.out.println("AI: Phase 5 in progress");
                phase5();
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

        //TODO Make correct

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
                        game.roboticonMarket.getActors().addRoboticonFunction();
                        game.roboticonMarket.getActors().buyRoboticonFunction();
                        game.roboticonMarket.getActors().buyCustomisationFunction(ResourceType.ORE, game.roboticonMarket.getActors().currentlySelectedRoboticonPos);
                        break;
                        case 1:
                        //FOOD
                        game.roboticonMarket.getActors().addRoboticonFunction();
                        game.roboticonMarket.getActors().buyRoboticonFunction();
                        game.roboticonMarket.getActors().buyCustomisationFunction(ResourceType.FOOD,  game.roboticonMarket.getActors().currentlySelectedRoboticonPos);
                            break;
                        case 2:
                        //ENERGY
                        game.roboticonMarket.getActors().addRoboticonFunction();
                        game.roboticonMarket.getActors().buyRoboticonFunction();
                        game.roboticonMarket.getActors().buyCustomisationFunction(ResourceType.ENERGY,  game.roboticonMarket.getActors().currentlySelectedRoboticonPos);
                        break;
                        default:
                        //uh oh

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
     * Function simulating the Player interaction during Phase 4.
     */
    private void phase4() {
        //Blank as no action required
    }

    /**
     * Function simulating the Player interaction during Phase 5.
     */
    private void phase5() {

        //TODO: Implement
        //game.nextPhase();
    }

}

package io.github.teamfractal.entity;

import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.ResourceType;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Duck Related Team Name
 * @version Assessment 3
 * @since Assessment 3
 */
public class AIPlayer extends Player {
    public RoboticonQuest game;
    Array<Roboticon> roboticonList;
    private ArrayList<LandPlot> landList = new ArrayList<LandPlot>();
    //<editor-fold desc="Resource getter and setter">
    private int money = 100;
    private int ore = 0;
    private int energy = 0;
    private int food = 0;

    public AIPlayer(RoboticonQuest game) {
        super(game);
    }

    /**
     * Function calling the AIPlayer to take action.
     *
     * @param phase int 1-5 containing the current phase of the system.
     */
    public void takeTurn(int phase) {
        switch (phase) {
            case 1:
                //"Buy Land Plot
                System.out.println(game.getPlotManager().height);
                phase1();
            case 2:
                //"Purchase Roboticons
                phase2();
            case 3:
                //Install Roboticons
                phase3();
            case 4:
                //Resource Generation
                phase4();
            case 5:
                //Resource Auction
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
        int x = game.getPlotManager().width;
        int y = game.getPlotManager().height;
        if (this.getMoney() >= 10) {
            while (!selected) {
                int i = random(x);
                int j = random(y);
                if (!game.getPlotManager().getPlot(i, j).hasOwner()) {
                    selected = true;
                    game.gameScreen.getActors().tileClicked(game.getPlotManager().getPlot(i, j), (float) i, (float) j);

                }
            }
        }
        game.nextPhase();
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
                switch (max_index) {
                    case 0:
                        //ORE
                        game.roboticonMarket.getActors().addRoboticonFunction();
                        game.roboticonMarket.getActors().buyRoboticonFunction();
                        game.roboticonMarket.getActors().buyCustomisationFunction(ResourceType.ORE, 0);

                        break;
                    case 1:
                        //FOOD
                        game.roboticonMarket.getActors().addRoboticonFunction();
                        game.roboticonMarket.getActors().buyRoboticonFunction();
                        game.roboticonMarket.getActors().buyCustomisationFunction(ResourceType.FOOD, 0);
                        break;
                    case 2:
                        //ENERGY
                        game.roboticonMarket.getActors().addRoboticonFunction();
                        game.roboticonMarket.getActors().buyRoboticonFunction();
                        game.roboticonMarket.getActors().buyCustomisationFunction(ResourceType.ENERGY, 0);
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

        //TODO: Implement
    }

    /**
     * Function simulating the Player interaction during Phase 4.
     */
    private void phase4() {
        //TODO: Implement
    }

    /**
     * Function simulating the Player interaction during Phase 5.
     */
    private void phase5() {

        //TODO: Implement
    }

}

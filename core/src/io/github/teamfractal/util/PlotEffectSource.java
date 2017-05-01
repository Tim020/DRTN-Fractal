/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.PlotEffect;
import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Joseph on 13/02/2017.
 * This class exists to declare and instantiate the different PlotEffects that can be randomly applied to different
 * plots at various stages in the game, thereby providing an interface through which the core game can access
 * them
 */
public class PlotEffectSource extends Array<PlotEffect> {

    /**
     * The game's engine
     */
    private RoboticonQuest game;

    public PlotEffect duckRelatedDisaster;

    public PlotEffect spicy;

    public PlotEffect earthquakeDisaster;

    public PlotEffect tornado;

    public PlotEffect strike;

    /**
     * Constructor that prepares a variety of PlotEffects and adds them all to the internal array structure for
     * later access and use by the game's engine
     *
     * @param game The game's engine
     */
    public PlotEffectSource(final RoboticonQuest game) {
        this.game = game;
        //Import the game's engine for use by the effects

        configureEffects();
        implementEffects();
    }

    /**
     * Subroutine that instantiates each effect declared above
     * ORE, ENERGY, FOOD
     */
    public void configureEffects() {
        duckRelatedDisaster = new PlotEffect("Duck-Related Disaster", "A horde of ducks pillage your most " +
                "food-producing tile, ruining many of the crops on it. Food\nproduction on that tile is reduced by " +
                "80% for this turn.", new ResourceGroupFloat(0.2f, 1f, 1f), new Runnable() {
            @Override
            public void run() {
                if (game.getPlayer().getLandList().size() == 0) {
                    return;
                }

                LandPlot foodProducer = game.getPlayer().getLandList().get(0);

                for (LandPlot plot : game.getPlayer().getLandList()) {
                    if (plot.getResource(ResourceType.FOOD) > foodProducer.getResource(ResourceType.FOOD)) {
                        foodProducer = plot;
                    }
                }

                duckRelatedDisaster.impose(foodProducer, 1);
            }
        });

        spicy = new PlotEffect("It's getting spicy", "Some students got hold of some hot pepper seeds and all of your food " +
                "production \nhas been turned over to peppers. Increasing Food output by 200% However this spicy craze " +
                "\nhas caused all other production values to drop to 0.", new ResourceGroupFloat(2, 0, 0), new Runnable() {
            @Override
            public void run() {
                if (game.getPlayer().getLandList().size() == 0) {
                    return;
                }

                for (LandPlot plot : game.getPlayer().getLandList()) {
                    spicy.impose(plot, 1);
                }


            }
        });

        earthquakeDisaster = new PlotEffect("Earthquake disaster", "Due to experiments committed in" +
                "University's of York secret laboratory, a massive\n earthquake hit the surroundings of York." +
                "Ore mines were severely damaged therefore\n ore production has dropped by 90% for this turn.",
                new ResourceGroupFloat(1, 1, 0.1f), new Runnable() {
            @Override
            public void run() {
                if (game.getPlayer().getLandList().size() == 0) {
                    return;
                }

                for (LandPlot plot : game.getPlayer().getLandList()) {
                    earthquakeDisaster.impose(plot, 1);
                }


            }
        });

        tornado = new PlotEffect("Tornado", "Looks like a tornado has struck the campus!" +
                "\nThe gale force winds have blown some of your crops away, reducing food production by 50%. " +
                "\nHowever the winds have increased the output of your wind farms, increasing energy production by 60%",
                new ResourceGroupFloat(1.6f, 0.5f, 1), new Runnable() {
            @Override
            public void run() {
                if (game.getPlayer().getLandList().size() == 0) {
                    return;
                }

                for (LandPlot plot : game.getPlayer().getLandList()) {
                    tornado.impose(plot, 1);
                }


            }
        });

        strike = new PlotEffect("Roboticon Strike", "Some of your roboticons have decided to go on strike." +
                "\nThey are bored of standing in the same place doing the same thing all the time." +
                "\nAll resource production has depleted by 30%",
                new ResourceGroupFloat(0.7f, 0.7f, 0.7f), new Runnable() {
            @Override
            public void run() {
                if (game.getPlayer().getLandList().size() == 0) {
                    return;
                }

                for (LandPlot plot : game.getPlayer().getLandList()) {
                    strike.impose(plot, 1);
                }


            }
        });
    }

    /**
     * Subroutine that adds the effects instantiated above to the internal array structure for future access
     */
    public void implementEffects() {
        add(duckRelatedDisaster);
        add(spicy);
        add(earthquakeDisaster);
        add(tornado);
        add(strike);
    }
}

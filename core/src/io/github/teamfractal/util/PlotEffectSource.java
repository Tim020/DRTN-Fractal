package io.github.teamfractal.util;

import com.badlogic.gdx.utils.Array;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.LandPlot;
import io.github.teamfractal.entity.PlotEffect;
import io.github.teamfractal.entity.enums.ResourceType;

public class PlotEffectSource extends Array<PlotEffect> {

    private RoboticonQuest engine;

    private PlotEffect duckRelatedDisaster;
    private PlotEffect earthquakeDisaster;

    private PlotEffect spicy;

    public PlotEffectSource(final RoboticonQuest engine) {
        this.engine = engine;

        configureEffects();
        implementEffects();
    }

    private void configureEffects() {
        duckRelatedDisaster = new PlotEffect("Duck-Related Disaster", "A horde of ducks pillage your most " +
                "food-producing tile, ruining many of the crops on it. Food\nproduction on that tile is reduced by " +
                "80% for this turn.", new Float[]{(float) 1, (float) 1, (float) 0.2}, new Runnable() {
            @Override
            public void run() {
                if (engine.getPlayer().getLandList().size() == 0) {
                    return;
                }

                LandPlot foodProducer = engine.getPlayer().getLandList().get(0);

                for (LandPlot plot : engine.getPlayer().getLandList()) {
                    if (plot.getResource(ResourceType.FOOD) > foodProducer.getResource(ResourceType.FOOD)) {
                        foodProducer = plot;
                    }
                }

                duckRelatedDisaster.impose(foodProducer, 1);
            }
        });

        earthquakeDisaster = new PlotEffect("Earthquake disaster", "Due to experiments committed in" +
                " University's of York secret laboratory, a massive\n earthquake hit the surroundings of York." +
        " Ore mines were severely damaged therefore\n ore production has dropped by 90% for this turn.",
                new Float[]{(float) 0.1, (float) 1, (float) 1}, new Runnable() {
            @Override
            public void run() {
                if (engine.getPlayer().getLandList().size() == 0) {
                    return;
                }

                for (LandPlot plot : engine.getPlayer().getLandList()) {
                    earthquakeDisaster.impose(plot, 1);
                }
            }
        });

        spicy = new PlotEffect("It's getting spicy", "Some students got hold of some hot pepper seeds and all of your food " +
                "production \nhas been turned over to peppers. Increasing Food output by 200% However this spicy craze " +
                "\nhas caused all other production values to drop to 0.", new Float[]{(float) 0, (float) 0, (float) 2}, new Runnable() {
            @Override
            public void run() {

                if (engine.getPlayer().getLandList().size() == 0) {
                    return;
                }

                for (LandPlot plot : engine.getPlayer().getLandList()) {
                    spicy.impose(plot, 1);
                }


            }
        });


    }

    private void implementEffects() {
        add(duckRelatedDisaster);
        add(earthquakeDisaster);
        add(spicy);

    }

}

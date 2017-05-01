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

import io.github.teamfractal.TesterFile;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.util.ResourceGroupInteger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LandPlotTest extends TesterFile {
    private LandPlot plot;

    @Before
    public void setup() {
        plot = new LandPlot(3, 0, 0);
    }

    @Test
    public void testInstallRobiticon() throws Exception {
        Roboticon roboticon = new Roboticon(0);
        ResourceGroupInteger productionModifiers;

        roboticon.setCustomisation(ResourceType.ORE);
        assertTrue(plot.installRoboticon(roboticon));

        productionModifiers = plot.productionModifiers.clone();
        assertEquals(new ResourceGroupInteger(0, 0, 1), productionModifiers);


        Roboticon roboticon2 = new Roboticon(0);
        roboticon2.setCustomisation(ResourceType.ENERGY);
        assertTrue(plot.installRoboticon(roboticon2));
        productionModifiers = plot.productionModifiers.clone();
        assertEquals(new ResourceGroupInteger(0, 1, 1), productionModifiers);

        Roboticon roboticon3 = new Roboticon(0);
        roboticon3.setCustomisation(ResourceType.ORE);
        assertTrue(plot.installRoboticon(roboticon3));
        productionModifiers = plot.productionModifiers.clone();
        assertEquals(new ResourceGroupInteger(0, 1, 2), productionModifiers);

        Roboticon roboticon4 = new Roboticon(0);
        roboticon4.setCustomisation(ResourceType.FOOD);
        assertTrue(plot.installRoboticon(roboticon4));
        productionModifiers = plot.productionModifiers.clone();
        assertEquals(new ResourceGroupInteger(1, 1, 2), productionModifiers);
    }

    @Test
    public void landPlotShouldNotReinstallRoboticon() {
        Roboticon roboticon = new Roboticon(0);
        ResourceGroupInteger productionModifiers;

        roboticon.setCustomisation(ResourceType.ORE);
        assertTrue(plot.installRoboticon(roboticon));

        productionModifiers = plot.productionModifiers.clone();
        assertEquals(new ResourceGroupInteger(0, 0, 1), productionModifiers);

        assertFalse(plot.installRoboticon(roboticon));
        productionModifiers = plot.productionModifiers.clone();
        assertEquals(new ResourceGroupInteger(0, 0, 1), productionModifiers);
    }

    @Test
    public void testProduceResources() throws Exception {
		Roboticon roboticon = new Roboticon(0);
		roboticon.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon);
		assertEquals(new ResourceGroupInteger(0,0,3), plot.produceResources());
		Roboticon roboticon2 = new Roboticon(0);
		roboticon2.setCustomisation(ResourceType.ORE);
		plot.installRoboticon(roboticon2);
        assertEquals(new ResourceGroupInteger(0,0,6), plot.produceResources());
    }

}

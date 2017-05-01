/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
import io.github.teamfractal.TesterFile;
import io.github.teamfractal.entity.enums.ResourceType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoboticonTest extends TesterFile {
	private Roboticon roboticon;
	
	@Before
	public void setup() {
		roboticon = new Roboticon(1);
	}
	
	@Test
	public void initialisationTest(){
		assertEquals(roboticon.getCustomisation(), ResourceType.Unknown);
		assertFalse(roboticon.isInstalled());
	}
	
	@Test
	public void customisationTest(){
		roboticon.setCustomisation(ResourceType.ORE);
		assertEquals(roboticon.getCustomisation(), ResourceType.ORE);
	}
	
	@Test
	public void installationTest(){
		LandPlot plot = new LandPlot(0, 0, 0);
		plot.installRoboticon(roboticon);
		assertTrue(roboticon.isInstalled());
	}

	

}

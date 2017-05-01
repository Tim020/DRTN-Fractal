/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
package io.github.teamfractal.entity;

import io.github.teamfractal.TesterFile;
import io.github.teamfractal.entity.enums.ResourceType;
import io.github.teamfractal.util.ResourceGroupInteger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MarketTest extends TesterFile {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	private Market market;

	/**
	 * Reset market to its default status.
	 */
	@Before
	public void Contractor() {
		market = new Market();
	}

	/**
	 * test start mo
	 * The market should start with correct amount of resources.
	 * 16 Food and Energy, 0 Ore, 12 Robotics
	 */
	@Test
	public void marketShouldInitWithCorrectValues() {
		assertEquals(16, market.getFood());
		assertEquals(16, market.getEnergy());
		assertEquals(0, market.getOre());
		assertEquals(12, market.getRoboticons());
	}

	/**
	 * test setEnergy(), setOre(), setFood(), setRoboticons()
	 * The market should be able to set and get resources.
	 */
	@Test
	public void marketShouldAbleToGetAndSetResources() {
		Random rnd = new Random();
		int valueToTest = rnd.nextInt(100);
		market.setEnergy(valueToTest);
		market.setOre(valueToTest);
		market.setFood(valueToTest);
		market.setRoboticons(valueToTest);


		assertEquals(valueToTest, market.getEnergy());
		assertEquals(valueToTest, market.getOre());
		assertEquals(valueToTest, market.getFood());
		assertEquals(valueToTest, market.getRoboticons());
	}

	/**
	 * test: getBuyPrice()
	 * The market should start with correct price for player to buy.
	 * The price is defined by the starting sell price.
	 * But is constant for the roboticon.
	 */
	@Test
	public void marketShouldHaveCorrectPricesForResources() throws Exception {
		assertEquals(10, market.getBuyPrice(ResourceType.ORE));
		assertEquals(10, market.getBuyPrice(ResourceType.ENERGY));
		assertEquals(10, market.getBuyPrice(ResourceType.FOOD));
		assertEquals(9, market.getBuyPrice(ResourceType.ROBOTICON));
	}


	/**
	 * test: hasEnoughResources
	 * player class can use this method to find out that the amount of resource
	 * player want to buy is available in the market, if the amount of resource
	 * in the market is less than the amount of resources player want to buy then
	 * throw exception
	 */

	@Test
	public void marketCanCheckResourceMoreThanAmountYouWantToBuy() {
		assertFalse(market.hasEnoughResources(ResourceType.ORE, 1000000));
		assertFalse(market.hasEnoughResources(ResourceType.ENERGY, 1000000));
		assertFalse(market.hasEnoughResources(ResourceType.ROBOTICON, 1000000));
		assertFalse(market.hasEnoughResources(ResourceType.FOOD, 1000000));
	}


	/**
	 * test: getSellPrice()
	 */

	@Test
	public void marketShouldReturnCorrectSellPrice(){
		int valueToTest1 = 20;
		market.setEnergy(valueToTest1);
		market.setOre(valueToTest1);
		market.setFood(valueToTest1);
		market.setRoboticons(valueToTest1);

		assertEquals(10,market.getSellPrice(ResourceType.FOOD));
		assertEquals(10,market.getSellPrice(ResourceType.ORE));
		assertEquals(10,market.getSellPrice(ResourceType.ROBOTICON));
		assertEquals(10,market.getSellPrice(ResourceType.ENERGY));
	}

	@Test
	public void marketShouldReduceResourcesWhenSells(){
		market.setEnergy(10);
		market.setOre(10);
		market.setFood(10);
		market.setRoboticons(10);

		market.buyResourceFromMarket(ResourceType.FOOD, 5);
		market.buyResourceFromMarket(ResourceType.ORE, 5);
		market.buyResourceFromMarket(ResourceType.ENERGY, 5);
		market.buyResourceFromMarket(ResourceType.ROBOTICON, 5);

		assertEquals(5, market.getFood() );
		assertEquals(5, market.getOre() );
		assertEquals(5, market.getEnergy() );
		assertEquals(5, market.getRoboticons() );

	}
	@Test
	public void marketShouldUseOreToCreateRoboticons(){
		market.setRoboticons(10);
		market.setOre(10);
		market.generateRoboticon();

		int roboticons = market.getRoboticons();

		assertEquals(10 - ((roboticons - 10)* 2),market.getOre());
	}
}

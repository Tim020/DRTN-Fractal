package io.github.teamfractal.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.Roboticon;
import io.github.teamfractal.entity.enums.PurchaseStatus;
import io.github.teamfractal.entity.enums.ResourceType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.ArrayList;

/**
 * Created by Joseph on 08/02/2017.
 */
public class RoboticonMarketActors extends Table {
    private static final Texture no_cust_texture;
    private static final Texture energy_texture;
    private static final Texture ore_texture;
    private static final Texture food_texture;
    private static final Texture no_robotic_texture;

    static {
        no_cust_texture = new Texture(Gdx.files.internal("roboticon_images/Robot.png"));
        energy_texture = new Texture(Gdx.files.internal("roboticon_images/Robot_energy.png"));
        ore_texture = new Texture(Gdx.files.internal("roboticon_images/Robot_ore.png"));
        food_texture = new Texture(Gdx.files.internal("roboticon_images/Robot_Food.png"));
        no_robotic_texture = new Texture(Gdx.files.internal("roboticon_images/No_Roboticons.png"));
    }

    private RoboticonQuest game;

    public ArrayList<Roboticon> roboticons = new ArrayList<Roboticon>();
    private Texture roboticonTexture;
    private Image roboticonImage;

    private Table purchaseTable;
    private Table selectionTable;
    private Table upgradeTable;

    private Integer roboticonPurchaseAmount;
    private Label roboticonPurchaseAmountLabel;

    private TextButton roboticonSubButton;
    private TextButton roboticonAddButton;
    private TextButton roboticonPurchaseButton;

    private Integer selectedRoboticonIndex;
    private Label selectedRoboticonIDLabel;
    private TextButton moveLeftInventoryButton;
    private TextButton moveRightInventoryButton;

    private SelectBox<String> customisationDropDown;
    private TextButton customisationPurchaseButton;

    private TextButton exitButton;

    /**
     * Constructor class that connects the roboticon market to the internal engine and builds its visual interface
     * @param game The engine driving the game forward
     */
    public RoboticonMarketActors(RoboticonQuest game) {
        this.game = game;

        this.roboticonImage = new Image();

        constructInterface();
    }

    /**
     * Builds the visual framework that serves as the market's interface
     */
    private void constructInterface() {
        purchaseTable = new Table();
        selectionTable = new Table();
        upgradeTable = new Table();

        roboticonPurchaseAmount = 1;

        constructLabels();
        constructButtons();

        purchaseTable.add(new Label("PURCHASE ROBOTICONS", new Label.LabelStyle(game.headerFontRegular.font(), Color.WHITE))).colspan(4);

        purchaseTable.row();
        purchaseTable.add(roboticonSubButton).width(25);
        purchaseTable.add(roboticonPurchaseAmountLabel).width(50).expandX();
        purchaseTable.add(roboticonAddButton).width(25);
        purchaseTable.add(roboticonPurchaseButton).align(Align.right).width(268).padLeft(14);

        add(purchaseTable).padBottom(35);
        row();

        selectionTable.add(new Label("CUSTOMISE ROBOTICONS", new Label.LabelStyle(game.headerFontRegular.font(), Color.WHITE))).colspan(3);

        selectionTable.row();
        selectionTable.add(moveLeftInventoryButton).width(50);
        selectionTable.add(roboticonImage).expandX();
        selectionTable.add(moveRightInventoryButton).width(50);

        selectionTable.row();
        selectionTable.add(selectedRoboticonIDLabel).colspan(3);

        add(selectionTable).padBottom(10);
        row();

        customisationDropDown = new SelectBox<String>(game.skin);
        customisationDropDown.setItems(new String[]{"Energy Generation", "Ore Mining", "Food Farming"});
        upgradeTable.add(customisationDropDown).expandX().padRight(14);
        upgradeTable.add(customisationPurchaseButton).width(196);

        add(upgradeTable).padBottom(35);
        row();

        add(exitButton).expandX().width(382);
    }

    /**
     * Constructs critical label objects that may change in appearance as the market is interacted with
     */
    private void constructLabels() {
        roboticonPurchaseAmountLabel = new Label(roboticonPurchaseAmount + "/" +  game.market.getResource(ResourceType.ROBOTICON), new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        roboticonPurchaseAmountLabel.setAlignment(Align.center);

        selectedRoboticonIDLabel = new Label("", new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        selectedRoboticonIDLabel.setAlignment(Align.center);
    }

    /**
     * Constructs the buttons that users can utilise to interact with the market
     */
    private void constructButtons() {
        //Increases number of roboticons to be purchased
        roboticonAddButton = new TextButton("+", game.skin);
        roboticonAddButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addRoboticonFunction();
            }
        });

        //Decreases number of roboticons to be purchased
        roboticonSubButton = new TextButton("-", game.skin);
        roboticonSubButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                subRoboticonFunction();
            }
        });

        //Purchases the specified number of roboticons
        roboticonPurchaseButton = new TextButton("[PRICE: " + (game.market.getSellPrice(ResourceType.ROBOTICON) * roboticonPurchaseAmount) + "] PURCHASE", game.skin);
        roboticonPurchaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                purchaseRoboticonFunction();
            }
        });

        //Goes back through the queue of roboticons owned by the current player
        moveLeftInventoryButton = new TextButton("<", game.skin);
        moveLeftInventoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (selectedRoboticonIndex > 0) {
                    selectedRoboticonIndex--;
                    refreshCurrentlySelectedRoboticon();
                }
            }
        });

        //Goes forward through the queue of roboticons owned by the current player
        moveRightInventoryButton = new TextButton(">", game.skin);
        moveRightInventoryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (selectedRoboticonIndex < roboticons.size() - 1) {
                    selectedRoboticonIndex++;
                    refreshCurrentlySelectedRoboticon();
                }
            }
        });

        //Purchases a customisation and applies it to the currently-selected Roboticon
        customisationPurchaseButton = new TextButton("[PRICE: " + game.market.getSellPrice(ResourceType.CUSTOMISATION) + "] PURCHASE", game.skin);
        customisationPurchaseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (selectedRoboticonIndex == -1) {
                    return;
                }

                ResourceType resource = null;

                switch(customisationDropDown.getSelectedIndex()) {
                    case (0):
                        resource = ResourceType.ENERGY;
                        break;
                    case (1):
                        resource = ResourceType.ORE;
                        break;
                    case (2):
                        resource = ResourceType.FOOD;
                        break;
                }

                purchaseCustomisationFunction(resource, selectedRoboticonIndex);
            }
        });

        //Exits the shop and advances the game upon being clicked
        exitButton = new TextButton("EXIT ROBOTICON SHOP", game.skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.nextPhase();
            }
        });
    }

    /**
     * Refreshes the interface of the Roboticon shop whenever it's interacted with
     * Also toggles the player's ability to make their desired purchase, depending on whether or not they hold
     * sufficient resources
     */
    public void refreshRoboticonShop() {
        roboticonPurchaseAmountLabel.setText(roboticonPurchaseAmount + "/" + game.market.getResource(ResourceType.ROBOTICON));
        roboticonPurchaseButton.setText("[PRICE: " + (game.market.getSellPrice(ResourceType.ROBOTICON) * roboticonPurchaseAmount) + "] PURCHASE");

        if (game.getPlayer().getMoney() >= game.market.getSellPrice(ResourceType.ROBOTICON) * roboticonPurchaseAmount) {
            roboticonPurchaseButton.setTouchable(Touchable.enabled);
        } else {
            roboticonPurchaseButton.setTouchable(Touchable.disabled);
        }
	}

    /**
     * Adds a roboticon to the quantity selected
     */
    public void addRoboticonFunction() {
        if (roboticonPurchaseAmount < game.market.getResource(ResourceType.ROBOTICON)) {
            roboticonPurchaseAmount += 1;
            refreshRoboticonShop();
        }
    }

    /**
     * Subtracts a roboticon from the quantity selected
     */
    public void subRoboticonFunction() {
        if (roboticonPurchaseAmount > 1) {
            roboticonPurchaseAmount -= 1;
            refreshRoboticonShop();
        }
    }

    /**
     * Buys the selected customisation and adds it to the player's inventory
     * @param resource The customisation that has been bought by the player
     * @param index The position of the currently selected, non-customised roboticon
     */
    public void purchaseCustomisationFunction(ResourceType resource, int index) {
        if (game.getPlayer().purchaseCustomisationFromMarket(resource, roboticons.get(index), game.market) == PurchaseStatus.Success) {
            widgetUpdate();
            game.gameScreen.getActors().textUpdate();

            customisationPurchaseButton.setText("CUSTOMISED");
            customisationPurchaseButton.setTouchable(Touchable.disabled);
        }
    }

    /**
     * Buys the selected amount of roboticons and places them in the player's inventory
     */
    public void purchaseRoboticonFunction() {
        if (game.getPlayer().purchaseRoboticonsFromMarket(roboticonPurchaseAmount, game.market) == PurchaseStatus.Success) {
            roboticonPurchaseAmount = 1;
            refreshRoboticonShop();
            widgetUpdate();
            game.gameScreen.getActors().textUpdate();
        }
    }

    /**
     * Retrieves and draws information to the screen relating to the turn and phase info as well as the player's
     * resource count
     */
    public void widgetUpdate() {
        roboticons.clear();
        for (Roboticon r : game.getPlayer().getRoboticons()) {
            if (!r.isInstalled()) {
                roboticons.add(r);
            }
        }

        if (roboticons.size() == 0) {
            selectedRoboticonIndex = -1;
        } else if (selectedRoboticonIndex == -1) {
            selectedRoboticonIndex = 0;
        }

        refreshCurrentlySelectedRoboticon();
    }

    /**
     * Sets the appearance of the current roboticon on the market screen. This is based on the customisation of the
     * selected roboticon as well as the ID number
     */
    private void refreshCurrentlySelectedRoboticon() {
        if (selectedRoboticonIndex != -1) {
            ResourceType roboticonType = roboticons.get(selectedRoboticonIndex).getCustomisation();

            switch (roboticonType) {
                case Unknown:
                    roboticonTexture = no_cust_texture;
                    break;
                case ENERGY:
                    roboticonTexture = energy_texture;
                    customisationDropDown.setSelectedIndex(0);
                    break;
                case ORE:
                    roboticonTexture = ore_texture;
                    customisationDropDown.setSelectedIndex(1);
                    break;
                case FOOD:
                    roboticonTexture = food_texture;
                    customisationDropDown.setSelectedIndex(2);
                    break;
                default:
                    break;
            }

            this.selectedRoboticonIDLabel.setText("[" + (selectedRoboticonIndex + 1) + "/" + roboticons.size() + "] ISSUE NUMBER: " + padZero(roboticons.get(selectedRoboticonIndex).getID(), 4));

            if (roboticonType == ResourceType.Unknown) {
                customisationPurchaseButton.setText("[PRICE: " + game.market.getSellPrice(ResourceType.CUSTOMISATION) + "] PURCHASE");

                if (game.getPlayer().getMoney() >= game.market.getSellPrice(ResourceType.CUSTOMISATION)) {
                    customisationPurchaseButton.setTouchable(Touchable.enabled);
                } else {
                    customisationPurchaseButton.setTouchable(Touchable.disabled);
                }

            } else {
                customisationPurchaseButton.setText("CUSTOMISED");
                customisationPurchaseButton.setTouchable(Touchable.disabled);
            }
        } else {
            roboticonTexture = no_robotic_texture;
            this.selectedRoboticonIDLabel.setText("ISSUE NUMBER: ####");

            customisationPurchaseButton.setText("[PRICE: " + game.market.getSellPrice(ResourceType.CUSTOMISATION) + "] PURCHASE");
            customisationPurchaseButton.setTouchable(Touchable.disabled);
        }

        roboticonImage.setDrawable(new TextureRegionDrawable(new TextureRegion(roboticonTexture)));
    }
    /**
     * Generates a string of a number followed by a certain amount of zeros
     * @param number The number that the string starts with
     * @param length The number of zeros that the number is followed by
     * @return The string that has been generated
     */

    public String padZero(int number, int length) {
        String s = "" + number;
        while (s.length() < length) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * Getter for the index of the selected Roboticon
     * @return The index of the selected Roboticon
     */
    public int selectedRoboticonIndex() {
        return selectedRoboticonIndex;
    }
}

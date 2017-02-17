package io.github.teamfractal.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.PurchaseStatus;
import io.github.teamfractal.entity.enums.ResourceType;

/**
 * Created by Joseph on 17/02/2017.
 */
public class ResourceMarketActors extends Table {

    private Table marketTable;

    private final MarketAdjustableActor oreBuyAdjustable;
    private final MarketAdjustableActor oreSellAdjustable;
    private final MarketAdjustableActor energyBuyAdjustable;
    private final MarketAdjustableActor energySellAdjustable;
    private final MarketAdjustableActor foodBuyAdjustable;
    private final MarketAdjustableActor foodSellAdjustable;

    private final TextField gambleField;
    private Label gambleStatusLabel;
    private Label gamblePlayerRoll;
    private Label gambleRNGesusRoll;
    private Label gambleMoneyWonLabel;
    private Label gambleMoneyLostLabel;
    private Label gambleWinLossLabel;

    private RoboticonQuest game;

    public ResourceMarketActors(final RoboticonQuest game) {
        final int spacing = 20;

        this.game = game;

        marketTable = new Table();
        Table gambleTable = new Table();

        oreBuyAdjustable = createAdjustable(ResourceType.ORE, true);
        oreSellAdjustable = createAdjustable(ResourceType.ORE, false);
        energyBuyAdjustable = createAdjustable(ResourceType.ENERGY, true);
        energySellAdjustable = createAdjustable(ResourceType.ENERGY, false);
        foodBuyAdjustable = createAdjustable(ResourceType.FOOD, true);
        foodSellAdjustable = createAdjustable(ResourceType.FOOD, false);

        // Row: Headers
        marketTable.add(new Label("BUY", new Label.LabelStyle(game.headerFontRegular.font(), Color.WHITE))).padRight(spacing).padBottom(5);
        marketTable.add(new Label("SELL", new Label.LabelStyle(game.headerFontRegular.font(), Color.WHITE))).padBottom(5);
        rowWithHeight(10);

        // Row: Ore buy/sell
        marketTable.add(oreBuyAdjustable).padRight(spacing);
        marketTable.add(oreSellAdjustable);
        rowWithHeight(10);

        // Row: Energy buy/sell
        marketTable.add(energyBuyAdjustable).padRight(spacing);
        marketTable.add(energySellAdjustable);
        rowWithHeight(10);

        // Row: Food buy/sell
        marketTable.add(foodBuyAdjustable).padRight(spacing);
        marketTable.add(foodSellAdjustable);
        rowWithHeight(20);

        add(marketTable).padRight(spacing);

        gambleTable.add(new Label("PUB", new Label.LabelStyle(game.headerFontRegular.font(), Color.WHITE))).padBottom(10).colspan(2);
        gambleTable.row();

        gambleField = new TextField("", game.skin);
        gambleField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
        gambleTable.add(gambleField).padBottom(3).colspan(2);
        gambleTable.row();

        TextButton gambleButton = new TextButton("Gamble Money", game.skin);
        gambleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.resourceMarket.gamble();
                widgetUpdate();
                game.gameScreen.getActors().textUpdate();
            }
        });
        gambleButton.setWidth(150);
        gambleTable.add(gambleButton).width(150).padBottom(10).colspan(2);
        gambleTable.row();

        gambleStatusLabel = new Label(" \n ", new Label.LabelStyle(game.smallFontRegular.font(), Color.WHITE));
        gambleStatusLabel.setAlignment(Align.center);
        gambleTable.add(gambleStatusLabel).padBottom(10).colspan(2);
        gambleTable.row();

        gamblePlayerRoll = new Label("-", new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        gambleRNGesusRoll = new Label("-", new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        gambleTable.add(new Label("Your Roll", new Label.LabelStyle(game.smallFontRegular.font(), Color.WHITE))).left().width(110);
        gambleTable.add(gamblePlayerRoll).expandX().left();
        gambleTable.row();
        gambleTable.add(new Label("AI's Roll", new Label.LabelStyle(game.smallFontRegular.font(), Color.WHITE))).left().width(110).padBottom(10);
        gambleTable.add(gambleRNGesusRoll).expandX().left().padBottom(10);
        gambleTable.row();

        gambleMoneyWonLabel = new Label("0", new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        gambleMoneyLostLabel = new Label("0", new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        gambleWinLossLabel = new Label("0", new Label.LabelStyle(game.smallFontLight.font(), Color.WHITE));
        gambleTable.add(new Label("Money Won", new Label.LabelStyle(game.smallFontRegular.font(), Color.WHITE))).left().width(110);
        gambleTable.add(gambleMoneyWonLabel).expandX().left();
        gambleTable.row();
        gambleTable.add(new Label("Money Lost", new Label.LabelStyle(game.smallFontRegular.font(), Color.WHITE))).left().width(110);
        gambleTable.add(gambleMoneyLostLabel).expandX().left();
        gambleTable.row();
        gambleTable.add(new Label("W/L", new Label.LabelStyle(game.smallFontRegular.font(), Color.WHITE))).left().width(110);
        gambleTable.add(gambleWinLossLabel).expandX().left();

        add(gambleTable).top();

        row();

        TextButton exitButton = new TextButton("EXIT MARKET", game.skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.nextPhase();
            }
        });
        add(exitButton).colspan(2).expandX().width(490);

        widgetUpdate();
    }

    /**
     * Generate an adjustable actor for sell/buy.
     *
     * @param resource The resource type.
     * @param buy  <code>true</code> if is for buying,
     *             or <code>false</code> if is for selling
     * @return The adjustable actor generated.
     */
    private MarketAdjustableActor createAdjustable(final ResourceType resource, final boolean buy) {
        final MarketAdjustableActor adjustableActor = new MarketAdjustableActor(game, game.skin, resource, buy);

        adjustableActor.setActionEvent(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (buy) {
                    // Buy from market
                    if (game.getPlayer().purchaseResourceFromMarket(adjustableActor.getValue(), game.market, resource) == PurchaseStatus.Success) {
                        game.gameScreen.getActors().textUpdate();
                        widgetUpdate();
                    }
                } else {
                    // Sell to market
                    if (adjustableActor.getValue() <= game.getPlayer().getResource(resource)) {
                        game.getPlayer().sellResourceToMarket(adjustableActor.getValue(), game.market, resource);
                        game.gameScreen.getActors().textUpdate();
                        widgetUpdate();
                    }
                }


            }
        });
        return adjustableActor;
    }

    /**
     * Get price in string format
     *
     * @param resource The resource type.
     * @param buy  <code>true</code> if is for buying,
     *             or <code>false</code> if is for selling
     * @return The formatted string for the resource.
     */
    private String getPriceString(ResourceType resource, boolean buy) {
        // getBuyPrice: market buy-in price (user sell price)
        // getSellPrice: market sell price (user buy price)
        return resource.toString() + ": "
                + (buy
                ? game.market.getSellPrice(resource)
                : game.market.getBuyPrice(resource))
                + " Gold";
    }

    /**
     * Sync. information with the adjustable.
     *
     * @param adjustableActor The adjustable to manipulate with.
     * @param resource        The resource type.
     * @param buy  <code>true</code> if is for buying,
     *             or <code>false</code> if is for selling
     */
    private void updateAdjustable(MarketAdjustableActor adjustableActor, ResourceType resource,
                                  boolean buy) {
        if (buy) {
            adjustableActor.setMax(game.market.getResource(resource));
        } else {
            adjustableActor.setMax(game.getPlayer().getResource(resource));
        }

        adjustableActor.setTitle(getPriceString(resource, buy));
    }

    /**
     * Updates all widgets on screen
     */
    public void widgetUpdate() {
        updateAdjustable(oreBuyAdjustable, ResourceType.ORE, true);
        if (game.market.getResource(ResourceType.ORE) == 0) {
            oreBuyAdjustable.setValue(0);
        } else {
            oreBuyAdjustable.setValue(1);
        }

        updateAdjustable(oreSellAdjustable, ResourceType.ORE, false);
        if (game.getPlayer().getResource(ResourceType.ORE) == 0) {
            oreSellAdjustable.setValue(0);
        } else {
            oreSellAdjustable.setValue(1);
        }

        updateAdjustable(energyBuyAdjustable, ResourceType.ENERGY, true);
        if (game.market.getResource(ResourceType.ENERGY) == 0) {
            energyBuyAdjustable.setValue(0);
        } else {
            energyBuyAdjustable.setValue(1);
        }

        updateAdjustable(energySellAdjustable, ResourceType.ENERGY, false);
        if (game.getPlayer().getResource(ResourceType.ENERGY) == 0) {
            energySellAdjustable.setValue(0);
        } else {
            energySellAdjustable.setValue(1);
        }

        updateAdjustable(foodBuyAdjustable, ResourceType.FOOD, true);
        if (game.market.getResource(ResourceType.FOOD) == 0) {
            foodBuyAdjustable.setValue(0);
        } else {
            foodBuyAdjustable.setValue(1);
        }

        updateAdjustable(foodSellAdjustable, ResourceType.FOOD, false);
        if (game.getPlayer().getResource(ResourceType.FOOD) == 0) {
            foodSellAdjustable.setValue(0);
        } else {
            foodSellAdjustable.setValue(1);
        }

        setButtonStates();


    }

    /**
     * Add an empty row to current table.
     * @param height  The y for that empty row.
     */
    private void rowWithHeight(int height) {
        marketTable.row();
        marketTable.add().spaceTop(height);
        marketTable.row();
    }

    public void setButtonStates() {
        if (game.market.getResource(ResourceType.ORE) == 0 || (oreBuyAdjustable.getValue() * game.market.getSellPrice(ResourceType.ORE) > game.getPlayer().getMoney())) {
            oreBuyAdjustable.setButtonState(Touchable.disabled);
        } else {
            oreBuyAdjustable.setButtonState(Touchable.enabled);
        }

        if (game.getPlayer().getResource(ResourceType.ORE) > 0) {
            oreSellAdjustable.setButtonState(Touchable.enabled);
        } else {
            oreSellAdjustable.setButtonState(Touchable.disabled);
        }

        if (game.market.getResource(ResourceType.ENERGY) == 0 || (energyBuyAdjustable.getValue() * game.market.getSellPrice(ResourceType.ENERGY) > game.getPlayer().getMoney())) {
            energyBuyAdjustable.setButtonState(Touchable.disabled);
        } else {
            energyBuyAdjustable.setButtonState(Touchable.enabled);
        }

        if (game.getPlayer().getResource(ResourceType.ENERGY) > 0) {
            energySellAdjustable.setButtonState(Touchable.enabled);
        } else {
            energySellAdjustable.setButtonState(Touchable.disabled);
        }

        if (game.market.getResource(ResourceType.FOOD) == 0 || (foodBuyAdjustable.getValue() * game.market.getSellPrice(ResourceType.FOOD) > game.getPlayer().getMoney())) {
            foodBuyAdjustable.setButtonState(Touchable.disabled);
        } else {
            foodBuyAdjustable.setButtonState(Touchable.enabled);
        }

        if (game.getPlayer().getResource(ResourceType.FOOD) > 0) {
            foodSellAdjustable.setButtonState(Touchable.enabled);
        } else {
            foodSellAdjustable.setButtonState(Touchable.disabled);
        }
    }

    public String gambleFieldValue() {
        return gambleField.getText();
    }

    public void setGambleStatusLabel(String status, Color color) {
        gambleStatusLabel.setText(status);
        gambleStatusLabel.setColor(color);
    }

    public void setGambleRollLabels(int playerRoll, int AIroll) {
        gamblePlayerRoll.setText(String.valueOf(playerRoll));
        gambleRNGesusRoll.setText(String.valueOf(AIroll));
    }

    public void setGambleRollLabels(String playerRoll, String AIroll) {
        gamblePlayerRoll.setText(playerRoll);
        gambleRNGesusRoll.setText(AIroll);
    }

    public void setGambleStatisticsLabels(int moneyWon, int moneyLost, int winLoss) {
        gambleMoneyWonLabel.setText(String.valueOf(moneyWon));
        gambleMoneyLostLabel.setText(String.valueOf(moneyLost));
        gambleWinLossLabel.setText(String.valueOf(winLoss));
    }
}

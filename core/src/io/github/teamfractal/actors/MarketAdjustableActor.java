package io.github.teamfractal.actors;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import io.github.teamfractal.RoboticonQuest;
import io.github.teamfractal.entity.enums.ResourceType;

public class MarketAdjustableActor extends Table {

	private RoboticonQuest game;

	private final TextButton subButton;
	private final TextButton addButton;
	private final TextButton actButton;
	private final Label valueLabel;
	private final Label titleLabel;

	private int value;
	private int min;
	private int max;
	private ChangeListener actionEvent;

	private boolean buy;
	private ResourceType resourceType;

	/**
	 * Get current title string.
	 * @return     The title text.
	 */
	public String getTitle() {
		return titleLabel.getText().toString();
	}

	/**
	 * Set a new title string.
	 * @param title  The new Title.
	 */
	public void setTitle(String title) {
		titleLabel.setText(title);
	}

	/**
	 * Get current adjusted value
	 * @return     Current value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Set a new adjusted value.
	 * @param value       The new value.
	 */
	public void setValue(int value) {
		this.value = value;

		if (value > max) {
			value = max;
		} else if (value < min) {
			value = min;
		}

		updateInterface();
	}

	/**
	 * Get current minimum value.
	 * @return     Currently set minimum value.
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Set a new minimum value.
	 * @param min       The new minimum value.
	 */
	public void setMin(int min) {
		this.min = min;

		if (value < min) {
			value = min;
		}

		updateInterface();
	}

	/**
	 * Get current maximum value.
	 * @return     Currently set maximum value.
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Set a new maximum value.
	 * @param max       The new maximum value.
	 */
	public void setMax(int max) {
		this.max = max;

		if (value > max) {
			value = max;
		}

		updateInterface();
	}

	/**
	 * Set the new action button event.
	 * @param actionEvent        The new action button handle event.
	 */
	public void setActionEvent(ChangeListener actionEvent) {
		this.actionEvent = actionEvent;
	}
	//</editor-fold>

	/**
	 * The adjustable actor
	 * For an easy way to adjust values in a step of 1 / -1
	 *
	 * @param skin    The skin file for the UI.
	 */
	public MarketAdjustableActor(RoboticonQuest game, Skin skin, ResourceType resourceType, boolean buy) {
		this.game = game;

		this.value = 1;
		this.min = 1;

		this.buy = buy;
		this.resourceType = resourceType;

		this.max = game.market.getResource(resourceType);

		subButton = new TextButton("<", skin);
		addButton = new TextButton(">", skin);
		actButton = new TextButton("", skin);
		valueLabel = new Label("", skin);

		if (buy == true) {
			titleLabel = new Label(resourceType.toString() + " - Price: " + game.market.getSellPrice(resourceType), skin);
		} else {
			titleLabel = new Label(resourceType.toString() + " - Price: " + game.market.getBuyPrice(resourceType), skin);
		}

		bindButtonEvents();
		updateInterface();

		/*
		 *   +---------------+
		 *   |   [ title ]   |
		 *   +---------------+
		 *   | < | value | > |
		 *   +---------------+
		 *   |   [ button ]  |
		 *   +---------------+
		 */

		titleLabel.setAlignment(Align.center);
		valueLabel.setWidth(80);
		valueLabel.setAlignment(Align.center);
		subButton.padLeft(5).padRight(5);
		addButton.padLeft(5).padRight(5);

		add(titleLabel).colspan(3).fillX().spaceBottom(10);
		row();

		add(subButton).align(Align.left);
		add(valueLabel).fillX().width(100);
		add(addButton).align(Align.right);
		row();

		add(actButton).colspan(3).fillX().spaceTop(10).width(150);
		row();
	}

	/**
	 * Binds events to buttons.
	 */
	private void bindButtonEvents() {
		actButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (actionEvent != null)
					actionEvent.changed(event, actor);
			}
		});

		subButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (value > min) {
					value--;
					updateInterface();
					game.resourceMarket.actors().setButtonStates();
				}
			}
		});

		addButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (value < max) {
					value++;
					updateInterface();
					game.resourceMarket.actors().setButtonStates();
				}
			}
		});
	}

	/**
	 * Update label text to current value.
	 */
	private void updateInterface() {
		valueLabel.setText(value + "/" + max);

		if (buy == true) {
			actButton.setText("[PRICE: " + (value * game.market.getSellPrice(resourceType)) + "] Buy");
		} else {
			actButton.setText("[PRICE: " + (value * game.market.getBuyPrice(resourceType)) + "] Sell");
		}
	}

	@Override
	protected void sizeChanged() {
		super.sizeChanged();

		// TODO: manipulate actor size?
	}


	public void setButtonState (Touchable state) {
		actButton.setTouchable(state);
	}
}

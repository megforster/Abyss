package mainGame;

/**
 * The upgrades that a user can have (they modify the game for the user)
 * 
 * @author Brandon Loehle 5/30/16
 *
 */

public class Upgrades {

	//Instance variable
	private Game game;
	private Handler handler;
	private HUD hud;
	private Player player;
	private Spawn1to10 spawner;
	private Spawn10to20 spawner2;
	private UpgradeScreen upgradeScreen;
	private String ability;
	private CrabBoss redboss;
	private ShooterEnemy shooter;

	//Constructor 
	public Upgrades(Game game, Handler handler, HUD hud, UpgradeScreen upgradeScreen, Player player, Spawn1to10 spawner,
			Spawn10to20 spawner2) {
		this.game = game;
		this.handler = handler;
		this.hud = hud;
		this.upgradeScreen = upgradeScreen;
		this.player = player;
		this.spawner = spawner;
		this.spawner2 = spawner2;
	}

	//Code for the Clear Screen power-up
	public void clearScreenAbility() {
		handler.clearEnemies();
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = ""; //same for all power-ups, code for power-up being used up
		}
	}

	//Code for the decrease size power-up
	public void decreasePlayerSize() {
		player.setPlayerSize(24);
	}

	//Code for the extra life power-up
	public void extraLife() {
		hud.setExtraLives(hud.getExtraLives() + 1);
	}

	//Control (call) for the health increase power-up
	public void healthIncrease() {
		hud.healthIncrease();
	}

	//Code (call) for the health regeneration power up 
	public void healthRegeneration() {
		hud.setRegen();
	}

	//Code (call) for the increased damage resistance power-up
	public void improvedDamageResistance() {
		player.setDamage(1);
	}

	//Code for the skip level ability
	public void levelSkipAbility() {
		handler.clearEnemies();
		hud.setLevel(hud.getLevel() + 1);
		if (Spawn1to10.LEVEL_SET == 1) {
			spawner.skipLevel();
		} else if (Spawn1to10.LEVEL_SET == 2) {
			spawner2.skipLevel();
		}
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}

	}

	//Code for the freeze time ability 
	public void freezeTimeAbility() {
		handler.pause();
		hud.setAbilityUses(hud.getAbilityUses() - 1);
		if (hud.getAbilityUses() == 0) {
			ability = "";
		}
	}
	
	//Code for the damage boost ability
	public void damageBoost() {
		CrabBoss.enemyDamage *= 2;
		ShooterEnemy.sizeDecrease *= 2;
		System.out.print("DamageBoost Active");
	}

	//Returns the ability 
	public String getAbility() {
		return ability;
	}

	/**
	 * Activate the correct upgrade
	 * 
	 * @param path
	 *            is to the image of the upgrade that was pressed by the user
	 */
	public void activateUpgrade(String path) {
		if (path.equals("images/clearscreenability.png")) {
			ability = "clearScreen";
			hud.setAbility(ability);
			hud.setAbilityUses(3);
		} else if (path.equals("images/decreaseplayersize.png")) {
			decreasePlayerSize();
		} else if (path.equals("images/extralife.png")) {
			extraLife();
		} else if (path.equals("images/healthincrease.png")) {
			healthIncrease();
		} else if (path.equals("images/healthregeneration.png")) {
			healthRegeneration();
		} else if (path.equals("images/improveddamageresistance.png")) {
			improvedDamageResistance();
		} else if (path.equals("images/levelskipability.png")) {
			ability = "levelSkip";
			hud.setAbility(ability);
			hud.setAbilityUses(1);
		} else if (path.equals("images/freezetimeability.png")) {
			ability = "freezeTime";
			hud.setAbility(ability);
			hud.setAbilityUses(5);
		} else if (path.equals("images/damageboost.png")) {
			damageBoost();
		}

	}

	//Resets all power-up effect 
	public void resetUpgrades() {
		Player.playerSpeed = 10;
		hud.resetHealth();
		hud.resetRegen();
		hud.setExtraLives(0);
		player.setPlayerSize(32);
		upgradeScreen.resetPaths();
	}

}


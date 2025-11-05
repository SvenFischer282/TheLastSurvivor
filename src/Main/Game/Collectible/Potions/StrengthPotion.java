package Main.Game.Collectible.Potions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Game.Inventory;

/**
 * A temporary strength-boosting potion that increases player damage after a delay.
 * Implements a timed effect using a scheduled executor service.
 */
public class StrengthPotion extends Potion implements Collectible {

    // Scheduler for delayed effect activation
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final Logger logger = LoggerFactory.getLogger(Inventory.class);

    /**
     * Creates a new strength potion at specified coordinates.
     * @param x Horizontal position in the game world
     * @param y Vertical position in the game world
     * @param effectStrength Amount of damage boost to apply
     */
    public StrengthPotion(int x, int y, int effectStrength) {
        super(x, y, effectStrength);
        this.setType(PotionType.STRENGTH);
    }

    /**
     * Activates the potion's timed strength effect:
     * 1. Immediately increases player damage
     * 2. After 10 seconds, the strength is restored
     *
     * @param player The player character receiving the effect
     */
    @Override
    public void use(Player player) {
        // Ensure thread-safe damage modification
        synchronized (player) {
            // Store original damage for restoration
            double originalDamage = player.getDamage();
            double effectStrength = this.getEffectStrength();

            // Immediately increase damage
            player.setDamage(((int)originalDamage +(int) effectStrength)*2);
            logger.info("Strength potion used: Damage increased by " + effectStrength + " to " + player.getDamage());

            // Schedule damage restoration after 10 seconds
            scheduler.schedule(() -> {
                synchronized (player) {
                    player.setDamage((int) originalDamage);
                    logger.info("Strength potion expired: Damage restored to " + originalDamage);
                }
            }, 10, TimeUnit.SECONDS);
        }}

    /**
     * @return Brief description of the potion's effect
     */
    @Override
    public String getDescription() {
        return "This potion makes you stronger after a short delay";
    }
}
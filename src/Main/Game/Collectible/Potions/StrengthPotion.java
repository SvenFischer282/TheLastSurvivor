package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A temporary strength-boosting potion that increases player damage after a delay.
 * Implements a timed effect using a scheduled executor service.
 */
public class StrengthPotion extends Potion implements Collectible {

    // Scheduler for delayed effect activation
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

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
        // Schedule the strength boost after 10 seconds
        scheduler.schedule(() -> {
            player.setDamage(player.getDamage() + this.getEffectStrength());
            System.out.println("Strength increased by " + this.getEffectStrength());
        }, 10, TimeUnit.SECONDS);

        // Apply initial penalty
        player.setDamage(player.getDamage() - this.getEffectStrength());
    }

    /**
     * @return Brief description of the potion's effect
     */
    @Override
    public String getDescription() {
        return "This potion makes you stronger after a short delay";
    }
}
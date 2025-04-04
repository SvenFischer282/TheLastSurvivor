package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Game.Collectible.Item;
import Main.Game.Inventory;

/**
 * Represents a potion item in the game that can be collected and used.
 * Serves as the base class for different potion types with various effects.
 * Extends Item and implements Collectible interface.
 */
public class Potion extends Item implements Collectible {
    /**
     * Enum defining available potion types and their purposes.
     */
    public enum PotionType {
        /** Restores player health */ HEAL,
        /** Temporarily increases attack power */ STRENGTH,
        /** Base potion with no effect (used for brewing) */ PLAIN
    }

    private int effectStrength;
    private PotionType type;

    /**
     * Creates a new plain potion at specified coordinates.
     * @param x X position in game world
     * @param y Y position in game world
     * @param effectStrength Base potency for derived potion effects
     */
    public Potion(int x, int y, int effectStrength) {
        super(x, y, "Plain potion");
        this.effectStrength = effectStrength;
        this.type = PotionType.PLAIN;
    }

    /**
     * Activates the potion's effect on the player.
     * Base implementation only displays usage message.
     * @param player The player character using the potion
     */
    @Override
    public void use(Player player) {
        System.out.println("You used a plain potion");
    }

    /**
     * Adds this potion to a player's inventory when collected.
     * @param inventory Target inventory for collection
     */
    public void collect(Inventory inventory) {
        inventory.addPotion(this);
    }

    /**
     * @return  Text describing the potion's properties
     */
    public String getDescription() {
        return "This potion is used for brewing potions. It does not have any effects on its own";
    }

    // Basic property accessors
    public int getEffectStrength() { return effectStrength; }
    public void setEffectStrength(int effectStrength) { this.effectStrength = effectStrength; }
    public PotionType getType() { return this.type; }
    public void setType(PotionType type) { this.type = type; }
}
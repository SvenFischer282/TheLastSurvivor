package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Utils.Exceptions.NegativeValueException;

/**
 * A healing potion that restores player health when used.
 * Extends the base Potion class with HEAL type functionality.
 */
public class HealPotion extends Potion implements Collectible {

    /**
     * Creates a new healing potion at specified coordinates.
     * @param x Horizontal position in the game world
     * @param y Vertical position in the game world
     * @param effectStrenght Amount of health to restore when used
     */
    public HealPotion(int x, int y, int effectStrenght) {
        super(x, y, effectStrenght);
        this.setType(PotionType.HEAL);
    }

    /**
     * Applies the healing effect to the player.
     * Restores health equal to the potion's effect strength.
     * @param player The player character to heal
     */
    @Override
    public void use(Player player){
        try  {
            player.heal(this.getEffectStrength());
            System.out.println("Healed " + this.getEffectStrength());
        } catch (NegativeValueException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @return Brief description of the healing effect
     */
    @Override
    public String getDescription(){
        return "This potion heals you";
    }
}
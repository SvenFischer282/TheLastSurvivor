package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;
import Main.Game.Collectible.Item;
import Main.Game.Inventory;

public class Potion extends Item implements Collectible {
   public enum PotionType{
        HEAL,
        STRENGTH,
       PLAIN
    }

    private int effectStrength;
    private PotionType type;
    public Potion(int x, int y,int effectStrength) {
        super(x, y, "Plain potion");
        this.effectStrength = effectStrength;
        this.type = PotionType.PLAIN;
    }
    @Override
    public void use(Player player) {
        System.out.println("You used a plain potion");
    }
    public void collect(Inventory inventory) {
        inventory.addPotion(this);
    }
    public String getDescription(){
        return "This potion is used for brewing potions. It does not have any effects on its own ";
    }

    public int getEffectStrength() {
        return effectStrength;
    }

    public void setEffectStrength(int effectStrength) {
        this.effectStrength = effectStrength;
    }

    public PotionType getType() {
        return this.type;
    }

    public void setType(PotionType type) {
        this.type = type;
    }
}

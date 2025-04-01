package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;

public class HealPotion extends Potion implements Collectible {
    public HealPotion(int x, int y, int effectStrenght) {
        super(x, y, effectStrenght);
        this.setType(PotionType.HEAL);
    }

    @Override
    public void use(Player player){
        player.heal(this.getEffectStrength());
        System.out.println("Healed " + this.getEffectStrength());
    }
    @Override
    public String getDescription(){
        return "This potion heals you";
    }

}

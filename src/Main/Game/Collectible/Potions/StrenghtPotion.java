package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;

public class StrenghtPotion extends Potion implements Collectible {
    public StrenghtPotion(int x, int y, int effectStrength) {
        super(x, y, effectStrength);
        this.setType(PotionType.STRENGTH);
    }
    @Override
    public void use(Player player){
        player.setDamage(player.getDamage()+this.getEffectStrength());
        System.out.println("Strenght increased by " + this.getEffectStrength());
    }


}

package Main.Game.Collectible.Potions;

import Main.Game.Character.Player;
import Main.Game.Collectible.Collectible;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StrenghtPotion extends Potion implements Collectible {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public StrenghtPotion(int x, int y, int effectStrength) {
        super(x, y, effectStrength);
        this.setType(PotionType.STRENGTH);
    }
    @Override
    public void use(Player player){
        scheduler.schedule(()->{
            player.setDamage(player.getDamage()+this.getEffectStrength());
            System.out.println("Strenght increased by " + this.getEffectStrength());
        },15, TimeUnit.SECONDS);
        player.setDamage(player.getDamage()-this.getEffectStrength());


    }
    @Override
    public String getDescription(){
        return "This potion makes you stronger";
    }


}

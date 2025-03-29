package Main.Game.Weapons;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Main.Game.Character.Player;
public class Gun extends Weapon {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Player player;
    private boolean canShoot;
    public Gun(int damage, Player player ) {
        super(damage);
        this.player = player;
        canShoot = true;
    }

    public void shoot(  int target_x, int target_y) {
        if (canShoot){
            canShoot = false;
            System.out.println("Bang!!");
            scheduler.schedule(()-> {
                System.out.println("reloaded");
                canShoot = true;
            },1000,TimeUnit.MILLISECONDS);

        }

    }
}

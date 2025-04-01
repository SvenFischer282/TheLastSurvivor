package Main.Game.Character;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Enemy extends Character {
    int damage;
    float speed;
    boolean ableToHit;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    public Enemy(int health, int positionX, int positionY,int damage ) {
        super(health, positionX, positionY);
        this.damage = damage;
        this.speed = 500f;
        this.ableToHit = true;
    }
    @Override
    public void update(float deltaTime, Player player){
        moveToPlayer(player);
    }
    void moveToPlayer(Player player) {
        float hitboxRadius = 32;
        float dx = Math.abs( this.getX() - player.getX());
        float dy = Math.abs( this.getY() - player.getY());
        float distance = (float) Math.sqrt(dx*dx + dy*dy);
        if (distance < hitboxRadius) {
            if (ableToHit) {
                attackPlayer(player);
            }

        }
    }
    void attackPlayer(Player player) {
            System.out.println("Player was attacked!");
            ableToHit = false;
            scheduler.schedule(() -> ableToHit = true, 5, TimeUnit.SECONDS);
    }
}

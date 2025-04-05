package Main.Game.Character;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Represents an enemy character in the game.
 * Enemies move towards the player (to be implemented)
 * and attack when within range. They have a cooldown period after attacking.
 * Extends the base {@link Character} class.
 */
public class Enemy extends Character {

    /** The movement speed of the enemy (currently unused ). */
    float speed;

    /** Flag indicating if the enemy is currently capable of attacking. */
    boolean ableToHit;

    /** Scheduler service to handle the attack cooldown timing. */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Constructs a new Enemy instance.
     *
     * @param health     The initial health points of the enemy.
     * @param positionX  The initial X coordinate of the enemy.
     * @param positionY  The initial Y coordinate of the enemy.
     * @param damage     The amount of damage the enemy inflicts per attack.
     */
    public Enemy(int health, int positionX, int positionY, int damage) {
        super(health, positionX, positionY, damage);
        this.speed = 500f;
        this.ableToHit = true;
    }

    /**
     * Updates the enemy's state, primarily checking for player proximity to attack.
     * @param deltaTime The time elapsed since the last update .
     * @param player    A reference to the player character for interaction.
     */
    @Override
    public void update(float deltaTime, Player player) {

        moveToPlayer(player,deltaTime);

    }

    /**
     * Move the enemy towards the player
     * Checks the distance to the player and initiates an attack if within range and ready.
     * @param player The Player object to check the distance against.
     * @param deltaTime The time elapsed from the last update
     */
    void moveToPlayer(Player player, float deltaTime) {
        float hitboxRadius = 32;
        float speed = 100.0f; // Movement speed in units per second

        // Calculate direction vector
        float dx = player.getX() - this.getX();
        float dy = player.getY() - this.getY();

        // Normalize the direction vector (convert to unit vector)
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance > 0) {
            dx /= distance;
            dy /= distance;
        }

        // Move toward player
        this.setPositionX(this.getX() + (dx * speed * deltaTime));
        this.setPositionY(this.getY() + (dy * speed * deltaTime));

        // Check collision after movement
        float newDistanceX = Math.abs(this.getX() - player.getX());
        float newDistanceY = Math.abs(this.getY() - player.getY());
        float newDistance = (float) Math.sqrt(newDistanceX * newDistanceX + newDistanceY * newDistanceY);

        if (newDistance < hitboxRadius && ableToHit) {
            attackPlayer(player);
        }
    }

    /**
     * Performs an attack on the specified player and starts the attack cooldown.
     * Inflicts damage, prevents immediate re-attacking, and schedules the attack
     * availability to be restored after a delay.
     *
     * @param player The Player object to attack.
     */
    void attackPlayer(Player player) {
        System.out.println("Player was attacked by Enemy!");
        player.takeDamage(this.getDamage());
        ableToHit = false;


        scheduler.schedule(() -> {
            ableToHit = true;
            System.out.println("Enemy attack ready again.");
        }, 5, TimeUnit.SECONDS);
    }

    /**
     * Function to shut down the scheduler when the enemy is killed
     */
     public void cleanup() {
         scheduler.shutdown();
    }
}
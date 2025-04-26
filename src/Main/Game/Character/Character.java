package Main.Game.Character;

/**
 * Abstract class representing a game character.
 */
public abstract class Character {
    private int health;
    private float positionX;
    private float positionY;
    private int damage;

    /**
     * Constructor for Character.
     * @param health Initial health of the character.
     * @param positionX Initial X position of the character.
     * @param positionY Initial Y position of the character.
     * @param damage Damage the character can deal.
     */
    public Character(int health, int positionX, int positionY, int damage) {
        this.health = health;
        this.positionX = positionX;
        this.positionY = positionY;
        this.damage = damage;
    }

    /**
     * Gets the character's health.
     * @return Current health.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Sets the character's health.
     * @param health New health value.
     */
    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Reduces character's health by damage amount, ensuring health doesn't go below 0.
     * @param damage Amount of damage to take.
     */
    public void takeDamage(int damage) {
        if (this.health - damage > 0) {
            this.health -= damage;
        } else {
            this.health = 0;
        }
    }

    /**
     * Gets the character's X position.
     * @return Current X position.
     */
    public float getX() {
        return positionX;
    }

    /**
     * Updates the character's state (empty implementation).
     * @param delta Time since last update.
     */
    public void update(float delta) {
        ;
    }

    /**
     * Sets the character's X position.
     * @param positionX New X position.
     */
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    /**
     * Gets the character's Y position.
     * @return Current Y position.
     */
    public float getY() {
        return positionY;
    }

    /**
     * Sets the character's Y position.
     * @param positionY New Y position.
     */
    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    /**
     * Gets the character's damage value.
     * @return Current damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Sets the character's damage value.
     * @param damage New damage value.
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Abstract method to update character state based on time and player.
     * @param deltaTime Time since last update.
     * @param player Reference to the player.
     */
    public abstract void update(float deltaTime, Player player);
}
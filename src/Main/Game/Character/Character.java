package Main.Game.Character;

public abstract class Character {
    private int health;
    private float positionX;
    private float positionY;
    private int damage;

    public Character(int health, int positionX, int positionY,int damage) {
        this.health = health;
        this.positionX = positionX;
        this.positionY = positionY;
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void takeDamage(int damage){
        if (this.health - damage > 0){
            this.health -= damage;
        }else {
            this.health = 0;
        }
    }

    public float getX() {
        return positionX;
    }
   public void update(float delta){
        ;
   }
    public void setPositionX(float positionX) {
        this.positionX = positionX;
    }

    public float getY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public abstract void update(float deltaTime, Player player);
}


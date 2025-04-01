package Main.Game.Character;

public class Enemy extends Character {
    int damage;
    float speed;
    public Enemy(int health, int positionX, int positionY,int damage ) {
        super(health, positionX, positionY);
        this.damage = damage;
        this.speed = 500f;
    }
    @Override
    public void update(float deltaTime, Player player){
        moveToPlayer(player);
    }
    void moveToPlayer(Player player) {
        if(this.getX()>=(player.getX()-10) &&this.getX()<=(player.getX()+10) &&this.getY()>=(player.getY()-10)&&this.getY()<=(player.getY()+10)) {
            attackPlayer(player);
        }
        }
    void attackPlayer(Player player) {
            player.setHealth(player.getHealth()-damage);
    }
}

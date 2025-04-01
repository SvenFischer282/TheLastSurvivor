package Main.Game.Collectible;


import Main.Game.Character.Player;

public class GoldCoin extends Coins implements Collectible {
    public GoldCoin(int x, int y, String name) {
        super(x, y, "Gold coin");
    }
    @Override
    public void collect(Player player ) {
        System.out.println("Coins collected");
        player.addScore(5);
    }
}

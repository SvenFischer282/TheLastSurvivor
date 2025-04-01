package Main.Game;

import Main.Game.Collectible.Potions.Potion;
import Main.Game.Character.Player;
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Potion> items;
    private final Player player;

    public Inventory(Player player) {
        this.items = new ArrayList<>();
        this.player = player;
    }

    // Pridanie potionu do inventára
    public void addPotion(Potion potion) {
        items.add(potion);
        System.out.println("Added " + potion.getClass().getSimpleName() + " to inventory.");
    }

    // Použitie potionu podľa typu
    public boolean usePotion(Potion.PotionType type) {
        for (int i = 0; i < items.size(); i++) {
            Potion potion = items.get(i);
            if (potion.getType() == type) {
                potion.use(player); // Zavolá špecifickú metódu use pre daný potion
                items.remove(i);   // Odstráni použitý potion
                return true;
            }
        }
        System.out.println("No " + type + " potion available!");
        return false;
    }

    // Zobrazenie inventára
    public void showInventory() {
        System.out.println("Inventory:");
        if (items.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Potion potion : items) {
                System.out.println(potion.getClass().getSimpleName() + " (Type: " + potion.getType() + ", Effect: " + potion.getEffectStrength() + ")");
            }
        }
    }

    // Getter pre počet potionov daného typu
    public int getPotionCount(Potion.PotionType type) {
        int count = 0;
        for (Potion potion : items) {
            if (potion.getType() == type) {
                count++;
            }
        }
        return count;
    }
}
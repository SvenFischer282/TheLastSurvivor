package Main.Game;

import Main.Game.Collectible.Potions.Potion;
import Main.Game.Character.Player;
import Main.Utils.Observer.GameStateObserver;
import Main.Utils.Observer.GameStateSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;/**
 * Represents a player's inventory system that stores and manages potions.
 */
public class Inventory implements GameStateSubject {
    /**
     * The list of potions currently in the inventory.
     */
    private List<Potion> items;
    private List<GameStateObserver> observers = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(Inventory.class);

    /**
     * The player who owns this inventory.
     */
    private final Player player;

    /**
     * Constructs a new Inventory for the specified player.
     * @param player The player who owns this inventory
     */
    public Inventory(Player player) {
        this.items = new ArrayList<>();
        this.player = player;
    }

    /**
     * Add observer to the list of observers
     * @param observer observer we want to add
     */

    @Override
    public void addObserver(GameStateObserver observer) {
        observers.add(observer);
    }

    /**
     * Remove the observer from list
     * @param observer observer we want to remove
     */
    @Override
    public void removeObserver(GameStateObserver observer) {
        observers.remove(observer);
    }

    /**
     * updates every observer in the list
     */
    @Override
    public void notifyObservers() {
        for (GameStateObserver observer : observers) {
            observer.update();
        }
    }

    /**
     * Adds a potion to the inventory.
     * @param potion The potion to add
     */
    public void addPotion(Potion potion) {
        items.add(potion);
        System.out.println("Added " + potion.getClass().getSimpleName() + " to inventory.");
        notifyObservers();
    }

    /**
     * Attempts to use a potion of the specified type from the inventory.
     * @param type The type of potion to use
     * @return true if potion was found and used, false otherwise
     */
    public boolean usePotion(Potion.PotionType type) {
        for (int i = 0; i < items.size(); i++) {
            Potion potion = items.get(i);
            if (potion.getType() == type) {
                potion.use(player);
                items.remove(i);
                logger.info("Potion " + potion.getName() +  " from inventory.");
                notifyObservers();
                return true;
            }
        }
        System.out.println("No " + type + " potion available!");
        return false;
    }

    /**
     * Displays the current contents of the inventory to the console.
     */
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

    /**
     * Counts how many potions of a specific type are in the inventory.
     * @param type The type of potion to count
     * @return The number of potions of the specified type
     */
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
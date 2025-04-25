package Main.Game.Weapons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {
    Weapon weapon;

    @BeforeEach
    void setUp() {
        weapon =new Weapon(1);
    }

    @Test
    void getDamage() {
        assertEquals(1, weapon.getDamage());
    }

    @Test
    void setDamage() {
        weapon.setDamage(2);
        assertEquals(2,weapon.getDamage());
    }
}
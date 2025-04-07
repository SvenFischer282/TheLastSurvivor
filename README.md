# Projekt: Last Survivor

## 1. Meno a priezvisko
**Sven Fischer**

## 2. Názov projektu
**Last Survivor**

## 3. Zámer projektu
Hra *Last Survivor* je akčná hra, kde hráč ovláda postavu pohybujúcu sa po obrazovke plnej vĺn zombie. Hlavným cieľom je prežiť čo najdlhšie, porážať nepriateľov a zbierať mince, ktoré zvyšujú skóre. Hráč využíva klávesy **WASD** na pohyb, myš na streľbu projektilov, **medzerník** na boj mečom a zbiera potiony (napr. na liečenie alebo posilnenie), aby zvýšil svoje šance na prežitie. Zombie neustále prenasledujú hráča a pri kontakte mu uberajú životy, čo ho núti naplno využiť svoje schopnosti.

### Hlavné mechaniky hry
- **Plynulý pohyb**: Ovládanie postavy pomocou WASD.
- **Boj**: Streľba na diaľku (myš) a útok mečom na blízko (medzerník).
- **Zber predmetov**: Potiony (liečenie, sila) ovládané klávesami 1 a 2.
- **Systém skóre**: Založený na zbieraní mincí od porazených nepriateľov.
- **Inventár**: Jednoduchý prehľad potionov a ukazovateľ zostávajúcich životov.
- **Rozmanitosť nepriateľov**:
   - **Štandardní zombie**: Útočia v skupinách.
   - **Pomalí zombie**: Vyššia odolnosť a viac životov.
   - **Rýchli zombie**: Rýchlejší, no s menej životmi.

## 4. Diagram hierarchie tried (dedenia)
![Hierarchia tried](img.png)

## 5. Zhodnotenie OOP princípov

### 1. Dedenie (Inheritance)
- **Hierarchia postáv**:
   - `Character` (základná trieda) → `Player` a `Enemy`
   - `Enemy` → `Zombie` (špeciálny typ nepriateľa)
- **Hierarchia predmetov**:
   - `Item` → `Collectible` → `Potion` → (`HealPotion`, `StrengthPotion`)
   - `Item` → `Collectible` → `Coins` → (`GoldCoin`, `SilverCoin`)

### 2. Zapuzdrenie (Encapsulation)
- **Príklady**:
   - Privátne premenné: `private int health`, `private float x, y`
   - Verejné metódy: `getHealth()`, `setPosition()`
   - Trieda `Inventory` kontroluje prístup k predmetom.

### 3. Polymorfizmus (Polymorphism)
- **Prekonávanie metód**:
   - `Potion.use()`: Rôzne implementácie v `HealPotion` a `StrengthPotion`.
   - `Collectible.collect()`: Odlišné správanie pre `Potion` a `Coins`.
- **Runtime method dispatch**:
   - `Potion.use()` sa volá podľa skutočného typu (`Heal`/`Strength`).
   - `collect()` funguje rozdielne pre `Potion` a `Coins`.

### 4. Abstrakcia (Abstraction)
- **Abstraktné triedy**:
   - `Character`: Spoločné vlastnosti postáv.
   - `Item`: Základ pre herné predmety.
- **Rozhrania**:
   - `Collectible`: Povinné metódy pre zberateľné predmety.

### 5. Rozhrania (Interfaces)
- **Collectible**:
   - Implementované v `Potion` a `Coins` s vlastnou logikou `collect()`.
- **GUI ovládanie**:
   - `PlayerGunController` implementuje `KeyListener` a `MouseListener`.

## 6. Singleton vzor
- **ScoreCounter**:
   - Jediná inštancia cez `getInstance()`.
   - Globálny prístup k hernému skóre.

---

# Záver projektu Last Survivor

## Hlavné princípy OOP
- ✅ **Dedenie**: Logická hierarchia postáv (`Character → Player/Enemy`) a predmetov (`Item → Potion/Coins`).
- ✅ **Zapuzdrenie**: Dáta chránené gettermi a settermi.
- ✅ **Polymorfizmus**: Rovnaké metódy s rôznym správaním (`use()`, `collect()`).

## Výhody implementácie
- ✔ **Jednoduché rozširovanie**: Nové typy postáv/predmetov s minimálnymi úpravami.
- ✔ **Prehľadnosť**: Oddelenie logiky, GUI a ovládania.
- ✔ **Flexibilita**: Dynamické správanie objektov podľa typu.

## Možné vylepšenia
- **Návrhové vzory**: Použitie Factory na tvorbu predmetov.
- **Testovanie**: Rozšírenie unit testov pre kľúčové časti.

## Záverečné hodnotenie
Projekt úspešne aplikuje OOP princípy, čo vedie k:
- **Modulárnemu dizajnu**: Jasne oddelené časti.
- **Udržateľnému kódu**: Ľahko pochopiteľný a upraviteľný.
- **Rozšíriteľnému systému**: Jednoduché pridávanie nových funkcií.

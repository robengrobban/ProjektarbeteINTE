package se.su.dsv.inte.projektarbete.player;

import org.junit.jupiter.api.Test;
import se.su.dsv.inte.projektarbete.ElementType;
import se.su.dsv.inte.projektarbete.armour.Armour;
import se.su.dsv.inte.projektarbete.armour.ArmourType;
import se.su.dsv.inte.projektarbete.armour.SimpleDefenceModifier;
import se.su.dsv.inte.projektarbete.characters.Character;
import se.su.dsv.inte.projektarbete.characters.NonPlayerCharacter;
import se.su.dsv.inte.projektarbete.characters.StateType;
import se.su.dsv.inte.projektarbete.magic.FireSpell;
import se.su.dsv.inte.projektarbete.magic.Spell;
import se.su.dsv.inte.projektarbete.map.Map;
import se.su.dsv.inte.projektarbete.quest.Quest;
import se.su.dsv.inte.projektarbete.quest.QuestManager;
import se.su.dsv.inte.projektarbete.weapon.SimpleDamageModifier;
import se.su.dsv.inte.projektarbete.weapon.Weapon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void newPlayerConstructorSetValuesCorrectly() {
        int expectedLevel = 1;
        int expectedExperience = 0;
        int expectedDefence = 2;
        int expectedAttack = 2;
        int expectedMagicalAttack= 2;
        int expectedMagicalDefence = 2;
        String name = "test";

        Player player = new Player("test") {

            @Override
            public String getName() {
                return super.getName();
            }
            @Override
            public int getTotalHealth() {
                return super.getTotalHealth();
            }
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
            @Override
            public int getExperience() {
                return super.getExperience();
            }
            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        assertEquals(name, player.getName());
        assertEquals(expectedLevel, player.getLevel());
        assertEquals(expectedDefence, player.getDefence());
        assertEquals(expectedMagicalDefence, player.getTotalMagicDefence());
        assertEquals(expectedAttack, player.getTotalAttack());
        assertEquals(expectedMagicalAttack, player.getTotalMagicAttack());
        assertEquals(expectedExperience, player.getExperience());
    }

    @Test
    void emptyNameNotAccepted() {
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player("") {
                @Override
                public String getName() {
                    return super.getName();
                }
            };
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player("   ") {
                @Override
                public String getName() {
                    return super.getName();
                }
            };
        });
        assertThrows(IllegalArgumentException.class, () -> {
            Player player = new Player(null) {
                @Override
                public String getName() {
                    return super.getName();
                }
            };
        });
    }

    @Test
    void playerConstructorSetValuesCorrectly() {
        String name = "test";
        int totalHealth = 100;
        int damage = 40;
        int maxMana = 50;
        final int CURRENT_HEALTH = 60;
        int defence = 20;
        int attack = 25;
        int experience = 200;
        int level = 5;


        Player player = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, null, null, null, null ) {
            @Override
            public String getName() {
                return super.getName();
            }
            @Override
            public int getTotalHealth() {
                return super.getTotalHealth();
            }
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
            @Override
            public int getExperience() {
                return super.getExperience();
            }
            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        assertEquals(name, player.getName());
        assertEquals(level, player.getLevel());
        assertEquals(experience, player.getExperience());
        assertEquals(totalHealth, player.getTotalHealth());
        assertEquals(maxMana, player.getMaxMana());
        assertEquals(CURRENT_HEALTH, player.getCurrentHealth());
    }

    @Test void defenceProtectsAsExpected() {
        int totalHealth = 100;
        int damage = 30;
        int defence = 20;

        Player player = new Player("test", totalHealth, 30, damage,
                defence, 20, 2, 2, 57, 2, null, null, null, null ) {
            @Override
            public boolean damaged(Weapon weapon) {
                return super.damaged(weapon);
            }
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
        };

        //Setup weapon
        int baseDamage = 50;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        Weapon sword = new Weapon("sword", "A sword.", baseDamage, 3, canAttack);

        player.damaged(sword);
        assertEquals(40, player.getCurrentHealth());
    }

    @Test
    void playerHPLeftAfterDamageCorrect() {
        int totalHealth = 100;
        int damage = 40;
        int maxMana = 30;
        int defence = 20;
        int attack = 25;
        int experience = 200;
        int level = 5;

        Player player = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, null, null, null, null ) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
        };

        //Setup weapon
        int baseDamage = 50;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        Weapon sword = new Weapon("sword", "A sword.", baseDamage, 3, canAttack);

        player.damaged(sword);
        assertEquals(30, player.getCurrentHealth());
    }

    @Test
    void playerAliveAfterDamagedLessThanHPLeft() {
        String name = "test";
        int totalHealth = 100;
        int damage = 40;
        int maxMana = 40;
        int defence = 20;
        int attack = 25;
        int experience = 200;
        int level = 5;

        Player player = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, null, null, null, null ) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
        };

        //Setup weapons
        int baseDamage = 21;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        Weapon sword = new Weapon("sword", "A sword.", baseDamage, 3, canAttack);

        int baseDamage2 = 30;
        Weapon sword2 = new Weapon("sword", "A sword.", baseDamage2, 3, canAttack);
        //Setup weapon used by player
        int baseDamage3 = 68;
        Weapon sword3 = new Weapon("sword", "A sword.", baseDamage3, 3, canAttack);

        assertTrue(player.damaged(sword));
        assertTrue(player.damaged(sword2));
        assertTrue(player.damaged(sword3));
    }

    @Test
    void playerDeadAfterDamagedMorOrSameAsHPLeft() {
        String name = "test";
        int totalHealth = 100;
        int maxMana = 50;
        int damage = 40;
        int defence = 20;
        int attack = 25;
        int experience = 200;
        int level = 5;

        Player player1 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, null, null, null, null) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
        };

        Player player2 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, null, null, null, null) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
        };

        Player player3 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, null, null, null, null) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
        };

        //Setup weapons
        int baseDamage = 80;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        Weapon sword = new Weapon("sword", "A sword.", baseDamage, 3, canAttack);

        int baseDamage2 = 81;
        Weapon sword2 = new Weapon("sword", "A sword.", baseDamage2, 3, canAttack);
        //Setup weapon used by player
        int baseDamage3 = 1000000;
        Weapon sword3 = new Weapon("sword", "A sword.", baseDamage3, 3, canAttack);

        //False means dead
        assertFalse(player1.damaged(sword));
        assertFalse(player2.damaged(sword2));
        assertFalse(player3.damaged(sword3));
    }

    @Test
    void enemyDamagedCorrectlyAfterAttack() {

        //Setup player
        int totalHealth = 100;
        int maxMana = 50;
        int damage = 40;
        int defence = 20;
        int attack = 25;
        int experience = 200;
        int level = 5;

        //Setup weapon used by player
        int baseDamage = 10;
        int range = 3;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        Weapon sword = new Weapon("sword", "A sword.", baseDamage, range, canAttack);

        Player player1 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, sword, null, null, null) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
        };

        //Setup enemy
        String enemyName = "test";
        int enemyTotalHealth = 100;
        int enemyMaxMana = 50;
        Character enemy = new Character(enemyName, null, null, enemyTotalHealth, enemyMaxMana) {
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
        };

        //Player damaging enemy
        player1.attack(enemy);

        //Asserting correct damage done.
        assertEquals(65, enemy.getCurrentHealth());
    }

    @Test
    void enemyWithArmourDamagedCorrectlyAfterAttack() {

        //Setup player
        String name = "test";
        int totalHealth = 100;
        int maxMana = 50;
        int damage = 40;
        int defence = 20;
        int attack = 40;
        int experience = 200;
        int level = 5;

        //Setup weapon used by player
        String swordName = "Sword";
        String desc = "A sword.";
        int baseDamage = 20;
        int range = 3;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        Weapon sword = new Weapon(name, desc, baseDamage, range, canAttack);

        Player player1 = new Player(name, totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level, sword, null, null, null) {
            @Override
            public boolean damaged(Weapon sword) {
                return super.damaged(sword);
            }
        };

        //Setup enemy
        String enemyName = "test";
        int enemyTotalHealth = 95;
        int enemyMaxMana = 50;
        Armour enemyArmour = new Armour("armour", "bad armour", ArmourType.LIGHT, 3);
        Character enemy = new Character(enemyName, enemyArmour, null, enemyTotalHealth, enemyMaxMana) {
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
        };

        //Player damaging enemy
        player1.attack(enemy);

        //Asserting correct damage done.
        assertEquals(38, enemy.getCurrentHealth());
    }

    @Test void playerDamagedCorrectlyWithFireSpell() {
        //Setup player
        int totalHealth = 100;
        int maxMana = 50;
        int damage = 40;
        int defence = 20;
        int attack = 25;
        int magicalDefence = 3;
        int experience = 200;
        int level = 5;

        //Setup spell used on the player
        int spellDamage = 10;
        HashSet<ElementType> canAttack = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        FireSpell spell = new FireSpell("firespell" ,"a fire spell", 5 ,5, spellDamage, canAttack);

        Player player = new Player("test", totalHealth, maxMana, damage,
                defence, attack, magicalDefence, 2, experience, level, null, null, null, null) {

        };

        NonPlayerCharacter npc = new NonPlayerCharacter("name", null, null, 50, 100, StateType.HOSTILE);

        Map map = new Map(10,10);
        map.placeCharacter(npc, 0, 0);
        map.placeCharacter(player, 1, 1);
        spell.use(npc, player);

        assertEquals(53, player.getCurrentHealth());
    }

    @Test
    void levelUpDoesNotWorkIfLevelIs20OrAbove() {

        //Setup players
        int totalHealth = 100;
        int damage = 0;
        int maxMana = 50;
        int defence = 20;
        int attack = 25;
        int experience = 200;
        int level1 = 20;
        int level2 = 21;
        int level3 = 1999956;

        Player player1 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level1, null, null, null, null) {
            @Override
            public int getTotalHealth() {
                return super.getTotalHealth();
            }
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
            @Override
            public int getExperience() {
                return super.getExperience();
            }
            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        Player player2 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level2, null, null, null, null) {
            @Override
            public int getTotalHealth() {
                return super.getTotalHealth();
            }
            @Override
            public int getCurrentHealth() {
                return super.getCurrentHealth();
            }
            @Override
            public int getExperience() {
                return super.getExperience();
            }
            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        Player player3 = new Player("test", totalHealth, maxMana, damage,
                defence, attack, 2, 2, experience, level3, null, null, null, null) {
            @Override
            public int getTotalHealth() {
                return super.getTotalHealth();
            }
            @Override
            public int getCurrentHealth() {
                return super.getTotalAttack();
            }
            @Override
            public int getExperience() {
                return super.getDefence();
            }
            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        //Try leveling up players
        player1.levelUp();
        player2.levelUp();
        player3.levelUp();

        //Assert no changes
        assertEquals(100, player1.getTotalHealth());
        assertEquals(25, player1.getTotalAttack());
        assertEquals(20, player1.getDefence());

        assertEquals(100, player2.getTotalHealth());
        assertEquals(25, player2.getTotalAttack());
        assertEquals(20, player2.getDefence());

        assertEquals(100, player3.getTotalHealth());
        assertEquals(25, player3.getTotalAttack());
        assertEquals(20, player3.getDefence());

    }

    @Test
    void addExperienceLevelUpCorrectly() {
        //Setup players
        int experience1 = 10;
        int experience2 = 10;
        int experience3 = 10;
        int level1 = 1;
        int level2 = 1;
        int level3 = 1;

        Player player1 = new Player("test", 10, 0, 10,
                10, 10, 2, 2, experience1, level1, null, null, null, null) {
            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        Player player2 = new Player("test", 10, 0, 10,
                10, 10, 2, 2, experience2, level2, null, null, null, null) {

            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        Player player3 = new Player("test", 10, 0, 10,
                10, 10, 2, 2, experience3, level3, null, null, null, null) {

            @Override
            public int getLevel() {
                return super.getLevel();
            }
        };

        //Adding experience to the players
        player1.addExperience(39);
        player2.addExperience(40);
        player3.addExperience(41);

        //Check correct level up.
        assertEquals(1, player1.getLevel());
        assertEquals(2, player2.getLevel());
        assertEquals(2, player3.getLevel());
    }

    @Test
    void canOnlyAddTenSpells() {
        Player player = new Player("name") {
            @Override
            public boolean addSpell(Spell spell) {
                return super.addSpell(spell);
            }
        };

        for (int i = 1; i <= 10; i++) {
            int range = i;
            int manaCost = 10;
            int damage = 20;
            HashSet<ElementType> canAttack1 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.AIR));
            FireSpell f = new FireSpell("spellname" + i, "firespell", range, manaCost, damage, canAttack1);

            assertTrue(player.addSpell(f));
        }

        int range = 11;
        int manaCost = 10;
        int damage = 20;
        HashSet<ElementType> canAttack1 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.AIR));
        FireSpell f = new FireSpell("spellname" + 11, "firespell", range, manaCost, damage, canAttack1);

        assertFalse(player.addSpell(f));
    }

    @Test
    void cannotAddDuplicateSpells() {
        Player player = new Player("name") {
            @Override
            public boolean addSpell(Spell spell) {
                return super.addSpell(spell);
            }
        };

        int range = 3;
        int manaCost = 13;
        int damage = 25;
        HashSet<ElementType> canAttack1 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.AIR));
        FireSpell f = new FireSpell("spellname", "firespell", range, manaCost, damage, canAttack1);

        assertDoesNotThrow(() -> {
            player.addSpell(f);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            player.addSpell(f);
        });

    }

    @Test void getSpellReturnsCorrectSpell() {
        int range = 3;
        int manaCost = 13;
        int damage = 25;
        HashSet<ElementType> canAttack1 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.AIR));
        FireSpell spell1 = new FireSpell("spellname", "firespell", range, manaCost, damage, canAttack1);

        Player player = new Player("name") {
            @Override
            public boolean addSpell(Spell spell) {
                return super.addSpell(spell);
            }
            @Override
            public Spell getSpell(int index) {
                return super.getSpell(index);
            }
        };

        player.addSpell(spell1);
    }

    @Test void cannotRetrieveSpellOutOfRange() {
        int range = 3;
        int manaCost = 13;
        int damage = 25;
        HashSet<ElementType> canAttack1 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.AIR));
        FireSpell spell = new FireSpell("spellname", "firespell", range, manaCost, damage, canAttack1);

        Player player = new Player("name") {
            @Override
            public boolean addSpell(Spell spell) {
                return super.addSpell(spell);
            }
            @Override
            public Spell getSpell(int index) {
                return super.getSpell(index);
            }
        };

        player.addSpell(spell);

        assertDoesNotThrow(() -> {
            Spell testRetrieveSpell = player.getSpell(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            player.getSpell(-1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.getSpell(-456);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.getSpell(1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            player.getSpell(10);
        });
    }

    @Test void replaceSpellWorksCorrectly() {
        int range = 3;
        int manaCost = 13;
        int damage = 25;
        HashSet<ElementType> canAttack1 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.AIR));
        FireSpell spell1 = new FireSpell("spellname", "firespell", range, manaCost, damage, canAttack1);

        int range2 = 5;
        int manaCost2 = 20;
        int damage2 = 30;
        HashSet<ElementType> canAttack2 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        FireSpell spell2 = new FireSpell("spellname", "firespell", range2, manaCost2, damage2, canAttack2);
        int range3 = 6;
        int manaCost3 = 30;
        int damage3 = 40;
        HashSet<ElementType> canAttack3 = new HashSet<>(Arrays.asList(ElementType.LAND, ElementType.WATER));
        FireSpell spell3 = new FireSpell("spellname", "firespell", range3, manaCost3, damage3, canAttack3);

        Player player = new Player("name") {
            @Override
            public boolean addSpell(Spell spell) {
                return super.addSpell(spell);
            }
            @Override
            public Spell getSpell(int index) {
                return super.getSpell(index);
            }
            @Override
            public void replaceSpell(Spell newSpell, int replacedIndex) {
                super.replaceSpell(newSpell, replacedIndex);
            }
        };

        //Add two spells
        player.addSpell(spell1);
        player.addSpell(spell2);

        //Confirm not equal to spell to replace before replacing.
        assertNotEquals(spell3, player.getSpell(0));
        assertNotEquals(spell3, player.getSpell(1));

        //Replace and assert spell has been replaced.
        player.replaceSpell(spell3, 0);
        assertEquals(spell3, player.getSpell(0));

        //Confirm second spell has not ben replaced.
        assertNotEquals(spell3, player.getSpell(1));
        assertEquals(spell2, player.getSpell(1));
    }




    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * FROM THIS LINE ONLY TESTCASES FROM EQUIVALENCE CLASS PARTITIONING *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    /**
     * Testcase 1
     */
    @Test
    public void testPlayerAttackWithWeaponCaseOne() {
        Weapon weapon = new Weapon("Sword", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.LAND)));

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);

        Character target = new Character("Bob", null, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 2);

        human.attack(target);

        assertEquals( 88, target.getCurrentHealth() );
    }

    /**
     * Testcase 2
     */
    @Test
    public void testPlayerAttackWithWeaponCasetwo() {
        Weapon weapon = new Weapon("Sword", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.LAND)));
        SimpleDamageModifier sdm = new SimpleDamageModifier("More", 10, 2);
        weapon.setModifier(sdm);

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);
        // TODO : FIX PLAYER CLASS


        Armour armour = new Armour("Chestplate", "Rusty", ArmourType.LIGHT, 10);

        Character target = new Character("Bob", armour, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 1);

        human.attack(target);

        assertEquals( 93, target.getCurrentHealth() );
    }

    /**
     * Testcase 3
     */
    @Test
    public void testPlayerAttackWithWeaponCaseThree() {
        Weapon weapon = new Weapon("Sword", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.LAND)));

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);

        Armour armour = new Armour("Chestplate", "Rusty", ArmourType.LIGHT, 4);
        SimpleDefenceModifier sdm = new SimpleDefenceModifier("Meh..", 10, 2);
        armour.setModifier(sdm);

        Character target = new Character("Bob", armour, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 2);

        human.attack(target);

        assertEquals( 94, target.getCurrentHealth() );
    }

    /**
     * Testcase 4
     */
    @Test
    public void testPlayerAttackWithWeaponCaseFour() {
        Weapon weapon = new Weapon("Sword", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.LAND)));
        SimpleDamageModifier sdm = new SimpleDamageModifier("More", 10, 2);
        weapon.setModifier(sdm);

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);
        // TODO : FIX PLAYER CLASS

        Armour armour = new Armour("Chestplate", "Rusty", ArmourType.LIGHT, 5);

        Character target = new Character("Bob", armour, null, 100, 120) {};


        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 1);


        human.attack(target);

        assertEquals( 91, target.getCurrentHealth() );
    }

    /**
     * Testcase 5
     */
    @Test
    public void testPlayerAttackWithWeaponCaseFive() {
        Human human = new Human("Test Subject");

        Character target = new Character("Bob", null, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 2);

        human.attack(target);

        assertEquals( 100, target.getCurrentHealth() );
    }

    /**
     * Testcase 6
     */
    @Test
    public void testPlayerAttackWithWeaponCaseSix() {
        Weapon weapon = new Weapon("Sword", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.LAND)), 0);

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);

        Character target = new Character("Bob", null, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 2);

        human.attack(target);

        assertEquals( 100, target.getCurrentHealth() );
    }

    /**
     * Testcase 7
     */
    @Test
    public void testPlayerAttackWithWeaponCaseSeven() {
        Weapon weapon = new Weapon("Bow... for air only", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.AIR)));

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);

        Character target = new Character("Bob", null, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 2);

        human.attack(target);

        assertEquals( 100, target.getCurrentHealth() );
    }

    /**
     * Testcase 8
     */
    @Test
    public void testPlayerAttackWithWeaponCaseEight() {
        Weapon weapon = new Weapon("Sword", "Shiny...", 10, 2, new HashSet<>(Arrays.asList(ElementType.LAND)));

        Human human = new Human("Test Subject");
        human.setWeapon(weapon);

        Character target = new Character("Bob", null, null, 100, 120) {};

        Map map = new Map(10, 10);

        map.placeCharacter(human, 0, 0);
        map.placeCharacter(target, 0, 3);

        human.attack(target);

        assertEquals( 100, target.getCurrentHealth() );
    }

}
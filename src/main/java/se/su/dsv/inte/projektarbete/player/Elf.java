package se.su.dsv.inte.projektarbete.player;

import se.su.dsv.inte.projektarbete.armour.Armour;
import se.su.dsv.inte.projektarbete.weapon.Weapon;

public class Elf extends Player{

    public Elf(String name) {
        super(name);
    }

    public Elf(String name, int health, int maxMana, int damage, int stamina, int staminaUsed,
               int defence, int attack, int experience, int level, Armour armour, Weapon weapon) {
        super(name, health, maxMana, damage, defence, attack, experience, level, null, null, null);
    }

    public boolean attacked(Weapon weapon) {
        return damaged(weapon.getTotalDamage());
    }
}

package se.su.dsv.inte.projektarbete.weapons;

/**
 * Class that represents an increase in base damage
 */
public class SimpleDamageModifier extends WeaponModifier {

    // Instance variables
    private int damageModifier;

    // Constructors
    public SimpleDamageModifier(String name, int cost, int damageModifier) {
        // TODO: Verify damageModifier
        super(name, cost);
        this.damageModifier = damageModifier;
    }

    // Methods

    /**
     * Get the modifying damage. Should be added to the base damage
     * @return int, modifying damage
     */
    @Override
    public int getBaseDamageModifier() {
        return this.damageModifier;
    }


    /**
     * Determine if two Simple Damage Modifiers are the same
     * @param o Object, other WeaponModifier
     * @return boolean, true if they are the same and false if they are different.
     */
    @Override
    public boolean equals(Object o) {
        if ( o instanceof SimpleDamageModifier ) {
            SimpleDamageModifier other = (SimpleDamageModifier) o;

            return super.equals(other) && this.damageModifier == other.damageModifier;
        }
        else {
            return false;
        }
    }
}

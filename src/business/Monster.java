package business;

public class Monster extends Entities{

    /**
     * Desafió que supondrá el monstruo
     */
    private final String challenge;
    /**
     * Experiencia que conseguiremos después de derrotar al monstruo
     */
    private final int experience;
    /**
     * Iniciativa inicial del monstruo
     */
    private int initiative;
    /**
     * dado de daño del monstruo
     */
    private final String damageDice;
    /**
     * Tipo de daño realizado por el monstruo
     */
    private final String damageType;

    public Monster(String name, String challenge, int experience, int hitPoints, int initiative, String damageDice, String damageType) {
        super(name);
        this.challenge = challenge;
        this.experience = experience;
        setHitPoints(hitPoints);
        this.initiative = initiative;
        this.damageDice = damageDice;
        this.damageType = damageType;
    }

    public String getChallenge() {
        return challenge;
    }

    public int getExperience() {
        return experience;
    }

    public int getInitiative() {
        return initiative;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public String getDamageType() {
        return damageType;
    }
}

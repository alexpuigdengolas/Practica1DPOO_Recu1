package business;

public class Monster extends Entities{

    /**
     * Desafió que supondrá el monstruo
     */
    private String challenge;
    /**
     * Experiencia que conseguiremos después de derrotar al monstruo
     */
    private int experience;
    /**
     * Iniciativa inicial del monstruo
     */
    private int initiative;
    /**
     * dado de daño del monstruo
     */
    private String damageDice;
    /**
     * Tipo de daño realizado por el monstruo
     */
    private String damageType;

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
}

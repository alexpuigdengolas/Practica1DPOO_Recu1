package business.entities.monster;

import business.Char;
import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;
import java.util.Random;

public class Monster extends Entity {

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
    private final int initiative;
    /**
     * dado de daño del monstruo
     */
    private final String damageDice;

    public Monster(String name, String challenge, int experience, int hitPoints, int initiative, String damageDice, String dmgType) {
        super(name);
        this.challenge = challenge;
        this.experience = experience;
        setHitPoints(hitPoints);
        this.initiative = initiative;
        this.damageDice = damageDice;
        this.setDamageType(dmgType);
    }

    public Monster(Monster monster) {
        super(monster.getName());
        this.challenge = monster.challenge;
        this.experience = monster.experience;
        setHitPoints(monster.getHitPoints());
        this.initiative = monster.initiative;
        this.damageDice = monster.damageDice;
        this.setDamageType(monster.getDamageType());
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

    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(12);
        setCurrentInitiative(initiative+dice.throwDice());
    }

    public Char selectCharacterObjective(LinkedList<Char> characters){
        Random random = new Random();
        int objectiveIndex;
        do {
            objectiveIndex = random.nextInt(characters.size());
        }while(characters.get(objectiveIndex).getHitPoints() == 0);
        return characters.get(objectiveIndex);
    }

    @Override
    public int attack(Entity entity, int critical) {
        String auxDamageDice = damageDice.replace("d", "");
        Dice dice = new Dice(Integer.parseInt(auxDamageDice));

        int dmgDone = 0;
        if(critical >= 2) {
            if(critical == 2) {
                dmgDone = dice.throwDice();
                entity.setHitPoints(entity.getHitPoints() - dmgDone);
            }
            else{
                dmgDone = (dice.throwDice() * 2);
                entity.setHitPoints(entity.getHitPoints() - dmgDone);
            }
        }

        if(entity.getHitPoints() < 0){
            entity.setHitPoints(0);
        }

        return dmgDone;
    }
}

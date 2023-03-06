package business.entities.monster;

import business.entities.characters.Char;
import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;
import java.util.Random;

/**
 * Esta clase es util para poder representar los monstruos de nuestro programa
 */
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

    /**
     * Este es el constructor. Nos permite crear monstruos a partir de otros y gestionar su información.
     * @param monster la información para poder crear nuestros monstruos
     */
    public Monster(Monster monster) {
        super(monster.getName());
        this.challenge = monster.challenge;
        this.experience = monster.experience;
        setHitPoints(monster.getHitPoints());
        this.initiative = monster.initiative;
        this.damageDice = monster.damageDice;
        this.setDamageType(monster.getDamageType());
    }

    /**
     * Getter del reto que supone este monstruo
     * @return un string con el reto que supone
     */
    public String getChallenge() {
        return challenge;
    }

    /**
     * Getter de la experiencia que se consigue al derrotar a este monstruo
     * @return la experiencia conseguida al derrotar a este monstruo
     */
    public int getExperience() {
        return experience;
    }

    /**
     * Getter de la iniciativa "inicial" de nuestro monstruo
     * @return la iniciativa
     */
    public int getInitiative() {
        return initiative;
    }

    /**
     * Getter del tipo de dado empleado con este monstruo
     * @return el tipo de dado del monstruo
     */
    public String getDamageDice() {
        return damageDice;
    }

    /**
     * Método para calcular la iniciativa actual de nuestra entidad. Esto permitirá ordenar las entidades
     * por su iniciativa y el orden marcará el orden en el que deben atacar.
     */
    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(12);
        setCurrentInitiative(initiative+dice.throwDice());
    }

    /**
     * Este método permite a los monstruos seleccionar un objetivo para su ataque
     * @param characters el listado de posibles objetivos
     * @return el objetivo del ataque
     */
    public Char selectCharacterObjective(LinkedList<Char> characters){
        Random random = new Random();
        int objectiveIndex;
        do {
            objectiveIndex = random.nextInt(characters.size());
        }while(characters.get(objectiveIndex).getHitPoints() == 0);
        return characters.get(objectiveIndex);
    }

    /**
     * Método que permite a la entidad atacar a otra entidad
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return un entero con el daño hecho por el atacante
     */
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

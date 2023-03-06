package business.entities.characters;

import business.Dice;
import business.entities.Entity;
import business.entities.monster.Monster;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Esta clase nos permite representar una de las multiples clase que pueden representar nuestros personajes (Aventurero)
 */
public class Adventurer extends Char {

    /**
     * Este es el constructor de nuestra clase que recibiendo un Personaje es capaz de generar un aventurero
     * @param aChar la información del personaje con la que podemos crear nuestro aventurero
     */
    public Adventurer(Char aChar) {
        super(aChar.getName(), aChar.getPlayer(), aChar.getXp());
        this.setType("Adventurer");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    /**
     * Método para calcular la vida maxima actual del personaje
     */
    @Override
    public void calcMaxLife() {
        this.setMaxLife(((10 + getBody())*this.getLevel()));
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     */
    @Override
    public void preparationStage() {
        setSpirit(getSpirit()+1);
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     */
    @Override
    public void stopPreparationStage() {
        setSpirit(getSpirit()-1);
    }

    /**
     * Método para calcular la iniciativa actual de nuestra entidad. Esto permitirá ordenar las entidades
     * por su iniciativa y el orden marcará el orden en el que deben atacar.
     */
    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(12);
        this.setCurrentInitiative(getSpirit() + dice.throwDice());
    }

    /**
     * Este método será capaz de implementar una manera en la que se puede seleccionar el objetivo de su ataque
     * @param monsters esta será la lista de posibles objetivos del ataque
     * @return el objetivo del ataque
     */
    @Override
    public Monster selectMonsterObjective(LinkedList<Monster> monsters) {
        Comparator<Monster> hitpointsComparator = new Comparator<Monster>() {
            public int compare(Monster m1, Monster m2) {
                return Integer.compare(m1.getHitPoints(), m2.getHitPoints());
            }
        };

        monsters.sort(hitpointsComparator);
        for (Monster monster : monsters) {
            if (monster.getHitPoints() > 0) {
                return monster;
            }
        }
        return monsters.get(0);
    }

    /**
     * Método que permite a la entidad atacar a otra entidad
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return un entero con el daño hecho por el atacante
     */
    @Override
    public int attack(Entity entity, int critical) {
        Dice dice = new Dice(6);
        int dmgDone = 0;
        if(critical >=2) {
            if(critical == 2){
                dmgDone = dice.throwDice() + this.getBody();
                entity.setHitPoints(entity.getHitPoints() - dmgDone);
            }
            else {
                dmgDone =  (dice.throwDice() + this.getBody()) * 2;
                entity.setHitPoints(entity.getHitPoints() - dmgDone);
            }
        }

        if(entity.getHitPoints() < 0){
            entity.setHitPoints(0);
        }
        return dmgDone;
    }

    /**
     * Es el método que implementa el descanso que deben tener cada personaje
     * @return el entero con el resultado de lo que hayan hecho
     */
    @Override
    public int shortBrake() {
        Dice dice = new Dice(8);
        int healAmount =  dice.throwDice() + this.getMind();
        this.setHitPoints(this.getHitPoints() + healAmount);
        if(this.getHitPoints() > this.getMaxLife()){
            this.setHitPoints(this.getMaxLife());
        }
        return healAmount;
    }
}

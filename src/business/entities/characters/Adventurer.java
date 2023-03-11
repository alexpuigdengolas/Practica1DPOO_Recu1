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
        super(aChar);
        this.setType("Adventurer");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    /**
     * Este es uno de los constructores de esta clase. Este se utilizará para la fase de creación de un personaje nuevo
     * @param name el nombre de nuestro personaje
     * @param player el nombre del jugador que lo ha creado
     * @param level el nivel de nuestro personaje
     * @param body la estadística de corpulencia
     * @param mind la estadística de mentalidad
     * @param spirit la estadística de espiritualidad
     */
    public Adventurer(String name, String player, int level, int body, int mind, int spirit) {
        super(name, player, level);
        this.setBody(body);
        this.setMind(mind);
        this.setSpirit(spirit);
        this.setType("Adventurer");
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     */
    @Override
    public void preparationStage(LinkedList<Char> party) {
        setSpirit(getSpirit()+1);
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     */
    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
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
     * Este método implementará la función de selección de objetivo de los aventureros, donde esté ara daño al
     * monstruo que esté más débil
     * @param entities el listado de entidades que pueden ser los objetivos de un ataque
     * @return la entidad seleccionada como objetivo
     */
    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {

        LinkedList<Entity> monsters = new LinkedList<>();
        for (Entity entity : entities) {
            if (entity instanceof Monster) {
                monsters.add(entity);
            }
        }

        Comparator<Entity> hitpointsComparator = new Comparator<Entity>() {
            public int compare(Entity m1, Entity m2) {
                return Integer.compare(m1.getHitPoints(), m2.getHitPoints());
            }
        };

        monsters.sort(hitpointsComparator);
        for (Entity monster : monsters) {
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
            }
            else {
                dmgDone =  (dice.throwDice() + this.getBody()) * 2;
            }
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

    /**
     * Este método servirá para poder gestionar que los personajes al alcanzar un nivel evolucionen
     * @return el personaje evolucionado
     */
    @Override
    public Char levelUp() {
        if(getLevel() >= 4){
            return new Warrior(this);
        }
        return super.levelUp();
    }
}

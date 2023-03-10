package business.entities;

import business.Dice;

import java.util.LinkedList;

/**
 * Esta clase sirve para poder representar cualquier tipo de personaje o monstruo que puedan haber en un combate
 */
public abstract class Entity {

    /**
     * El nombre de la entidad
     */
    private final String name;

    /**
     * La iniciativa actual de la entidad
     */
    private int currentInitiative;

    /**
     * La vida de la entidad
     */
    private int hitPoints;

    /**
     * El tipo de daño que ara la entidad
     */
    private String damageType;

    /**
     * Este es el constructor de la entidad
     * @param name el nombre de la entidad que queremos representar
     */
    public Entity(String name) {
        this.name = name;
    }

    /**
     * Getter del nombre que tiene esa entidad
     * @return el nombre de la entidad
     */
    public String getName() {
        return name;
    }

    /**
     * Setter de la vida de la entidad
     * @param hitPoints la vida de la entidad
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     * Getter de la iniciativa actual de la entidad
     * @return la iniciativa actual
     */
    public int getCurrentInitiative() {
        return currentInitiative;
    }

    /**
     * Setter de la iniciativa actual de la entidad
     * @param currentInitiative la iniciativa actual
     */
    public void setCurrentInitiative(int currentInitiative) {
        this.currentInitiative = currentInitiative;
    }

    /**
     * Getter de la vida de la entidad
     * @return la vida de la entidad
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * Método para calcular la iniciativa actual de nuestra entidad. Esto permitirá ordenar las entidades
     * por su iniciativa y el orden marcará el orden en el que deben atacar.
     */
    public void calculateCurrentInitiative() {
    }

    /**
     * Método que permite a la entidad atacar a otra entidad
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return un entero con el daño hecho por el atacante
     */
    public int attack(Entity entity, int critical){
        return 0;
    }

    /**
     * Getter del tipo de daño hecho por la entidad
     * @return el tipo de daño hecho por la entidad
     */
    public String getDamageType() {
        return damageType;
    }

    /**
     * Setter del tipo de daño hecho por la entidad
     * @param damageType el daño hecho por la entidad
     */
    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    /**
     * Método que permite saber si el ataque de la entidad es crítico
     * @return un entero que indica si el ataque es crítico, falla o es normal
     */
    public int isCriticalDmg() {
        Dice attack = new Dice(10);
        int num = attack.throwDice();
        if(num == 10){
            return 3;
        }else if(num > 1){
            return 2;
        }else{
            return 1;
        }
    }

    /**
     * Este método se usará para que esta entidad pueda escoger un objetivo para su ataque
     * @param entities el listado de entidades que pueden ser los objetivos de un ataque
     * @return el objetivo de dicho ataque
     */
    public Entity selectObjective(LinkedList<Entity> entities){
        return null;
    }

    /**
     * Este método ara que los personajes reciban daño sabiendo que con el tipo de daño hecho también influencia en el
     * daño recibido hemos decidido enviarlo como parámetro.
     * @param dmgDone la cantidad de daño recibido
     * @param damageType el tipo de daño recibido
     */
    public void getDamaged(int dmgDone, String damageType){
        this.hitPoints -= dmgDone;
        if(this.hitPoints < 0){
            this.hitPoints = 0;
        }
    }
}

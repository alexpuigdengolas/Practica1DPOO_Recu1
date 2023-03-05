package business.entities;

import business.Dice;

/**
 * Esta clase sirve para poder representar cualquier tipo de personaje o monstruo que puedan haber en un combate
 */
public class Entity {
    private final String name;
    private int currentInitiative;
    private int hitPoints;

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

    public int getCurrentInitiative() {
        return currentInitiative;
    }

    public void setCurrentInitiative(int currentInitiative) {
        this.currentInitiative = currentInitiative;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void calculateCurrentInitiative() {
    }

    public int attack(Entity entity, int critical){
        return 0;
    }

    public String getDamageType() {
        return damageType;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

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
}

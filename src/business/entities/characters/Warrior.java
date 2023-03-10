package business.entities.characters;

import business.Dice;
import business.entities.Entity;

/**
 * Esta clase nos será util para poder representar a la clase personajes que son guerreros
 */
public class Warrior extends Adventurer{

    /**
     * Este será el constructor de nuestra clase. Nos permitirá crear un guerrero a partir de otro personaje
     * @param aChar Este será el personaje original del que crearemos el nuestro
     */
    public Warrior(Char aChar) {
        super(aChar);
        this.setType("Warrior");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    /**
     * Método que permite a la entidad atacar a otra entidad
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return la cantidad de ataque generado por esta acción
     */
    @Override
    public int attack(Entity entity, int critical) {
        Dice dice = new Dice(10);
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
     * Este método ara que los personajes reciban daño sabiendo que con el tipo de daño hecho también influencia en el
     * daño recibido hemos decidido enviarlo como parámetro. Este se verá reducido si el ataque es del mismo tipo que
     * el del personaje.
     * @param dmgDone la cantidad de daño recibido
     * @param damageType el tipo de daño recibido
     */
    @Override
    public void getDamaged(int dmgDone, String damageType) {
        if(damageType.equals(this.getDamageType())){
            dmgDone /= 2;
        }
        setHitPoints(getHitPoints() - (dmgDone)/2);
        if(getHitPoints() < 0){
            setHitPoints(0);
        }
    }

    /**
     * Este método sirve para poder evolucionar de personaje al subir a cierto nivel
     * @return el nuevo personaje al que evoluciona el personaje actual (Warrior -> Champion)
     */
    @Override
    public Char levelUp() {
        if(getLevel() >= 8){
            return new Champion(this);
        }
        return super.levelUp();
    }
}

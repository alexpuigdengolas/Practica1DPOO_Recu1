package business.entities.characters;

import business.Dice;
import business.entities.Entity;
import business.entities.monster.Monster;

import java.util.LinkedList;
import java.util.Random;

/**
 * Esta clase nos será util para poder representar a la clase personajes que son magos
 */
public class Mage extends Char{

    /**
     * El elemento de escudo es propio de los magos. Esta variable representa la vida sumada que tienen nuestro mago.
     * Siempre que nos ataquen, los primeros que se verán afectados son los escudos
     */
    private int shield = 0;

    /**
     * Este es uno de los constructores de esta clase. Este se utilizará para la fase de creación de un personaje nuevo
     * @param name el nombre de nuestro personaje
     * @param player el nombre del jugador que lo ha creado
     * @param level el nivel de nuestro personaje
     * @param body la estadística de corpulencia
     * @param mind la estadística de mentalidad
     * @param spirit la estadística de espiritualidad
     */
    public Mage(String name, String player, int level, int body, int mind, int spirit) {
        super(name, player, level);
        this.setBody(body);
        this.setMind(mind);
        this.setSpirit(spirit);
        this.setType("Mage");
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Magical");
    }

    /**
     * Este será el otro constructor de nuestra clase. Nos permitirá crear un mago a partir de otro personaje
     * @param aChar Este será el personaje original del que crearemos el nuestro
     */
    public Mage(Char aChar) {
        super(aChar);
        this.setType("Mage");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Magical");
    }

    /**
     * Este será el getter del escudo
     * @return el valor actual del escudo
     */
    public int getShield() {
        return shield;
    }

    /**
     * Este método servirá para impelementar el metodo del cálculo de iniciativa el cual usa un dado de 20 caras
     */
    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(20);
        this.setCurrentInitiative(getMind() + dice.throwDice());
    }

    /**
     * Este método servirá para poder implementar la fase de preparación de nuestros magos.
     * Esta regenerará el escudo siguiendo una fórmula específica.
     * @param party es el grupo de personajes que acompañaran a nuestro mago
     */
    @Override
    public void preparationStage(LinkedList<Char> party) {
        Dice dice = new Dice(6);
        shield = (dice.throwDice() + getMind()) * getLevel();
    }

    /**
     * Este método servirá para poder eliminar los efectos generados por la fase de preparación
     * @param party es el grupo de personajes que acompañan a nuestro mago
     */
    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        shield = 0;
    }

    /**
     * Este método servirá para poder implementar el ataque de nuestros magos. Estos atacarán en área o con un objetivo
     * preciso dependiendo de la cantidad de enemigos que haya en el combate.
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return la cantidad de ataque generado por esta acción
     */
    @Override
    public int attack(Entity entity, int critical) {
        Dice dice;
        if(entity == null){
            dice = new Dice(4);
        }else{
            dice = new Dice(6);
        }
        return dice.throwDice() + getMind();
    }

    /**
     * Este método servirá para poder escoger el objetivo que tendrá nuestro ataque. Los magos atacarán en área (retorna null) o con un objetivo
     * preciso dependiendo de la cantidad de enemigos que haya en el combate.
     * @param entities el listado de entidades que pueden ser los objetivos de un ataque
     * @return el objetivo del ataque, retorna null si el ataque es en area
     */
    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {
        LinkedList<Entity> monsters = new LinkedList<>();

        for (Entity entity : entities) {
            if (entity instanceof Monster) {
                monsters.add(entity);
            }
        }

        if(monsters.size() < 3){
            Random random = new Random();
            return monsters.get(random.nextInt(monsters.size()));
        }
        return null;
    }

    /**
     * Este método servirá para poder recibir daño de parte de un ataque enemigo
     * @param dmgDone la cantidad de daño recibido
     * @param damageType el tipo de daño recibido
     */
    @Override
    public void getDamaged(int dmgDone, String damageType) {
        if(shield > 0) {
            shield -= dmgDone;
            if (shield < 0) {
                setHitPoints(getHitPoints() + shield);
                shield = 0;
            }
        }else if(shield == 0){
            setHitPoints(getHitPoints()-dmgDone);
        }
    }
}

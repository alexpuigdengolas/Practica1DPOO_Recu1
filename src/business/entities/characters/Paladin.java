package business.entities.characters;

import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;
import java.util.Random;

/**
 * Esta clase nos será util para poder representar a la clase personajes que son paladines
 */
public class Paladin extends Cleric{

    /**
     * Aquí guardaremos la cantidad de puntos de mentalidad dados en la fase de preparación
     */
    private int mindAmount;

    /**
     * Este será el constructor de nuestra clase. Nos permitirá crear un paladin a partir de otro personaje
     * @param character Este será el personaje original del que crearemos el nuestro
     */
    public Paladin(Char character) {
        super(character);
        this.setType("Paladin");
        this.setBody(character.getBody());
        this.setMind(character.getMind());
        this.setSpirit(character.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Psychical");
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     * @param party es el grupo de personas que va a acompañar a nuestros personajes
     */
    @Override
    public void preparationStage(LinkedList<Char> party) {
        Dice dice = new Dice(3);
        mindAmount = dice.throwDice();
        for (Char aChar : party) {
            aChar.addMindPoints(mindAmount);
        }
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     * @param party es el grupo de personas que va a acompañar a nuestros personajes
     */
    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.addMindPoints(-mindAmount);
        }
    }

    /**
     * Método que permite a la entidad atacar a otra entidad
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return un entero con el daño hecho por el atacante
     */
    @Override
    public int attack(Entity entity, int critical) {
        if(entity == null){
            Dice dice = new Dice(10);
            return dice.throwDice() + getSpirit();
        }else{
            Dice dice = new Dice(8);
            int dmgDone = dice.throwDice() + getSpirit();
            if(critical == 3){
                dmgDone *= 2;
            }else if(critical == 1){
                dmgDone = 0;
            }
            return dmgDone;
        }
    }

    /**
     * Este método se usará para que esta entidad pueda escoger un objetivo para su ataque
     * @param entities el listado de entidades que pueden ser los objetivos de un ataque
     * @return el objetivo de dicho ataque
     */
    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {
        LinkedList<Char> characters = new LinkedList<>();
        LinkedList<Entity> monsters = new LinkedList<>();

        for (Entity entity : entities) {
            if(entity.getHitPoints() > 0) {
                if (entity instanceof Char) {
                    characters.add((Char) entity);
                } else {
                    monsters.add(entity);
                }
            }
        }

        for(Char character: characters){
            if(character.getHitPoints() < character.getMaxLife()/2){
                return null;
            }
        }

        if(monsters.size() == 0){
            return null;
        }else {
            Random random = new Random();
            int num = random.nextInt(monsters.size());
            return monsters.get(num);
        }
    }

    /**
     * Es el método que implementa el descanso que deben tener cada personaje
     * @return el entero con el resultado de lo que hayan hecho
     */
    @Override
    public int shortBrake() {
        Dice dice = new Dice(10);
        int healing = dice.throwDice() + getSpirit();
        heal(healing);
        return healing;
    }

    /**
     * Este método ara que los personajes reciban daño sabiendo que con el tipo de daño hecho también influencia en el
     * daño recibido hemos decidido enviarlo como parámetro. Esta clase reducirá el daño de tipo Psíquico
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
}

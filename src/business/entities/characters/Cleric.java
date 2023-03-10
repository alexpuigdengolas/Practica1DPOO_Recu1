package business.entities.characters;

import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;
import java.util.Random;
/**
 * Esta clase nos será util para poder representar a la clase personajes que son clérigos
 */
public class Cleric extends Char{

    /**
     * Este es uno de los constructores de esta clase. Este se utilizará para la fase de creación de un personaje nuevo
     * @param name el nombre de nuestro personaje
     * @param player el nombre del jugador que lo ha creado
     * @param level el nivel de nuestro personaje
     * @param body la estadística de corpulencia
     * @param mind la estadística de mentalidad
     * @param spirit la estadística de espiritualidad
     */
    public Cleric(String name, String player, int level, int body, int mind, int spirit) {
        super(name, player, level);
        this.setBody(body);
        this.setMind(mind);
        this.setSpirit(spirit);
        this.setType("Cleric");
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Psychical");
    }

    /**
     * Este será el otro constructor de nuestra clase. Nos permitirá crear un clérigo a partir de otro personaje
     * @param aChar Este será el personaje original del que crearemos el nuestro
     */
    public Cleric(Char aChar) {
        super(aChar);
        this.setType("Cleric");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Psychical");
    }

    /**
     * Método para calcular la iniciativa actual de nuestra entidad. Esto permitirá ordenar las entidades
     * por su iniciativa y el orden marcará el orden en el que deben atacar.
     */
    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(10);
        this.setCurrentInitiative(getSpirit() + dice.throwDice());
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     */
    @Override
    public void preparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.addMindPoints(1);
        }
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     */
    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.addMindPoints(-1);
        }
    }

    /**
     * Método que permite a la entidad atacar a otra entidad
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return la cantidad de ataque generado por esta acción
     */
    @Override
    public int attack(Entity entity, int critical) {
        if(entity instanceof Char){
            Dice dice = new Dice(10);
            return dice.throwDice() + getSpirit();
        }else{
            Dice dice = new Dice(4);
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
                return character;
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
     * Este método sirve para poder evolucionar de personaje al subir a cierto nivel
     * @return el nuevo personaje al que evoluciona el personaje actual (Cleric -> Paladin)
     */
    @Override
    public Char levelUp() {
        if(getLevel() >= 5){
            return new Paladin(this);
        }
        return super.levelUp();
    }
}

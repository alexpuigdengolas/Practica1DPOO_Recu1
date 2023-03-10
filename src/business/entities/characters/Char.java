package business.entities.characters;

import business.Dice;
import business.entities.Entity;
import business.entities.monster.Monster;

import java.util.LinkedList;

/**
 * Esta clase representa a todos los personajes que queramos crear durante el funcionamiento del código
 * Esta clase extiende de la clase Entity para poder aplicar ciertas funciones y servirá para poder
 * extender de ella y sacar distintos tipos de personajes
 */
public class Char extends Entity {
    /**
     * Estas serán las variables que representaran a nuestros personajes
     */
    private final String player; //El nombre del jugador
    private int xp; //La experiencia de nuestro personaje
    private int body; //El valor de cuerpo de nuestro personaje
    private int mind; //El valor de mente de nuestro personaje
    private int spirit; //El valor de espíritu de nuestro personaje
    private int maxLife; //La vida maxima que puede tener nuestro personaje
    private String type; //El tipo de personaje que es

    /**
     * Este será el constructor de nuestro personaje
     * @param name el nombre que le queremos asignar
     * @param player el nombre de nuestro jugador
     * @param xp la experiencia inicial de nuestro personaje
     */
    public Char(String name, String player, int xp) {
        super(name);
        this.player = player;
        this.xp = xp;
    }

    /**
     * Este será el constructor de nuestro personaje que por entrada recibe a otro personaje.
     * @param character es el personaje que trae los datos con los que queremos inicializar el nuevo personaje
     */
    public Char (Char character){
        super(character.getName());
        this.player = character.getPlayer();
        this.xp = character.getXp();
    }

    /**
     * Getter del valor de cuerpo
     * @return el valor de cuerpo
     */
    public int getBody() {
        return body;
    }

    /**
     * Setter del valor de cuerpo
     * @param body el valor de cuerpo
     */
    public void setBody(int body) {
        this.body = body;
    }

    /**
     * Getter del valor de mente
     * @return el valor de mente
     */
    public int getMind() {
        return mind;
    }

    /**
     * Setter del valor de mente
     * @param mind el valor de mente
     */
    public void setMind(int mind) {
        this.mind = mind;
    }

    /**
     * Getter del valor de espíritu
     * @return el valor de espíritu
     */
    public int getSpirit() {
        return spirit;
    }

    /**
     * Setter del valor de espíritu
     * @param spirit el valor de espíritu
     */
    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    /**
     * Getter del valor de vida maxima
     * @return el valor de vida maxima
     */
    public int getMaxLife() {
        return maxLife;
    }

    /**
     * Setter del valor de vida maxima
     * @param maxLife el valor de vida maxima
     */
    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    /**
     * Getter del valor del tipo
     * @return el valor del tipo
     */
    public String getType() {
        return type;
    }

    /**
     * Setter del valor del tipo
     * @param type el tipo
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter del nivel de nuestro personaje
     * @return el nivel
     */
    public int getLevel(){
        return (int) (Math.floor(xp/100) +1);
    }

    /**
     * Este método nos retorna el nombre del jugador que lo creo
     * @return el nombre del jugador
     */
    public String getPlayer() {
        return player;
    }

    /**
     * Este método es para conseguir la experiencia del personaje
     * @return la experiencia actual del personaje
     */
    public int getXp() {
        return xp;
    }

    /**
     * Este método es para leer la experiencia del personaje
     * @param xp la experiencia del personaje
     */
    public void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Método para calcular la vida maxima actual del personaje
     */
    public void calcMaxLife(){
        setMaxLife(10);
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     */
    public void preparationStage() {
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     */
    public void stopPreparationStage(){

    }

    /**
     * Este método será capaz de implementar una manera en la que se puede seleccionar el objetivo de su ataque
     * @param monsters esta será la lista de posibles objetivos del ataque
     * @return el objetivo del ataque
     */
    public Monster selectMonsterObjective(LinkedList<Monster> monsters) {
        return monsters.get(0);
    }

    /**
     * Es el método que implementa el descanso que deben tener cada personaje
     * @return el entero con el resultado de lo que hayan hecho
     */
    public int shortBrake() {
        return 0;
    }

    /**
     * Este método generará los stats del personaje. Como los tres stats se calculan de la misma manera hemos decidido
     * que se introduzca el personaje y el stat a modificar. Como esto se ara con dos dados y se deben mostrar por
     * pantalla, retornamos los valores extraídos por los dadoes en un array de enteros.
     * @param stat es la estadística a modificar
     * @param character el personaje a modificar
     * @return los números extraídos por los dos dados
     */
    public int[] generateStats(String stat, Char character) {
        Dice dice = new Dice(6);

        int num1 = dice.throwDice();
        int num2 = dice.throwDice();

        int[] nums = new int[2];

        nums[0] = num1;
        nums[1] = num2;

        int sum = num1 + num2;

        switch (stat) {
            case "Body" -> {
                if (sum <= 2) {
                    character.setBody(-1);
                } else if (sum <= 5) {
                    character.setBody(0);
                } else if (sum <= 9) {
                    character.setBody(1);
                } else if (sum <= 11) {
                    character.setBody(2);
                } else {
                    character.setBody(3);
                }
            }
            case "Mind" -> {
                if (sum <= 2) {
                    character.setMind(-1);
                } else if (sum <= 5) {
                    character.setMind(0);
                } else if (sum <= 9) {
                    character.setMind(1);
                } else if (sum <= 11) {
                    character.setMind(2);
                } else {
                    character.setMind(3);
                }
            }
            case "Spirit" -> {
                if (sum <= 2) {
                    character.setSpirit(-1);
                } else if (sum <= 5) {
                    character.setSpirit(0);
                } else if (sum <= 9) {
                    character.setSpirit(1);
                } else if (sum <= 11) {
                    character.setSpirit(2);
                } else {
                    character.setSpirit(3);
                }
            }
        }
        return nums;
    }
}

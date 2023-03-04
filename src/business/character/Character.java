package business.character;

import business.Entities;

/**
 * Esta clase representa a todos los personajes que queramos crear durante el funcionamiento del código
 * Esta clase extiende de la clase Entities para poder aplicar ciertas funciones y servirá para poder
 * extender de ella y sacar distintos tipos de personajes
 */
public class Character extends Entities {
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
    public Character(String name, String player, int xp) {
        super(name);
        this.player = player;
        this.xp = (xp*100)-101;
        this.type = "Adventurer";
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
}

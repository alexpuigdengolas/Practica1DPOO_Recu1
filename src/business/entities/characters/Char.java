package business.entities.characters;

import business.entities.Entity;

import java.util.LinkedList;

/**
 * Esta clase representa a todos los personajes que queramos crear durante el funcionamiento del código
 * Esta clase extiende de la clase Entity para poder aplicar ciertas funciones y servirá para poder
 * extender de ella y sacar distintos tipos de personajes
 */

//WARNING: Si la clase char es abstracta tengo que implementar un instance creator para poder leer los personajes de la base de datos
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
     * @param level el nivel inicial del personaje
     */
    public Char(String name, String player, int level) {
        super(name);
        this.player = player;
        this.xp = calcXp(level);
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
     * Método para poder calcular la experiencia a la que equivale un nivel
     * @param level el nivel que queremos analizar
     * @return la experiencia equivalente a dicho nivel
     */
    public int calcXp(int level){
        int xp = (level*100)-1;
        return Math.max(xp, 0);
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
        this.setMaxLife(((10 + getBody())*this.getLevel()));
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     * @param party es el grupo de personas que va a acompañar a nuestros personajes
     */
    public void preparationStage(LinkedList<Char> party) {
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     * @param party es el grupo de personas que va a acompañar a nuestros personajes
     */
    public void stopPreparationStage(LinkedList<Char> party){

    }

    /**
     * Es el método que implementa el descanso que deben tener cada personaje
     * @return el entero con el resultado de lo que hayan hecho
     */
    public int shortBrake() {
        return 0;
    }

    /**
     * Este método permite a los personajes curarse entre sí
     * @param heal la cantidad de healing que queremos darle al personaje
     */
    public void heal(int heal){
        setHitPoints(getHitPoints() + heal);
        if(getHitPoints() > maxLife){
            setHitPoints(maxLife);
        }
    }

    /**
     * Este método sirve para poder aumentar los puntos de la estadística de mentalidad
     * @param mindAdded es la cantidad de puntos a añadir
     */
    protected void addMindPoints(int mindAdded) {
        this.mind += mindAdded;
    }

    /**
     * Este método sirve para poder evolucionar de personaje al subir a cierto nivel
     * @return el nuevo personaje al que evoluciona el personaje actual
     */
    public Char levelUp(){
        return null;
    }
}

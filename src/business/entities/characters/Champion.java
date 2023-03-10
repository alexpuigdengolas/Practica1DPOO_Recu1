package business.entities.characters;

import java.util.LinkedList;

/**
 * Esta clase nos será util para poder representar a la clase personajes que son campeón
 */
public class Champion extends Warrior{

    /**
     * Este será el constructor de nuestra clase. Nos permitirá crear un campeón a partir de otro personaje
     * @param aChar Este será el personaje original del que crearemos el nuestro
     */
    public Champion(Char aChar) {
        super(aChar);
        this.setType("Champion");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    /**
     * Método para calcular la vida maxima actual del personaje
     */
    @Override
    public void calcMaxLife() {
        int maxLife = ((10 + getBody())*this.getLevel())+(getBody()*getLevel());
        this.setMaxLife(maxLife);
    }

    /**
     * Este método implementa la etapa de preparación de cada personaje.
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propia etapa de preparación.
     * @param party es el grupo de personas que va a acompañar a nuestros personajes
     */
    @Override
    public void preparationStage(LinkedList<Char> party) {
        for(Char character: party){
            character.setSpirit(character.getSpirit() + 1);
        }
    }

    /**
     * Este método implementa el fin de la etapa de preparación de cada personaje
     * Empleando polimorfismo cada clase de personaje será capaz de poder implementar su propio fin de la etapa de preparación.
     * @param party es el grupo de personas que va a acompañar a nuestros personajes
     */
    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        for(Char character: party){
            character.setSpirit(character.getSpirit() - 1);
        }
    }

    /**
     * Es el método que implementa el descanso que deben tener cada personaje
     * @return el entero con el resultado de lo que hayan hecho
     */
    @Override
    public int shortBrake() {
        int num = getMaxLife()- getHitPoints();
        this.setHitPoints(getMaxLife());
        return num;
    }
}

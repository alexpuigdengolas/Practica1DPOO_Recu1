package business;

import java.util.Random;
/**
 * clase correspondiente al dado
 */
public class Dice {

    /**
     * Número de caras del dado representado por una "d" y luego el número de caras (Ex: d6)
     */
    private final int faceNum;

    /**
     * Constructor del Dado
     * @param faceNum: guardamos el número del dado
     */
    public Dice(int faceNum){
        this.faceNum = faceNum;
    }
    /**
     * función que devuelve un int, el número del dado
     * @return numero del dado
     */
    public int throwDice(){
        Random random = new Random();
        return random.nextInt(faceNum) + 1;
    }
}
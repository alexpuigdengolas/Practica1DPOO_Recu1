package business;

import business.entities.monster.Boss;
import business.entities.monster.Monster;

import java.util.LinkedList;

/**
 * Esta clase nos permite representar toda la información relacionada con los combates
 */
public class Combat {
    /**
     * Listado de monstruos que componen el combate
     */
    LinkedList<Monster> monsters;

    /**
     * Este es el constructor de nuestra clase
     */
    public Combat() {
        monsters = new LinkedList<>();
    }

    /**
     * Este es el getter de nuestra lista de monstruos
     * @return la lista de monstruos que tenemos almacenados en este combate
     */
    public LinkedList<Monster> getMonsters() {
        return monsters;
    }

    /**
     * Este método nos permite eliminar un tipo concreto de monstruo de este combate
     * @param monsterType es el nombre de los monstruos que queremos eliminar
     * @return el número de monstruos eliminados
     */
    public int removeMonster(String monsterType) {
        int numMonsters = 0;
        for(int i = getMonsters().size()-1; i >= 0; i--){
            if(getMonsters().get(i).getName().equals(monsterType)){
                getMonsters().remove(i);
                numMonsters++;
            }
        }
        return numMonsters;
    }

    /**
     * Este método nos permite añadir monstruos a este combate
     * @param monsterAmount el número de monstruos de un mismo tipo que queremos añadir
     * @param monster el tipo de monstruos que queremos añadir
     */
    public void addMonsters(int monsterAmount, Monster monster) {
        for (int i = 0; i < monsterAmount; i++) {
            if(monster.getChallenge().equals("Boss")){
                getMonsters().add(new Boss(monster));
            }else {
                getMonsters().add(monster);
            }
        }
    }
}

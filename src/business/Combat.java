package business;

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
}

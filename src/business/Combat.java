package business;

import business.entities.monster.Monster;

import java.util.LinkedList;

public class Combat {
    /**
     * Listado de monstruos que componen el combate
     */
    LinkedList<Monster> monsters;

    public Combat() {
        monsters = new LinkedList<>();
    }

    public LinkedList<Monster> getMonsters() {
        return monsters;
    }
}

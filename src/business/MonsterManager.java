package business;

import persistence.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class MonsterManager {
    private static final int JSON = 1;
    private static final int API = 2;
    private MonsterDAO monsterDAO;

    /**
     * Este será el constructor de nuestra clase. Tendrá en cuenta el valor introducido por nuestro usuario al inicio
     * del programa para determinar cuál será la base de datos escogida para trabajar
     * @param option es la opción que determina la base de datos
     */
    public MonsterManager(int option){
        switch (option){
            case JSON -> monsterDAO = new MonsterJsonDAO();
            case API -> monsterDAO = new MonsterApiDAO();
        }
    }

    /**
     * Este método comprobará que la base de datos de las aventuras sea accesible desde el principio
     * @return booleano que indica si es accesible
     */
    public boolean fileExists() {
        return monsterDAO.fileExists();
    }


    public LinkedList<Monster> getMonsterList() {
        try {
            return monsterDAO.getMonsterList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

package business.entities.monster;

import persistence.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * Esta clase sirve para poder gestionar toda la información relacionada con los monstruos
 */
public class MonsterManager {

    /**
     * Esta será la constante que indica si se accede al JSON
     */
    private static final int JSON = 1;

    /**
     * Esta será la constante que indica si se accede a al API
     */
    private static final int API = 2;

    /**
     * Esta será la DAO clase que nos permitirá acceder a la base de datos de aventuras
     */
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

    /**
     * Este método nos permite conseguir el listado de monstruos que tenemos en nuestra base de datos
     * @return el listado de monstruos
     */
    public LinkedList<Monster> getMonsterList() {
        try {
            return monsterDAO.getMonsterList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

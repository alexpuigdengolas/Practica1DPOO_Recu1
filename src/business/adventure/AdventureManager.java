package business.adventure;

import persistence.AdventureApiDAO;
import persistence.AdventureDAO;
import persistence.AdventureJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class AdventureManager {
    private static final int JSON = 1;
    private static final int API = 2;
    private AdventureDAO adventureDAO;

    /**
     * Este será el constructor de nuestra clase. Tendrá en cuenta el valor introducido por nuestro usuario al inicio
     * del programa para determinar cuál será la base de datos escogida para trabajar
     * @param option es la opción que determina la base de datos
     */
    public AdventureManager(int option){
        switch (option){
            case JSON -> adventureDAO = new AdventureJsonDAO();
            case API -> adventureDAO = new AdventureApiDAO();
        }
    }

    /**
     * Este método comprobará si el nombre de nuestra aventura cumple lo que se demanda en el enunciado
     * @param adventureName es el nombre la aventura a checkear
     * @return un booleano que definirá si está bien o mal el formato del nombre
     */
    public boolean checkAdventureName(String adventureName){
        LinkedList<Adventure> adventures;
        try {
            adventures = adventureDAO.getAdventureList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Adventure adventure : adventures) {
            if (adventure.getName().equals(adventureName)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Este método comprobará que la base de datos de las aventuras sea accesible desde el principio
     * @return booleano que indica si es accesible
     */
    public boolean fileExists() {
        return adventureDAO.fileExists();
    }

    /**
     * Este método servirá para actualizar la base de datos de nuestras aventuras añadiendo una única aventura a
     * la base de datos
     * @param adventure es la aventura que queremos añadir a la base de datos
     */
    public void updateAdventureList(Adventure adventure) {
        try {
            LinkedList<Adventure> adventures = adventureDAO.getAdventureList();
            adventures.add(adventure);
            adventureDAO.updateAdventureList(adventures);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

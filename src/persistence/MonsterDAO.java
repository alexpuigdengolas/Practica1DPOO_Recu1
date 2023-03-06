package persistence;

import business.entities.monster.Monster;

import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 * Esta interficie nos permitirá acceder a nuestra base de datos. Es indiferente si esta es interficie se utiliza para
 * implementar el acceso a la API o al JSON.
 */
public interface MonsterDAO {
    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros Monstruos
     * @return Listado de los monstruos guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    LinkedList<Monster> getMonsterList() throws FileNotFoundException;

    /**
     * Este método comprobará que el archivo exista y que por ende sea accesible
     * @return un booleano que nos dara la respuesta a nuestra duda
     */
    boolean fileExists();
}

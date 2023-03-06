package persistence;

import business.entities.monster.Monster;

import java.util.LinkedList;

/**
 * Esta clase implementa lo necesario para poder acceder a los datos guardados en la API sobre nuestros Monstruos
 */
public class MonsterApiDAO implements MonsterDAO{

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros Monstruos
     * @return Listado de los monstruos guardado en la base de datos seleccionada
     */
    @Override
    public LinkedList<Monster> getMonsterList(){
        return null;
    }

    /**
     * Este método comprobará que el archivo exista y que por ende sea accesible
     * @return un booleano que nos dara la respuesta a nuestra duda
     */
    @Override
    public boolean fileExists() {
        return false;
    }
}

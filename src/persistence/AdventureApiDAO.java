package persistence;

import business.adventure.Adventure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Esta clase implementa lo necesario para poder acceder a los datos guardados en la API sobre nuestras Aventuras
 */
public class AdventureApiDAO implements AdventureDAO{

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestras aventuras
     * @return Listado de las aventuras guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    @Override
    public LinkedList<Adventure> getAdventureList() throws FileNotFoundException {
        return null;
    }

    /**
     * Este método va a permitirnos enviar una lista de aventuras para después poder actualizar la base de datos
     * y guardar nueva información.
     * @param adventures es el listado de aventuras que queremos guardar
     */
    @Override
    public void updateAdventureList(LinkedList<Adventure> adventures) throws IOException {

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

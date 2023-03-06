package persistence;


import business.entities.characters.Char;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Esta interficie nos permitirá acceder a nuestra base de datos. Es indiferente si esta es interficie se utiliza para
 * implementar el acceso a la API o al JSON.
 */
public interface CharacterDAO {

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros personajes
     * @return Listado de los personajes guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    LinkedList<Char> getCharList() throws FileNotFoundException;

    /**
     * Este método va a permitirnos enviar una lista de personajes para después poder actualizar la base de datos
     * y guardar nueva información.
     * @param characters es el listado de personajes que queremos guardar
     * @throws IOException sirve para comprobar que los inputs sean correctos
     */
    void updateCharList (LinkedList<Char> characters) throws IOException;

    /**
     * Este método comprobará que el archivo exista y que por ende sea accesible
     * @return un booleano que nos dara la respuesta a nuestra duda
     */
    boolean fileExists();

}

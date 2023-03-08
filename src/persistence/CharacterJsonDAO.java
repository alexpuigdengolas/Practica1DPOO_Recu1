package persistence;

import business.entities.characters.Adventurer;
import business.entities.characters.Char;
import business.entities.characters.Cleric;
import business.entities.characters.Mage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Esta clase nos permite acceder a todos los datos del JSON relacionado con los personajes
 */
public class CharacterJsonDAO implements CharacterDAO{

    static final String PATH = "data/character.json";
    private final Gson gson;

    /**
     * Este es el constructor de nuestra clase. Declarará el gson para poder usarlo más tarde.
     */
    public CharacterJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros personajes
     * @return Listado de los personajes guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    @Override
    public LinkedList<Char> getCharList() throws FileNotFoundException {
        LinkedList<Char> classifiedCharacters = new LinkedList<>();
        LinkedList<Char> characters = new LinkedList<>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Char[].class)));
        for (Char character : characters) {
            switch (character.getType()) {
                case "Adventurer" -> classifiedCharacters.add(new Adventurer(character));
                case "Mage" -> classifiedCharacters.add(new Mage(character));
                case "Cleric" -> classifiedCharacters.add(new Cleric(character));
            }
        }
        return classifiedCharacters;
    }

    /**
     * Este método va a permitirnos enviar una lista de personajes para después poder actualizar la base de datos
     * y guardar nueva información.
     * @param characters es el listado de personajes que queremos guardar
     * @throws IOException sirve para comprobar que los inputs sean correctos
     */
    @Override
    public void updateCharList(LinkedList<Char> characters) throws IOException {
        FileWriter writer = new FileWriter(PATH);
        gson.toJson(characters, writer);
        writer.close();
    }

    /**
     * Este método comprobará que el archivo exista y que por ende sea accesible
     * @return un booleano que nos dara la respuesta a nuestra duda
     */
    @Override
    public boolean fileExists() {
        File file = new File(PATH);
        return file.exists();
    }
}

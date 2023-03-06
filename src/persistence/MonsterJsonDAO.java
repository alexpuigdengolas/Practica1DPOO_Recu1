package persistence;

import business.entities.monster.Monster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Esta clase nos permite acceder a la información relacionada con nuestros monstruos guardada en el JSON
 */
public class MonsterJsonDAO implements MonsterDAO{
    static final String PATH = "data/monster.json";
    private final Gson gson;

    /**
     * Este es el constructor de nuestra clase. Declarará el gson para poder usarlo más tarde.
     */
    public MonsterJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros Monstruos
     * @return Listado de los monstruos guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    @Override
    public LinkedList<Monster> getMonsterList() throws FileNotFoundException {
        return new LinkedList<>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Monster[].class)));
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

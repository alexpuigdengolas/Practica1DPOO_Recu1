package persistence;

import business.adventure.Adventure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase será util para poder acceder a toda la base de datos tanto leer como escribir información relacionada con
 * las aventuras
 */
public class AdventureJsonDAO implements AdventureDAO{

    /**
     * Es la dirección del archivo JSON
     */
    static final String PATH = "data/adventure.json";
    /**
     * Esta será la herramienta gson que nos permitirá leer/escribir toda la información de las aventuras
     */
    private final Gson gson;

    /**
     * Es el constructor de nuestra clase que inicializara el gson para poder gestionar toda la base de datos
     */
    public AdventureJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestras aventuras
     * @return Listado de las aventuras guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    @Override
    public LinkedList<Adventure> getAdventureList() throws FileNotFoundException {
        return new LinkedList<>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Adventure[].class)));
    }

    /**
     * Este método va a permitirnos enviar una lista de aventuras para después poder actualizar la base de datos
     * y guardar nueva información.
     * @param adventures es el listado de aventuras que queremos guardar
     */
    @Override
    public void updateAdventureList(LinkedList<Adventure> adventures){
        JSONArray jsonArray = new JSONArray();
        for(Adventure adventure: adventures) {
            createJSONAdventure(jsonArray, adventure);
        }

        try (FileWriter file = new FileWriter(PATH)) {
            file.write(prettyJson(jsonArray));
            file.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    /**
     * Este método serviría para poder decidir que información de la aventura guardamos y como lo hacemos a la hora de
     * escribir la información en el JSON
     * @param jsonArray Es el array de objetos en el que queremos guardar la información de la aventura
     * @param adventure la aventura que queremos guardar
     */
    private void createJSONAdventure(JSONArray jsonArray, Adventure adventure) {
        JSONObject adventureJSON = new JSONObject();
        adventureJSON.put("name", adventure.getName());
        adventureJSON.put("numCombat", adventure.getNumCombat());
        List<JSONObject> jsonCombatArray = new ArrayList<>();
        for(int i = 0; i < adventure.getNumCombat(); i++){
            JSONObject jsonMonsterObject = new JSONObject();
            List<JSONObject> jsonMonsterArray = new ArrayList<>();
            for(int j = 0; j < adventure.getCombats().get(i).getMonsters().size(); j++){
                JSONObject monstersJSON = new JSONObject();
                monstersJSON.put("name", adventure.getCombats().get(i).getMonsters().get(j).getName());
                monstersJSON.put("challenge", adventure.getCombats().get(i).getMonsters().get(j).getChallenge());
                monstersJSON.put("experience", adventure.getCombats().get(i).getMonsters().get(j).getExperience());
                monstersJSON.put("hitPoints", adventure.getCombats().get(i).getMonsters().get(j).getHitPoints());
                monstersJSON.put("initiative", adventure.getCombats().get(i).getMonsters().get(j).getInitiative());
                monstersJSON.put("damageDice", adventure.getCombats().get(i).getMonsters().get(j).getDamageDice());
                monstersJSON.put("damageType", adventure.getCombats().get(i).getMonsters().get(j).getDamageType());
                monstersJSON.put("currentInitiative", adventure.getCombats().get(i).getMonsters().get(j).getCurrentInitiative());
                jsonMonsterArray.add(monstersJSON);
            }
            jsonMonsterObject.put("monsters" ,jsonMonsterArray);
            jsonCombatArray.add(jsonMonsterObject);
        }
        adventureJSON.put("combats" ,jsonCombatArray);
        jsonArray.put(adventureJSON);
    }

    /**
     * Este método sirve para poder poner la información del JSON en formato vertical y no horizontal
     * @param json Es el array con la información que hay dentro del JSON
     * @return un String con toda la información puesta en vertical
     */
    private static String prettyJson(JSONArray json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(json.toString());
        return gson.toJson(je);
    }
}

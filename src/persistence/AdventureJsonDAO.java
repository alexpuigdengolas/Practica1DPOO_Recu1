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

public class AdventureJsonDAO implements AdventureDAO{

    static final String PATH = "data/adventure.json";
    private final Gson gson;

    public AdventureJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public LinkedList<Adventure> getAdventureList() throws FileNotFoundException {
        return new LinkedList<>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Adventure[].class)));
    }

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

package persistance;

import business.character.Character;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class CharacterJsonDAO implements CharacterDAO{

    static final String PATH = "data/character.json";
    private final Gson gson;

    public CharacterJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Override
    public LinkedList<Character> getCharList() throws FileNotFoundException {
        return new LinkedList<Character>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Character[].class)));
    }

    @Override
    public void updateCharList(LinkedList<Character> characters) throws IOException {
        FileWriter writer = new FileWriter(PATH);
        gson.toJson(characters, writer);
        writer.close();
    }

    @Override
    public boolean fileExists() {
        File file = new File(PATH);
        return file.exists();
    }
}

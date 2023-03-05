package persistence;

import business.Adventurer;
import business.Char;
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
    public LinkedList<Char> getCharList() throws FileNotFoundException {
        LinkedList<Char> classifiedCharacters = new LinkedList<>();
        LinkedList<Char> characters = new LinkedList<>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Char[].class)));
        for (Char character : characters) {
            switch (character.getType()) {
                case "Adventurer" -> classifiedCharacters.add(new Adventurer(character));
            }
        }
        return classifiedCharacters;
    }

    @Override
    public void updateCharList(LinkedList<Char> characters) throws IOException {
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

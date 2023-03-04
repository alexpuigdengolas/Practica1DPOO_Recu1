package persistence;

import business.adventure.Adventure;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;

public class AdventureJsonDAO implements AdventureDAO{

    static final String PATH = "data/adventure.json";
    private final Gson gson;

    public AdventureJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public LinkedList<Adventure> getAdventureList() throws FileNotFoundException {
        return new LinkedList<Adventure>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Adventure[].class)));
    }

    @Override
    public void updateAdventureList(LinkedList<Adventure> adventures) throws IOException {
        //TODO: Que no escriba la party al intentar guardar la aventura
        FileWriter writer = new FileWriter(PATH);
        gson.toJson(adventures, writer);
        writer.close();
    }

    @Override
    public boolean fileExists() {
        File file = new File(PATH);
        return file.exists();
    }
}

package persistence;

import business.entities.monster.Monster;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;

public class MonsterJsonDAO implements MonsterDAO{
    static final String PATH = "data/monster.json";
    private final Gson gson;

    public MonsterJsonDAO() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }


    @Override
    public LinkedList<Monster> getMonsterList() throws FileNotFoundException {
        return new LinkedList<>(Arrays.asList(gson.fromJson(gson.newJsonReader(new FileReader(PATH)), Monster[].class)));
    }

    @Override
    public boolean fileExists() {
        File file = new File(PATH);
        return file.exists();
    }
}

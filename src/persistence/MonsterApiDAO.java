package persistence;

import business.Monster;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class MonsterApiDAO implements MonsterDAO{
    @Override
    public LinkedList<Monster> getMonsterList() throws FileNotFoundException {
        return null;
    }

    @Override
    public boolean fileExists() {
        return false;
    }
}

package persistence;

import business.entities.monster.Monster;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public interface MonsterDAO {
    LinkedList<Monster> getMonsterList() throws FileNotFoundException;
    boolean fileExists();
}

package persistence;

import business.entities.monster.Monster;

import java.util.LinkedList;

public class MonsterApiDAO implements MonsterDAO{
    @Override
    public LinkedList<Monster> getMonsterList(){
        return null;
    }

    @Override
    public boolean fileExists() {
        return false;
    }
}

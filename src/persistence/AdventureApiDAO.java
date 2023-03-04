package persistence;

import business.adventure.Adventure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class AdventureApiDAO implements AdventureDAO{
    @Override
    public LinkedList<Adventure> getAdventureList() throws FileNotFoundException {
        return null;
    }

    @Override
    public void updateAdventureList(LinkedList<Adventure> adventures) throws IOException {

    }

    @Override
    public boolean fileExists() {
        return false;
    }
}

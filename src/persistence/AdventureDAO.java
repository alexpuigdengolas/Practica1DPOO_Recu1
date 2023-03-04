package persistence;

import business.adventure.Adventure;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface AdventureDAO {
    LinkedList<Adventure> getAdventureList() throws FileNotFoundException;

    void updateAdventureList(LinkedList<Adventure> adventures) throws IOException;

    boolean fileExists();
}

package persistence;


import business.character.Char;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface CharacterDAO {
    LinkedList<Char> getCharList() throws FileNotFoundException;

    void updateCharList (LinkedList<Char> characters) throws IOException;

    boolean fileExists();

}

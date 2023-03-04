package persistence;


import business.character.Character;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public interface CharacterDAO {
    LinkedList<Character> getCharList() throws FileNotFoundException;

    void updateCharList (LinkedList<Character> characters) throws IOException;

    boolean fileExists();

}

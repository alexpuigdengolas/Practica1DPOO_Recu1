package persistence;

import business.character.Char;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class CharacterApiDAO implements CharacterDAO{
    @Override
    public LinkedList<Char> getCharList() throws FileNotFoundException {
        return null;
    }

    @Override
    public void updateCharList(LinkedList<Char> characters) throws IOException {

    }

    @Override
    public boolean fileExists() {
        return false;
    }
}

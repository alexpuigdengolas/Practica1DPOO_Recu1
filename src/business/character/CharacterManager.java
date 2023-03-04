package business.character;

import business.Dice;
import persistence.CharacterApiDAO;
import persistence.CharacterDAO;
import persistence.CharacterJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

public class CharacterManager {
    private static final int JSON = 1;
    private static final int API = 2;
    private CharacterDAO characterDAO;

    public CharacterManager(int option){
        switch (option){
            case JSON -> characterDAO = new CharacterJsonDAO();
            case API -> characterDAO = new CharacterApiDAO();
        }
    }

    public boolean checkCharacterName(String charName){
        LinkedList<Character> characters;
        try {
            characters = characterDAO.getCharList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Character character : characters) {
            if (character.getName().equals(charName)) {
                return false;
            }
        }
        return true;
    }

    public boolean fileExists() {
        return characterDAO.fileExists();
    }

    public int[] generateCharacterStat(String stat, Character character) {
        Dice dice = new Dice(6);

        int num1 = dice.throwDice();
        int num2 = dice.throwDice();

        int[] nums = new int[2];

        nums[0] = num1;
        nums[1] = num2;

        int sum = num1 + num2;

        switch (stat) {
            case "Body" -> {
                if (sum <= 2) {
                    character.setBody(-1);
                } else if (sum <= 5) {
                    character.setBody(0);
                } else if (sum <= 9) {
                    character.setBody(1);
                } else if (sum <= 11) {
                    character.setBody(2);
                } else {
                    character.setBody(3);
                }
            }
            case "Mind" -> {
                if (sum <= 2) {
                    character.setMind(-1);
                } else if (sum <= 5) {
                    character.setMind(0);
                } else if (sum <= 9) {
                    character.setMind(1);
                } else if (sum <= 11) {
                    character.setMind(2);
                } else {
                    character.setMind(3);
                }
            }
            case "Spirit" -> {
                if (sum <= 2) {
                    character.setSpirit(-1);
                } else if (sum <= 5) {
                    character.setSpirit(0);
                } else if (sum <= 9) {
                    character.setSpirit(1);
                } else if (sum <= 11) {
                    character.setSpirit(2);
                } else {
                    character.setSpirit(3);
                }
            }
        }

        return nums;
    }

    public void updateCharacterList(Character character) {
        try {
            LinkedList<Character> characters = characterDAO.getCharList();
            characters.add(character);
            characterDAO.updateCharList(characters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

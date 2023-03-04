package business;

import business.character.Character;
import business.character.CharacterManager;

public class BusinessController {
    private CharacterManager characterManager;

    public BusinessController(int option) {
        this.characterManager = new CharacterManager(option);
    }

    public boolean addCharacter(String charName, String playersName, int level) {
        return characterManager.addCharacter(charName, playersName, level);
    }

    public boolean fileExists() {
        return characterManager.fileExists();
    }

    public int[] generateCharacterStat(String stat, Character character) {
        return characterManager.generateCharacterStat(stat, character);
    }

    public void updateCharacterList(Character character) {
        characterManager.updateCharacterList(character);
    }
}

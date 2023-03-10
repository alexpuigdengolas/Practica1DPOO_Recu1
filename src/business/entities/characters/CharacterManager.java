package business.entities.characters;

import business.Dice;
import persistence.CharacterApiDAO;
import persistence.CharacterDAO;
import persistence.CharacterJsonDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Esta clase gestionará toda la información relacionada con nuestros personajes.
 */
public class CharacterManager {

    /**
     * Esta será la constante que indica si se accede al JSON
     */
    private static final int JSON = 1;


    /**
     * Esta será la constante que indica si se accede a al API
     */
    private static final int API = 2;

    /**
     * Esta será la DAO clase que nos permitirá acceder a la base de datos de aventuras
     */
    private CharacterDAO characterDAO;

    /**
     * Este será el constructor de nuestra clase. Tendrá en cuenta el valor introducido por nuestro usuario al inicio
     * del programa para determinar cuál será la base de datos escogida para trabajar
     * @param option es la opción que determina la base de datos
     */
    public CharacterManager(int option){
        switch (option){
            case JSON -> characterDAO = new CharacterJsonDAO();
            case API -> characterDAO = new CharacterApiDAO();
        }
    }

    /**
     * Este método comprobará si el nombre de nuestro personaje cumple lo que se demanda en el enunciado
     * @param charName es el nombre del personaje a checkear
     * @return un booleano que definirá si está bien o mal el formato del nombre
     */
    public boolean checkCharacterName(String charName){
        LinkedList<Char> characters;
        try {
            characters = characterDAO.getCharList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (Char character : characters) {
            if (character.getName().equals(charName)) {
                return false;
            }
        }

        return !charName.matches(".*\\d.*");
    }

    /**
     * Este método comprobará que la base de datos de los personajes sea accesible desde el principio
     * @return booleano que indica si es accesible
     */
    public boolean fileExists() {
        return characterDAO.fileExists();
    }

    /**
     * Este método service para generar las stats de nuestro personaje. Para economizar el crear tres métodos para lo mismo,
     * hemos decidido enviar el stat a modificar con el resultado de los dados.
     * @return un array de enteros con los valores de nuestros dados
     */
    public int[] generateDiceCharacterStat() {
        Dice dice = new Dice(6);

        int num1 = dice.throwDice();
        int num2 = dice.throwDice();

        int[] nums = new int[2];

        nums[0] = num1;
        nums[1] = num2;

        return nums;
    }

    /**
     * Este método servirá para actualizar la base de datos de nuestros personajes añadiendo un único personaje a
     * la base de datos
     * @param character es el personaje que queremos añadir a la base de datos
     */
    public void updateCharacterList(Char character) {
        try {
            LinkedList<Char> characters = characterDAO.getCharList();
            characters.add(character);
            characterDAO.updateCharList(characters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método se utiliza para enviar un listado de los personajes hechos por el jugador especificado
     * @param player el nombre del jugador a analizar
     * @return el listado de personajes que queremos mostrar
     */
    public LinkedList<Char> getCharacterListByPlayer(String player) {
        try {
            LinkedList<Char> originalCharacters = characterDAO.getCharList();
            if(player.equals("")) return originalCharacters;
            else{
                LinkedList<Char> characters = new LinkedList<>();
                for (Char originalCharacter : originalCharacters) {
                    if (originalCharacter.getPlayer().equals(player)) {
                        characters.add(originalCharacter);
                    }
                }
                return characters;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método nos retorna un personaje ya clasificado en su nuevo tipo. Es decir que pasa de ser Char a, por ejemplo,
     * Adventurer
     * @param charName el nombre del personaje
     * @param playerName el nombre del jugador que lo creo
     * @param level el nivel del personaje
     * @param body la estadística de corpulencia
     * @param mind la estadística de mentalidad
     * @param spirit la estadística de espiritualidad
     * @param type la clase del personaje
     * @return retorna un personaje del tipo que hemos determinado pero con todos los datos introducidos
     */
    public Char generateClassifiedChar(String charName, String playerName, int level, int body, int mind, int spirit, String type) {
         Char character;
        switch (type) {
            case "Adventurer" ->{
                character = new Adventurer(charName, playerName, level, body, mind, spirit);
                if(character.getLevel() >= 8){
                    character = character.levelUp();
                    character = character.levelUp();
                }else if(character.getLevel() >= 4){
                    character = character.levelUp();
                }
            }
            case "Cleric" -> {
                character = new Cleric(charName, playerName, level, body, mind, spirit);
                if(character.getLevel() >= 5){
                    character = character.levelUp();
                }
            }
            case "Mage" -> character = new Mage(charName, playerName, level, body, mind, spirit);
            default -> character = null;
        }
         return character;
    }

    /**
     * Este método sirve para poder eliminar personajes de la base de datos
     * @param name el nombre del personaje que queremos eliminar
     */
    public void deleteCharacter(String name) {
        try {
            LinkedList<Char> characters = characterDAO.getCharList();
            characters.removeIf(character -> character.getName().equals(name));
            characterDAO.updateCharList(characters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método accederá al characterDAO para poder retornar los personajes de nuestra base de datos
     * @return la lista de nuestros personajes
     */
    public LinkedList<Char> getCharacterList() {
        try {
            return characterDAO.getCharList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método se utilizará para poder settear la vida de un listado de personajes al máximo
     * @param party es la lista de personajes que queremos settear al máximo de vida
     */
    public void setCharsAfterGame(LinkedList<Char> party) {
        try {
            LinkedList<Char> characters = characterDAO.getCharList();
            for (Char character : characters) {
                for (Char aChar : party) {
                    if (character.getName().equals(aChar.getName())) {
                        character.setXp(aChar.getXp());
                    }
                }
                character.setHitPoints(character.getMaxLife());
            }
            characterDAO.updateCharList(characters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método permite al personaje ejecutar la función de tomarse un descanso para poder entre combates
     * @param character el personaje que se toma el descanso
     * @param party el listado de los personajes que participan en la aventura
     * @return la cantidad de lo que haga este personaje durante su descanso (vida, escudo, ...)
     */
    public int shortBrake(Char character, LinkedList<Char> party) {
        int breakAmount = character.shortBrake();

        if(character instanceof Paladin){
            for(Char healedChar: party){
                healedChar.heal(breakAmount);
            }
        }

        return breakAmount;
    }

    /**
     * Este método generará las estadísticas de nuestros personajes
     * @param dices los dados que hemos sacado con la generación de estadísticas
     * @return el valor generado para la stat por los dados
     */
    public int generateCharacterStat(int[] dices) {
        int num1 = dices[0], num2 = dices[1];
        int sum = num1 + num2;

        if (sum <= 2) {
            return -1;
        } else if (sum <= 5) {
            return 0;
        } else if (sum <= 9) {
            return 1;
        } else if (sum <= 11) {
            return 2;
        } else {
            return 3;
        }
    }

    public Char charLevelUp(Char character) {
        return character.levelUp();
    }
}

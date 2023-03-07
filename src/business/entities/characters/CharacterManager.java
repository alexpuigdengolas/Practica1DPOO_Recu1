package business.entities.characters;

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
    private static final int JSON = 1;
    private static final int API = 2;
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
     * @param stat el stat a modificar [Body, Mind o Spirit]
     * @param character el personaje a modificar
     * @return un array de enteros con los valores de nuestros dados
     */
    public int[] generateCharacterStat(String stat, Char character) {


        return character.generateStats(stat, character);
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
     * Este método generará los personajes nuevos dependiendo de su nivel y la clase seleccionada
     * @param character el personaje que queremos generar
     * @param type la clase seleccionada
     * @return Es el personaje que queremos recibir
     */
    public Char generateClassifiedChar(Char character, String type) {
        switch (type){
            case "Adventurer": return new Adventurer(character);
            //case "Cleric": return new Cleric(character);
            //case "Paladin": //return new Mage(character);
            default: return character;
        }
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
     * @return la cantidad de lo que haga este personaje durante su descanso (vida, escudo, ...)
     */
    public int shortBrake(Char character) {
        return character.shortBrake();
    }
}

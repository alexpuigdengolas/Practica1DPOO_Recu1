package business;

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
            case "Adventurer": return new Adventurer(character.getName(), character.getPlayer(), character.getLevel(), character.getMind(), character.getBody(), character.getSpirit());
            case "Cleric": break;//return new Cleric(character.getName(), character.getPlayer(), character.getLevel());
            case "Paladin": break;//return new Paladin(character.getName(), character.getPlayer(), character.getLevel());
            default: return character;
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

    public LinkedList<Char> getCharacterList() {
        try {
            return characterDAO.getCharList();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setCharsAfterGame(LinkedList<Char> party) {
        try {
            LinkedList<Char> characters = characterDAO.getCharList();
            for(int i = 0; i < characters.size(); i++){
                for (Char aChar : party) {
                    if (characters.get(i).equals(aChar)) {
                        characters.set(i, aChar);
                    }
                }
                characters.get(i).setHitPoints(characters.get(i).getMaxLife());
            }
            characterDAO.updateCharList(characters);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

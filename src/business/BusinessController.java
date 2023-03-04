package business;

import business.character.Char;
import business.character.CharacterManager;

import java.util.LinkedList;

/**
 * Este es el controlador de nuestro Business. Aquí gestionaremos toda la información que se nos proporciones tanto
 * desde Persistence como desde el resto de lugares de Business
 */
public class BusinessController {
    /**
     * Aquí crearemos las variables de todos los managers que necesitemos en nuestro código
     */
    private CharacterManager characterManager; //Manager de personajes

    /**
     * Aquí tenemos el constructor de nuestro BusinessController el cual se comunicara con el resto de Managers dentro
     * del Business
     * @param option esta es la option de la base de datos seleccionada por nuestro usuario
     */
    public BusinessController(int option) {
        this.characterManager = new CharacterManager(option);
    }

    /**
     * Este método nos permitira llamar a nuestra clase de CharacterManager para poder comprobar que los datos son
     * correctos y el nombre no coincide con ningún otro
     * @param charName es el nombre del personaje a crear
     * @return un booleano con el estado de nuestra búsqueda
     */
    public boolean checkCharacterName(String charName) {
        return characterManager.checkCharacterName(charName);

    }

    /**
     * Este método se utiliza para poder poner en mayúsculas todas las palabras que sean precedidas por un espacio
     * para poder cumplir todas las demandas del enunciado
     * @param string es la string con la que queremos trabajar
     * @return La string modificada
     */
    public String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    /**
     * Este método nos permite comprobar que todos los archivos existen
     * @return un booleano para saber si estos archivos son accesibles
     */
    public boolean fileExists() {
        return characterManager.fileExists();
    }

    /**
     * Este método nos permite generar las stats de nuestros personajes con un único método
     * @param stat el nombre del stat que queremos modificar
     * @param character es el personaje al que le afecta este cambio
     * @return un array de enteros que dirán cuál ha sido el valor de los dados lanzados en este método
     */
    public int[] generateCharacterStat(String stat, Char character) {
        return characterManager.generateCharacterStat(stat, character);
    }

    /**
     * Este método llamará a character manager para poder actualizar la lista de personajes
     * @param character el personaje que queremos añadir
     */
    public void updateCharacterList(Char character) {
        characterManager.updateCharacterList(character);
    }

    /**
     * Este método nos retorna una lista con todos los personajes creados por un jugador
     * en específico
     * @param player el nombre del jugador del cual queremos la información
     * @return una lista de los jugadores de ese jugador
     */
    public LinkedList<Char> getCharacterListByPlayer(String player) {
        return characterManager.getCharacterListByPlayer(player);
    }

    /**
     * Este método nos retorna un personaje ya clasificado en su nuevo tipo. Es decir que pasa de ser Char a, por ejemplo,
     * Adventurer
     * @param character el personaje original
     * @param type el tipo al que se quiere llegar
     * @return el personaje ya modificado dentro de su tipo
     */
    public Char generateClassifiedChar(Char character, String type) {
        return characterManager.generateClassifiedChar(character, type);
    }

    /**
     * Método para eliminar personajes de la base de datos
     * @param name el nombre del personaje a eliminar
     */
    public void deleteCharacter(String name) {
        characterManager.deleteCharacter(name);
    }
}

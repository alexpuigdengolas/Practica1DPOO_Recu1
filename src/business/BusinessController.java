package business;

import business.character.Character;
import business.character.CharacterManager;

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
    public int[] generateCharacterStat(String stat, Character character) {
        return characterManager.generateCharacterStat(stat, character);
    }

    /**
     * Este método llamará a character manager para poder actualizar la lista de personajes
     * @param character el personaje que queremos añadir
     */
    public void updateCharacterList(Character character) {
        characterManager.updateCharacterList(character);
    }
}

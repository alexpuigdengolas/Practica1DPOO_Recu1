package business;

import business.adventure.Adventure;
import business.adventure.AdventureManager;
import business.entities.Entity;
import business.entities.monster.Monster;
import business.entities.monster.MonsterManager;

import java.util.Comparator;
import java.util.LinkedList;

/**
 * Este es el controlador de nuestro Business. Aquí gestionaremos toda la información que se nos proporciones tanto
 * desde Persistence como desde el resto de lugares de Business
 */
public class BusinessController {
    /**
     * Aquí crearemos las variables de todos los managers que necesitemos en nuestro código
     */
    private final CharacterManager characterManager; //Manager de personajes
    private final AdventureManager adventureManager; //Manager de aventuras
    private final MonsterManager monsterManager; //Manager de monstruos

    /**
     * Aquí tenemos el constructor de nuestro BusinessController el cual se comunicara con el resto de Managers dentro
     * del Business
     * @param option esta es la option de la base de datos seleccionada por nuestro usuario
     */
    public BusinessController(int option) {
        this.characterManager = new CharacterManager(option);
        this.adventureManager = new AdventureManager(option);
        this.monsterManager = new MonsterManager(option);
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
        return (characterManager.fileExists() && monsterManager.fileExists() && adventureManager.fileExists());
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

    public boolean checkAdventureName(String name) {
        return adventureManager.checkAdventureName(name);
    }

    public LinkedList<Monster> getMonsterList() {
        return monsterManager.getMonsterList();
    }

    public void addAdventure(Adventure adventure) {
        adventureManager.updateAdventureList(adventure);
    }

    public LinkedList<Adventure> getAdventureList() {
        return adventureManager.getAdventureList();
    }

    public LinkedList<Char> getCharacterList() {
        return characterManager.getCharacterList();
    }

    public void preparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.preparationStage();
        }
    }

    public void calculateInitiative(LinkedList<Entity> entitiesOnGame) {
        for (Entity entity : entitiesOnGame) {
            entity.calculateCurrentInitiative();
        }

        Comparator<Entity> initiativeComparator = new Comparator<Entity>() {
            public int compare(Entity e1, Entity e2) {
                return Integer.compare(e2.getCurrentInitiative(), e1.getCurrentInitiative());
            }
        };

        entitiesOnGame.sort(initiativeComparator);
    }

    public void stopPreparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.stopPreparationStage();
        }
    }

    public int attackStage(Entity entity, Entity objective, int critical) {
        if (entity.getClass().getSimpleName().equals("Monster")) {
            Monster monster = new Monster((Monster) entity);
            return monster.attack(objective, critical);
        }
        Char character = (Char) entity;
        return character.attack(objective, critical);
    }

    public Entity objectiveSelection(Entity entity, LinkedList<Char> characters, LinkedList<Monster> monsters) {
        if (entity.getClass().getSimpleName().equals("Monster")) {
            Monster monster = new Monster((Monster) entity);
            return monster.selectCharacterObjective(characters);
        }
        Char character = (Char) entity;
        return character.selectMonsterObjective(monsters);
    }

    public int attackCritical(Entity entity) {
        return entity.isCriticalDmg();
    }

    public int getXpEarned(Adventure adventure, int i) {
        int xpSum = 0;
        for(int j = 0; j < adventure.getCombats().get(i).getMonsters().size(); j++){
            xpSum += adventure.getCombats().get(i).getMonsters().get(j).getExperience();
        }
        return xpSum;
    }

    public void setCharsAfterGame(LinkedList<Char> party) {
        characterManager.setCharsAfterGame(party);
    }

    public int shortBrake(Char character) {
        return characterManager.shortBrake(character);
    }
}

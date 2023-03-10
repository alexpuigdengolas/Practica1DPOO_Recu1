package business;

import business.adventure.Adventure;
import business.adventure.AdventureManager;
import business.entities.Entity;
import business.entities.characters.Char;
import business.entities.characters.CharacterManager;
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
     * Parámetro para acceder a la base de datos de los personajes
     */
    private final CharacterManager characterManager; //Manager de personajes

    /**
     * Parámetro para acceder a la base de datos de las aventuras
     */
    private final AdventureManager adventureManager; //Manager de aventuras

    /**
     * Parámetro para acceder a la base de datos de los monstruos
     */
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
     * @return un array de enteros que dirán cuál ha sido el valor de los dados lanzados en este método
     */
    public int[] generateDiceCharacterStat() {
        return characterManager.generateDiceCharacterStat();
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
     * @param charName el nombre del personaje
     * @param playerName el nombre del jugador que lo creo
     * @param level el nivel del personaje
     * @param body la estadística de corpulencia
     * @param mind la estadística de mentalidad
     * @param spirit la estadística de espiritualidad
     * @param type la clase del personaje
     * @return retorna un personaje del tipo que hemos determinado pero con todos los datos introducidos
     */
    public Char generateClassifiedChar(String charName, String playerName, int level, int body, int mind, int spirit , String type) {
        return characterManager.generateClassifiedChar(charName, playerName, level, body, mind, spirit, type);
    }

    /**
     * Método para eliminar personajes de la base de datos
     * @param name el nombre del personaje a eliminar
     */
    public void deleteCharacter(String name) {
        characterManager.deleteCharacter(name);
    }

    /**
     *Este método chequeará que el nombre de la aventura sea único y que pueda existir una aventura que el nombre introducido
     * @param name es el nombre de la aventura seleccionada
     * @return el resultado de sí el nombre es apto o no
     */
    public boolean checkAdventureName(String name) {
        return adventureManager.checkAdventureName(name);
    }

    /**
     * Este método recogerá la lista de monstruos de la base de datos y la retornará
     * @return la lista de monstruos de la base de datos
     */
    public LinkedList<Monster> getMonsterList() {
        return monsterManager.getMonsterList();
    }

    /**
     * Añadirá una aventura a nuestra base de datos recibiendo una y añadiéndola a la lista de aventuras existentes
     * y sobreescribiendo el archivo
     * @param adventure la aventura que queremos añadir
     */
    public void addAdventure(Adventure adventure) {
        adventureManager.updateAdventureList(adventure);
    }

    /**
     * Este método nos permite recibir el listado de aventuras que tenemos en nuestra base de datos
     * @return el listado de aventuras de nuestra base de datos
     */
    public LinkedList<Adventure> getAdventureList() {
        return adventureManager.getAdventureList();
    }

    /**
     * Este método nos permite recibir el listado de personajes que tenemos en nuestra base de datos
     * @return el listado de personajes de nuestra base de datos
     */
    public LinkedList<Char> getCharacterList() {
        return characterManager.getCharacterList();
    }

    /**
     * Este método nos permite hacer que los personajes de la party pasen por la etapa de preparación (Support)
     * @param party los personajes que pertenecen a nuestra aventura
     */
    public void preparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.preparationStage(party);
        }
    }

    /**
     * Este método calculará la iniciativa de todas nuestras entidades y las ordenará en función de ese valor
     * @param entitiesOnGame estas serán las entidades que compongan nuestro combate
     */
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

    /**
     * Este método es contrario a uno visto antes y lo que ara seré eliminar los efectos de la etapa de preparación anterior (Support)
     * @param party el listado de personajes que deben dejar de ser afectados y generar estos efectos
     */
    public void stopPreparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.stopPreparationStage(party);
        }
    }

    /**
     * Este método permite a cualquier entidad atacar a cualquier otra entidad y no solo calcula el daño realizado por esta
     * acción, sino que también retira la cantidad de vida de ese ataque al objetivo
     * @param entity es la entidad que realiza el ataque
     * @param objective es el objetivo del ataque
     * @param critical es un entero que indica si el ataque ha sido critico o simplemente ha fallado
     * @param entities es el listado de las entidades de la partida
     * @return el daño realizado por el ataque
     */
    public int attackStage(Entity entity, Entity objective, int critical, LinkedList<Entity> entities) {
        LinkedList<Char> characters = new LinkedList<>();
        LinkedList<Monster> monsters = new LinkedList<>();

        for (Entity entityAux : entities) {
            if(entityAux.getHitPoints() > 0) {
                if (entityAux instanceof Char) {
                    characters.add((Char) entityAux);
                } else {
                    monsters.add((Monster) entityAux);
                }
            }
        }

        int dmgDone = entity.attack(objective, critical);
        if(entity instanceof Char && objective instanceof Char){
            ((Char) objective).heal(dmgDone);
        }else {
            if(objective != null) {
                objective.getDamaged(dmgDone, entity.getDamageType());
            }else{
                if(entity instanceof Char) {
                    for (Monster monster : monsters) {
                        monster.getDamaged(dmgDone, entity.getDamageType());
                    }
                }else{
                    for (Char character : characters) {
                        character.getDamaged(dmgDone, entity.getDamageType());
                    }
                }
            }
        }
        return dmgDone;
    }

    /**
     * Este método permite a las entidades escoger cúal será el objetivo de su ataque.
     * @param entity la entidad que va a atacar
     * @param entities el resto de entidades que hay en la partida
     * @return la entidad que será objetivo del ataque
     */
    public Entity objectiveSelection(Entity entity, LinkedList<Entity> entities) {
        return entity.selectObjective(entities);
    }

    /**
     * Este método nos permite ver si un ataque es critico, si simplemente golpeará o si se fallara el ataque
     * @param entity es la entidad que atacara
     * @return un entero indicando el resultado del ataque (3 => Critico; 2 => Acierto; 1 => Fallo)
     */
    public int attackCritical(Entity entity) {
        return entity.isCriticalDmg();
    }

    /**
     * Este método permitirá saber cuanta experiencia se ha ganado al derrotar a multiples monstruos en un combate
     * @param adventure la aventura en la que se participa
     * @param i el indice del combate a analizar
     * @return la cantidad de experiencia ganada en este combate
     */
    public int getXpEarned(Adventure adventure, int i) {
        int xpSum = 0;
        for(int j = 0; j < adventure.getCombats().get(i).getMonsters().size(); j++){
            xpSum += adventure.getCombats().get(i).getMonsters().get(j).getExperience();
        }
        return xpSum;
    }

    /**
     * Este método reseteará la vida de todos los personajes antes de que estos vuelvan a ser introducidos en la base
     * de datos.
     * @param party la lista de personajes a resetear
     */
    public void setCharsAfterGame(LinkedList<Char> party) {
        characterManager.setCharsAfterGame(party);
    }

    /**
     * Este método permite a todos los personajes tomarse un descanso y realizar la acción que se les asigne durante este
     * @param character el personaje que realizará la acción
     * @param party es un listado de los personajes que pueden beneficiarse de esta acción
     * @return un entero con la cantidad de lo que haya hecho durante este descanso
     */
    public int shortBrake(Char character, LinkedList<Char> party) {
        return characterManager.shortBrake(character, party);
    }

    /**
     * Este método permite eliminar monstruos de un combate.
     * @param combat el combate del que se eliminaran los monstruos
     * @param monsterType el tipo de monstruos a eliminar
     * @return la cantidad de monstruos eliminados
     */
    public int getMonsterRemoved(Combat combat, String monsterType) {
        return combat.removeMonster(monsterType);
    }

    /**
     * Este método permite añadir monstruos a un combate
     * @param combat el combate al que se quieren añadir monstruos
     * @param monsterAmount la cantidad de monstruos a añadir
     * @param monster el tipo de monstruos a añadir
     */
    public void addMonsters(Combat combat, int monsterAmount, Monster monster) {
        combat.addMonsters(monsterAmount, monster);
    }

    /**
     * Este método servirá para llamar al método de generación de estadísticas de personajes
     * @param dices los dados lanzados en la etapa anterior
     * @return el valor final de la estadística
     */
    public int generateCharacterStat(int[] dices) {
        return characterManager.generateCharacterStat(dices);
    }

    /**
     * Este método permite saber si nuestro personaje está listo para evolucionar y si es el caso se añadirá el
     * personaje evolucionado a la party eliminando el anterior
     * @param character el personaje que queremos evolucionar
     * @param adventure la aventura que estamos jugando
     * @return el personaje evolucionado o no
     */
    public Char charLevelUp(Char character, Adventure adventure) {
        Char charAux = characterManager.charLevelUp(character);
        if(charAux != null){
            for(int i = 0; i < adventure.getParty().size(); i++){
                if(adventure.getParty().get(i).getName().equals(charAux.getName())){
                    adventure.getParty().add(i, charAux);
                    adventure.getParty().remove(i+1);
                    break;
                }
            }
        }
        return charAux;
    }
}

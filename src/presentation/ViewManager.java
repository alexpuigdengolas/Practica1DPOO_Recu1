package presentation;

import business.Combat;
import business.adventure.Adventure;
import business.entities.Entity;
import business.entities.characters.Char;
import business.entities.characters.Mage;
import business.entities.monster.Monster;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Esta clase contiene todos los "print's" que podamos necesitar para poder mostrar la información por pantalla.
 * Hemos utilizado una clase mostrada en clase como referencia para esta clase.
 */
public class ViewManager {
    private final Scanner scanner;//Empleamos un scanner como variable para poder leer toda la información necesaria

    /**
     * Este es el constructor de nuestra clase
     */
    public ViewManager() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Este método mostrara el menu principal del programa
     */
    public void showMenu() {
        System.out.println();
        System.out.println("    1) Character creation");
        System.out.println("    2) List characters");
        System.out.println("    3) Create an adventure");
        System.out.println("    4) Start an adventure");
        System.out.println("    5) Exit");
        System.out.println();
    }

    /**
     * Este método sirve para poder mostrar cualquier mensaje por pantalla
     * @param message el mensaje que queremos mostrar
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Este método nos permite controlar que se nos pase una String
     * @param message el mensaje que queremos mostrar antes de pedir la string
     * @return La string introducida por el usuario
     */
    public String askForString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    /**
     * Este método nos permite controlar que se nos pase un entero
     * @param message el mensaje que queremos mostrar antes de pedir el entero
     * @return el entero introducido por el usuario
     */
    public int askForInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("That's not a valid integer, try again!");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Este método permite colocar un salto de línea
     */
    public void spacing() {
        System.out.println();
    }

    /**
     * Este método nos mostrará el primer menu para pedir el formato de la base de datos
     */
    public void DAOMenu() {
        System.out.println();
        System.out.println("Do you want to use your local or cloud data?");
        System.out.println("    1) Local data");
        System.out.println("    2) Cloud data");
        System.out.println();
    }

    /**
     * Este método nos permite mostrar el listado de los personajes de la base de datos
     * @param characterListByPlayer es el listado de personajes
     */
    public void showCharacterList(LinkedList<Char> characterListByPlayer) {
        for(int i = 0; i < characterListByPlayer.size(); i++){
            System.out.println("    "+(i+1)+". "+characterListByPlayer.get(i).getName());
        }
        System.out.println();
        System.out.println("    0. Back");
    }

    /**
     * Este método nos mostrará toda la información específica de un personaje
     * @param character el personaje que queremos analizar
     */
    public void showCharacterInfo(Char character) {
        System.out.println("Tavern keeper: Hey "+character.getName()+" get here; the boss wants to see you!");
        System.out.println();
        System.out.println("* Name: "+character.getName());
        System.out.println("* Player: "+character.getPlayer());
        System.out.println("* Class: "+character.getType());
        System.out.println("* Level: "+character.getLevel());
        System.out.println("* XP: "+character.getXp());
        System.out.println("* Body: "+character.getBody());
        System.out.println("* Mind: "+character.getMind());
        System.out.println("* Spirit: "+character.getSpirit());
        System.out.println();

    }

    /**
     * Este método nos permite mostrar por pantalla al usuario el menu para crear un combate con sus respectivas funciones.
     * Añadir un monstruo en el combate, eliminarlo o pasar al siguiente combate.
     * @param i es el índice del combate actual
     * @param numCombats el numero de combates totales de la aventura
     * @param combat la información que contiene el combate en sí
     * @param monsterNames una lista con los nombres de los monstrous distintos a mostrar
     * @param monsterCount una lista con el número de monstruos iguales que hay en el combate
     */
    public void showCombatCreationMenu(int i, int numCombats, Combat combat, LinkedList<String> monsterNames, LinkedList<Integer> monsterCount) {
        System.out.println("* Encounter "+i+" / "+numCombats);
        System.out.println("* Monsters in encounter");
        boolean found;
        if(!(combat.getMonsters().isEmpty())) {
            for (int j = 0; j < combat.getMonsters().size(); j++) {
                found = false;
                for(int x = 0; x < monsterNames.size(); x++){
                    if(monsterNames.get(x).equals(combat.getMonsters().get(j).getName())){
                        monsterCount.set(x, monsterCount.get(x) + 1);
                        found = true;
                    }
                }
                if(!found){
                    monsterNames.add(combat.getMonsters().get(j).getName());
                    monsterCount.add(1);
                }
            }

            for(int j = 0; j < monsterNames.size(); j++){
                System.out.println("    "+(j+1)+". "+monsterNames.get(j)+" ( x"+monsterCount.get(j)+" )");
            }
        }else{
            System.out.println("    # Empty");
        }
        System.out.println();
        System.out.println("1. Add monster");
        System.out.println("2. Remove monster");
        System.out.println("3. Continue");
        System.out.println();
    }

    /**
     * Este método mostrará todos los monstruos que le pasemos en una lista
     * @param monsterList la lista de monstruos a mostrar
     */
    public void showMonsterList(LinkedList<Monster> monsterList) {
        for(int i = 0; i < monsterList.size(); i++){
            System.out.println((i+1)+". "+monsterList.get(i).getName()+" ("+monsterList.get(i).getChallenge()+")");
        }
    }

    /**
     * Este método mostrará todas la aventuras que tenemos disponibles
     * @param adventureList el listado de aventuras
     */
    public void showAdventureList(LinkedList<Adventure> adventureList) {
        System.out.println("Available adventures: ");
        for(int i = 0; i < adventureList.size(); i++){
            System.out.println("   "+(i+1)+". "+adventureList.get(i).getName());
        }
        System.out.println();
    }

    /**
     * Este método nos permite mostrar la pantalla de selección de personajes que se van a escoger para formar nuestra party
     * @param characters es el listado de todos los personajes de la base de datos
     * @param party es el listado de personajes que ya pertenecen a nuestra party
     * @param index es el índice que nos dice cuantos personajes hay ya dentro de nuestra party
     * @param numCharsInParty es el número de personajes que formaran nuestra party
     */
    public void showSelectionCharacterScreen(LinkedList<Char> characters, LinkedList<Char> party, int index, int numCharsInParty) {
        System.out.println();
        System.out.println("--------------------------------");
        System.out.println("Your party ("+index+"/"+numCharsInParty+"):");
        int num = 0;
        for(int i = 0; party.size() > i; i++){
            System.out.println("   " + (i + 1) + ". " + party.get(i).getName());
            num = i+1;
        }
        for(int i = 0; i < numCharsInParty - party.size(); i++) {
                System.out.println("   " + (i + 1 + num) + ". Empty");
        }
        System.out.println("--------------------------------");
        System.out.println("Available characters: ");
        for(int i = 0; i < characters.size(); i++){
            System.out.println("   "+(i+1)+". "+characters.get(i).getName());
        }
    }

    /**
     * Este método mostrara un listado de todas los monstruos que van a haber en nuestro combate
     * @param combat es el combate del cual extraemos nuestros monstruos
     * @param currentCombat es un número que no indica por que combate de la aventura estamos
     */
    public void showCombatMonsterList(Combat combat, int currentCombat) {
        System.out.println("----------------------");
        System.out.println("Starting Encounter "+currentCombat+":");
        LinkedList<Monster> shownMonsters = new LinkedList<>();
        LinkedList<Integer> countMonsters = new LinkedList<>();
        for(int i = 0; i < combat.getMonsters().size(); i++){
            boolean found = false;
            for(int j = 0; j < shownMonsters.size(); j++){
                if(shownMonsters.get(j).getName().equals(combat.getMonsters().get(i).getName())){
                    countMonsters.set(j, countMonsters.get(j) + 1);
                    found = true;
                }
            }
            if(!found){
                shownMonsters.add(combat.getMonsters().get(i));
                countMonsters.add(1);
            }
        }

        for(int i = 0; i < shownMonsters.size(); i++){
            System.out.println("    - "+countMonsters.get(i)+"x "+shownMonsters.get(i).getName());
        }
    }

    /**
     * Este método mostrará todas las acciones de descanso nuestra party
     * @param party el listado de personajes que harán una acción de descanso
     */
    public void preparationStageShow(LinkedList<Char> party) {
        for (Char aChar : party) {
            switch (aChar.getType()) {
                case "Adventurer" -> System.out.println(aChar.getName() + " uses Self-Motivated. Their Spirit increases in +1.");
                case "Mage" -> System.out.println(aChar.getName() + " uses Mage shield. Their regenerate the shield.");
                case "Cleric" -> System.out.println(aChar.getName() + " uses Blessing of good luck. Mind stat has been increased");
            }
        }
    }

    /**
     * Este método nos permite mostrar por pantalla el resultado de la etapa de cálculo de iniciativa y mostrará en
     * orden cuál ha sido el resultado de esta etapa.
     * @param entitiesOnGame son las entidades que vamos a analizar y mostrar su iniciativa
     */
    public void showInitiativeOrder(LinkedList<Entity> entitiesOnGame) {
        System.out.println();
        System.out.println("Rolling initiative...");
        for (Entity entity : entitiesOnGame) {
            System.out.println("    - " + entity.getCurrentInitiative() + "  " + entity.getName());
        }
        System.out.println();
    }

    /**
     * Este método se utiliza para poder mostrar él inició de la etapa de combate.
     * @param party es el listado de personajes que pertenecen a la party
     * @param round es el numero de rondas realizada para este combate
     */
    public void showCombatStageStart(LinkedList<Char> party, int round) {
        System.out.println("--------------------");
        System.out.println("*** Combat stage ***");
        System.out.println("--------------------");
        System.out.println("Round "+round+":");
        System.out.println("Party:");
        for (Char aChar : party) {
            if (aChar instanceof Mage) {
                System.out.println("    - " + aChar.getName() + "    " + aChar.getHitPoints() + " / " + aChar.getMaxLife() + " hit points (Shield: "+((Mage) aChar).getShield()+")");
            } else {
                System.out.println("    - " + aChar.getName() + "    " + aChar.getHitPoints() + " / " + aChar.getMaxLife() + " hit points");
            }
        }
    }

    /**
     * Este método mostrará el ataque de alguna de nuestras entidades siendo indiferente si esta es un personaje o un
     * monstruo.
     * @param entity Esta es la entidad atacante
     * @param objective Esta será el objetivo del ataque
     * @param dmgDone Este será el daño realizado en esta acción
     * @param critical Este será el indicador de crítico del ataque [3 => Critico; 2=> Acierto; 1 => Fallo]
     */
    public void showAttack(Entity entity, Entity objective, int dmgDone, int critical) {
        if (entity instanceof Char) {
            switch (((Char) entity).getType()) {
                case "Adventurer" ->
                        System.out.print(entity.getName() + " attacks " + objective.getName() + " with Sword slash. ");
                case "Mage" -> {
                    if (objective == null) {
                        System.out.print(entity.getName() + " attacks all the monsters with Fireball. ");
                    } else {
                        System.out.print(entity.getName() + " attacks " + objective.getName() + " with Arcane missile. ");
                    }
                }
                case "Cleric" -> {
                    if (objective instanceof Char) {
                        System.out.print(entity.getName() + " heals " + objective.getName() + " with Prayer of healing. ");
                    } else {
                        System.out.print(entity.getName() + " attacks " + objective.getName() + " with Not on my watch. ");
                    }
                }
            }
        }else{
            System.out.print(entity.getName()+" attacks "+objective.getName()+". ");
        }

        if(!(objective instanceof Char && entity instanceof Char)) {
            if (dmgDone != 0) {
                if (critical == 2) {
                    System.out.println("Hits and deals " + dmgDone + " " + entity.getDamageType());
                } else if (critical == 3) {
                    System.out.println("Critical hit and deals " + dmgDone + " " + entity.getDamageType());
                }
            } else {
                System.out.println("Fails and deals 0 " + entity.getDamageType());
            }
        }else{
            System.out.println("Deals "+dmgDone+" of healing.");
        }

        if(objective != null) {
            if (objective.getHitPoints() <= 0) {
                if (objective instanceof Monster) {
                    System.out.print(objective.getName() + " dies.");
                } else {
                    System.out.print(objective.getName() + " falls unconscious.");
                }
            }
        }
        System.out.println();

    }

    /**
     * Este método nos mostrará los descansos de nuestros personajes que se harán entre combates.
     * @param aChar el personaje que hace la acción de descanso
     * @param amount la cantidad de (la acción que haga cada personaje en su descanso [Ex: Curar, dar espíritu, ...]) realizada
     */
    public void showBrake(Char aChar, int amount) {
        switch (aChar.getType()){
            case "Adventurer" -> System.out.println(aChar.getName()+" uses Bandage time. Heals "+amount+" hit points");
            case "Cleric" -> System.out.println(aChar.getName()+" uses Prayer of self-healing. Heals "+amount+" hit points");
        }

    }
}

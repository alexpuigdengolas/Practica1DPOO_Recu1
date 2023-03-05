package presentation;

import business.Char;
import business.Combat;
import business.entities.Entity;
import business.entities.monster.Monster;
import business.adventure.Adventure;

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
    public void showChararcterInfo(Char character) {
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

    public void showMonsterList(LinkedList<Monster> monsterList) {
        for(int i = 0; i < monsterList.size(); i++){
            System.out.println((i+1)+". "+monsterList.get(i).getName()+" ("+monsterList.get(i).getChallenge()+")");
        }
    }

    public void showAdventureListe(LinkedList<Adventure> adventureList) {
        System.out.println("Available adventures: ");
        for(int i = 0; i < adventureList.size(); i++){
            System.out.println("   "+(i+1)+". "+adventureList.get(i).getName());
        }
        System.out.println();
    }

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

    public void showCombatMonsterList(Combat combat, int currentCombat) {
        System.out.println("----------------------");
        System.out.println("Starting Encounter "+currentCombat+":");
        LinkedList<Monster> shownMonsters = new LinkedList<>();
        LinkedList<Integer> countMonsters = new LinkedList<>();
        for(int i = 0; i < combat.getMonsters().size(); i++){
            if(shownMonsters.contains(combat.getMonsters().get(i))){
                countMonsters.set(i, countMonsters.get(i) + 1);
            }else{
                shownMonsters.add(combat.getMonsters().get(i));
                countMonsters.add(1);
            }
        }

        for(int i = 0; i < shownMonsters.size(); i++){
            System.out.println("    - "+countMonsters.get(i)+"x "+shownMonsters.get(i).getName());
        }
    }

    public void preparationStageShow(LinkedList<Char> party) {
        for (Char aChar : party) {
            switch (aChar.getType()) {
                case "Adventurer":
                    System.out.println(aChar.getName() + " uses Self-Motivated. Their Spirit increases in +1.");
                    break;
            }
        }
    }

    public void showInitiativeOrder(LinkedList<Entity> entitiesOnGame) {
        System.out.println();
        System.out.println("Rolling initiative...");
        for (Entity entity : entitiesOnGame) {
            System.out.println("    - " + entity.getCurrentInitiative() + "  " + entity.getName());
        }
        System.out.println();
    }

    public void showCombatStageStart(LinkedList<Char> party, int round) {
        System.out.println("--------------------");
        System.out.println("*** Combat stage ***");
        System.out.println("--------------------");
        System.out.println("Round "+round+":");
        System.out.println("Party:");
        for (Char aChar : party) {
            System.out.println("    - " + aChar.getName() + "    " + aChar.getHitPoints() + " / " + aChar.getMaxLife() + " hit points");
        }
    }

    public void showAttack(Entity entity, Entity objective, int dmgDone, int critical) {
        System.out.print(entity.getName()+" attacks "+objective.getName());
        switch (entity.getClass().getSimpleName()){
            case "Adventurer":
                System.out.print(" with Sword slash.");
                break;
        }
        System.out.println();
        if(dmgDone != 0) {
            if (critical == 2) {
                System.out.println("Hits and deals " + dmgDone + " " + entity.getDamageType());
            } else if(critical == 3){
                System.out.println("Critical hit and deals " + dmgDone + " " + entity.getDamageType());
            }
        }else{
            System.out.println("Fails and deals 0 "+ entity.getDamageType());
        }
        if(objective.getHitPoints() <= 0){
            if(objective.getClass().getSimpleName().equals("Monster")){
                System.out.println(objective.getName()+" dies.");
            }else{
                System.out.println(objective.getName()+" falls unconscious.");
            }
        }
        System.out.println();

    }
}

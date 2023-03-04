package presentation;

import business.Char;
import business.Combat;
import business.Monster;

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
     * Este método nos permite controlar que se nos pase un double
     * @param message el mensaje que queremos mostrar antes de pedir el double
     * @return el double introducido por el usuario
     */
    public double askForDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("That's not a valid double, try again!");
            } finally {
                scanner.nextLine();
            }
        }
    }

    /**
     * Este método nos permite mostrar varios mensajes tabulados
     * @param messages el listado de mensajes que queremos mostrar
     */
    public void showTabulatedList(String[] messages) {
        for (String message : messages) {
            showTabulatedMessage(message);
        }
    }

    /**
     * Este método nos permite mostrar un mensaje tabulado
     * @param message el mensaje que queremos mostrar
     */
    public void showTabulatedMessage(String message) {
        System.out.println("\t" + message);
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

    public void showCombatCreationMenu(int i, int numCombats, Combat combat) {
        System.out.println("* Encounter "+i+" / "+numCombats);
        System.out.println("* Monsters in encounter");
        if(!(combat.getMonsters().isEmpty())) {
            for (int j = 0; j < combat.getMonsters().size(); j++) {
                //TODO: Mostrar todos los mosntruos del mismo tipo juntos
                System.out.println("    # "+combat.getMonsters().get(j).getName());
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
}

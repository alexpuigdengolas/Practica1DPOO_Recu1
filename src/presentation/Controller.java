package presentation;

import business.BusinessController;
import business.character.Char;

import java.util.LinkedList;

/**
 * Clase que servirá de controlador principal del programa
 */
public class Controller {

    /**
     * Listado de constantes para poder gestionar el menu principal
     */
    private static final int ADD_CHARACTER = 1;
    private static final int LIST_CHARACTER = 2;
    private static final int ADD_ADVENTURE = 3;
    private static final int PLAY_ADVENTURE = 4;
    private static final int EXIT_MENU = 5;

    /**
     * Manager de todas las vistas
     */
    private final ViewManager viewManager;

    /**
     * Manager de la gestión interna del código
     */
    private BusinessController businessController;

    /**
     * Constructor de esta clase. Conecta el ViewManager con esta clase para poder gestionar
     * todas las entradas que puedan poder los usuarios
     * @param viewManager Manager de todas las vistas
     */
    public Controller(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    /**
     * Este método contiene el bucle de funcionamiento principal del código
     * Servirá para comprobar que durante el funcionamiento del código se puedan leer todos los archivos necesarios
     * y seguido a esto lanzara el menu principal
     */
    public void run(){
        int option;
        if(setBusinessController()) {
            do {

                viewManager.showMenu();
                option = viewManager.askForInteger("Your answer: ");

                runOption(option);
            } while (option != EXIT_MENU);
        }
    }

    /**
     * Este método lo utilizamos para poder declarar nuestro BusinessController. Aun asi, este método es mucho
     * más que un simple getter. Como podemos observar, pedirá el valor inicial para saber que base de datos quiere
     * emplear el usuario y comprobara que esta sea funcional, esto es para evitar la creación de un BusinessController
     * que no sea funcional al cien por cien.
     * @return un boolean que dirá si estos archivos son accesibles o no.
     */
    private boolean setBusinessController() {
        do {
            viewManager.DAOMenu();
            int option = viewManager.askForInteger("-> Answer: ");
            if(option < 1 || option > 2){
                viewManager.showMessage("You have to select a correct answer [1..2]");
            }else {
                this.businessController = new BusinessController(option);
                viewManager.spacing();
                viewManager.showMessage("Loading data...");
                boolean answer = businessController.fileExists();
                if (answer) {
                    viewManager.showMessage("Data was successfully loaded.");
                    return true;
                } else {
                    viewManager.showMessage("One of the files does not exist.");
                    return false;
                }
            }
        }while(true);
    }

    /**
     * Este método contiene el funcionamiento del menu principal de cada partida
     * @param option es la opción que nos permite decidir cuál será la siguiente función que se le pedirá al código
     */
    private void runOption(int option) {
        switch (option) {
            case ADD_CHARACTER -> addCharacter();
            case LIST_CHARACTER -> listCharacters();
            case ADD_ADVENTURE -> addAdventure();
            case PLAY_ADVENTURE -> playAdventure();
            case EXIT_MENU -> exitMenu();
            default -> viewManager.showMessage("Please select an answer among the ones you can [1..5]");
        }
    }

    /**
     * Este método servirá para añadir un personaje a nuestra base de datos, enviando la información necesaria a
     * BusinessController que se encargara de hacer las comprobaciones necesarias para poder crear un nuevo personaje
     */
    private void addCharacter() {
        //Pidiendo el nombre del personaje al usuario
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Oh, so you are new to this land.");
        viewManager.showMessage("What’s your name?");
        viewManager.spacing();
        String charName = viewManager.askForString("-> Enter your name: ");

        //Pidiendo el nombre del usuario
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Hello,"+ charName +", be welcome.");
        viewManager.showMessage("And now, if I may break the fourth wall, who is your Player?");
        viewManager.spacing();
        String playersName = viewManager.askForString("-> Enter the player’s name: ");

        //Pidiendo el nivel del personaje
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: I see, I see...");
        viewManager.showMessage("Now, are you an experienced adventurer?");
        viewManager.spacing();
        int level = viewManager.askForInteger("-> Enter the character’s level [1..10]: ");
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Oh, so you are level "+level+"!");


        boolean charCreated = businessController.checkCharacterName(charName);

        if(charCreated){
            charName = businessController.capitalizeString(charName);

            //Calculamos las stats
            viewManager.showMessage("Great, let me get a closer look at you...");
            viewManager.spacing();
            Char character = new Char(charName, playersName, level);
            viewManager.showMessage("Generating your stats...");
            //Cuerpo
            int[] dices = businessController.generateCharacterStat("Body", character);
            viewManager.showMessage("Body:  You rolled "+(dices[0]+dices[1])+" ("+dices[0]+" and "+dices[1]+").");
            //Mente
            dices = businessController.generateCharacterStat("Mind", character);
            viewManager.showMessage("Mind:  You rolled "+(dices[0]+dices[1])+" ("+dices[0]+" and "+dices[1]+").");
            //Alma
            dices = businessController.generateCharacterStat("Spirit", character);
            viewManager.showMessage("Spirit:  You rolled "+(dices[0]+dices[1])+" ("+dices[0]+" and "+dices[1]+").");

            //Mostramos el resultado de las tiradas
            viewManager.spacing();
            viewManager.showMessage("   - Body: "+character.getBody());
            viewManager.showMessage("   - Mind: "+character.getMind());
            viewManager.showMessage("   - Spirit: "+character.getSpirit());
            viewManager.spacing();

            //Seleccionamos la clase
            viewManager.showMessage("Tavern keeper: “Looking good!”");
            viewManager.showMessage("“And, lastly, ?”");
            viewManager.spacing();
            Char definitiveChar;
            do {
                String type = viewManager.askForString("-> Enter the character’s initial class [Adventurer, Cleric, Mage]: ");
                viewManager.spacing();
                viewManager.showMessage("Tavern keeper: Any decent party needs one of those.");
                viewManager.showMessage("I guess that means you’re a " + type + " by now, nice!");
                definitiveChar = businessController.generateClassifiedChar(character, type);
                if(character == definitiveChar){
                    viewManager.showMessage("You have not selected an adecuate class");
                }
            }while(character == definitiveChar);

            viewManager.showMessage("The new character "+charName+" has been created.");

            //Añadimos el personaje a la base de datos seleccionada
            businessController.updateCharacterList(definitiveChar);
        }else{
            //Enviamos un mensaje de error porque el nombre ya existe
            viewManager.showMessage("It looks like there is a problem with the character name");
            viewManager.spacing();
        }

    }

    /**
     * Este método se emplea para poder listar todos los personajes que tenemos en la base de datos. Se tendrá en cuenta
     * que estos personajes hayan sido creados por un jugador en específico para solo mostrar esos personajes.
     * Si se selecciona un personaje se mostrará su información y se permitirá eliminar a dicho personaje de la base de
     * datos.
     */
    private void listCharacters() {
        //Seleccionamos el nombre del jugador a investigar y mostramos los resultados
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Lads! They want to see you!");
        viewManager.showMessage("Who piques your interest?");
        viewManager.spacing();
        String player = viewManager.askForString("-> Enter the name of the Player to filter: ");
        viewManager.spacing();

        //Seleccionamos alguno de los personajes
        LinkedList<Char> characters = businessController.getCharacterListByPlayer(player);
        viewManager.showCharacterList(characters);
        viewManager.spacing();

        boolean charSelectedStatus = false;
        do {
            int charSelected = viewManager.askForInteger("Who would you like to meet [0.."+characters.size()+"]: ") - 1;
            if (charSelected >= 1 && charSelected < characters.size()) {
                charSelectedStatus = true;
                viewManager.showChararcterInfo(characters.get(charSelected));

                //Preguntamos si desean eliminar al personaje seleccionado
                viewManager.showMessage("[Enter name to delete, or press enter to cancel]");
                String charNameIntroduced = viewManager.askForString("Do you want to delete " + characters.get(charSelected).getName() + "? ");
                if (charNameIntroduced.equals(characters.get(charSelected).getName())) {
                    businessController.deleteCharacter(characters.get(charSelected).getName());
                    viewManager.showMessage("Tavern keeper: I’m sorry kiddo, but you have to leave.");
                    viewManager.spacing();
                    viewManager.showMessage("Character " + characters.get(charSelected).getName() + " left the Guild.");
                    viewManager.spacing();
                }
            } else if (charSelected != 0) {
                viewManager.showMessage("You have to select a number between [0.."+characters.size()+"]");
            }
        }while (!charSelectedStatus);

    }

    private void addAdventure() {

    }

    private void playAdventure() {

    }

    /**
     * Este método nos mostrará que el funcionamiento del programa está por concluir
     */
    private void exitMenu() {
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Are you leaving already? See you soon, adventurer.");
    }
}

package presentation;

import business.BusinessController;
import business.character.Character;

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
    private ViewManager viewManager;

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
     * más que un simple getter. Como podemos observar, pedira el valor inicial para saber que base de datos quiere
     * emplear el ususario y comprobara que esta sea funcional, esto es para evitar la creación de un BusinessController
     * que no sea funcional al cien por cien.
     * @return un boleano que dira si estos archivos son accesibles o no.
     */
    private boolean setBusinessController() {
        viewManager.DAOMenu();
        int option = viewManager.askForInteger("-> Answer: ");
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

    /**
     * Este metodo contiene el funcionamiento del menu principal de cada partida
     * @param option es la opcion que nos permite decidir cual sera la siguiente funcion que se le pedira al codigo
     */
    private void runOption(int option) {
        switch (option) {
            case ADD_CHARACTER -> addCharacter();
            case LIST_CHARACTER -> listCharacters();
            case ADD_ADVENTURE -> addAdventure();
            case PLAY_ADVENTURE -> playAdventure();
            case EXIT_MENU -> exitMenu();
            default -> viewManager.showMessage("Wrong option. Enter a number from 1 to 6, both included");
        }
    }

    /**
     * Este metodo servira para añadir un personaje a nuestra base de datos, enviando la informaicon necesaria a
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
            //Calculamos las stats
            viewManager.showMessage("Great, let me get a closer look at you...");
            viewManager.spacing();
            Character character = new Character(charName, playersName, level);
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
            viewManager.showMessage("The new character "+charName+" has been created.");

            //Añadimos el personaje a la base de datos seleccionada
            businessController.updateCharacterList(character);
        }else{
            //Enviamos un mensaje de error porque el nombre ya existe
            viewManager.showMessage("It looks like there is a character that already uses that name");
            viewManager.spacing();
        }

    }

    private void listCharacters() {

    }

    private void addAdventure() {

    }

    private void playAdventure() {

    }


    private void exitMenu() {
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Are you leaving already? See you soon, adventurer.");
    }
}

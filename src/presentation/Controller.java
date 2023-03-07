package presentation;

import business.BusinessController;
import business.entities.characters.Char;
import business.Combat;
import business.adventure.Adventure;
import business.entities.Entity;
import business.entities.monster.Monster;

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
            int xp = (level*100)-101;
            if(xp < 0) xp = 0;
            Char character = new Char(charName, playersName, xp);
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
            viewManager.showMessage("Tavern keeper: Looking good!");
            viewManager.showMessage("And, lastly, ?");
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
            int charSelected = viewManager.askForInteger("Who would you like to meet [0.."+characters.size()+"]: ");
            if (charSelected >= 1 && charSelected <= characters.size()) {
                charSelectedStatus = true;
                viewManager.showCharacterInfo(characters.get(charSelected-1));

                //Preguntamos si desean eliminar al personaje seleccionado
                viewManager.showMessage("[Enter name to delete, or press enter to cancel]");
                String charNameIntroduced = viewManager.askForString("Do you want to delete " + characters.get(charSelected-1).getName() + "? ");
                if (charNameIntroduced.equals(characters.get(charSelected-1).getName())) {
                    businessController.deleteCharacter(characters.get(charSelected-1).getName());
                    viewManager.showMessage("Tavern keeper: I’m sorry kiddo, but you have to leave.");
                    viewManager.spacing();
                    viewManager.showMessage("Character " + characters.get(charSelected-1).getName() + " left the Guild.");
                    viewManager.spacing();
                }
            } else if (charSelected != 0) {
                viewManager.showMessage("You have to select a number between [0.."+characters.size()+"]");
            }else{
                charSelectedStatus = true;
            }
        }while (!charSelectedStatus);

    }

    /**
     * Este método va a encargarse de que se puedan crear aventuras nuevas mostrando los menus necesarios para poder
     * acceder a esta function del menu principal
     */
    private void addAdventure() {
        viewManager.showMessage("Tavern keeper: Planning an adventure? Good luck with that!");
        viewManager.spacing();
        String name = viewManager.askForString("-> Name your adventure: ");
        if(businessController.checkAdventureName(name)){
            viewManager.spacing();
            viewManager.showMessage("Tavern keeper: You plan to undertake "+name+", really?");
            viewManager.showMessage("How long will that take?");
            viewManager.spacing();
            int numCombats;
            do {
                numCombats = viewManager.askForInteger("-> How many encounters do you want [1..4]: ");
            }while(numCombats < 1 || numCombats > 4);
            viewManager.spacing();
            viewManager.showMessage("Tavern keeper: "+numCombats+" encounters? That is too much for me...");
            viewManager.spacing();
            viewManager.spacing();

            LinkedList<Combat> combats = new LinkedList<>();
            Adventure adventure = new Adventure(name, numCombats);

            for(int i = 0; i < numCombats; i++){
                Combat newCombat = new Combat();
                while(true) {
                    LinkedList<String> monsterName = new LinkedList<>();
                    LinkedList<Integer> monsterCount = new LinkedList<>();
                    viewManager.showCombatCreationMenu(i + 1, numCombats, newCombat, monsterName, monsterCount);
                    int option;
                    do {
                        option = viewManager.askForInteger("-> Enter an option [1..3]: ");
                    } while (option < 1 || option > 3);

                    switch (option) {
                        case 1 -> addMonster(newCombat);

                        case 2 -> removeMonster(newCombat, monsterName);
                    }
                    if(option == 3){
                        break;
                    }
                }
                combats.add(newCombat);
            }

            adventure.setCombats(combats);
            businessController.addAdventure(adventure);
            viewManager.spacing();
            viewManager.showMessage("Tavern keeper: Great plan lad! I hope you won’t die!");
            viewManager.spacing();
            viewManager.showMessage("The new adventure "+adventure.getName()+" has been created.");
        }else{
            viewManager.showMessage("There is already a Adventure named "+name);
            viewManager.spacing();
        }

    }

    /**
     * Este método servirá para añadir monstruos dentro de un combate. Este método lleva a cabo esta opción siguiendo
     * todas las instrucciones de nuestro enunciado.
     * @param combat el combate en el que queremos añadir los monstruos
     */
    private void addMonster(Combat combat) {
        viewManager.spacing();
        LinkedList<Monster> monsters = businessController.getMonsterList();
        viewManager.showMonsterList(monsters);
        viewManager.spacing();
        boolean bossIn = false;
        for(int i = 0; i < combat.getMonsters().size(); i++){
            if(combat.getMonsters().get(i).getChallenge().equals("Boss")){
                bossIn = true;
                break;
            }
        }

        int monsterChosen = viewManager.askForInteger("-> Choose a monster to add [1.."+monsters.size()+"]: ") -1;

        if(monsters.get(monsterChosen).getChallenge().equals("Boss") && bossIn){
            viewManager.showMessage("The monster selected is a classified as a Boss and this combat already has one");
        }else {
            if (!monsters.get(monsterChosen).getChallenge().equals("Boss")) {
                int monsterAmount = viewManager.askForInteger("-> How many " + monsters.get(monsterChosen).getName() + "(s) do you want to add: ");
                for (int i = 0; i < monsterAmount; i++) {
                    combat.getMonsters().add(monsters.get(monsterChosen));
                }
            } else {
                viewManager.showMessage("The monster selected is a classified as a Boss and there can only be one per combat");
                combat.getMonsters().add(monsters.get(monsterChosen));
            }
        }
        viewManager.spacing();


    }

    /**
     * Este método servirá para eliminar monstruos dentro de un combate. Este método lleva a cabo esta opción siguiendo
     * todas las instrucciones de nuestro enunciado.
     * @param combat El combate en el que queremos añadir los monstruos
     * @param monsterName Listado con los nombres de todos los monstruos del combate
     */
    private void removeMonster(Combat combat, LinkedList<String> monsterName) {
        if(combat.getMonsters().isEmpty()){
            viewManager.spacing();
            viewManager.showMessage("There are no monsters at the moment in this combat");
            viewManager.spacing();
        }else{
            int numMonsters = 0;
            int monsterSelected;
            do {
                 monsterSelected = viewManager.askForInteger("-> Which monster do you want to delete: ") - 1;
                 if(monsterSelected < 0 || monsterSelected > monsterName.size()){
                     viewManager.showMessage("That is no an existing enemy");
                 }
            }while (monsterSelected < 0 || monsterSelected > monsterName.size());
            String monsterType = monsterName.get(monsterSelected);
            for(int i = combat.getMonsters().size()-1; i >= 0; i--){
                if(combat.getMonsters().get(i).getName().equals(monsterType)){
                    combat.getMonsters().remove(i);
                    numMonsters++;
                }
            }
            viewManager.spacing();
            viewManager.showMessage(numMonsters+" "+monsterType+" were removed from the encounter.");
            viewManager.spacing();
        }
    }

    /**
     * Este método servirá para poder jugar una aventura siguiendo todas las especificaciones de nuestro enunciado
     */
    private void playAdventure() {
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: So, you are looking to go on an adventure?");
        viewManager.showMessage("Where do you fancy going?");
        viewManager.spacing();
        LinkedList<Adventure> adventureList = businessController.getAdventureList();
        for (Adventure adventure : adventureList) {
            adventure.setParty(new LinkedList<>());
        }
        viewManager.showAdventureList(adventureList);
        int adventureSelected;
        do {
            adventureSelected = viewManager.askForInteger("-> Choose an adventure: ") -1;
            if(adventureSelected < 0 || adventureSelected >= adventureList.size()){
                viewManager.showMessage("You haven't selected any real adventure, please try again");
            }
        }while (adventureSelected < 0 || adventureSelected >= adventureList.size());

        Adventure adventure = adventureList.get(adventureSelected);
        characterSelectionScreen(adventure);

        boolean adventureLost = false;
        //Jugar partida
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Great, good luck on your adventure lads!");
        viewManager.spacing();
        viewManager.showMessage("The "+adventure.getName()+" will start soon...");

        //Preparation Stage
        businessController.preparationStage(adventure.getParty());
        viewManager.preparationStageShow(adventure.getParty());

        //Jugar Combate
        for(int i = 0; i < adventure.getNumCombat(); i++){

            //Show Monsters
            viewManager.showCombatMonsterList(adventure.getCombats().get(i), i+1);
            viewManager.spacing();
            viewManager.spacing();

            //Calculating Initiative
            LinkedList<Entity> entitiesOnGame = new LinkedList<>();
            entitiesOnGame.addAll(adventure.getParty());
            entitiesOnGame.addAll(adventure.getCombats().get(i).getMonsters());
            businessController.calculateInitiative(entitiesOnGame);
            viewManager.showInitiativeOrder(entitiesOnGame);

            //Combat Stage
            boolean combatDone = false, combatOver = false;
            int round = 1;
            do {
                //Show Chars Life
                viewManager.showCombatStageStart(adventure.getParty(), round);

                //Attack
                for (Entity entity : entitiesOnGame) {
                    int dmgDone;
                    int critical;
                    if(entity.getHitPoints() > 0) {
                        combatDone = true;
                        combatOver  = true;

                        Entity objective = businessController.objectiveSelection(entity, adventure.getParty(), adventure.getCombats().get(i).getMonsters());
                        critical = businessController.attackCritical(entity);
                        dmgDone = businessController.attackStage(entity, objective, entitiesOnGame, critical);
                        viewManager.showAttack(entity, objective, dmgDone, critical);

                        for (int x = 0; x < adventure.getParty().size(); x++) {
                            if (adventure.getParty().get(x).getHitPoints() > 0) {
                                combatOver = false;
                                break;
                            }
                        }

                        for (int y = 0; y < adventure.getCombats().get(i).getMonsters().size(); y++) {
                            if (adventure.getCombats().get(i).getMonsters().get(y).getHitPoints() > 0) {
                                combatDone = false;
                                break;
                            }
                        }
                    }


                }
                viewManager.showMessage("End of round "+round);
                round++;
            }while(!combatDone && !combatOver);

            //End of combat
            if(combatDone){
                viewManager.showMessage("All enemies are defeated.");
                int xpSum = businessController.getXpEarned(adventure, i);
                for(int j = 0; j < adventure.getParty().size(); j++){
                    int oldXp = adventure.getParty().get(j).getLevel();
                    adventure.getParty().get(j).setXp(adventure.getParty().get(j).getXp() + xpSum);
                    if(adventure.getParty().get(j).getXp() > 999){
                        adventure.getParty().get(j).setXp(999);
                    }
                    viewManager.showMessage(adventure.getParty().get(j).getName()+" gains "+xpSum+" xp.");

                    if(Math.floor(oldXp) < Math.floor(adventure.getParty().get(j).getLevel())){
                        viewManager.showMessage(adventure.getParty().get(j).getName()+" levels up. They are now lvl "+adventure.getParty().get(j).getLevel()+"!");
                    }
                }
            }
            if(combatOver){
                viewManager.showMessage("Tavern keeper: Lad, wake up. Yes, your party fell unconscious.");
                viewManager.showMessage("Don’t worry, you are safe back at the Tavern.");
                adventureLost = true;
                break;
            }

            //Short Rest
            viewManager.spacing();
            viewManager.showMessage("------------------------");
            viewManager.showMessage("*** Short rest stage ***");
            viewManager.showMessage("------------------------");
            for(int j = 0; j < adventure.getParty().size(); j++) {
                int amount = businessController.shortBrake(adventure.getParty().get(j));
                viewManager.showBrake(adventure.getParty().get(j), amount);
            }


        }
        businessController.stopPreparationStage(adventure.getParty());
        viewManager.spacing();
        if(!adventureLost){
            System.out.println("Congratulations, your party completed "+adventure.getName());
        }

        businessController.setCharsAfterGame(adventure.getParty());
    }

    /**
     * Este método permitirá escoger y mostrar todos los personajes seleccionables
     * @param adventure la aventura en la que participan nuestros personajes
     */
    private void characterSelectionScreen(Adventure adventure){
        viewManager.showMessage("Tavern keeper: "+adventure.getName()+" it is!");
        viewManager.showMessage("And how many people shall join you?");
        viewManager.spacing();
        int numPartyMembers;
        do {
            numPartyMembers = viewManager.askForInteger("-> Choose a number of characters [3..5]: ");
            if(numPartyMembers < 3 || numPartyMembers > 5){
                viewManager.showMessage("You have to select a good number");
            }
        }while (numPartyMembers < 3 || numPartyMembers > 5);
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Great, "+numPartyMembers+" it is.");
        viewManager.showMessage("Who among these lads shall join you?");
        viewManager.spacing();
        LinkedList<Char> characters = businessController.getCharacterList();
        int index = 1;
        do {
            viewManager.showSelectionCharacterScreen(characters, adventure.getParty(), index, numPartyMembers);
            int characterSelected;
            boolean charNotInParty = false;
            do {

                characterSelected = viewManager.askForInteger("-> Choose character " + index + " in your party: ") -1;
                if(!(characterSelected < 0 || characterSelected >= characters.size())) {
                    if (adventure.getParty().contains(characters.get(characterSelected))) {
                        viewManager.showMessage("This character already belongs to the party");
                    } else {
                        charNotInParty = true;
                    }
                }else{
                    viewManager.showMessage("You have to select an available member to the party");
                }
            }while((characterSelected < 0 || characterSelected >= characters.size()) || !charNotInParty);

            adventure.getParty().add(characters.get(characterSelected));

            index++;
        }while (adventure.getParty().size() < numPartyMembers);
    }

    /**
     * Este método nos mostrará que el funcionamiento del programa está por concluir
     */
    private void exitMenu() {
        viewManager.spacing();
        viewManager.showMessage("Tavern keeper: Are you leaving already? See you soon, adventurer.");
    }
}

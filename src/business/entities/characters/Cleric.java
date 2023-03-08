package business.entities.characters;

import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;

public class Cleric extends Char{

    public Cleric(String name, String player, int level, int body, int mind, int spirit) {
        super(name, player, level);
        this.setBody(body);
        this.setMind(mind);
        this.setSpirit(spirit);
        this.setType("Cleric");
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Psychical");
    }

    public Cleric(Char aChar) {
        super(aChar);
        this.setType("Cleric");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Psychical");
    }

    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(10);
        this.setCurrentInitiative(getSpirit() + dice.throwDice());
    }

    @Override
    public void preparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.addMindPoitns(1);
        }
    }

    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.addMindPoitns(-1);
        }
    }

    @Override
    public int attack(Entity entity, int critical) {
        if(entity instanceof Char){
            Dice dice = new Dice(10);
            return dice.throwDice() + getSpirit();
        }else{
            Dice dice = new Dice(4);
            int dmgDone = dice.throwDice() + getSpirit();
            if(critical == 3){
                dmgDone *= 2;
            }else if(critical == 1){
                dmgDone = 0;
            }
            return dmgDone;
        }
    }

    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {
        LinkedList<Char> characters = new LinkedList<>();
        LinkedList<Entity> monsters = new LinkedList<>();

        for (Entity entity : entities) {
            if (entity instanceof Char) {
                characters.add((Char) entity);
            } else {
                monsters.add(entity);
            }
        }

        for(Char character: characters){
            if(character.getHitPoints() < character.getMaxLife()/2){
                return character;
            }
        }

        return monsters.get(0);
    }

    @Override
    public int shortBrake() {
        Dice dice = new Dice(10);
        int healing = dice.throwDice() + getSpirit();
        heal(healing);
        return healing;
    }
}

package business.entities.characters;

import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;
import java.util.Random;

public class Paladin extends Cleric{

    private int mindAmount;

    public Paladin(Char character) {
        super(character);
        this.setType("Paladin");
        this.setBody(character.getBody());
        this.setMind(character.getMind());
        this.setSpirit(character.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Psychical");
    }

    @Override
    public void preparationStage(LinkedList<Char> party) {
        Dice dice = new Dice(3);
        mindAmount = dice.throwDice();
        for (Char aChar : party) {
            aChar.addMindPoitns(mindAmount);
        }
    }

    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        for (Char aChar : party) {
            aChar.addMindPoitns(-mindAmount);
        }
    }

    @Override
    public int attack(Entity entity, int critical) {
        if(entity == null){
            Dice dice = new Dice(10);
            return dice.throwDice() + getSpirit();
        }else{
            Dice dice = new Dice(8);
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
            if(entity.getHitPoints() > 0) {
                if (entity instanceof Char) {
                    characters.add((Char) entity);
                } else {
                    monsters.add(entity);
                }
            }
        }

        for(Char character: characters){
            if(character.getHitPoints() < character.getMaxLife()/2){
                return null;
            }
        }

        if(monsters.size() == 0){
            return null;
        }else {
            Random random = new Random();
            int num = random.nextInt(monsters.size());
            return monsters.get(num);
        }
    }

    @Override
    public int shortBrake() {
        Dice dice = new Dice(10);
        int healing = dice.throwDice() + getSpirit();
        heal(healing);
        return healing;
    }

    @Override
    public void getDamaged(int dmgDone, String damageType) {

        if(damageType.equals(this.getDamageType())){
            dmgDone /= 2;
        }
        setHitPoints(getHitPoints() - (dmgDone)/2);
        if(getHitPoints() < 0){
            setHitPoints(0);
        }
    }
}

package business.entities.characters;

import business.Dice;
import business.entities.Entity;
import business.entities.monster.Monster;

import java.util.LinkedList;
import java.util.Random;

public class Mage extends Char{

    private int shield = 0;

    public Mage(String name, String player, int level, int body, int mind, int spirit) {
        super(name, player, level);
        this.setBody(body);
        this.setMind(mind);
        this.setSpirit(spirit);
        this.setType("Mage");
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Magical");
    }

    public Mage(Char aChar) {
        super(aChar);
        this.setType("Mage");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Magical");
    }

    public int getShield() {
        return shield;
    }

    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(20);
        this.setCurrentInitiative(getMind() + dice.throwDice());
    }

    @Override
    public void preparationStage(LinkedList<Char> party) {
        Dice dice = new Dice(6);
        shield = (dice.throwDice() + getMind()) * getLevel();
    }

    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        shield = 0;
    }

    @Override
    public int attack(Entity entity, int critical) {
        Dice dice;
        if(entity == null){
            dice = new Dice(4);
        }else{
            dice = new Dice(6);
        }
        return dice.throwDice() + getMind();
    }

    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {
        LinkedList<Entity> monsters = new LinkedList<>();

        for (Entity entity : entities) {
            if (entity instanceof Monster) {
                monsters.add(entity);
            }
        }

        if(monsters.size() < 3){
            Random random = new Random();
            return monsters.get(random.nextInt(monsters.size())+1);
        }
        return null;
    }

    @Override
    public void getDamaged(int dmgDone, String damageType) {
        if(shield > 0) {
            shield -= dmgDone;
            if (shield < 0) {
                setHitPoints(getHitPoints() + shield);
                shield = 0;
            }
        }else if(shield == 0){
            setHitPoints(getHitPoints()-dmgDone);
        }
    }
}

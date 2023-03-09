package business.entities.characters;

import business.Dice;
import business.entities.Entity;

public class Warrior extends Adventurer{
    public Warrior(Char aChar) {
        super(aChar);
        this.setType("Warrior");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    @Override
    public int attack(Entity entity, int critical) {
        Dice dice = new Dice(10);
        int dmgDone = 0;
        if(critical >=2) {
            if(critical == 2){
                dmgDone = dice.throwDice() + this.getBody();
            }
            else {
                dmgDone =  (dice.throwDice() + this.getBody()) * 2;
            }
        }
        return dmgDone;
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

    @Override
    public Char levelUp() {
        if(getLevel() >= 8){
            return new Champion(this);
        }
        return super.levelUp();
    }
}

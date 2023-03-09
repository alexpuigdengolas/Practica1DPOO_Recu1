package business.entities.characters;

import java.util.LinkedList;

public class Champion extends Warrior{
    public Champion(Char aChar) {
        super(aChar);
        this.setType("Champion");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    @Override
    public void calcMaxLife() {
        int maxLife = ((10 + getBody())*this.getLevel())+(getBody()*getLevel());
        this.setMaxLife(maxLife);
    }

    @Override
    public void preparationStage(LinkedList<Char> party) {
        for(Char character: party){
            character.setSpirit(character.getSpirit() + 1);
        }
    }

    @Override
    public void stopPreparationStage(LinkedList<Char> party) {
        for(Char character: party){
            character.setSpirit(character.getSpirit() - 1);
        }
    }

    @Override
    public int shortBrake() {
        int num = getMaxLife()- getHitPoints();
        this.setHitPoints(getMaxLife());
        return num;
    }
}

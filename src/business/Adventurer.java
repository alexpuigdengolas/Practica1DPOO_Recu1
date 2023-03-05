package business;

import business.entities.Entity;
import business.entities.monster.Monster;

import java.util.Comparator;
import java.util.LinkedList;

public class Adventurer extends Char {
    /**
     * Este será el constructor de nuestro personaje
     *
     * @param name   el nombre que le queremos asignar
     * @param player el nombre de nuestro jugador
     * @param xp     la experiencia inicial de nuestro personaje
     */
    public Adventurer(String name, String player, int xp, int mind, int body, int spirit) {
        super(name, player, xp);
        this.setType("Adventurer");
        this.setBody(body);
        this.setMind(mind);
        this.setSpirit(spirit);
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    public Adventurer(Char aChar) {
        super(aChar.getName(), aChar.getPlayer(), aChar.getXp());
        this.setType("Adventurer");
        this.setBody(aChar.getBody());
        this.setMind(aChar.getMind());
        this.setSpirit(aChar.getSpirit());
        this.calcMaxLife();
        this.setHitPoints(this.getMaxLife());
        this.setDamageType("Physical");
    }

    /**
     * Método para calcular la vida maxima actual del personaje
     */
    @Override
    public void calcMaxLife() {
        this.setMaxLife(((10 + getBody())*this.getLevel()));
    }

    @Override
    public void preparationStage() {
        setSpirit(getSpirit()+1);
    }

    @Override
    public void stopPreparationStage() {
        setSpirit(getSpirit()-1);
    }

    @Override
    public void calculateCurrentInitiative() {
        Dice dice = new Dice(12);
        this.setCurrentInitiative(getSpirit() + dice.throwDice());
    }

    @Override
    public Monster selectMonsterObjective(LinkedList<Monster> monsters) {
        Comparator<Monster> hitpointsComparator = new Comparator<Monster>() {
            public int compare(Monster m1, Monster m2) {
                return Integer.compare(m1.getHitPoints(), m2.getHitPoints());
            }
        };

        monsters.sort(hitpointsComparator);
        for (Monster monster : monsters) {
            if (monster.getHitPoints() > 0) {
                return monster;
            }
        }
        return monsters.get(0);
    }

    @Override
    public int attack(Entity entity, int critical) {
        Dice dice = new Dice(6);
        int dmgDone = 0;
        if(critical >=2) {
            if(critical == 2){
                dmgDone = dice.throwDice() + this.getBody();
                entity.setHitPoints(entity.getHitPoints() - dmgDone);
            }
            else {
                dmgDone =  (dice.throwDice() + this.getBody()) * 2;
                entity.setHitPoints(entity.getHitPoints() - dmgDone);
            }
        }

        if(entity.getHitPoints() < 0){
            entity.setHitPoints(0);
        }
        return dmgDone;
    }
}

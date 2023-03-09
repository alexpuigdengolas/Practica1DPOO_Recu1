package business.entities.monster;

import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;

public class Boss extends Monster{
    /**
     * Este es el constructor. Nos permite crear monstruos a partir de otros y gestionar su información.
     *
     * @param monster la información para poder crear nuestros monstruos
     */
    public Boss(Monster monster) {
        super(monster);
    }

    @Override
    public int attack(Entity entity, int critical) {
        String auxDamageDice = this.getDamageDice().replace("d", "");
        Dice dice = new Dice(Integer.parseInt(auxDamageDice));
        return dice.throwDice();
    }

    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {
        return null;
    }
}

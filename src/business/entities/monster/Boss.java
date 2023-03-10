package business.entities.monster;

import business.Dice;
import business.entities.Entity;

import java.util.LinkedList;

/**
 * Esta clase nos permite crear un monstruo de tipo boss y representarlos en una pàrtida
 */
public class Boss extends Monster{
    /**
     * Este es el constructor. Nos permite crear monstruos a partir de otros y gestionar su información.
     *
     * @param monster la información para poder crear nuestros monstruos
     */
    public Boss(Monster monster) {
        super(monster);
    }

    /**
     * Este método permite a los bosses atacar de una forma distinta a los monstruos originales
     * @param entity entidad atacada
     * @param critical entero que marca si el ataque es crítico o se falla.
     * @return la cantidad de daño generada por el Boss
     */
    @Override
    public int attack(Entity entity, int critical) {
        String auxDamageDice = this.getDamageDice().replace("d", "");
        Dice dice = new Dice(Integer.parseInt(auxDamageDice));
        return dice.throwDice();
    }

    /**
     * Este método retornará null para marcar que el Boss atacara a toda la party
     * @param entities el listado de posibles objetivos
     * @return null para marcar que todos los aliados son objetivos del ataque
     */
    @Override
    public Entity selectObjective(LinkedList<Entity> entities) {
        return null;
    }
}

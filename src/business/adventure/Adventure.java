package business.adventure;

import business.Char;
import business.Combat;

import java.util.LinkedList;

public class Adventure {
    /**
     * Nombre de la aventura
     */
    private final String name;
    /**
     * Numero de combates que componen nuestra aventura
     */
    private final int numCombat;
    /**
     * Listado de combates que componen la aventura
     */
    private LinkedList<Combat> combats = new LinkedList<>();
    /**
     * Listado de personajes que participan en la aventura
     */
    private LinkedList<Char> party;

    public Adventure(String name, int numCombat) {
        this.name = name;
        this.numCombat = numCombat;
        party = new LinkedList<>();
    }

    public void setCombats(LinkedList<Combat> combats) {
        this.combats = combats;
    }

    public String getName() {
        return name;
    }

    public int getNumCombat() {
        return numCombat;
    }

    public LinkedList<Combat> getCombats() {
        return combats;
    }

    public LinkedList<Char> getParty() {
        return party;
    }

    public void setParty(LinkedList<Char> party) {
        this.party = party;
    }
}

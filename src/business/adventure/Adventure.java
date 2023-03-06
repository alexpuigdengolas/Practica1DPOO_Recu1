package business.adventure;

import business.Char;
import business.Combat;

import java.util.LinkedList;

/**
 * Esta clase sirve para representar las aventuras
 */
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

    /**
     * Este es el constructor de nuestra clase
     * @param name el nombre de la aventura
     * @param numCombat el número de combates que lo componen
     */
    public Adventure(String name, int numCombat) {
        this.name = name;
        this.numCombat = numCombat;
        party = new LinkedList<>();
    }

    /***
     * Setter de los combates
     * @param combats los combates que queremos settear
     */
    public void setCombats(LinkedList<Combat> combats) {
        this.combats = combats;
    }

    /**
     * Getter del nombre de la aventura
     * @return el nombre de la aventura
     */
    public String getName() {
        return name;
    }

    /**
     * Getter del numero de combates de la aventura
     * @return el numero de combates de la aventura
     */
    public int getNumCombat() {
        return numCombat;
    }

    /**
     * Getter de los combates de la aventura
     * @return los combates de la aventura
     */
    public LinkedList<Combat> getCombats() {
        return combats;
    }

    /**
     * Getter de los miembros que participan en la aventura
     * @return los miembros que participan en la aventura
     */
    public LinkedList<Char> getParty() {
        return party;
    }

    /**
     * Setter de los miembros que participan en la aventura
     * @param party los miembros que participan en la aventura
     */
    public void setParty(LinkedList<Char> party) {
        this.party = party;
    }
}

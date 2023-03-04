package business.adventure;

import business.Char;
import business.Combat;

import java.util.LinkedList;

public class Adventure {
    /**
     * Nombre de la aventura
     */
    private String name;
    /**
     * Numero de combates que componen nuestra aventura
     */
    private int numCombat;
    /**
     * Listado de combates que componen la aventura
     */
    private LinkedList<Combat> combats = new LinkedList<>();
    /**
     * Listado de personajes que participan en la aventura
     */
    private final LinkedList<Char> party = new LinkedList<>();

    public Adventure(String name, int numCombat) {
        this.name = name;
        this.numCombat = numCombat;
    }

    public void setCombats(LinkedList<Combat> combats) {
        this.combats = combats;
    }

    public String getName() {
        return name;
    }
}

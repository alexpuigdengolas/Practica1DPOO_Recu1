package business;

/**
 * Esta clase sirve para poder representar cualquier tipo de personaje o monstruo que puedan haber en un combate
 */
public class Entities {
    private final String name;
    private int currentInitiative;
    private int hitPoints;

    /**
     * Este es el constructor de la entidad
     * @param name el nombre de la entidad que queremos representar
     */
    public Entities(String name) {
        this.name = name;
    }

    /**
     * Getter del nombre que tiene esa entidad
     * @return el nombre de la entidad
     */
    public String getName() {
        return name;
    }

    /**
     * Setter de la vida de la entidad
     * @param hitPoints la vida de la entidad
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getCurrentInitiative() {
        return currentInitiative;
    }

    public int getHitPoints() {
        return hitPoints;
    }
}

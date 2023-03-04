package business;

public class Entities {
    private final String name;
    private int currentInitiative;
    private int hitPoints;

    public Entities(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCurrentInitiative() {
        return currentInitiative;
    }

    public void setCurrentInitiative(int currentInitiative) {
        this.currentInitiative = currentInitiative;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}

package business;

public class Entities {
    private String name;
    private int currentInitiative;
    private int hitPoints;

    public Entities(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

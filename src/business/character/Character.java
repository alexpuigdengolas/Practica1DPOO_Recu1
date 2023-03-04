package business.character;

import business.Entities;

public class Character extends Entities {
    private String player;
    private int xp;
    private int body;
    private int mind;
    private int spirit;
    private int maxLife;
    private  String type;

    public Character(String name, String player, int xp) {
        super(name);
        this.player = player;
        this.xp = (xp*100)-1;
        this.type = "Adventurer";
    }

    public int getBody() {
        return body;
    }

    public void setBody(int body) {
        this.body = body;
    }

    public int getMind() {
        return mind;
    }

    public void setMind(int mind) {
        this.mind = mind;
    }

    public int getSpirit() {
        return spirit;
    }

    public void setSpirit(int spirit) {
        this.spirit = spirit;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public void setMaxLife(int maxLife) {
        this.maxLife = maxLife;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLevel(){
        return (int) Math.floor(xp/100);
    }
}

package business;

public class Adventurer extends Char{
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
    }

    /**
     * Método para calcular la vida maxima actual del personaje
     */
    @Override
    public void calcMaxLife() {
        this.setMaxLife(((10 + getBody())*this.getLevel()));
    }
}

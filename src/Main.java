import presentation.Controller;
import presentation.ViewManager;

public class Main {
    public static void main(String[] args) {
        new Controller(new ViewManager()).run();
    }
}
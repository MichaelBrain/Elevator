package aston.jpc;

public class Main {
    private UserInterface userInterface;

    private Main() {
        userInterface = new UserInterface(this);
    }

    public static void main(String[] args) {
        // Start application here
        Main app = new Main();
        app.prepare();
    }

    private void prepare() {
        userInterface.run();
    }

    void start(int numEmployees, int numDevelopers, float p, float q, long seed) {
        Simulation simulationResult = new Simulation(numEmployees, numDevelopers, p, q, seed);
        userInterface.complete(simulationResult);
    }
}

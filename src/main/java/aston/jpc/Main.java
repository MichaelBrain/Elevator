package aston.jpc;

/**
 * Main application class, top of hierarchy
 */
class Main {
    private UserInterface userInterface;

    private Main() {
        setUserInterface(new UserInterface(this));
    }

    public static void main(String[] args) {
        // Start application
        Main app = new Main();
        app.prepare();
    }

    /**
     * Initiates user interface and retrieves input.
     */
    private void prepare() {
        getUserInterface().run();
    }

    /**
     * Creates Simulation instance and sends the result to the UI.
     *
     * @param numEmployees int
     * @param numDevelopers int
     * @param p float
     * @param q float
     * @param seed long
     */
    void start(int numEmployees, int numDevelopers, float p, float q, long seed) {
        Simulation simulationResult = new Simulation(numEmployees, numDevelopers, p, q, seed);
        getUserInterface().complete(simulationResult);
    }

    /**
     * Getter for the userInterface field.
     *
     * @return UserInterface used for interfacing with the user.
     */
    private UserInterface getUserInterface() {
        return userInterface;
    }

    /**
     * Setter for the userInterface field.
     *
     * @param userInterface UserInterface used for interfacing with the user.
     */
    private void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
}

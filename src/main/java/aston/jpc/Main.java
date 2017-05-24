package aston.jpc;

import java.nio.Buffer;

public class Main {
    private Building building;

    private Main() {
        this.building = new Building();
    }

    private void createEmployees(int amount) {
        for (int i = 0; i < amount; i++) {
            this.building.people.add(new Employee(building.people.size() + 1));
        }
    }

    private void createDevelopers(int amount) {
        for (int i = 0; i < amount; i++) {
            this.building.people.add(new Developer(building.people.size() + 1));
        }
    }

    public static void main(String[] args) {
        // Start application here
        // @todo Get input from user for number of employees and developers
        // @todo Get input from user for p and q
        Main app = new Main();
        app.createEmployees(20);
        app.createDevelopers(20);
        System.out.println(app.building.people.toString());
    }
}

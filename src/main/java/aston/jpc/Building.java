package aston.jpc;

import java.util.ArrayList;

class Building {
    protected Simulation simulation;
    private ArrayList<Person> people;
    private ArrayList<Floor> floors;
    private Elevator elevator;

    Building(Simulation simulation) {
        this.simulation = simulation;
        this.floors = new ArrayList<Floor>();
        addFloors();
        this.people = new ArrayList<Person>();
        this.elevator = new Elevator(floors.get(0), this.simulation);
    }

    public Elevator getElevator() {
        return elevator;
    }

    ArrayList<Person> getPeople() {
        return people;
    }

    ArrayList<Floor> getFloors() {
        return floors;
    }

    /**
     * @param amount
     */
    void addEmployees(int amount) {
        for (int i = 0; i < amount; i++) {
            people.add(new Employee(people.size() + 1, this.simulation, this.floors));
        }
    }

    /**
     * @param amount
     */
    void addDevelopers(int amount) {
        for (int i = 0; i < amount; i++) {
            people.add(new Developer(people.size() + 1, this.simulation, this.floors));
        }
    }

    void addClient() {
        people.add(new Client(people.size() + 1, this.simulation, this.floors));
    }

    void addMaintenanceCrew() {
        people.add(new MaintenanceCrew(people.size() + 1, this.simulation, this.floors));
    }

    void removePerson(Person person) {
        people.remove(person);
    }

    /**
     *
     */
    private void addFloors() {
        for (int i = 0; i < 6; i++) {
            this.floors.add(new Floor(i + 1));
        }
    }

    void decide() {
        if (simulation.dice.nextFloat() < this.simulation.q) {
            addClient();
        }
        if (simulation.dice.nextFloat() < 0.005) {
            addMaintenanceCrew();
        }
    }
}

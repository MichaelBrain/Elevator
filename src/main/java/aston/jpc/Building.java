package aston.jpc;

import java.util.ArrayList;

/**
 * Represents the building object created through a Simulation instance and stores the people, floors and elevator.
 */
class Building {
    private Simulation simulation;
    private ArrayList<Person> people;
    private int nextId;
    private ArrayList<Floor> floors;
    private Elevator elevator;

    /**
     * @param simulation Simulation
     */
    Building(Simulation simulation) {
        setSimulation(simulation);
        setNextId(0);
        setFloors(new ArrayList<Floor>());
        addFloors();
        setPeople(new ArrayList<Person>());
        setElevator(new Elevator(getFloors().get(0), getSimulation()));
    }

    /**
     * Adds a specified number of Employees to the building.
     *
     * @param amount an int representing the number of employees to add.
     */
    void addEmployees(int amount) {
        for (int i = 0; i < amount; i++) {
            getPeople().add(new Employee(getNextId(), getSimulation(), getFloors()));
        }
    }

    /**
     * Adds a specified number of Developers to the building.
     *
     * @param amount an int representing the number of developers to add.
     */
    void addDevelopers(int amount) {
        for (int i = 0; i < amount; i++) {
            getPeople().add(new Developer(getNextId(), getSimulation(), getFloors()));
        }
    }

    /**
     * Adds a single client to the building.
     */
    private void addClient() {
        getPeople().add(new Client(getNextId(), getSimulation(), getFloors()));
    }

    /**
     * Adds a single MaintenanceCrew to the building.
     */
    private void addMaintenanceCrew() {
        getPeople().add(new MaintenanceCrew(getNextId(), getSimulation(), getFloors()));
    }

    /**
     * Removes a specified person from the building.
     *
     * @param person a Person representing the Person object to remove from the building.
     */
    void removePerson(Person person) {
        getPeople().remove(person);
    }

    /**
     * Adds 6 floors to the building.
     */
    private void addFloors() {
        for (int i = 0; i < 6; i++) {
            getFloors().add(new Floor(i + 1));
        }
    }

    /**
     * Run every tick to calculate the probability for a new Client or MaintenanceCrew person and adds them if matched.
     */
    void decide() {
        if (getSimulation().getDice().nextFloat() < getSimulation().getQ()) {
            addClient();
        }
        if (getSimulation().getDice().nextFloat() < 0.005) {
            addMaintenanceCrew();
        }
    }

    /**
     * Getter for the simulation instance passed into the Building instance.
     *
     * @return the Simulation object instance.
     */
    private Simulation getSimulation() {
        return simulation;
    }

    /**
     * Setter for the simulation instance passed into the Building instance.
     *
     * @param simulation the Simulation object instance.
     */
    private void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Getter for the elevator field created by the Building instance.
     *
     * @return the Elevator object instance.
     */
    Elevator getElevator() {
        return elevator;
    }

    /**
     * Setter for the elevator field created by the Building instance.
     *
     * @param elevator the Elevator object instance.
     */
    private void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    /**
     * Getter for the people field created by the Building instance.
     *
     * @return an ArrayList of type Person.
     */
    ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * Setter for the people field created by the Building instance.
     *
     * @param people an ArrayList of type Person.
     */
    private void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    /**
     * Getter for the floors field created by the Building instance.
     *
     * @return an ArrayList of type Floor.
     */
    ArrayList<Floor> getFloors() {
        return floors;
    }

    /**
     * Setter for the floors field created by the Building instance.
     *
     * @param floors an ArrayList of type Floor.
     */
    private void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    /**
     * Returns the next available ID for a new Person.
     *
     * @return an int as the next available ID.
     */
    int getNextId() {
        setNextId(nextId + 1);
        return nextId;
    }

    /**
     * Allocates the next available ID for a new Person.
     *
     * @param nextId an int as the next available ID.
     */
    private void setNextId(int nextId) {
        this.nextId = nextId;
    }
}

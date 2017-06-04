package aston.jpc;

import java.util.ArrayList;

class Building {
    protected Simulation simulation;
    private ArrayList<Person> people;
    private ArrayList<Floor> floors;
    private Elevator elevator;

    /**
     * @param numEmp
     * @param numDev
     */
    Building(int numEmp, int numDev, Simulation simulation) {
        this.simulation = simulation;
        this.floors = new ArrayList<Floor>();
        createFloors();
        this.people = new ArrayList<Person>();
        addEmployees(numEmp);
        addDevelopers(numDev);
        addAllToFirstFloor();
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
    private void addEmployees(int amount) {
        for (int i = 0; i < amount; i++) {
            people.add(new Employee(people.size() + 1, this.simulation, this.floors));
        }
    }

    /**
     * @param amount
     */
    private void addDevelopers(int amount) {
        for (int i = 0; i < amount; i++) {
            people.add(new Developer(people.size() + 1, this.simulation, this.floors));
        }
    }

    /**
     *
     */
    private void createFloors() {
        for (int i = 0; i < 6; i++) {
            this.floors.add(new Floor(i + 1));
        }
    }

    private void addAllToFirstFloor() {
        for (Person person : people) {
            this.floors.get(0).addPerson(person);
            this.floors.get(0).standardQueue.add(person);
        }
    }
}

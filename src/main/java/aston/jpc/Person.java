package aston.jpc;

public abstract class Person {
    protected Simulation simulation;
    final int id;
    int weight;
    Floor currentFloor;
    Floor requestedFloor;
    boolean lowerFloors, upperFloors, inElevator, waiting = false, firstRequest = true;

    public Person(int id, Simulation simulation, Floor currentFloor) {
        this.simulation = simulation;
        this.id = id;
        this.currentFloor = currentFloor;
        this.currentFloor.addPerson(this);
        if (this instanceof Employee || this instanceof Developer) {
            decide();
        }
    }

    void enterBuilding() {
        simulation.file.println("[Tick " + simulation.tick + "] Person " + this.id + " (" + this.getClass() + ") has entered the building");
        decide();
    }

    void exitBuilding() {
        requestFloor(this.simulation.getBuilding().getFloors().get(0));
    }

    abstract void decide();

    Floor calculateNewFloor() {
        int newFloorNum;

        if (this.lowerFloors && this.upperFloors) {
            newFloorNum = simulation.dice.nextInt(6);
        } else if (this.lowerFloors) {
            newFloorNum = simulation.dice.nextInt(3);
        } else {
            newFloorNum = simulation.dice.nextInt(3) + 3;
        }

        Floor newFloor = simulation.getBuilding().getFloors().get(newFloorNum);

        while (newFloor.equals(this.currentFloor) && !(this instanceof Client)) {
            newFloor = calculateNewFloor();
        }

        return newFloor;
    }

    void requestFloor(Floor floor) {
        this.firstRequest = false;
        this.requestedFloor = floor;
        this.currentFloor.standardQueue.add(this);
        this.simulation.getBuilding().getElevator().requests.add(this);
        this.waiting = true;
    }

    void enterElevator() {
        this.currentFloor.standardQueue.remove(this);
        this.currentFloor.removePerson(this);
        this.inElevator = true;
    }

    abstract void exitElevator(Floor newFloor);
}

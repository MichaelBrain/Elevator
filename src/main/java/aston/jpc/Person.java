package aston.jpc;


public abstract class Person {
    protected Simulation simulation;
    final int id;
    int weight;
    private Floor currentFloor;
    Floor requestedFloor;
    boolean lowerFloors, upperFloors, inElevator, canEnterBuilding, waiting;

    public Person(int id, Simulation simulation, Floor currentFloor) {
        this.simulation = simulation;
        this.id = id;
        this.currentFloor = currentFloor;
        this.waiting = true;
    }

//    abstract void enterBuilding();

//    abstract void exitBuilding();

    void decide(Simulation simulation) {
        int numFloors, numFloorsStart;
        if (!this.waiting) {
            if (simulation.dice.nextFloat() < simulation.p) {
                if (lowerFloors && upperFloors) {
                    numFloors = 6;
                    numFloorsStart = 1;
                } else if (lowerFloors) {
                    numFloors = 3;
                    numFloorsStart = 1;
                } else {
                    numFloors = 3;
                    numFloorsStart = 4;
                }

                // Get new floor
                Floor newFloor = simulation.getBuilding().getFloors().get((int) (Math.random() * numFloors) + numFloorsStart);

                // Send request
                requestFloor(newFloor);

                // Add to current floor queue
                currentFloor.standardQueue.add(this);

                simulation.getBuilding().getElevator().requests.add(this);
            }
        }
    }

    /**
     * Sends a request to the elevator.
     *
     * @param floor an int that gives the floor number that the person wants to travel to.
     */
    // @todo Request floor method
    private void requestFloor(Floor floor) {
        this.requestedFloor = floor;
        this.waiting = true;
    }

    // @todo enter elevator method
    void enterElevator() {
        this.currentFloor.standardQueue.remove();
        this.currentFloor.removePerson(this);
        this.waiting = false;
        this.inElevator = true;
    }

    // @todo exit elevator method
    void exitElevator(Floor newFloor) {
        this.currentFloor = newFloor;
        this.currentFloor.addPerson(this);
        this.requestedFloor = null;
        this.inElevator = false;
    }

}

package aston.jpc;

/**
 * Base class for all user types.
 */
abstract class Person {
    private final Simulation simulation;
    private final int id;
    private final int weight;
    private Floor currentFloor, requestedFloor;
    private boolean lowerFloors, upperFloors, waiting, firstRequest;

    /**
     * @param id int
     * @param simulation Simulation
     * @param currentFloor Floor
     * @param weight int
     */
    Person(int id, Simulation simulation, Floor currentFloor, int weight) {
        this.simulation = simulation;
        this.id = id;
        this.weight = weight;
        setWaiting(false);
        setFirstRequest(true);
        setCurrentFloor(currentFloor);
        getCurrentFloor().addPerson(this);
        if (this instanceof Employee || this instanceof Developer) {
            decide();
        }
    }

    /**
     * Implemented within each subclass.
     */
    abstract void decide();

    /**
     * Called when a Person is created during the simulation to request a floor.
     */
    void enterBuilding() {
        getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Person " + getId() + " (" + getClass() + ") has entered the building");
        decide();
    }

    /**
     * Called when a Person has decided to leave the building and requests the ground floor.
     */
    void exitBuilding() {
        requestFloor(getSimulation().getBuilding().getFloors().get(0));
    }

    /**
     * Calculates a random new floor to request based on the Person type.
     *
     * @return a Floor representing the floor to be requested.
     */
    Floor calculateNewFloor() {
        int newFloorNum;

        if (isLowerFloors() && isUpperFloors()) {
            newFloorNum = getSimulation().getDice().nextInt(6);
        } else if (isLowerFloors()) {
            newFloorNum = getSimulation().getDice().nextInt(3);
        } else {
            newFloorNum = getSimulation().getDice().nextInt(3) + 3;
        }

        Floor newFloor = getSimulation().getBuilding().getFloors().get(newFloorNum);

        while (newFloor.equals(getCurrentFloor()) && !(this instanceof Client)) {
            newFloor = calculateNewFloor();
        }

        return newFloor;
    }

    /**
     * Adds a floor to the requests list and the person to the current floor queue.
     *
     * @param floor the Floor instance to be requested.
     */
    void requestFloor(Floor floor) {
        setFirstRequest(false);
        setRequestedFloor(floor);
        getCurrentFloor().getStandardQueue().add(this);
        getSimulation().getBuilding().getElevator().getRequests().add(this);
        setWaiting(true);
    }

    /**
     * Removes the Person from the current floor.
     */
    void enterElevator() {
        getCurrentFloor().getStandardQueue().remove(this);
        getCurrentFloor().removePerson(this);
    }

    /**
     * Implemented within each subclass.
     */
    abstract void exitElevator(Floor newFloor);

    /**
     * Getter for id field.
     *
     * @return int unique identifier.
     */
    int getId() {
        return id;
    }

    /**
     * Getter for Simulation instance.
     *
     * @return Simulation.
     */
    Simulation getSimulation() {
        return simulation;
    }

    /**
     * Getter for weight field.
     *
     * @return int.
     */
    int getWeight() {
        return weight;
    }

    /**
     * Getter for currentFloor field.
     *
     * @return Floor.
     */
    Floor getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Setter for currentFloor field.
     *
     * @param currentFloor Floor.
     */
    void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     * Getter for requestedFloor field.
     *
     * @return Floor.
     */
    Floor getRequestedFloor() {
        return requestedFloor;
    }

    /**
     * Setter for requestedFloor field.
     *
     * @param requestedFloor Floor.
     */
    void setRequestedFloor(Floor requestedFloor) {
        this.requestedFloor = requestedFloor;
    }

    /**
     * Getter for lowerFloors field.
     *
     * @return true if Person wants to travel to lower floors (1, 2, 3), false if not.
     */
    private boolean isLowerFloors() {
        return lowerFloors;
    }

    /**
     * Setter for lowerFloors field.
     *
     * @param lowerFloors true if Person wants to travel to lower floors (1, 2, 3), false if not.
     */
    void setLowerFloors(boolean lowerFloors) {
        this.lowerFloors = lowerFloors;
    }

    /**
     * Getter for upperFloors field.
     *
     * @return true if Person wants to travel to upper floors (4, 5, 6), false if not.
     */
    private boolean isUpperFloors() {
        return upperFloors;
    }

    /**
     * Setter for upperFloors field.
     *
     * @param upperFloors true if Person wants to travel to upper floors (4, 5, 6), false if not.
     */
    void setUpperFloors(boolean upperFloors) {
        this.upperFloors = upperFloors;
    }

    /**
     * Getter for waiting field.
     *
     * @return true if Person has already requested and is waiting, false if not.
     */
    boolean isWaiting() {
        return waiting;
    }

    /**
     * Setter for waiting field.
     *
     * @param waiting true if Person has already requested and is waiting, false if not.
     */
    void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }

    /**
     * Getter for firstRequest field.
     *
     * @return true if Person has not previously requested and travelled, false if not.
     */
    boolean isFirstRequest() {
        return firstRequest;
    }

    /**
     * Setter for firstRequest field.
     *
     * @param firstRequest true if Person has not previously requested and travelled, false if not.
     */
    void setFirstRequest(boolean firstRequest) {
        this.firstRequest = firstRequest;
    }
}

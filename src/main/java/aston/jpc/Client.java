package aston.jpc;

import java.util.ArrayList;

/**
 * A Client type subclass of Person.
 */
class Client extends Person {
    private int timeArrivedAtFloor;
    private int timeInBuilding;
    private boolean exitingBuilding;

    /**
     * @param id int
     * @param simulation Simulation
     * @param floors ArrayList
     */
    Client(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0), 1);
        setLowerFloors(true);
        setUpperFloors(false);
        setExitingBuilding(false);
        enterBuilding();
        calculateTimeInBuilding();
    }

    /**
     * Run every tick to request a new floor, calculate time left in building, and exit building.
     */
    void decide() {
        if (!isWaiting() && isFirstRequest()) {
            Floor newFloor = calculateNewFloor();

            if (newFloor.equals(getCurrentFloor())) {
                setFirstRequest(false);
            } else {
                requestFloor(newFloor);
            }
        } else if (!isWaiting()) {
            if (getTimeArrivedAtFloor() + getTimeInBuilding() == getSimulation().getTick()) {
                setExitingBuilding(true);
                exitBuilding();
            }
        }
    }

    /**
     * Calculates a random amount of minutes for the Client to stay in the building.
     */
    void calculateTimeInBuilding() {
        setTimeInBuilding(getSimulation().getDice().nextInt(120) + 60);
    }

    /**
     * Adds the Client to the new floor or removes from building.
     *
     * @param newFloor the Floor instance originally requested.
     */
    void exitElevator(Floor newFloor) {
        setRequestedFloor(null);
        setWaiting(false);

        if (!isExitingBuilding()) {
            setTimeArrivedAtFloor(getSimulation().getTick());
            setCurrentFloor(newFloor);
            getCurrentFloor().addPerson(this);
        } else {
            getSimulation().getBuilding().removePerson(this);
            getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Person " + getId() + " (" + getClass() + ") has exited the building");
        }
    }

    /**
     * Getter for timeArrivedAtFloor field.
     *
     * @return an int representing the tick the Client exited the elevator.
     */
    private int getTimeArrivedAtFloor() {
        return timeArrivedAtFloor;
    }

    /**
     * Setter for timeArrivedAtFloor field.
     *
     * @param timeArrivedAtFloor an int representing the tick the Client exited the elevator.
     */
    private void setTimeArrivedAtFloor(int timeArrivedAtFloor) {
        this.timeArrivedAtFloor = timeArrivedAtFloor;
    }

    /**
     * Getter for timeInBuilding field.
     *
     * @return an int representing the number of ticks the Client will spend on the floor.
     */
    int getTimeInBuilding() {
        return timeInBuilding;
    }

    /**
     * Setter for timeInBuilding field.
     *
     * @param timeInBuilding an int representing the number of ticks the Client will spend on the floor.
     */
    void setTimeInBuilding(int timeInBuilding) {
        this.timeInBuilding = timeInBuilding;
    }

    /**
     * Getter for exitingBuilding field.
     *
     * @return true if the Client is on the way to exit the building, false if not.
     */
    private boolean isExitingBuilding() {
        return exitingBuilding;
    }

    /**
     * Setter for exitingBuilding field.
     *
     * @param exitingBuilding true if the Client is on the way to exit the building, false if not.
     */
    private void setExitingBuilding(boolean exitingBuilding) {
        this.exitingBuilding = exitingBuilding;
    }
}

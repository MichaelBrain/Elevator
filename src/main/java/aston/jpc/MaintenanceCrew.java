package aston.jpc;

import java.util.ArrayList;

/**
 * A MaintenanceCrew type subclass of Person.
 */
class MaintenanceCrew extends Person {
    private int timeArrivedAtFloor;
    private int timeInBuilding;
    private boolean exitingBuilding;

    /**
     * @param id int
     * @param simulation Simulation
     * @param floors ArrayList
     */
    MaintenanceCrew(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0), 4);
        enterBuilding();
        calculateTimeInBuilding();
        setExitingBuilding(false);
    }

    /**
     * Run every tick to request a new floor, calculate time left in building, and exit building.
     */
    void decide() {
        if (!isWaiting() && isFirstRequest()) {
            requestFloor(getSimulation().getBuilding().getFloors().get(5));
        } else if (!isWaiting()) {
            if (getTimeArrivedAtFloor() + getTimeInBuilding() == getSimulation().getTick()) {
                setExitingBuilding(true);
                exitBuilding();
            }
        }
    }

    /**
     * Calculates a random amount of minutes for the MaintenanceCrew to stay in the building.
     */
    void calculateTimeInBuilding() {
        setTimeInBuilding(getSimulation().getDice().nextInt(120) + 120);
    }

    /**
     * Adds the MaintenanceCrew to the new floor or removes from building.
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
     * @return an int representing the tick the MaintenanceCrew exited the elevator.
     */
    private int getTimeArrivedAtFloor() {
        return timeArrivedAtFloor;
    }

    /**
     * Setter for timeArrivedAtFloor field.
     *
     * @param timeArrivedAtFloor an int representing the tick the MaintenanceCrew exited the elevator.
     */
    private void setTimeArrivedAtFloor(int timeArrivedAtFloor) {
        this.timeArrivedAtFloor = timeArrivedAtFloor;
    }

    /**
     * Getter for timeInBuilding field.
     *
     * @return an int representing the number of ticks the MaintenanceCrew will spend on the floor.
     */
    int getTimeInBuilding() {
        return timeInBuilding;
    }

    /**
     * Setter for timeInBuilding field.
     *
     * @param timeInBuilding an int representing the number of ticks the MaintenanceCrew will spend on the floor.
     */
    void setTimeInBuilding(int timeInBuilding) {
        this.timeInBuilding = timeInBuilding;
    }

    /**
     * Getter for exitingBuilding field.
     *
     * @return true if the MaintenanceCrew is on the way to exit the building, false if not.
     */
    private boolean isExitingBuilding() {
        return exitingBuilding;
    }

    /**
     * Setter for exitingBuilding field.
     *
     * @param exitingBuilding true if the MaintenanceCrew is on the way to exit the building, false if not.
     */
    private void setExitingBuilding(boolean exitingBuilding) {
        this.exitingBuilding = exitingBuilding;
    }
}

package aston.jpc;

import java.util.ArrayList;

/**
 * A Developer type subclass of Person.
 */
class Developer extends Person {
    /**
     * @param id int
     * @param simulation Simulation
     * @param floors ArrayList
     */
    Developer(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0), 1);
        setLowerFloors(false);
        setUpperFloors(true);
    }

    /**
     * Run every tick to request a new floor if not already requested.
     */
    void decide() {
        if (!isWaiting()) {
            if (getSimulation().getDice().nextFloat() < getSimulation().getP() || isFirstRequest()) {
                // Get new floor
                Floor newFloor = calculateNewFloor();

                if (!newFloor.equals(getCurrentFloor())) {
                    // Send request
                    requestFloor(newFloor);
                }
            }
        }
    }

    /**
     * Adds the Developer to the new floor.
     *
     * @param newFloor the Floor instance originally requested.
     */
    void exitElevator(Floor newFloor) {
        setCurrentFloor(newFloor);
        getCurrentFloor().addPerson(this);
        setRequestedFloor(null);
        setWaiting(false);
    }
}

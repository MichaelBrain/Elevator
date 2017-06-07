package aston.jpc;

import java.util.ArrayList;

/**
 * A Employee type subclass of Person.
 */
class Employee extends Person {
    /**
     * @param id int
     * @param simulation Simulation
     * @param floors ArrayList
     */
    Employee(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0), 1);
        setLowerFloors(true);
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
     * Adds the Employee to the new floor.
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

package aston.jpc;

import java.util.ArrayList;

class Developer extends Person {
    Developer(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0));
        this.lowerFloors = false;
        this.upperFloors = true;
        this.weight = 1;
    }

    void decide() {
        if (!this.waiting) {
            if (simulation.dice.nextFloat() < this.simulation.p || this.firstRequest) {
                // Get new floor
                Floor newFloor = calculateNewFloor();

                if (!newFloor.equals(this.currentFloor)) {
                    // Send request
                    this.requestFloor(newFloor);
                }
            }
        }
    }

    void exitElevator(Floor newFloor) {
        this.currentFloor = newFloor;
        this.currentFloor.addPerson(this);
        this.requestedFloor = null;
        this.waiting = false;
        this.inElevator = false;
    }
}

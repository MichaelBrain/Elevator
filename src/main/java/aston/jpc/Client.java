package aston.jpc;

import java.util.ArrayList;

class Client extends Person {
    private int timeArrivedAtFloor;
    private int timeInBuilding;
    private boolean exitingBuilding;

    Client(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0));
        this.lowerFloors = true;
        this.upperFloors = false;
        this.weight = 1;
        enterBuilding();
        calculateTimeInBuilding();
    }

    void decide() {
        if (!this.waiting && this.firstRequest) {
            // Get new floor
            Floor newFloor = calculateNewFloor();

            if (newFloor.equals(this.currentFloor)) {
                this.firstRequest = false;
                this.waiting = false;
            } else {
                // Send request
                this.requestFloor(newFloor);
            }
        } else if (!this.waiting) {
            if (timeArrivedAtFloor + timeInBuilding == simulation.tick) {
                exitingBuilding = true;
                exitBuilding();
            }
        }
    }

    private void calculateTimeInBuilding() {
        this.timeInBuilding = simulation.dice.nextInt(120) + 60;
    }

    void exitElevator(Floor newFloor) {
        this.requestedFloor = null;
        this.waiting = false;
        this.inElevator = false;

        if (!exitingBuilding) {
            this.timeArrivedAtFloor = simulation.tick;
            this.currentFloor = newFloor;
            this.currentFloor.addPerson(this);
        } else {
            simulation.getBuilding().removePerson(this);
            simulation.file.println("[Tick " + simulation.tick + "] Person " + this.id + " (" + this.getClass() + ") has exited the building");
        }
    }
}

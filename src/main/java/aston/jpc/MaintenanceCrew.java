package aston.jpc;

import java.util.ArrayList;

class MaintenanceCrew extends Person {
    private int timeArrivedAtFloor;
    private int timeInBuilding;
    private boolean exitingBuilding;

    MaintenanceCrew(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0));
        this.weight = 4;
        enterBuilding();
        calculateTimeInBuilding();
    }

    void decide() {
        if (!this.waiting && this.firstRequest) {
            this.requestFloor(simulation.getBuilding().getFloors().get(5));
        } else if (!this.waiting) {
            if (timeArrivedAtFloor + timeInBuilding == simulation.tick) {
                exitingBuilding = true;
                exitBuilding();
            }
        }
    }

    private void calculateTimeInBuilding() {
        this.timeInBuilding = simulation.dice.nextInt(120) + 120;
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

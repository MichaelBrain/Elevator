package aston.jpc;

import java.util.ArrayList;

class Developer extends Person {
    Developer(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0));
        this.lowerFloors = false;
        this.upperFloors = true;
        this.weight = 1;
        this.requestedFloor = (Floor) floors.get((int)(Math.random() * 5) + 1);
    }
}

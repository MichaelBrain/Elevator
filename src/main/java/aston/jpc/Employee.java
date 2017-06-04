package aston.jpc;

import java.util.ArrayList;

class Employee extends Person {
    Employee(int id, Simulation simulation, ArrayList floors) {
        super(id, simulation, (Floor) floors.get(0));
        this.lowerFloors = true;
        this.upperFloors = true;
        this.weight = 1;
        this.requestedFloor = (Floor) floors.get((int)(Math.random() * 5) + 1);
    }
}

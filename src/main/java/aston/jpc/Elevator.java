package aston.jpc;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Elevator {
    public Simulation simulation;
    private boolean doorsClosed = true;
    private boolean loadingPeople = false;
    private boolean readyToDepart = false;
    private boolean travellingUpwards = true;
    private int currentWeight = 0;
    private Floor currentFloor;
    private ArrayList<Person> occupants;
    ConcurrentLinkedQueue<Person> requests;

    protected Elevator(Floor startingFloor, Simulation simulation) {
        this.occupants = new ArrayList<Person>(4);
        this.requests = new ConcurrentLinkedQueue<Person>();
        this.currentFloor = startingFloor;
        this.simulation = simulation;
    }

    void decide() {
        if (doorsClosed) {
            if (readyToDepart) {
                move();
                readyToDepart = false;
            } else {
                if (checkRequests()) {
                    openDoors();
                    doorsClosed = false;
                } else {
                   move();
                }
            }
        } else {
            if (loadingPeople) {
                closeDoors();
                doorsClosed = true;
                readyToDepart = true;
                loadingPeople = false;
            } else {
                loadPeople();
                loadingPeople = true;
            }
        }
    }

    private void openDoors() {
        simulation.file.println("[Tick " + simulation.tick + "] Elevator doors opening on floor " + currentFloor.level);
        doorsClosed = false;
    }

    private void loadPeople() {
        if (!this.occupants.isEmpty()) {
            for (int i = 0; i < occupants.size(); i++) {
                Person person = occupants.get(i);
                if (person.requestedFloor.equals(currentFloor)) {
                    simulation.file.println("[Tick " + simulation.tick + "] Person " + person.id + " (" + person.getClass() + ") has exited the elevator");
                    person.exitElevator(currentFloor);
                    occupants.remove(person);
                    this.currentWeight -= person.weight;
                    this.requests.remove(person);
                    i--;
                }
            }
        }

        for (Person person : currentFloor.standardQueue) {
            if (this.occupants.size() < 4) {
                if (this.currentWeight + person.weight < 5) {
                    person.enterElevator();
                    this.occupants.add(person);
                    this.currentWeight += person.weight;
                    simulation.file.println("[Tick " + simulation.tick + "] Person " + person.id + " (" + person.getClass() + ") has entered the elevator to floor " + person.requestedFloor.level);
                } else {
                    simulation.file.println("[Tick " + simulation.tick + "] Not enough space in the elevator for Person " + person.id + " (" + person.getClass() + ")");
                }
            }
        }
    }

    private void closeDoors() {
        simulation.file.println("[Tick " + simulation.tick + "] Elevator doors closing on floor " + currentFloor.level);
    }

    private void move() {
        String action = "has moved to";
        if (travellingUpwards) {
            if (currentFloor.level < 6 && !this.occupants.isEmpty() || currentFloor.level < 6 && !this.requests.isEmpty()) {
                currentFloor = simulation.getBuilding().getFloors().get(currentFloor.level);
            } else if(currentFloor.level != 1) {
                currentFloor = simulation.getBuilding().getFloors().get(currentFloor.level - 2);
                travellingUpwards = false;
            } else {
                action = "is resting at";
            }
        } else {
            if (currentFloor.level > 1) {
                currentFloor = simulation.getBuilding().getFloors().get(currentFloor.level - 2);
            } else if (!this.occupants.isEmpty() || !this.requests.isEmpty()) {
                currentFloor = simulation.getBuilding().getFloors().get(currentFloor.level);
                travellingUpwards = true;
            } else {
                action = "is resting at";
            }
        }
        simulation.file.println("[Tick " + simulation.tick + "] Elevator " + action + " floor " + currentFloor.level);
    }

    private Boolean checkRequests() {
        if (!occupants.isEmpty()) {
            for (Person occupant : occupants) {
                if (occupant.requestedFloor.equals(currentFloor)) {
                    return true;
                }
            }
        }
        if (!this.currentFloor.standardQueue.isEmpty()) {
            return true;
        }
        return false;
    }
}

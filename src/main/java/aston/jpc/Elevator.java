package aston.jpc;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Enables the movement of people between different floors.
 */
class Elevator {
    private ConcurrentLinkedQueue<Person> requests;
    private Simulation simulation;
    private boolean doorsClosed, loadingPeople, readyToDepart, travellingUpwards;
    private int currentWeight;
    private Floor currentFloor;
    private ArrayList<Person> occupants;

    /**
     * @param startingFloor Floor
     * @param simulation Simulation
     */
    Elevator(Floor startingFloor, Simulation simulation) {
        setOccupants(new ArrayList<Person>(4));
        setRequests(new ConcurrentLinkedQueue<Person>());
        setCurrentFloor(startingFloor);
        setCurrentWeight(0);
        setDoorsClosed(true);
        setLoadingPeople(false);
        setReadyToDepart(false);
        setTravellingUpwards(true);
        setSimulation(simulation);
    }

    /**
     * Run every tick to move, open doors, close doors and load/unload people.
     */
    void decide() {
        if (isDoorsClosed()) {
            if (isReadyToDepart()) {
                move();
                setReadyToDepart(false);
            } else {
                if (checkRequests()) {
                    openDoors();
                } else {
                    move();
                }
            }
        } else {
            if (isLoadingPeople()) {
                closeDoors();
                setReadyToDepart(true);
                setLoadingPeople(false);
            } else {
                loadPeople();
                setLoadingPeople(true);
            }
        }
    }

    /**
     * Sets the doorsClosed field to false.
     */
    private void openDoors() {
        getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Elevator doors opening on floor " + getCurrentFloor().getLevel());
        setDoorsClosed(false);
    }

    /**
     * Sets the doorsClosed field to true.
     */
    private void closeDoors() {
        getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Elevator doors closing on floor " + getCurrentFloor().getLevel());
        setDoorsClosed(true);
    }

    /**
     * Checks and adds/removes accordingly if any of the elevator occupants want to exit or if any of the current floor people want to enter.
     */
    private void loadPeople() {
        if (!getOccupants().isEmpty()) {
            for (int i = 0; i < getOccupants().size(); i++) {
                Person person = getOccupants().get(i);
                if (person.getRequestedFloor().equals(getCurrentFloor())) {
                    getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Person " + person.getId() + " (" + person.getClass() + ") has exited the elevator");
                    person.exitElevator(getCurrentFloor());
                    getOccupants().remove(person);
                    setCurrentWeight(getCurrentWeight() - person.getWeight());
                    getRequests().remove(person);
                    i--;
                }
            }
        }

        for (Person person : getCurrentFloor().getStandardQueue()) {
            if (getOccupants().size() < 4) {
                if (getCurrentWeight() + person.getWeight() < 5) {
                    person.enterElevator();
                    getOccupants().add(person);
                    setCurrentWeight(getCurrentWeight() + person.getWeight());
                    getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Person " + person.getId() + " (" + person.getClass() + ") has entered the elevator to floor " + person.getRequestedFloor().getLevel());
                } else {
                    getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Not enough space in the elevator for Person " + person.getId() + " (" + person.getClass() + ")");
                }
            }
        }
    }

    /**
     * Calculates the next floor to move to and changes current floor, or rests at ground floor if no requests.
     */
    private void move() {
        String action = "has moved to";
        if (isTravellingUpwards()) {
            if (getCurrentFloor().getLevel() < 6 && !getOccupants().isEmpty() || getCurrentFloor().getLevel() < 6 && !getRequests().isEmpty()) {
                setCurrentFloor(getSimulation().getBuilding().getFloors().get(getCurrentFloor().getLevel()));
            } else if (getCurrentFloor().getLevel() != 1) {
                setCurrentFloor(getSimulation().getBuilding().getFloors().get(getCurrentFloor().getLevel() - 2));
                setTravellingUpwards(false);
            } else {
                action = "is resting at";
            }
        } else {
            if (getCurrentFloor().getLevel() > 1) {
                setCurrentFloor(getSimulation().getBuilding().getFloors().get(getCurrentFloor().getLevel() - 2));
            } else if (!getOccupants().isEmpty() || !getRequests().isEmpty()) {
                setCurrentFloor(getSimulation().getBuilding().getFloors().get(getCurrentFloor().getLevel()));
                setTravellingUpwards(true);
            } else {
                action = "is resting at";
            }
        }
        getSimulation().getFile().println("[Tick " + getSimulation().getTick() + "] Elevator " + action + " floor " + getCurrentFloor().getLevel());
    }

    /**
     * Calculates if there are any requests.
     *
     * @return true if requests found, false if not.
     */
    private Boolean checkRequests() {
        if (!getOccupants().isEmpty()) {
            for (Person occupant : getOccupants()) {
                if (occupant.getRequestedFloor().equals(getCurrentFloor())) {
                    return true;
                }
            }
        }
        return !getCurrentFloor().getStandardQueue().isEmpty();
    }

    /**
     * Getter for requests field.
     *
     * @return ConcurrentLinkedQueue of type Person.
     */
    ConcurrentLinkedQueue<Person> getRequests() {
        return requests;
    }

    /**
     * Setter for requests field.
     *
     * @param requests ConcurrentLinkedQueue of type Person.
     */
    private void setRequests(ConcurrentLinkedQueue<Person> requests) {
        this.requests = requests;
    }

    /**
     * Getter for the simulation instance passed into the Elevator instance.
     *
     * @return the Simulation object instance.
     */
    private Simulation getSimulation() {
        return simulation;
    }

    /**
     * Setter for the simulation instance passed into the Elevator instance.
     *
     * @param simulation the Simulation object instance.
     */
    private void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }


    /**
     * Getter for doorsClosed field.
     *
     * @return true if doors closed, false if not.
     */
    private boolean isDoorsClosed() {
        return doorsClosed;
    }

    /**
     * Setter for doorsClosed field.
     *
     * @param doorsClosed true if doors closed, false if not.
     */
    private void setDoorsClosed(boolean doorsClosed) {
        this.doorsClosed = doorsClosed;
    }

    /**
     * Getter for loadingPeople field.
     *
     * @return true if loading people, false if not.
     */
    private boolean isLoadingPeople() {
        return loadingPeople;
    }

    /**
     * Setter for loadingPeople field.
     *
     * @param loadingPeople true if loading people, false if not.
     */
    private void setLoadingPeople(boolean loadingPeople) {
        this.loadingPeople = loadingPeople;
    }

    /**
     * Getter for readyToDepart field.
     *
     * @return true if ready to depart after loading, false if not.
     */
    private boolean isReadyToDepart() {
        return readyToDepart;
    }

    /**
     * Setter for readyToDepart field.
     *
     * @param readyToDepart true if ready to depart after loading, false if not.
     */
    private void setReadyToDepart(boolean readyToDepart) {
        this.readyToDepart = readyToDepart;
    }

    /**
     * Getter for travellingUpwards field.
     *
     * @return true if currently travelling upwards, false if not.
     */
    private boolean isTravellingUpwards() {
        return travellingUpwards;
    }

    /**
     * Setter for travellingUpwards field.
     *
     * @param travellingUpwards true if currently travelling upwards, false if not.
     */
    private void setTravellingUpwards(boolean travellingUpwards) {
        this.travellingUpwards = travellingUpwards;
    }

    /**
     * Getter for currentWeight field.
     *
     * @return int representing current combined total of occupants.
     */
    private int getCurrentWeight() {
        return currentWeight;
    }

    /**
     * Setter for currentWeight field.
     *
     * @param currentWeight int representing current combined total of occupants.
     */
    private void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    /**
     * Getter for currentFloor field.
     *
     * @return Floor representing current floor.
     */
    private Floor getCurrentFloor() {
        return currentFloor;
    }

    /**
     * Setter for currentFloor field.
     *
     * @param currentFloor Floor representing current floor.
     */
    private void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     * Getter for occupants field.
     *
     * @return ArrayList of type Person.
     */
    private ArrayList<Person> getOccupants() {
        return occupants;
    }

    /**
     * Setter for occupants field.
     *
     * @param occupants ArrayList of type Person.
     */
    private void setOccupants(ArrayList<Person> occupants) {
        this.occupants = occupants;
    }
}

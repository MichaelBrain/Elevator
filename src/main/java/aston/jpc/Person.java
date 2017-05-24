package aston.jpc;


abstract class Person {
    private int id, weight, currentFloor, requestedFloor;
    private boolean lowerFloors, upperFloors, inElevator, canEnterBuilding, waiting;

//    abstract void enterBuilding();
//
//    abstract void exitBuilding();

    Person(int id) {
        this.id = id;
    }

    /**
     * Sends a request to the elevator.
     *
     * @param floor an int that gives the floor number that the person wants to travel to.
     */
    // @todo Request floor method
    void requestFloor(int floor) {

    }

    // @todo enter elevator method
    void enterElevator() {

    }

    // @todo exit elevator method
    void exitElevator() {

    }

}

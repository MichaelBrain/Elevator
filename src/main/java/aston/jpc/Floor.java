package aston.jpc;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Represents a single floor within the building.
 */
class Floor {
    private final int level;
    private ConcurrentLinkedQueue<Person> standardQueue;
    private ArrayList<Person> people;

    /**
     * @param level int
     */
    Floor(int level) {
        this.level = level;
        setStandardQueue(new ConcurrentLinkedQueue<Person>());
        setPeople(new ArrayList<Person>());
    }

    /**
     * Adds a specified Person to the list of people on the floor.
     *
     * @param person Person to add.
     */
    void addPerson(Person person) {
        getPeople().add(person);
    }

    /**
     * Removes a specified Person from the list of people on the floor.
     *
     * @param person Person to remove.
     */
    void removePerson(Person person) {
        getPeople().remove(person);
    }

    /**
     * Getter for people field.
     *
     * @return ArrayList of type Person.
     */
    ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * Setter for people field.
     *
     * @param people ArrayList of type Person.
     */
    private void setPeople(ArrayList<Person> people) {
        this.people = people;
    }

    /**
     * Getter for standardQueue field.
     *
     * @return ConcurrentLinkedQueue of type Person.
     */
    ConcurrentLinkedQueue<Person> getStandardQueue() {
        return standardQueue;
    }

    /**
     * Setter for standardQueue field.
     *
     * @param standardQueue ConcurrentLinkedQueue of type Person.
     */
    private void setStandardQueue(ConcurrentLinkedQueue<Person> standardQueue) {
        this.standardQueue = standardQueue;
    }

    /**
     * Getter for level field.
     *
     * @return int representing the floor number.
     */
    int getLevel() {
        return level;
    }
}

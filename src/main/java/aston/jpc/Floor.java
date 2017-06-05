package aston.jpc;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

class Floor {
    final int level;
    private PriorityQueue<Person> priorityQueue;
    ConcurrentLinkedQueue<Person> standardQueue;
    private ArrayList<Person> people;

    Floor(int level) {
        this.level = level;
        this.standardQueue = new ConcurrentLinkedQueue<Person>();
        this.people = new ArrayList<Person>();
    }

    void addPerson(Person person) {
        this.people.add(person);
    }

    void removePerson(Person person) {
        this.people.remove(person);
    }
}

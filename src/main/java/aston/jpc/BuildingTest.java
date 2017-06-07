package aston.jpc;

import org.junit.*;

public class BuildingTest {
    Simulation simulation;

    @Before
    public void setUp() throws Exception {
        simulation = new Simulation(1, 1, 0, 0, 1);
    }

    @Test
    public void addEmployees() throws Exception {
        Assert.assertEquals(2, simulation.getBuilding().getPeople().size());
        simulation.getBuilding().addEmployees(1);
        Assert.assertEquals(3, simulation.getBuilding().getPeople().size());
    }

    @Test
    public void addDevelopers() throws Exception {
        Assert.assertEquals(2, simulation.getBuilding().getPeople().size());
        simulation.getBuilding().addDevelopers(1);
        Assert.assertEquals(3, simulation.getBuilding().getPeople().size());
    }

    @Test
    public void removePerson() throws Exception {
        Assert.assertEquals(2, simulation.getBuilding().getPeople().size());
        simulation.getBuilding().removePerson(simulation.getBuilding().getPeople().get(0));
        Assert.assertEquals(1, simulation.getBuilding().getPeople().size());
    }

    @Test
    public void decide() throws Exception {
        simulation = new Simulation(1, 1, 0, 1, 1);
        Assert.assertEquals(24891, simulation.getBuilding().getPeople().size());
        simulation.getBuilding().decide();
        Assert.assertEquals(24892, simulation.getBuilding().getPeople().size());
    }

}
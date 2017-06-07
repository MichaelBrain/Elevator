package aston.jpc;

import org.junit.*;

import static org.hamcrest.Matchers.isOneOf;

public class DeveloperTest {
    Simulation simulation;
    Developer developer;

    @Before
    public void setUp() throws Exception {
        simulation = new Simulation(1, 1, 0, 0, 1);
        developer = new Developer(simulation.getBuilding().getNextId(), simulation, simulation.getBuilding().getFloors());
    }

    @Test
    public void decide() throws Exception {
        developer.setRequestedFloor(null);
        developer.setWaiting(false);
        developer.setFirstRequest(true);
        Assert.assertNull(developer.getRequestedFloor());
        Assert.assertEquals(false, developer.isWaiting());
        Assert.assertEquals(true, developer.isFirstRequest());
        developer.decide();
        Assert.assertEquals(true, developer.isWaiting());
        Assert.assertEquals(false, developer.isFirstRequest());
        Assert.assertNotNull(developer.getRequestedFloor());
    }

    @Test
    public void exitElevator() throws Exception {
        developer.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertEquals(simulation.getBuilding().getFloors().get(2), developer.getCurrentFloor());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(developer));
        Assert.assertNull(developer.getRequestedFloor());
        Assert.assertEquals(false, developer.isWaiting());
    }

    @Test
    public void enterElevator() throws Exception {
        developer.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(developer));
        developer.enterElevator();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(2).getPeople().contains(developer));
    }

    @Test
    public void calculateNewFloor() throws Exception {
        Assert.assertNotNull(developer.calculateNewFloor());
        Assert.assertThat(developer.calculateNewFloor(), isOneOf(simulation.getBuilding().getFloors().get(3), simulation.getBuilding().getFloors().get(4), simulation.getBuilding().getFloors().get(5)));
    }

    @Test
    public void requestFloor() throws Exception {
        developer.setRequestedFloor(null);
        simulation.getBuilding().getFloors().get(0).getStandardQueue().clear();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(developer));
        Assert.assertNull(developer.getRequestedFloor());
        developer.requestFloor(simulation.getBuilding().getFloors().get(2));
        Assert.assertFalse(developer.isFirstRequest());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(developer));
        Assert.assertTrue(simulation.getBuilding().getElevator().getRequests().contains(developer));
        Assert.assertTrue(developer.isWaiting());
    }
}
package aston.jpc;

import org.junit.*;

import static org.hamcrest.Matchers.*;

public class ClientTest {
    Simulation simulation;
    Client client;

    @Before
    public void setUp() throws Exception {
        simulation = new Simulation(1, 1, 0, 0, 1);
        client = new Client(simulation.getBuilding().getNextId(), simulation, simulation.getBuilding().getFloors());
    }

    @Test
    public void decide() throws Exception {
        client.setRequestedFloor(null);
        client.setWaiting(false);
        client.setFirstRequest(true);
        Assert.assertNull(client.getRequestedFloor());
        Assert.assertEquals(false, client.isWaiting());
        Assert.assertEquals(true, client.isFirstRequest());
        client.decide();
        Assert.assertEquals(false, client.isFirstRequest());
    }

    @Test
    public void exitElevator() throws Exception {
        client.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertEquals(simulation.getBuilding().getFloors().get(2), client.getCurrentFloor());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(client));
        Assert.assertNull(client.getRequestedFloor());
        Assert.assertEquals(false, client.isWaiting());
    }

    @Test
    public void enterElevator() throws Exception {
        client.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(client));
        client.enterElevator();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(2).getPeople().contains(client));
    }

    @Test
    public void calculateNewFloor() throws Exception {
        Assert.assertNotNull(client.calculateNewFloor());
        Assert.assertThat(client.calculateNewFloor(), isOneOf(simulation.getBuilding().getFloors().get(0), simulation.getBuilding().getFloors().get(1), simulation.getBuilding().getFloors().get(2)));
    }

    @Test
    public void requestFloor() throws Exception {
        client.setRequestedFloor(null);
        simulation.getBuilding().getFloors().get(0).getStandardQueue().clear();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(client));
        Assert.assertNull(client.getRequestedFloor());
        client.requestFloor(simulation.getBuilding().getFloors().get(2));
        Assert.assertFalse(client.isFirstRequest());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(client));
        Assert.assertTrue(simulation.getBuilding().getElevator().getRequests().contains(client));
        Assert.assertTrue(client.isWaiting());
    }

    @Test
    public void calculateTimeInBuilding() throws Exception {
        client.setTimeInBuilding(0);
        Assert.assertEquals(0, client.getTimeInBuilding());
        client.calculateTimeInBuilding();
        Assert.assertThat(client.getTimeInBuilding(), allOf(greaterThanOrEqualTo(60),lessThanOrEqualTo(180)));
    }

    @Test
    public void enterBuilding() throws Exception {
        client.setRequestedFloor(null);
        client.setWaiting(false);
        client.setFirstRequest(true);
        Assert.assertNull(client.getRequestedFloor());
        Assert.assertEquals(false, client.isWaiting());
        Assert.assertEquals(true, client.isFirstRequest());
        client.enterBuilding();
        Assert.assertEquals(false, client.isFirstRequest());
    }

    @Test
    public void exitBuilding() throws Exception {
        client.setRequestedFloor(null);
        simulation.getBuilding().getFloors().get(2).getStandardQueue().clear();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(2).getStandardQueue().contains(client));
        Assert.assertNull(client.getRequestedFloor());
        client.setCurrentFloor(simulation.getBuilding().getFloors().get(2));
        Assert.assertEquals(simulation.getBuilding().getFloors().get(2), client.getCurrentFloor());
        client.exitBuilding();
        Assert.assertFalse(client.isFirstRequest());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getStandardQueue().contains(client));
        Assert.assertTrue(simulation.getBuilding().getElevator().getRequests().contains(client));
        Assert.assertTrue(client.isWaiting());
    }
}
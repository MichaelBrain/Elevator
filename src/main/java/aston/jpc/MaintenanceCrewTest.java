package aston.jpc;

import org.junit.*;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class MaintenanceCrewTest {
    Simulation simulation;
    MaintenanceCrew maintenanceCrew;

    @Before
    public void setUp() throws Exception {
        simulation = new Simulation(1, 1, 0, 0, 1);
        maintenanceCrew = new MaintenanceCrew(simulation.getBuilding().getNextId(), simulation, simulation.getBuilding().getFloors());
    }

    @Test
    public void decide() throws Exception {
        maintenanceCrew.setRequestedFloor(null);
        maintenanceCrew.setWaiting(false);
        maintenanceCrew.setFirstRequest(true);
        Assert.assertNull(maintenanceCrew.getRequestedFloor());
        Assert.assertEquals(false, maintenanceCrew.isWaiting());
        Assert.assertEquals(true, maintenanceCrew.isFirstRequest());
        maintenanceCrew.decide();
        Assert.assertEquals(false, maintenanceCrew.isFirstRequest());
    }

    @Test
    public void exitElevator() throws Exception {
        maintenanceCrew.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertEquals(simulation.getBuilding().getFloors().get(2), maintenanceCrew.getCurrentFloor());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(maintenanceCrew));
        Assert.assertNull(maintenanceCrew.getRequestedFloor());
        Assert.assertEquals(false, maintenanceCrew.isWaiting());
    }

    @Test
    public void enterElevator() throws Exception {
        maintenanceCrew.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(maintenanceCrew));
        maintenanceCrew.enterElevator();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(2).getPeople().contains(maintenanceCrew));
    }

    @Test
    public void requestFloor() throws Exception {
        maintenanceCrew.setRequestedFloor(null);
        simulation.getBuilding().getFloors().get(0).getStandardQueue().clear();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(maintenanceCrew));
        Assert.assertNull(maintenanceCrew.getRequestedFloor());
        maintenanceCrew.requestFloor(simulation.getBuilding().getFloors().get(2));
        Assert.assertFalse(maintenanceCrew.isFirstRequest());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(maintenanceCrew));
        Assert.assertTrue(simulation.getBuilding().getElevator().getRequests().contains(maintenanceCrew));
        Assert.assertTrue(maintenanceCrew.isWaiting());
    }

    @Test
    public void calculateTimeInBuilding() throws Exception {
        maintenanceCrew.setTimeInBuilding(0);
        Assert.assertEquals(0, maintenanceCrew.getTimeInBuilding());
        maintenanceCrew.calculateTimeInBuilding();
        Assert.assertThat(maintenanceCrew.getTimeInBuilding(), allOf(greaterThanOrEqualTo(120),lessThanOrEqualTo(240)));
    }

    @Test
    public void enterBuilding() throws Exception {
        maintenanceCrew.setRequestedFloor(null);
        maintenanceCrew.setWaiting(false);
        maintenanceCrew.setFirstRequest(true);
        Assert.assertNull(maintenanceCrew.getRequestedFloor());
        Assert.assertEquals(false, maintenanceCrew.isWaiting());
        Assert.assertEquals(true, maintenanceCrew.isFirstRequest());
        maintenanceCrew.enterBuilding();
        Assert.assertEquals(false, maintenanceCrew.isFirstRequest());
    }

    @Test
    public void exitBuilding() throws Exception {
        maintenanceCrew.setRequestedFloor(null);
        simulation.getBuilding().getFloors().get(2).getStandardQueue().clear();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(2).getStandardQueue().contains(maintenanceCrew));
        Assert.assertNull(maintenanceCrew.getRequestedFloor());
        maintenanceCrew.setCurrentFloor(simulation.getBuilding().getFloors().get(2));
        Assert.assertEquals(simulation.getBuilding().getFloors().get(2), maintenanceCrew.getCurrentFloor());
        maintenanceCrew.exitBuilding();
        Assert.assertFalse(maintenanceCrew.isFirstRequest());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getStandardQueue().contains(maintenanceCrew));
        Assert.assertTrue(simulation.getBuilding().getElevator().getRequests().contains(maintenanceCrew));
        Assert.assertTrue(maintenanceCrew.isWaiting());
    }
}
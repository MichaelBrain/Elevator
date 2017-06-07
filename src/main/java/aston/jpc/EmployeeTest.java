package aston.jpc;

import org.junit.*;

import static org.hamcrest.Matchers.*;

public class EmployeeTest {
    Simulation simulation;
    Employee employee;

    @Before
    public void setUp() throws Exception {
        simulation = new Simulation(1, 1, 0, 0, 1);
        employee = new Employee(simulation.getBuilding().getNextId(), simulation, simulation.getBuilding().getFloors());
    }

    @Test
    public void decide() throws Exception {
        employee.setRequestedFloor(null);
        employee.setWaiting(false);
        employee.setFirstRequest(true);
        Assert.assertNull(employee.getRequestedFloor());
        Assert.assertEquals(false, employee.isWaiting());
        Assert.assertEquals(true, employee.isFirstRequest());
        employee.decide();
        Assert.assertEquals(true, employee.isWaiting());
        Assert.assertEquals(false, employee.isFirstRequest());
        Assert.assertNotNull(employee.getRequestedFloor());
    }

    @Test
    public void exitElevator() throws Exception {
        employee.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertEquals(simulation.getBuilding().getFloors().get(2), employee.getCurrentFloor());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(employee));
        Assert.assertNull(employee.getRequestedFloor());
        Assert.assertEquals(false, employee.isWaiting());
    }

    @Test
    public void enterElevator() throws Exception {
        employee.exitElevator(simulation.getBuilding().getFloors().get(2));
        Assert.assertTrue(simulation.getBuilding().getFloors().get(2).getPeople().contains(employee));
        employee.enterElevator();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(2).getPeople().contains(employee));
    }

    @Test
    public void calculateNewFloor() throws Exception {
        Assert.assertNotNull(employee.calculateNewFloor());
        Assert.assertThat(employee.calculateNewFloor(), isOneOf(simulation.getBuilding().getFloors().get(0), simulation.getBuilding().getFloors().get(1), simulation.getBuilding().getFloors().get(2), simulation.getBuilding().getFloors().get(3), simulation.getBuilding().getFloors().get(4), simulation.getBuilding().getFloors().get(5)));
    }

    @Test
    public void requestFloor() throws Exception {
        employee.setRequestedFloor(null);
        simulation.getBuilding().getFloors().get(0).getStandardQueue().clear();
        Assert.assertFalse(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(employee));
        Assert.assertNull(employee.getRequestedFloor());
        employee.requestFloor(simulation.getBuilding().getFloors().get(2));
        Assert.assertFalse(employee.isFirstRequest());
        Assert.assertTrue(simulation.getBuilding().getFloors().get(0).getStandardQueue().contains(employee));
        Assert.assertTrue(simulation.getBuilding().getElevator().getRequests().contains(employee));
        Assert.assertTrue(employee.isWaiting());
    }
}
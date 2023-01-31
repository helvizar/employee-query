package com.thecompany.service;

import com.thecompany.model.Employee;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EmployeeService {

    // create get employee
    @Transactional
    public Response create(JsonObject request) {
        Employee employee = new Employee();
        employee.name = request.getString("name");
        employee.manager_id = request.getInteger("manager_id");

        employee.persist();
        return Response.ok().entity(new HashMap<>()).build();
    }

    // get all data employee
    public Response getAll() {
        List<Employee> employee = Employee.listAll();
        return Response.ok().entity(employee).build();
    }

    // get employee by id
    public Response getById(Integer id) {
        Employee employee = Employee.findById(id);
        return Response.ok().entity(employee).build();
    }

    public Response getByManagerId() {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> manager = new HashMap<>();
        HashMap<String, Object> employee = new HashMap<>();
        HashMap<String, Object> employee2 = new HashMap<>();
        HashMap<String, Object> employee3 = new HashMap<>();


        List<Employee> budi = Employee.list(
                "SELECT e FROM Employee e WHERE id = ?1", 2);

        List<Employee> bawahanBudi = Employee.list(
                "SELECT e FROM Employee e WHERE manager_id = ?1", 2);

        List<Employee> bawahanLisa = Employee.list(
                "SELECT e FROM Employee e WHERE manager_id = ?1", 5);

        List<Employee> bawahanEmma = Employee.list(
                "SELECT e FROM Employee e WHERE manager_id = ?1", 6);

        ArrayList<Map<String, Object>> managerresult = new ArrayList<>();
        HashMap<String, Object> value = new HashMap<>();



        employee3.put("BawahanLisa", bawahanLisa);
        employee3.put("BawahanEmma", bawahanEmma);

        employee2.put("Manager/Employee", bawahanBudi);
        employee2.put("Employee", employee3);

        employee.put("Manager", budi);
        employee.put("BawahanBudi", employee2);

        return Response.ok().entity(employee).build();

        /*
    recursive query:

    WITH RECURSIVE underlink AS (
	SELECT
		e.id,
		e.name,
		e.manager_id
	FROM
		employee_service.employee e
	WHERE
		e.id = 2
	UNION
		SELECT
			e.id,
			e.name,
			e.manager_id
		FROM
			employee_service.employee e
		INNER JOIN underlink u ON u.id = e.manager_id
) SELECT
	*
FROM
	underlink;
        * */
    }

    @Transactional
    public Response updateById(Integer id, JsonObject request) {
        Employee employee = Employee.findById(id);

        employee.name = request.getString("name");
        employee.manager_id = request.getInteger("manager_id");

        employee.persist();

        return Response
                .ok()
                .entity(Map.of(
                        "id",
                        employee.id))
                .build();
    }

    @Transactional
    public Response deleteById(Integer id) {
        Employee employee = Employee.findById(id);

        employee.delete();

        return Response
                .status(Response.Status.NO_CONTENT)
                .entity(Map.of("Message", "Data_Deleted"))
                .build();
    }
}

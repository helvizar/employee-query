package com.thecompany.controller;

import com.thecompany.model.Employee;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController {

    @POST
    @Transactional
    public Response create(JsonObject request) {
        Employee employee = new Employee();
        employee.name = request.getString("name");
        employee.manager_id = request.getInteger("manager_id");

        employee.persist();
        return Response.ok().entity(new HashMap<>()).build();
    }

    @GET
    public Response getAll() {
        List<Employee> employee = Employee.listAll();
        return Response.ok().entity(employee).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        Employee employee = Employee.findById(id);
        return Response.ok().entity(employee).build();
    }

    @GET
    @Path("/manager/2")
    public Response getByManagerId() {
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> manager = new HashMap<>();
        HashMap<String, Object> employee = new HashMap<>();
        HashMap<String, Object> employee2 = new HashMap<>();
        HashMap<String, Object> employee3 = new HashMap<>();


        List<Employee> budi = Employee.list(
                "SELECT e FROM Employee e WHERE id = ?1", 2);

        List<Employee> lisa = Employee.list(
                "SELECT e FROM Employee e WHERE manager_id = ?1", 2);

        List<Employee> harry = Employee.list(
                "SELECT e FROM Employee e WHERE manager_id = ?1", 5);

        List<Employee> ismet = Employee.list(
                "SELECT e FROM Employee e WHERE manager_id = ?1", 6);

        employee3.put("ismet", ismet);

        employee2.put("bawahanLisa", harry);
        employee2.put("bawahanEmma", employee3);

        employee.put("bawahanBudi", lisa);
        employee.put("harry", employee2);

        manager.put("manager", budi);
        manager.put("employee", employee);

        result.put("data", manager);

        return Response.ok().entity(result).build();

        /*
        Seharusnya menggunakan recursive query:

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
}




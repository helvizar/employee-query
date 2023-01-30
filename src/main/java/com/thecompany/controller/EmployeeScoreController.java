package com.thecompany.controller;

import com.thecompany.model.Employee;
import com.thecompany.model.EmployeeScore;
import io.vertx.core.json.JsonObject;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/score")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeScoreController {

    @POST
    @Transactional
    public Response create(JsonObject request) {
        EmployeeScore employeeScore = new EmployeeScore();
        employeeScore.name = request.getString("name");
        employeeScore.score = request.getInteger("score");

        employeeScore.persist();
        return Response.ok().entity(new HashMap<>()).build();
    }

    @GET
    public Response getScore() {
        List<Employee> employee = Employee.list(
                "SELECT AVG(score) FROM EmployeeScore e WHERE id = ?1 OR id = ?2 OR id = ?3 OR id = ?4 OR id = ?5 OR id = ?6",2, 5, 6, 11, 12, 13);


        return Response.ok().entity(Map.of("Average Score:", employee)).build();

        /*
        Seharusnya menggunakan recursive query, tetapi saya mengalami kendala untuk mengimplementasikannya ke quarkus:

        WITH RECURSIVE underlink AS (
	SELECT
		id,
		name,
		manager_id
	FROM
		employee_service.employee
	WHERE
		id = 2
	UNION
		SELECT
			e.id,
			e.name,
			e.manager_id
		FROM
			employee_service.employee e
		INNER JOIN underlink u ON u.id = e.manager_id
) SELECT AVG(score) FROM underlink u
	JOIN employee_service.employeescore es ON (u.id = es.id);
        */
    }
}

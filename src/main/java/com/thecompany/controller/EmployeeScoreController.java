package com.thecompany.controller;


import com.thecompany.service.EmployeeScoreService;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/employee/score")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeScoreController {

    @Inject
    EmployeeScoreService employeeScoreService;

    @POST
    public Response create(JsonObject request) {
        return employeeScoreService.create(request);
    }

    @GET
    public Response getScore() {
    return employeeScoreService.getScore();
    }
}

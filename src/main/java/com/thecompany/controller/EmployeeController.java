package com.thecompany.controller;

import com.thecompany.service.EmployeeService;
import io.vertx.core.json.JsonObject;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/employee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController {

    @Inject
    EmployeeService employeeService;

    @POST
    public Response create(JsonObject request) {
        return employeeService.create(request);
    }

    @GET
    public Response getAll() {
        return employeeService.getAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        return employeeService.getById(id);
    }

    @GET
    @Path("/manager/2")
    public Response getByManagerId() {
        return employeeService.getByManagerId();
    }

    @PUT
    @Path("/{id}")
    public Response updateById(@PathParam("id") Integer id, JsonObject request) {
        return employeeService.updateById(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteById(@PathParam("id") Integer id) {
        return employeeService.deleteById(id);
    }
}
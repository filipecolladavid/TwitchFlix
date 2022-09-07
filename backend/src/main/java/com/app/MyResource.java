package com.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/hello")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Jersey Jetty example.";
    }

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Users_test hello(@PathParam("name") String name) {

        Users_test obj = new Users_test();
        obj.setId(0);
        obj.setName(name);

        return obj;

    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Users_test> helloList() {

        List<Users_test> list = new ArrayList<>();

        Users_test obj1 = new Users_test();
        obj1.setId(1);
        obj1.setName("mkyong");
        list.add(obj1);

        Users_test obj2 = new Users_test();
        obj2.setId(2);
        obj2.setName("zilap");
        list.add(obj2);

        return list;

    }

}
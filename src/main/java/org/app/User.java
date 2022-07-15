package org.app;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.sources.tables.Users;

import java.sql.*;
import java.util.List;

@Path("/user")
public class User {
    /**
     * Endpoint to check if username exists
     * @param userClient - username given by client
     * @return username if exists
     */
    @GET
    @Path("/info/{user}")
    @Produces("application/json")
    public Response getUserInformation(@PathParam("user") String userClient) {
        // Returns username if exists
        try (Connection connection = DriverManager.getConnection(
                GlobalConst.url,
                GlobalConst.user,
                GlobalConst.pass)
        ) {
            System.out.println("Connected");
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            create.select(Users.USERS.USERNAME).from(Users.USERS).fetch();
            List<?> r = create.select(Users.USERS.USERNAME).from(Users.USERS).where(Users.USERS.USERNAME.eq(userClient)).fetch("username");
            if(r.size() == 1) return Response.ok("User Exists").build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(500,"Server error").build();
        }
        return Response.status(404,"User not found").build();
    }

    /**
     * TODO - password integration
     * Endpoint to registration in the database
     * @param nick - nickname intended for registration
     * @param pwd - password intended for registration
     * @return string with failed or accepted
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces ("application/json")
    public Response register(@FormParam("nick") String nick, @FormParam("pwd") String pwd) {
        System.out.println("NICK: " + nick);
        if(nick == null || nick.equals("")) return Response.status(400,"User can't be empty").build();

        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);

            create.insertInto(Users.USERS, Users.USERS.USERNAME).values(nick).execute();
            System.out.println("Success");
            return Response.ok("User registered with success").build();

        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.status(409, "User Already exists").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500,"Unknown Server Error").build();
        }
    }

    /**
     * TODO - password integration
     * Endpoint for login in the app
     * @param nick - nickname for login
     * @param pwd - password for login
     * @return success or fail
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response login(@FormParam("nick") String nick, @FormParam("pwd") String pwd) {
        System.out.println("Nick: " + nick);
        if(nick == null || nick.equals("")) return Response.status(400,"User can't be empty").build();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            System.out.println("Making query ...");
            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
            List<?> r = create.select(Users.USERS.USERNAME).from(Users.USERS).where(Users.USERS.USERNAME.eq(nick)).fetch("username");

            System.out.println(r);
            if (r.size() == 1) return Response.ok("User Exists").build();

            return Response.ok("Login successful").build();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return Response.status(409, "User Already exists").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500,"Unknown Server Error").build();
        }
    }
}

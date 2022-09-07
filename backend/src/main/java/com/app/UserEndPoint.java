package com.app;

import jakarta.ws.rs.*;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.jooq.sources.tables.Users;

@Path("/user")
public class UserEndPoint {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "User endpoint";
    }

    /**
     * Endpoint to registration in the database
     *
     * @param nick - nickname intended for registration
     * @param pwd  - password intended for registration
     * @return string with failed or accepted
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response register(@FormParam("nick") String nick, @FormParam("pwd") String pwd) {
        if (nick == null || nick.equals("")) return Response.status(400, "User can't be empty").build();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);

            User user = new User(nick, pwd);

            create.insertInto(Users.USERS, Users.USERS.USERNAME, Users.USERS.PASSWORD).values(user.getUsername(), user.getPassword()).execute();
            System.out.println("Success");
            return Response.ok("User registered with success").build();

        } catch (DataAccessException e) {
            e.printStackTrace();
                return Response.status(409, "User Already exists").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500, "Unknown Server Error").build();
        }
    }

    /**
     * Endpoint for login in the app
     *
     * @param nick - nickname for login
     * @param pwd  - password for login
     * @return success or fail
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response login(@FormParam("nick") String nick, @FormParam("pwd") String pwd) {
        if (nick == null || nick.equals("")) return Response.status(400, "User can't be empty").build();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            System.out.println("Making query ...");
            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
            List<?> r = create.select(Users.USERS.USERNAME).from(Users.USERS).where(Users.USERS.USERNAME.eq(nick)).fetch("username");

            System.out.println(r);
            if (r.size() == 1) {
                String storedPWD = create.select(Users.USERS.PASSWORD).from(Users.USERS).where(Users.USERS.USERNAME.eq(nick)).fetch("password").get(0).toString();
                User u = new User(nick, pwd);
                if (!u.checkPassword(storedPWD)) {
                    System.out.println("Wrong password");
                    return Response.status(403, "Wrong password").build();
                }
                return Response.ok("Login successful").build();
            }
            return Response.status(403, "User doesn't exist").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500, "Unknown Server Error").build();
        }
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response delete(@FormParam("nick") String nick, @FormParam("pwd") String pwd) {
        if (nick == null || nick.equals("")) return Response.status(400, "User can't be empty").build();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            System.out.println("Making query ...");
            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
            List<?> r = create.select(Users.USERS.USERNAME).from(Users.USERS).where(Users.USERS.USERNAME.eq(nick)).fetch("username");

            // System.out.println(r);
            if (r.size() == 1) {
                String storedPWD = create.select(Users.USERS.PASSWORD).from(Users.USERS).where(Users.USERS.USERNAME.eq(nick)).fetch("password").get(0).toString();
                User u = new User(nick, pwd);
                if (!u.checkPassword(storedPWD)) return Response.status(403, "Wrong password").build();
                create.delete(Users.USERS)
                        .where(Users.USERS.USERNAME.eq(nick))
                        .execute();
                return Response.ok("delete successful").build();
            }
            return Response.status(403, "User doesn't exist").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500, "Unknown Server Error").build();
        }
    }

}

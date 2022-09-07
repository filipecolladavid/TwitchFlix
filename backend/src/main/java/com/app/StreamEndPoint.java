package com.app;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.sources.tables.Movie;
import org.jooq.sources.tables.Streams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/stream")
public class StreamEndPoint {

    //List of all streams


    //base rtmp URL
    String baseURL = "something";

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stream> getAllStreams() throws SQLException {
        List<Stream> allStreams = new ArrayList<>();

        System.out.println("Connecting to db ...");
        Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
        System.out.println("Connected to db");

        System.out.println("Making query for movies ...");
        DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
        List<?> title = create.select(Streams.STREAMS.STREAM_TITLE).from(Streams.STREAMS).fetch("stream_title");
        List<?> nick = create.select(Streams.STREAMS.STREAM_USERNAME).from(Streams.STREAMS).fetch("stream_username");

        for (int i = 0; i < title.size(); i++) {
            String t = title.get(i).toString();
            String n = nick.get(i).toString();

            Stream s = new Stream(n, t);

            allStreams.add(s);
        }

        return allStreams;
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response addStream(@FormParam("nick") String nick, @FormParam("title") String title) {
        if (nick == null || nick.equals("") || title == null || title.equals(""))
            return Response.status(400, "StreamTitle or username can't be empty").build();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
            create.insertInto(Streams.STREAMS,Streams.STREAMS.STREAM_USERNAME,Streams.STREAMS.STREAM_TITLE).values(nick,title).execute();
            System.out.println("Added stream");
            return Response.status(200,"Stream Added with success ").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500, "Unknown Server Error").build();

        }
    }

    @POST
    @Path("/remove")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json")
    public Response removeStream(@FormParam("nick") String nick, @FormParam("title") String title) {
        if (nick == null || nick.equals("") || title == null || title.equals(""))
            return Response.status(400, "StreamTitle or username can't be empty").build();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
            List<?> r = create.select(Streams.STREAMS.STREAM_USERNAME).from(Streams.STREAMS).where(Streams.STREAMS.STREAM_USERNAME.eq(nick)).fetch("stream_username");
            if (r.size() == 1) {
                create.delete(Streams.STREAMS)
                        .where(Streams.STREAMS.STREAM_USERNAME.eq(nick))
                        .execute();
                return Response.ok("delete successful").build();
            }

            return Response.status(403, "Stream doesn't exist").build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500, "Unknown Server Error").build();
        }
    }

}

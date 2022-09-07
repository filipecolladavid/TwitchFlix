package com.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.jooq.sources.tables.Movie;
import org.jooq.sources.tables.Users;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/movies")
public class MoviesEndPoint {

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Movies> getAllMovies() throws SQLException {
        List<Movies> moviesList = new ArrayList<>();
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            System.out.println("Making query for movies ...");
            DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
            List<?> title = create.select(Movie.MOVIE.TITLE).from(Movie.MOVIE).fetch("title");
            List<?> release_year = create.select(Movie.MOVIE.RELEASE_YEAR).from(Movie.MOVIE).fetch("release_year");
            List<?> duration = create.select(Movie.MOVIE.DURATION).from(Movie.MOVIE).fetch("duration");
            List<?> link = create.select(Movie.MOVIE.LINK).from(Movie.MOVIE).fetch("link");
            List<?> link_pic = create.select(Movie.MOVIE.LINK_PIC).from(Movie.MOVIE).fetch("link_pic");

            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM movie";
            ArrayList<ArrayList<String>> genres = new ArrayList<>();

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Array s = rs.getArray("genre");
                String[] gs = (String[]) s.getArray();
                ArrayList<String> movieGenre = new ArrayList<>();
                movieGenre.addAll(Arrays.asList(gs));
                genres.add(movieGenre);
            }

            for (int i = 0; i < title.size(); i++) {
                String t = title.get(i).toString();
                int y = (int) release_year.get(i);
                String[] g = genres.get(i).toArray(new String[0]);
                System.out.println(g);
                int d = (int) duration.get(i);
                String l = link.get(i).toString();
                String p = "";
                if(link_pic.get(i) != null) p = link_pic.get(i).toString();
                System.out.println(p);

                Movies m = new Movies(t, y, g, d, l,p);
                System.out.println(m);
                moviesList.add(m);
            }

        } catch (
                DataAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moviesList;
    }

}

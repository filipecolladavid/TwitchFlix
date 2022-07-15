package org.app;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.sql.*;
 /*Integer[] movie_id = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.MOVIE_ID);
            String[] title = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.TITLE);
            Integer[] year = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.RELEASE_YEAR);
            String[][] genre = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.GENRE);
            Integer[] duration = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.DURATION);
            String[] link = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.LINK);
            String json = "";
            for(int i = 0; i< movie_id.length; i++) {
                json = Json.createObjectBuilder()
                        .add("movie_id", movie_id[i])
                        .add("title",title[i])
                        .add("year",year[i])
                        .add("duration",duration[i])
                        .add("link",link[i])
                        .build()
                        .toString();
            }

            System.out.println(json);*/

@Path("/movie")
public class Movies {


    @GET
    @Path("/getAllMovies")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonArray getAll() {

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        Statement stmt = null;
        try {
            System.out.println("Connecting to db ...");
            Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
            System.out.println("Connected to db");

            stmt = con.createStatement();

            String sql = "SELECT * FROM movie";

            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {
                Array s = rs.getArray("genre");
                String[] gs = (String[]) s.getArray();
                JsonArrayBuilder genreArray = Json.createArrayBuilder();
                for (int i = 0; i < gs.length; i++) {
                    genreArray.add(gs[i]);
                }
                arrayBuilder.add(Json.createObjectBuilder()
                        .add("movie_id", rs.getInt("movie_id"))
                        .add("title", rs.getString("title"))
                        .add("release_year", rs.getInt("release_year"))
                        .add("genre", genreArray)
                        .add("duration", rs.getString("duration"))
                        .add("link", rs.getString("link"))
                        .build());
            }
            return (JsonArray) arrayBuilder;
        } catch (SQLException e) {
            e.printStackTrace();
            // return Response.status(500,"Error getting data from database").build();
        } catch (Exception e) {
            e.printStackTrace();
            // return Response.status(500,"Error").build();
        }
        return null;
    }
}

package org.app;// For convenience, always static import your generated tables and jOOQ functions to decrease verbosity:

import org.jooq.tools.json.ParseException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException, SQLException {
        /*System.out.println("Making query ...");
        System.out.println("Making query ...");
        DSLContext create = DSL.using(con, SQLDialect.POSTGRES);
        Integer[] movie_id = create.select().from(Movie.MOVIE).fetchArray(Movie.MOVIE.MOVIE_ID);
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

        System.out.println("Connecting to db ...");
        Connection con = DriverManager.getConnection(GlobalConst.url, GlobalConst.user, GlobalConst.pass);
        System.out.println("Connected to db");

        Statement stmt = con.createStatement();

        String sql = "SELECT * FROM movie";

        ResultSet rs = stmt.executeQuery(sql);

        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();

        List<JSONArray> list = new ArrayList<>();

/*
        while(rs.next()) {
            jo.put("movie_id", rs.getInt("movie_id"));
            jo.put("title", rs.getString("title"));
            jo.put("release_year", rs.getInt("release_year"));
            Array s = rs.getArray("genre");
            String[] gs = (String[]) s.getArray();
            List<JSONArray> listGenre = new ArrayList<>();
            for(int i = 0; i<gs.length; i++) {
                JSONArray jag =  
            }

        }
        */
    }
}
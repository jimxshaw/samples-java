package com.github.flink;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FilterMovies {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple3<Long, String, String>> lines = env.readCsvFile("movies.csv")
                                                            .ignoreFirstLine()
                                                            .parseQuotedStrings('"')
                                                            .ignoreInvalidLines()
                                                            .types(Long.class, String.class, String.class);

        DataSet<Movie> movies = lines.map(new MapFunction<Tuple3<Long, String, String>, Movie>() {
            @Override
            public Movie map(Tuple3<Long, String, String> csvLine) {
                String movieName = csvLine.f1;
                String[] genres = csvLine.f2.split("\\|");

                return new Movie(movieName, new HashSet<String>(Arrays.asList(genres)));
            }
        });

        DataSet<Movie> filteredMovies = movies.filter(new FilterFunction<Movie>() {
            @Override
            public boolean filter(Movie movie) {
                return movie.getGenres().contains("Drama");
            }
        });

        filteredMovies.writeAsText("filter-output");

        env.execute();
    }
}

class Movie {
    private String name;
    private Set<String> genres;

    public Movie(String name, Set<String> genres) {
        this.name = name;
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public Set<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Movie{" + "name='" + name + '\'' + ", genres=" + genres + '}';
    }
}

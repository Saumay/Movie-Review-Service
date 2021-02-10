package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie implements Comparable<Movie> {
    private String name;
    private int year;
    private List<String> genres;

    private int numberOfReviews;
    private int totalRating;
    private Map<Status, Integer> ratingByStatus;

    public Movie(String name, int year, List<String> genres) {
        this.name = name;
        this.year = year;
        this.genres = genres;

        this.ratingByStatus = new HashMap<Status, Integer>() {{
            put(Status.VIEWER, 0);
            put(Status.CRITIC, 0);
            put(Status.EXPERT, 0);
            put(Status.ADMIN, 0);
        }};
    }

    @Override
    public int compareTo(Movie movie) {
        int criticRatingMovie1 = movie.ratingByStatus.get(Status.CRITIC);
        int criticRatingMovie2 = this.ratingByStatus.get(Status.CRITIC);
        return Integer.compare(criticRatingMovie1, criticRatingMovie2);
    }
}

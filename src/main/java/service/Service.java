package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.InvalidMovieDescriptionException;
import exceptions.MovieNotFoundException;
import exceptions.MovieYetToBeReleasedException;
import exceptions.MultipleReviewsNotAllowedException;
import exceptions.UserNotFoundException;
import model.Movie;
import model.Review;
import model.Status;
import model.User;

public class Service {

    private static final Pattern MOVIE_PATTERN = Pattern.compile("^\"([^\"]*)\" released in Year (\\d{4}).* for Genres? (.*)");

    private Map<String, Movie> movieMap = new HashMap<>();
    private Map<String, User> userMap = new HashMap<>();
    private List<Review> reviews = new ArrayList<>();

    public void addMovie(String movieStr) {
        Matcher matcher = MOVIE_PATTERN.matcher(movieStr);
        if (matcher.matches()) {
            String movieName = matcher.group(1);
            int movieYear = Integer.parseInt(matcher.group(2));
            String genreStr = matcher.group(3);
            movieMap.put(movieName, new Movie(movieName, movieYear, getGenres(genreStr)));
        } else {
            throw new InvalidMovieDescriptionException();
        }
    }

    private List<String> getGenres(String genreStr) {
        return Arrays.asList(genreStr.replaceAll("[\" ]", "").split("[,&]"));
    }

    public void addUser(String userName) {
        userMap.put(userName, new User(userName));
    }

    public void addReview(String userName, String movieName, int rating) {
        handleExceptions(userName, movieName, rating);

        User user = userMap.get(userName);

        Movie movie = movieMap.get(movieName);
        movie.setNumberOfReviews(movie.getNumberOfReviews() + 1);
        Map<Status, Integer> ratingByStatus = movie.getRatingByStatus();
        int weightedRating = rating * user.getStatus().getWeight();
        ratingByStatus.put(user.getStatus(), ratingByStatus.get(user.getStatus()) + weightedRating);
        movie.setTotalRating(movie.getTotalRating() + weightedRating);

        user.setNoOfPublishedReviews(user.getNoOfPublishedReviews() + 1);
        if (user.getNoOfPublishedReviews() == 3) {
            user.setStatus(Status.CRITIC);
        }

        reviews.add(new Review(userName, movieName, rating));
    }

    private void handleExceptions(String userName, String movieName, int rating) {
        if (!userMap.containsKey(userName)) {
            throw new UserNotFoundException(userName);
        } else if (!movieMap.containsKey(movieName)) {
            throw new MovieNotFoundException(movieName);
        } else if (reviews.contains(new Review(userName, movieName, rating))) {
            throw new MultipleReviewsNotAllowedException();
        } else if (movieMap.get(movieName).getYear() >= LocalDate.now().getYear()) {
            throw new MovieYetToBeReleasedException(movieName);
        }
    }

    public double getAverageReviewScoreForYear(int year) {
        int totalRating = 0;
        int totalNoOfReviews = 0;
        for (Movie movie : movieMap.values()) {
            if (movie.getYear() == year) {
                totalNoOfReviews += movie.getNumberOfReviews();
                totalRating += movie.getTotalRating();
            }
        }
        if (totalNoOfReviews > 0) {
            return (double) totalRating / (double) totalNoOfReviews;
        }
        return 0;
    }

    public double getAverageScoreForParticularMovie(String movieName) {
        Movie movie = movieMap.get(movieName);
        if (movie.getNumberOfReviews() > 0) {
            return (double) movie.getTotalRating() / (double) movie.getNumberOfReviews();
        }
        return 0;
    }

    public List<Movie> getTopMoviesByTotalReviewByCriticsInParticularGenre(int n, String genre) {
        PriorityQueue<Movie> moviesHeap = new PriorityQueue<>();

        movieMap.values().stream()
                .filter(movie -> movie.getGenres().contains(genre))
                .forEach(moviesHeap::add);

        List<Movie> topNMovies = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            topNMovies.add(moviesHeap.remove());
        }
        return topNMovies;
    }
}

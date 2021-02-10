import model.Movie;
import service.Service;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Service service = new Service();

        // Onboard movies onto your platform in 3 different years:
        service.addMovie("\"Don\" released in Year 2006 for Genres \"Action\" & \"Comedy\"");
        service.addMovie("\"Tiger\" released in Year 2008 for Genre \"Drama\"");
        service.addMovie("\"Padmaavat\" released in Year 2006 for Genre \"Comedy\"");
        service.addMovie("\"Lunchbox\" released in Year 2021 for Genre \"Drama\"");
        service.addMovie("\"Guru\" released in Year 2006 for Genre \"Drama\"");
        service.addMovie("\"Metro\" released in Year 2006 for Genre \"Romance\"");

        // Add users to the system:
        service.addUser("SRK");
        service.addUser("Salman");
        service.addUser("Deepika");

        // Add Reviews:
        service.addReview("SRK", "Don", 2);
        service.addReview("SRK", "Padmaavat", 8);
        service.addReview("Salman", "Don", 5);
        service.addReview("Deepika", "Don", 9);
        service.addReview("Deepika", "Guru", 6);
        service.addReview("SRK", "Don", 10);            // Exception
        service.addReview("Deepika", "Lunchbox", 5);    // Exception
        service.addReview("SRK", "Tiger", 5);
        service.addReview("SRK", "Metro", 7);

        service.addReview("SRK", "Guru", 9);
        service.addReview("Deepika", "Padmaavat", 6);
        service.addReview("Deepika", "Tiger", 5);


        // Get average review score in a particular year of release
        double averageScoreForAYear = service.getAverageReviewScoreForYear(2006);
        System.out.println(averageScoreForAYear);

        // Get average review score for a particular movie
        double averageScoreForAMovie = service.getAverageScoreForParticularMovie("Don");
        System.out.println(averageScoreForAMovie);

        // List top n movies by total review score by ‘critics’ in a particular genre
        List<Movie> topMovies = service.getTopMoviesByTotalReviewByCriticsInParticularGenre(3, "Drama");
        topMovies.forEach(movie -> System.out.println(movie.getName()));
    }
}

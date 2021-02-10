package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.InvalidMovieDescriptionException;
import exceptions.MovieNotFoundException;
import exceptions.MovieYetToBeReleasedException;
import exceptions.MultipleReviewsNotAllowedException;
import exceptions.UserNotFoundException;

class ServiceTest {

    private Service service;

    @BeforeEach
    void setup() {
        service = new Service();
    }

    @Test
    void testInvalidMovieDescriptionException() {
        // given
        String expectedMessage = "Invalid movie description. Doesn't match the regex";

        // when
        Exception exception = assertThrows(InvalidMovieDescriptionException.class, () -> {
            service.addMovie("\"Don\" invalid description \"Action\" & \"Comedy\"");
        });

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testUserNotFoundException() {
        // given
        service.addUser("SRK");
        String reviewingUser = "Salman";
        String expectedMessage = "Provided user '" + reviewingUser + "' doesn't exist";

        // when
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            service.addReview(reviewingUser, "Dangal", 2);
        });

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testMovieNotFoundException() {
        // given
        service.addMovie("\"Don\" released in Year 2006 for Genres \"Action\" & \"Comedy\"");
        service.addUser("SRK");
        String reviewingMovie = "Dangal";
        String expectedMessage = "Provided movie '" + reviewingMovie + "' doesn't exist";

        // when
        Exception exception = assertThrows(MovieNotFoundException.class, () -> {
            service.addReview("SRK", reviewingMovie, 2);
        });

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testMultipleReviewsNotAllowedException() {
        // given
        service.addMovie("\"Don\" released in Year 2006 for Genres \"Action\" & \"Comedy\"");
        service.addUser("SRK");
        String expectedMessage = "Multiple reviews not allowed";

        // when
        Exception exception = assertThrows(MultipleReviewsNotAllowedException.class, () -> {
            service.addReview("SRK", "Don", 2);
            service.addReview("SRK", "Don", 3);
        });

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testMovieYetToBeReleasedException() {
        // given
        String movieName = "Don";
        service.addMovie("\"" + movieName + "\" released in Year 2021 for Genres \"Action\" & \"Comedy\"");
        service.addUser("SRK");
        String expectedMessage = "'" + movieName + "' Multiple reviews not allowed";

        // when
        Exception exception = assertThrows(MovieYetToBeReleasedException.class, () -> {
            service.addReview("SRK", movieName, 2);
        });

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
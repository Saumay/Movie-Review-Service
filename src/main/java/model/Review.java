package model;

import lombok.Getter;

@Getter
public class Review {
    private String userName;
    private String movieName;
    private int rating;

    public Review(String userName, String movieName, int rating) {
        this.userName = userName;
        this.movieName = movieName;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object obj) {
        Review review = (Review) obj;
        return review.getMovieName().equals(this.movieName) && review.getUserName().equals(this.userName);
    }
}

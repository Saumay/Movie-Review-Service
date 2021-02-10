package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private String name;
    private Status status = Status.VIEWER;
    private int noOfPublishedReviews = 0;

    public User(String name) {
        this.name = name;
    }
}

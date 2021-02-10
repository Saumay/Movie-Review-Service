package model;

public enum Status {
    VIEWER(1),
    CRITIC(2),
    EXPERT(3),
    ADMIN(4);

    int weight;

    Status(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}

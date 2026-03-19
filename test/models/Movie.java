package models;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fer

public class Movie {
    private String title;
    private String director;
    private int year;
    private double rating;
    private String genre;
    private String review;

    public Movie(String title, String director, int year, double rating, String genre, String review) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.genre = genre;
        this.review = review;
    }

    public Movie() {
        this("unknown", "unknown", 0, (double) 0.0F, "unknown", "empty");
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return this.director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getRating() {
        return this.rating;
    }


    public void setRating(double rating) {
        if (rating >= (double) 0.0F && rating <= (double) 10.0F) {
            this.rating = rating;
        }

    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String toString() {
        return String.format("《%s》(%d) - director:%s - rating:%.1f", this.title, this.year, this.director, this.rating);
    }


    public int getRatingLevel(double rating) {
        if (this.rating >= 9.0) {
            return 1;
        } else if (this.rating >= 8.0) {
            return 2;
        } else if (this.rating >= 7.0) {
            return 3;
        } else {
            return this.rating >= 6.0 ? 4 : 5;
        }

    }
}
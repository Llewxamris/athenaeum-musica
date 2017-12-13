package ca.qc.cegep_heritage.athenaeummusica;

public class Album {
    /* Represents a single album item inside the users catalogue. */

    private int id;
    private String name;
    private String artist;
    private String genre;
    private int releaseYear = -1;
    private String format;
    private double price = -1;

    public Album() {
    }

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, String artist, String genre, int releaseYear, String format, double price) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.format = format;
        this.price = price;
    }

    public Album(String name, String artist, String genre, int releaseYear, String format, double price, byte[] image) {
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.format = format;
        this.price = price;
        this.image = image;
    }

    Album(int id, String name, String artist, String genre, int releaseYear, String format, double price, byte[] image) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.format = format;
        this.price = price;
        this.image = image;
    }

    private byte[] image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    String getArtist() {
        return artist;
    }

    String getGenre() {
        return genre;
    }

    int getReleaseYear() {
        return releaseYear;
    }

    String getFormat() {
        return format;
    }

    double getPrice() {
        return price;
    }

    byte[] getImage() {
        return image;
    }
}

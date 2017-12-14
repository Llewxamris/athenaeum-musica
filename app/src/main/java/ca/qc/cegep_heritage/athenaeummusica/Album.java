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

    public Album(String name, String genre, String format) {
        this.name = name;
        this.genre = genre;
        this.format = format;
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

    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

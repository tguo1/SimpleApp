package db;

import org.mongodb.morphia.annotations.Entity;

import java.util.List;

@Entity("games")
public class Game extends BaseEntity  {
    private String name;
    private String author;
    private String release_date;
    private List<String> tags;
    private List<String> imgs;

    public String getName() {
        return this.name;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getRelease_date() {
        return this.release_date;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public List<String> getImgs() { return this.imgs; }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setImgs(List<String> imgs) { this.imgs = imgs; }

    public String toString() {
        StringBuilder tagString = new StringBuilder();
        tagString.append("[ ");
        for (String s : this.tags) {
            tagString.append(s + ", ");
        }
        tagString.append("]");

        return "Game name: " + this.name + "\n" +
                "Author: " + this.author + "\n" +
                "Release Date: " + this.release_date + "\n" +
                "Tags: " + tagString;
    }
}

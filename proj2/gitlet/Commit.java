package gitlet;

// TODO: any imports you need here

import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private String author;
    private String date;
    private String parentID;
    private HashMap<String, String> map;

    public Commit(String message, String author, String date, String parentID) {
        this.message = message;
        this.author = author;
        this.date = date;
        this.parentID = parentID;
        this.map = null;
    }
    /* TODO: fill in the rest of this class. */
    public HashMap<String, String> getMap() {
        return this.map;
    }
    public void initMap() {
        this.map = new HashMap<>();
    }
    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
    public String getAuthor() {
        return author;
    }
}

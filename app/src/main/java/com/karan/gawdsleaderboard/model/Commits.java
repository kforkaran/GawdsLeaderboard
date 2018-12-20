package com.karan.gawdsleaderboard.model;

import java.util.ArrayList;
import java.util.List;

public class Commits {
    private List<String> author = new ArrayList<String>();

    public Commits() {
    }

    public Commits(List<String> author) {
        this.author = author;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }
}

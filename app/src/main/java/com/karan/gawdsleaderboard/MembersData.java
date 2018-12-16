package com.karan.gawdsleaderboard;

public class MembersData {
    private String name;
    private String handle;

    public MembersData() {
    }

    public MembersData(String name, String handle) {
        this.name = name;
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }
}

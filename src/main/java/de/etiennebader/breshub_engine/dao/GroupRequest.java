package de.etiennebader.breshub_engine.dao;

import java.util.ArrayList;
import java.util.List;

public class GroupRequest {

    private String name;
    private List<String> members = new ArrayList<String>();

    public GroupRequest(String name, List<String> members) {
        this.name = name;
        this.members = members;
    }

    public GroupRequest() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}

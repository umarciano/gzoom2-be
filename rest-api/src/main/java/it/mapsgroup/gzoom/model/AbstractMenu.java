package it.mapsgroup.gzoom.model;

import java.util.List;

public class AbstractMenu {
    private String id;
    private String label;
    private List<String> className;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getClassName() {
        return className;
    }

    public void setClassName(List<String> className) {
        this.className = className;
    }
}

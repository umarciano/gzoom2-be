package it.mapsgroup.gzoom.model;

import java.util.ArrayList;
import java.util.List;

public class AbstractMenu {
    private String id;
    private String label;
    private List<String> className;
    List<AbstractMenu> children = new ArrayList<AbstractMenu>();

    public List<AbstractMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AbstractMenu> children) {
        this.children = children;
    }

    public void addChild(AbstractMenu child) {
        children.add(child);
    }
    
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

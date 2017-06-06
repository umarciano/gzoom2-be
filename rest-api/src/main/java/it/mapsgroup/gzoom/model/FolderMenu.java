package it.mapsgroup.gzoom.model;

import java.util.ArrayList;
import java.util.List;

public class FolderMenu {
    private String id;
    private String label;
    private String classes;
    List<FolderMenu> children = new ArrayList<FolderMenu>();
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }
    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }
    /**
     * @return the classes
     */
    public String getClasses() {
        return classes;
    }
    /**
     * @param classes the classes to set
     */
    public void setClasses(String classes) {
        this.classes = classes;
    }

    /**
     * @return the children
     */
    public List<FolderMenu> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<FolderMenu> children) {
        this.children = children;
    }
    
    public void addChild(FolderMenu child) {
        children.add(child);
    }

}

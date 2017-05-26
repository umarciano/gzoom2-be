package it.mapsgroup.gzoom.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * RootMenu data structure.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RootMenu extends AbstractMenu{
    /*List<FolderMenu> children = new ArrayList<FolderMenu>();
    
    public List<FolderMenu> getChildren() {
        return children;
    }
    
    public void setChildren(List<FolderMenu> children) {
        this.children = children;
    }
    
    public void addChild(FolderMenu child) {
        children.add(child);
    }*/

}

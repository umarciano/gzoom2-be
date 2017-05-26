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
public class FolderMenu extends AbstractMenu {

    /*List<AbstractMenu> children = new ArrayList<AbstractMenu>();

    public List<AbstractMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AbstractMenu> children) {
        this.children = children;
    }

    public void addChild(AbstractMenu child) {
        children.add(child);
    }*/
}

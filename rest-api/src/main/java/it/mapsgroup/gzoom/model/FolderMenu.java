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

    List<LeafMenu> children;

    public List<LeafMenu> getChildren() {
        return children;
    }

    public void setChildren(List<LeafMenu> children) {
        this.children = children;
    }

}

package it.mapsgroup.gzoom.querydsl.dto;


/**
 * @author Antonio Calò.
 */
public class ContentAssocMenu extends ContentAssoc {
    private ContentAssoc parent;
    private ContentAssoc child;

    public ContentAssoc getParent() {
        return parent;
    }
    public void setParent(ContentAssoc parent) {
        this.parent = parent;
    }
    public ContentAssoc getChild() {
        return child;
    }
    public void setChild(ContentAssoc child) {
        this.child = child;
    }
}

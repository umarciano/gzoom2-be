package it.mapsgroup.gzoom.querydsl.dto;


/**
 * @author Andrea Fossi.
 */
public class ContentAndAttributes extends Content {
    private ContentAttribute link;
    private ContentAttribute title;
    private ContentAttribute classes;
    private ContentAssoc parent;
    
    /**
     * @return the link
     */
    public ContentAttribute getLink() {
        return link;
    }
    /**
     * @param link the link to set
     */
    public void setLink(ContentAttribute link) {
        this.link = link;
    }
    /**
     * @return the title
     */
    public ContentAttribute getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(ContentAttribute title) {
        this.title = title;
    }
    public ContentAssoc getParent() {
        return parent;
    }
    public void setParent(ContentAssoc parent) {
        this.parent = parent;
    }
    public ContentAttribute getClasses() {
        return classes;
    }
    public void setClasses(ContentAttribute classes) {
        this.classes = classes;
    }
}

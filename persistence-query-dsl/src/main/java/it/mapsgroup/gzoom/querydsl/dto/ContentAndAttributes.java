package it.mapsgroup.gzoom.querydsl.dto;


/**
 * @author Andrea Fossi.
 */
public class ContentAndAttributes extends Content {
    private ContentAttribute link;
    private ContentAttribute title;
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
    /**
     * @return the parent
     */
    public ContentAssoc getParent() {
        return parent;
    }
    /**
     * @param parent the parent to set
     */
    public void setParent(ContentAssoc parent) {
        this.parent = parent;
    }
    
   
}

package it.mapsgroup.gzoom.querydsl.dto;


/**
 * @author Andrea Fossi.
 */
public class ContentAndAttributes extends Content {
    private ContentAttribute link;
    private ContentAttribute title;
    
    public ContentAttribute getLink() {
        return link;
    }
    public void setLink(ContentAttribute link) {
        this.link = link;
    }
    public ContentAttribute getTitle() {
        return title;
    }
    public void setTitle(ContentAttribute title) {
        this.title = title;
    }
}

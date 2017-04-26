package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class CommentFilter extends BaseFilter {

	private Long commentTypeId;
	private Long id;
	private Long constructionSiteLogId;
	
	public void setId(Long id) {
		this.id = id;		
	}

	public Long getId() {
		return id;
	}

	public Long getCommentTypeId() {
        return commentTypeId;
    }

    public void setCommentTypeId(Long commentTypeId) {
        this.commentTypeId = commentTypeId;
    }

    public Long getConstructionSiteLogId() {
        return constructionSiteLogId;
    }

    public void setConstructionSiteLogId(Long constructionSiteLogId) {
        this.constructionSiteLogId = constructionSiteLogId;
    }
}

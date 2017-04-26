package it.memelabs.smartnebula.lmm.persistence.main.dto;

/**
 * @author Andrea Fossi.
 */
public class CommentEx extends Comment {
    private String username;
    private CommentType commentType;
    private ConstructionSiteLog constructionSiteLog;
    private ConstructionSite constructionSite;
    private Contract contract;
    private AntimafiaProcessEx antimafiaProcess;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public void setCommentType(CommentType commentType) {
        this.commentType = commentType;
    }

    public ConstructionSiteLog getConstructionSiteLog() {
        return constructionSiteLog;
    }

    public void setConstructionSiteLog(ConstructionSiteLog constructionSiteLog) {
        this.constructionSiteLog = constructionSiteLog;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public AntimafiaProcessEx getAntimafiaProcess() {
        return antimafiaProcess;
    }

    public void setAntimafiaProcess(AntimafiaProcessEx antimafiaProcess) {
        this.antimafiaProcess = antimafiaProcess;
    }

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(ConstructionSite constructionSite) {
        this.constructionSite = constructionSite;
    }
}

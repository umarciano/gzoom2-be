package it.memelabs.smartnebula.lmm.model;

/**
 * @author Andrea Fossi.
 */
public class ContractCatalogItem extends Identifiable {
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long authorizationId) {
        this.parentId = authorizationId;
    }
}

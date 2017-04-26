package it.memelabs.smartnebula.lmm.persistence.main.dao;

/**
 * @author Andrea Fossi.
 *
 *  TODO add tenant
 */
public class RoleFilter {
    private int page;
    private int size;
    private String filterText;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }


}

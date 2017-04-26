package it.memelabs.smartnebula.lmm.persistence.main.dao;

import org.apache.ibatis.session.RowBounds;

public class BaseFilter {

	private static final String PERC = "%";

	private Integer page;
	private Integer size;
	private Long ownerNodeId;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getOwnerNodeId() {
		return ownerNodeId;
	}

	public void setOwnerNodeId(Long ownerNodeId) {
		this.ownerNodeId = ownerNodeId;
	}

	public RowBounds makeRowBounds() {
		Integer page = getPage();
		Integer size = getSize();
		if (page == null) {
			page = Integer.valueOf(1);
		}
		if (size == null) {
			size = Integer.MAX_VALUE;
		}
		return new RowBounds((page - 1) * size, size);
	}

	public String makeLike(String value) {
		return PERC + value + PERC;
	}
}

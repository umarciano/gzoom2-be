package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class NodeSequenceGeneratorParam {

	private String tableName;
	private String columnName;
	private Long ownerNodeId;

	public NodeSequenceGeneratorParam() {
		this(null, null, null);
	}

	public NodeSequenceGeneratorParam(String tableName, String columnName, Long ownerNodeId) {
		this.tableName = tableName;
		this.columnName = columnName;
		this.ownerNodeId = ownerNodeId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Long getOwnerNodeId() {
		return ownerNodeId;
	}

	public void setOwnerNodeId(Long ownerNodeId) {
		this.ownerNodeId = ownerNodeId;
	}
}

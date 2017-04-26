package it.memelabs.smartnebula.lmm.persistence.main.dao;

public class PersonFilter extends BaseFilter {

	private String filterFirstName;
	private String filterLastName;
	private String filterFiscalCode;
	private Long filterTimeCardNumber;
	private String filterBadgeNumber;
	private String filterText;
	private Long filterCompanyId;
	private Long filterPeStatusId;
	private String filterType;
	private String taxIdNumber;
	private Boolean filterSecondments;
	private Long id;
	private Long filterCompanyStatusId;

	public String getFilterFirstName() {
		return filterFirstName;
	}

	public void setFilterFirstName(String filterFirstName) {
		this.filterFirstName = filterFirstName;
	}

	public String getFilterLastName() {
		return filterLastName;
	}

	public void setFilterLastName(String filterLastName) {
		this.filterLastName = filterLastName;
	}

	public String getFilterFiscalCode() {
		return filterFiscalCode;
	}

	public void setFilterFiscalCode(String filterFiscalCode) {
		this.filterFiscalCode = filterFiscalCode;
	}

	public Long getFilterTimeCardNumber() {
		return filterTimeCardNumber;
	}

	public void setFilterTimeCardNumber(Long timeCardNumber) {
		this.filterTimeCardNumber = timeCardNumber;
	}

	public String getFilterBadgeNumber() {
		return filterBadgeNumber;
	}

	public void setFilterBadgeNumber(String filterBadgeNumber) {
		this.filterBadgeNumber = filterBadgeNumber;
	}

	public String getFilterText() {
		return filterText;
	}

	public void setFilterText(String filterText) {
		this.filterText = filterText;
	}

	public Long getFilterCompanyId() {
		return filterCompanyId;
	}

	public void setFilterCompanyId(Long filterCompanyId) {
		this.filterCompanyId = filterCompanyId;
	}

	public void setFilterPeStatusId(Long filterPeStatusId) {
		this.filterPeStatusId = filterPeStatusId;		
	}

	public Long getFilterPeStatusId() {
		return filterPeStatusId;
	}

	public void setFilterType(String filterType) {
		this.filterType= filterType;		
	}

	public String getFilterType() {
		return filterType;
	}

	public void setTaxIdNumber(String taxIdentificationNumber) {
		this.taxIdNumber = taxIdentificationNumber;
	}

	public String getTaxIdNumber() {
		return taxIdNumber;
	}

	public void setId(Long id) {
		this.id = id;		
	}

	public Long getId() {
		return id;
	}

	public Boolean getFilterSecondments() {
		return filterSecondments;
	}

	public void setFilterSecondments(Boolean filterSecondments) {
		this.filterSecondments = filterSecondments;
	}

	public Long getFilterCompanyStatusId() {
		return filterCompanyStatusId;
	}

	public void setFilterCompanyStatusId(Long filterCompanyStatusId) {
		this.filterCompanyStatusId = filterCompanyStatusId;
	}
}

package it.mapsgroup.gzoom.model;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.mapsgroup.gzoom.querydsl.dto.ReportParam;
import it.mapsgroup.gzoom.querydsl.dto.ReportType;
import it.mapsgroup.gzoom.querydsl.dto.WorkEffortTypeExt;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Report extends Identifiable {

	private String reportName;
	private String reportContentId;
	private String workEffortTypeId;
	private String workEffortAnalysisId;
	private String parentTypeId;
	private String etch;
	private String etchLang;
	private String description;
	private String descriptionLang;
	private String descriptionType;
	private String descriptionTypeLang;	
	private String serviceName;
	private Boolean useFilter;
	private BigInteger sequenceNum;
	private String outputFormat;
	private String contentName;
	
	private List<ReportParam> params;
    private List<ReportType> outputFormats;
    private List<WorkEffortTypeExt> workEffortTypes;
    private Map<String, Object> paramsValue;
    

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the reportContentId
	 */
	public String getReportContentId() {
		return reportContentId;
	}

	/**
	 * @param reportContentId the reportContentId to set
	 */
	public void setReportContentId(String reportContentId) {
		this.reportContentId = reportContentId;
	}

	/**
	 * @return the workEffortTypeId
	 */
	public String getWorkEffortTypeId() {
		return workEffortTypeId;
	}

	/**
	 * @param workEffortTypeId the workEffortTypeId to set
	 */
	public void setWorkEffortTypeId(String workEffortTypeId) {
		this.workEffortTypeId = workEffortTypeId;
	}

	/**
	 * @return the workEffortAnalysisId
	 */
	public String getWorkEffortAnalysisId() {
		return workEffortAnalysisId;
	}

	/**
	 * @param workEffortAnalysisId the workEffortAnalysisId to set
	 */
	public void setWorkEffortAnalysisId(String workEffortAnalysisId) {
		this.workEffortAnalysisId = workEffortAnalysisId;
	}

	/**
	 * @return the parentTypeId
	 */
	public String getParentTypeId() {
		return parentTypeId;
	}

	/**
	 * @param parentTypeId the parentTypeId to set
	 */
	public void setParentTypeId(String parentTypeId) {
		this.parentTypeId = parentTypeId;
	}

	/**
	 * @return the etch
	 */
	public String getEtch() {
		return etch;
	}

	/**
	 * @param etch the etch to set
	 */
	public void setEtch(String etch) {
		this.etch = etch;
	}

	/**
	 * @return the etchLang
	 */
	public String getEtchLang() {
		return etchLang;
	}

	/**
	 * @param etchLang the etchLang to set
	 */
	public void setEtchLang(String etchLang) {
		this.etchLang = etchLang;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the descriptionLang
	 */
	public String getDescriptionLang() {
		return descriptionLang;
	}

	/**
	 * @param descriptionLang the descriptionLang to set
	 */
	public void setDescriptionLang(String descriptionLang) {
		this.descriptionLang = descriptionLang;
	}

	/**
	 * @return the descriptionType
	 */
	public String getDescriptionType() {
		return descriptionType;
	}

	/**
	 * @param descriptionType the descriptionType to set
	 */
	public void setDescriptionType(String descriptionType) {
		this.descriptionType = descriptionType;
	}

	/**
	 * @return the descriptionLangType
	 */
	public String getDescriptionTypeLang() {
		return descriptionTypeLang;
	}

	/**
	 * @param descriptionLangType the descriptionLangType to set
	 */
	public void setDescriptionTypeLang(String descriptionTypeLang) {
		this.descriptionTypeLang = descriptionTypeLang;
	}

	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * @return the useFilter
	 */
	public Boolean getUseFilter() {
		return useFilter;
	}

	/**
	 * @param useFilter the useFilter to set
	 */
	public void setUseFilter(Boolean useFilter) {
		this.useFilter = useFilter;
	}

	/**
	 * @return the sequenceNum
	 */
	public BigInteger getSequenceNum() {
		return sequenceNum;
	}

	/**
	 * @param sequenceNum the sequenceNum to set
	 */
	public void setSequenceNum(BigInteger sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	/**
	 * @return the params
	 */
	public List<ReportParam> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(List<ReportParam> params) {
		this.params = params;
	}

	/**
	 * @return the outputFormats
	 */
	public List<ReportType> getOutputFormats() {
		return outputFormats;
	}

	/**
	 * @param outputFormats the outputFormats to set
	 */
	public void setOutputFormats(List<ReportType> outputFormats) {
		this.outputFormats = outputFormats;
	}

	

	/**
	 * @return the workEffortTypes
	 */
	public List<WorkEffortTypeExt> getWorkEffortTypes() {
		return workEffortTypes;
	}

	/**
	 * @param workEffortTypes the workEffortTypes to set
	 */
	public void setWorkEffortTypes(List<WorkEffortTypeExt> workEffortTypes) {
		this.workEffortTypes = workEffortTypes;
	}

	/**
	 * @return the outputFormat
	 */
	public String getOutputFormat() {
		return outputFormat;
	}

	/**
	 * @param outputFormat the outputFormat to set
	 */
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}

	/**
	 * @return the contentName
	 */
	public String getContentName() {
		return contentName;
	}

	/**
	 * @param contentName the contentName to set
	 */
	public void setContentName(String contentName) {
		this.contentName = contentName;
	}

	/**
	 * @return the paramsValue
	 */
	public Map<String, Object> getParamsValue() {
		return paramsValue;
	}

	/**
	 * @param paramsValue the paramsValue to set
	 */
	public void setParamsValue(Map<String, Object> paramsValue) {
		this.paramsValue = paramsValue;
	}
	
}

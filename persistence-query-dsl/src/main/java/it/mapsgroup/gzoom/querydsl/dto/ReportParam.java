package it.mapsgroup.gzoom.querydsl.dto;


public class ReportParam {
    private String paramName;
    private Object paramDefault;
    private String paramType;
    private Boolean mandatory;
   
	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramDefault
	 */
	public Object getParamDefault() {
		return paramDefault;
	}

	/**
	 * @param paramDefault the paramDefault to set
	 */
	public void setParamDefault(Object paramDefault) {
		this.paramDefault = paramDefault;
	}

		

	/**
	 * 
     * @return the mandatory
     */
    public Boolean getMandatory() {
        return mandatory;
    }

    /**
	 * @return the paramType
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * LIST
	 * DATA
	 * INPUT
	 * BOOLEAN
	 * 
	 * @param paramType the paramType to set
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	/**
     * @param mandatory
     *            the mandatory to set
     */
    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }
}

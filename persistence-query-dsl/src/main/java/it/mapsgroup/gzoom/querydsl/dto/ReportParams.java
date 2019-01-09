package it.mapsgroup.gzoom.querydsl.dto;

import java.util.ArrayList;
import java.util.List;

public class ReportParams {

	private List<ReportParam> params = new ArrayList<ReportParam>();

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

}

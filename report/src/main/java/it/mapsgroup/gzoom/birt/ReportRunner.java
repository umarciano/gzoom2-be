package it.mapsgroup.gzoom.birt;

import it.mapsgroup.gzoom.birt.Report;

import java.io.ByteArrayOutputStream;

/**
 * @author Andrea Fossi.
 */
public interface ReportRunner {

    ByteArrayOutputStream runReport(Report report);
}
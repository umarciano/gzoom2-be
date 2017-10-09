package it.mapsgroup.gzoom;

import java.io.ByteArrayOutputStream;

/**
 * @author Andrea Fossi.
 */
public interface ReportRunner {

    ByteArrayOutputStream runReport(Report report);
}
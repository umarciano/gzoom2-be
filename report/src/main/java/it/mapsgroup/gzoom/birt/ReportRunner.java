package it.mapsgroup.gzoom.birt;

import it.mapsgroup.gzoom.birt.Report;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author Andrea Fossi.
 */
public interface ReportRunner {

    ReportHandler runReport(Report report);
}
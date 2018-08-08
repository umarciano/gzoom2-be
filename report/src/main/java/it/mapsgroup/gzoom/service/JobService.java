package it.mapsgroup.gzoom.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Andrea Fossi.
 */
public class JobService {

    private final BlockingQueue<ReportJob> queue = new LinkedBlockingQueue<>();

}

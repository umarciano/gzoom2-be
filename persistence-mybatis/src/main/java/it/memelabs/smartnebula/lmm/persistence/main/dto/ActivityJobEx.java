package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrea Fossi.
 */
public class ActivityJobEx extends ActivityJob {
    private List<ActivityJobAttribute> attributes;

    public ActivityJobEx() {
        attributes = new ArrayList<>();
    }

    public List<ActivityJobAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ActivityJobAttribute> attributes) {
        this.attributes = attributes;
    }
}

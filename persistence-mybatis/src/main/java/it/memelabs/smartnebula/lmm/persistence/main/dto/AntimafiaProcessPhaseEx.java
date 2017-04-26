package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class AntimafiaProcessPhaseEx extends AntimafiaProcessPhase {
	
	private AntimafiaProcessPhaseType type;
	private EntityState state;

	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}

	public AntimafiaProcessPhaseType getType() {
		return type;
	}

	public void setType(AntimafiaProcessPhaseType type) {
		this.type = type;
	}
}
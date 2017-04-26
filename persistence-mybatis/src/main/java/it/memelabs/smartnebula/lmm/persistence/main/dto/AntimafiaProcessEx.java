package it.memelabs.smartnebula.lmm.persistence.main.dto;

public class AntimafiaProcessEx extends AntimafiaProcess {

	private Company company;
	private Identifiable prefecture;
	private EntityState state;
	private Lot lot;
	private EntityState causal;

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Identifiable getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(Identifiable prefecture) {
		this.prefecture = prefecture;
	}

	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public EntityState getCausal() {
		return causal;
	}

	public void setCausal(EntityState causal) {
		this.causal = causal;
	}
}

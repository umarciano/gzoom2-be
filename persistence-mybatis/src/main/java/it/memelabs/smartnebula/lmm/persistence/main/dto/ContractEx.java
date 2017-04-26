package it.memelabs.smartnebula.lmm.persistence.main.dto;

import java.util.ArrayList;
import java.util.List;

public class ContractEx extends Contract {
	private Company company;
	private Company performingCompany;
	private Contract referenceContract;
	private AntimafiaProcessEx antimafiaProcess;
	private ContractCatalog contractType;
	private Lot lot;
	private List<ConstructionSite> constructionSites;
	private Contract contractExtension;
	private EntityState state;
	private ContractCatalog contractAuthorization;
	private ContractCatalog contractCommitmentMode;


	public ContractEx() {
		this.constructionSites = new ArrayList<>();
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Added for mgo tab
	 * @return
     */
	public Company getPerformingCompany() {
		return performingCompany;
	}

	public void setPerformingCompany(Company performingCompany) {
		this.performingCompany = performingCompany;
	}

	public Contract getReferenceContract() {
		return referenceContract;
	}

	public void setReferenceContract(Contract referenceContract) {
		this.referenceContract = referenceContract;
	}

	public ContractCatalog getContractType() {
		return contractType;
	}

	public void setContractType(ContractCatalog contractType) {
		this.contractType = contractType;
	}

	public Lot getLot() {
		return lot;
	}

	public void setLot(Lot lot) {
		this.lot = lot;
	}

	public List<ConstructionSite> getConstructionSites() {
		return constructionSites;
	}

	public void setConstructionSites(List<ConstructionSite> constructionSites) {
		this.constructionSites = constructionSites;
	}

	public Contract getContractExtension() {
		return contractExtension;
	}

	public void setContractExtension(Contract contractExtension) {
		this.contractExtension = contractExtension;
	}

	public EntityState getState() {
		return state;
	}

	public void setState(EntityState state) {
		this.state = state;
	}

	public AntimafiaProcessEx getAntimafiaProcess() {
		return antimafiaProcess;
	}

	public void setAntimafiaProcess(AntimafiaProcessEx antimafiaProcess) {
		this.antimafiaProcess = antimafiaProcess;
	}

	public ContractCatalog getContractAuthorization() {
		return contractAuthorization;
	}

	public void setContractAuthorization(ContractCatalog contractAuthorization) {
		this.contractAuthorization = contractAuthorization;
	}

	public ContractCatalog getContractCommitmentMode() {
		return contractCommitmentMode;
	}

	public void setContractCommitmentMode(ContractCatalog contractCommitmentMode) {
		this.contractCommitmentMode = contractCommitmentMode;
	}
}

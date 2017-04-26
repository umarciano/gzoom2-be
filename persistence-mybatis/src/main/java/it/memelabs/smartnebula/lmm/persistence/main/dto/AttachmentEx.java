package it.memelabs.smartnebula.lmm.persistence.main.dto;

/**
 * @author Andrea Fossi.
 */
public class AttachmentEx extends Attachment {
    private UserLogin createdByUserLogin;
    private UserLogin modifiedByUserLogin;
    private UserLogin uploadedByUserLogin;

    private String typeDescription;
    
    private Long phaseTypeId;
    private String phaseTypeDescription;
    
    private Company company;
    private PersonEx person;
    private EquipmentEx equipment;
    private AntimafiaProcessEx antimafiaProcess;
    private ContractEx contract;
    private AccidentEx accident;
    
    public UserLogin getCreatedByUserLogin() {
        return createdByUserLogin;
    }

    public void setCreatedByUserLogin(UserLogin createdByUserLogin) {
        this.createdByUserLogin = createdByUserLogin;
    }

    public UserLogin getModifiedByUserLogin() {
        return modifiedByUserLogin;
    }

    public void setModifiedByUserLogin(UserLogin modifiedByUserLogin) {
        this.modifiedByUserLogin = modifiedByUserLogin;
    }

    public UserLogin getUploadedByUserLogin() {
        return uploadedByUserLogin;
    }

    public void setUploadedByUserLogin(UserLogin uploadedByUserLogin) {
        this.uploadedByUserLogin = uploadedByUserLogin;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public EquipmentEx getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentEx equipment) {
        this.equipment = equipment;
    }

    public AntimafiaProcessEx getAntimafiaProcess() {
        return antimafiaProcess;
    }

    public void setAntimafiaProcess(AntimafiaProcessEx antimafiaProcess) {
        this.antimafiaProcess = antimafiaProcess;
    }

    public ContractEx getContract() {
        return contract;
    }

    public void setContract(ContractEx contract) {
        this.contract = contract;
    }

    public PersonEx getPerson() {
        return person;
    }

    public void setPerson(PersonEx person) {
        this.person = person;
    }

    public Long getPhaseTypeId() {
        return phaseTypeId;
    }

    public void setPhaseTypeId(Long phaseTypeId) {
        this.phaseTypeId = phaseTypeId;
    }

    public String getPhaseTypeDescription() {
        return phaseTypeDescription;
    }

    public void setPhaseTypeDescription(String phaseTypeDescription) {
        this.phaseTypeDescription = phaseTypeDescription;
    }

    public AccidentEx getAccident() {
        return accident;
    }

    public void setAccident(AccidentEx accident) {
        this.accident = accident;
    }
}

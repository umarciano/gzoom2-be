package it.mapsgroup.gzoom.rest.dto;

/**
 * DTO per le informazioni complete dell'utente
 * Contiene tutti i dati necessari per il modal "Informazioni Utente"
 */
public class UserInfoDto {
    
    private String userLoginId;
    private String firstName;
    private String lastName;
    private String matricola;          // parent_role_code dalla vista party_role_view
    private String fiscalCode;         // fiscal_code dalla tabella party
    private String positionType;       // description dalla tabella empl_position_type
    private String positionTypeId;     // empl_position_type_id per riferimento
    private String userPartyId;        // party_id dell'utente (non dell'organizzazione)
    
    // Costruttori
    public UserInfoDto() {}
    
    public UserInfoDto(String userLoginId, String firstName, String lastName) {
        this.userLoginId = userLoginId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Getters e Setters
    public String getUserLoginId() {
        return userLoginId;
    }
    
    public void setUserLoginId(String userLoginId) {
        this.userLoginId = userLoginId;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getMatricola() {
        return matricola;
    }
    
    public void setMatricola(String matricola) {
        this.matricola = matricola;
    }
    
    public String getFiscalCode() {
        return fiscalCode;
    }
    
    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }
    
    public String getPositionType() {
        return positionType;
    }
    
    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }
    
    public String getPositionTypeId() {
        return positionTypeId;
    }
    
    public void setPositionTypeId(String positionTypeId) {
        this.positionTypeId = positionTypeId;
    }
    
    public String getUserPartyId() {
        return userPartyId;
    }
    
    public void setUserPartyId(String userPartyId) {
        this.userPartyId = userPartyId;
    }
    
    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userLoginId='" + userLoginId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", matricola='" + matricola + '\'' +
                ", fiscalCode='" + fiscalCode + '\'' +
                ", positionType='" + positionType + '\'' +
                ", positionTypeId='" + positionTypeId + '\'' +
                ", userPartyId='" + userPartyId + '\'' +
                '}';
    }
}
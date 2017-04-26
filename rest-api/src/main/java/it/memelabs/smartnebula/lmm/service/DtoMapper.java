package it.memelabs.smartnebula.lmm.service;

import it.memelabs.smartnebula.commons.DateUtil;
import it.memelabs.smartnebula.lmm.model.Accident;
import it.memelabs.smartnebula.lmm.model.AntimafiaProcess;
import it.memelabs.smartnebula.lmm.model.AntimafiaProcessPhase;
import it.memelabs.smartnebula.lmm.model.AntimafiaProcessPhaseType;
import it.memelabs.smartnebula.lmm.model.Attachment;
import it.memelabs.smartnebula.lmm.model.Comment;
import it.memelabs.smartnebula.lmm.model.CommentType;
import it.memelabs.smartnebula.lmm.model.Company;
import it.memelabs.smartnebula.lmm.model.CompanyComposition;
import it.memelabs.smartnebula.lmm.model.ConstructionSite;
import it.memelabs.smartnebula.lmm.model.ConstructionSiteLog;
import it.memelabs.smartnebula.lmm.model.Contract;
import it.memelabs.smartnebula.lmm.model.*;
import it.memelabs.smartnebula.lmm.model.EntityState;
import it.memelabs.smartnebula.lmm.model.Equipment;
import it.memelabs.smartnebula.lmm.model.EquipmentEmployment;
import it.memelabs.smartnebula.lmm.model.Geo;
import it.memelabs.smartnebula.lmm.model.Identifiable;
import it.memelabs.smartnebula.lmm.model.JobOrder;
import it.memelabs.smartnebula.lmm.model.Lot;
import it.memelabs.smartnebula.lmm.model.NotificationEvent;
import it.memelabs.smartnebula.lmm.model.NotificationGroup;
import it.memelabs.smartnebula.lmm.model.Person;
import it.memelabs.smartnebula.lmm.model.PersonEmployment;
import it.memelabs.smartnebula.lmm.model.PostalAddress;
import it.memelabs.smartnebula.lmm.model.Wbs;
import it.memelabs.smartnebula.lmm.persistence.main.dto.*;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.AttachmentEntity;
import it.memelabs.smartnebula.lmm.persistence.main.enumeration.NotificationEventType;
import it.memelabs.smartnebula.lmm.util.TokenUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author Fabio G. Strozzi
 */
@Component
public class DtoMapper {

    private static final String SEP = " ";

    /**
     * Simplified user mapper (copy basic field only)
     * for full copy {@link SecurityDtoMapper#copy(UserLogin, User)}
     *
     * @param userLogin
     * @param user
     * @return
     */
    public User copy(UserLoginPersistent userLogin, User user) {
        user.setId(userLogin.getId());
        user.setUsername(userLogin.getUsername());
        user.setFirstName(userLogin.getName());
        user.setLastName(userLogin.getSurname());
        user.setEmail(userLogin.getEmail());
        return user;
    }

    public Identifiable copy(it.memelabs.smartnebula.lmm.persistence.main.dto.AbstractIdentifiable from, Identifiable to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public Identifiable copy(it.memelabs.smartnebula.lmm.persistence.main.dto.EntityState from, Identifiable to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public EntityState copy(it.memelabs.smartnebula.lmm.persistence.main.dto.EntityState from, EntityState to) {
        copy(from, (Identifiable) to);
        to.setEntity(from.getEntity());
        to.setName(from.getName());
        to.setOrdinal(from.getOrdinal());
        to.setParentId(from.getParentId());
        to.setValidUntil(from.getValidUntil());
        to.setTag((from.getTag() != null) ? from.getTag().name() : null);
        return to;
    }

    public Identifiable copy(PersonJob from, Identifiable to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public Identifiable copy(PersonRole from, Identifiable to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public Identifiable copy(Prefecture from, Identifiable to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public Company copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Company from, Company to, boolean includeAddress) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setBusinessName(from.getBusinessName());
        to.setTaxIdentificationNumber(from.getTaxIdentificationNumber());
        to.setVatNumber(from.getVatNumber());
        to.setCompanyPurpose(from.getCompanyPurpose());
        to.setStockCapital(from.getStockCapital());
        to.setLegalDelegate(from.getLegalDelegate());
        to.setPhone(from.getPhoneNumber());
        to.setFax(from.getFaxNumber());
        to.setEmail(from.getEmail());
        to.setRdlControl(from.getRdlControl());
        to.setCompanyType(from.getCompanyType());
        to.setCciaaDate(from.getCciaaDate());
        to.setConsortium(from.getConsortium());
        to.setWhiteListMember(from.getWhiteListMember());
        to.setWhiteListMembershipStartDate(from.getWhiteListMembershipStartDate());
        to.setWhiteListMembershipEndDate(from.getWhiteListMembershipEndDate());
        return to;
    }

    public Company copy(CompanyEx from, Company to, boolean includeAddress) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Company) from, to, includeAddress);
        to.setClassification(new Identifiable(from.getClassificationId(), from.getClassificationDesc()));
        to.setCategory(new Identifiable(from.getCategoryId(), from.getCategoryDesc()));
        EntityState state = new EntityState(from.getStateId(), from.getStateDesc());
        state.setTag(from.getStateTag().name());
        to.setState(state);
        if (includeAddress && from.getAddress() != null) {
            to.setAddress(copy(from.getAddress(), new PostalAddress()));
        }
        if (from.getConsortiumMembership() != null)
            to.setConsortiumMembership(copy(from.getConsortiumMembership(), new Company(), false));
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.Company copy(Company from, it.memelabs.smartnebula.lmm.persistence.main.dto.Company to, boolean includeAddress) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setBusinessName(from.getBusinessName());
        to.setTaxIdentificationNumber(from.getTaxIdentificationNumber());
        to.setVatNumber(from.getVatNumber());
        to.setCompanyPurpose(from.getCompanyPurpose());
        to.setStockCapital(from.getStockCapital());
        to.setLegalDelegate(from.getLegalDelegate());
        to.setPhoneNumber(from.getPhone());
        to.setFaxNumber(from.getFax());
        to.setEmail(from.getEmail());
        to.setRdlControl(from.getRdlControl());
        to.setCompanyType(from.getCompanyType());
        to.setCciaaDate(from.getCciaaDate());
        to.setConsortium(from.getConsortium());
        to.setWhiteListMember(from.getWhiteListMember());
        to.setWhiteListMembershipStartDate(from.getWhiteListMembershipStartDate());
        to.setWhiteListMembershipEndDate(from.getWhiteListMembershipEndDate());
        return to;
    }

    public CompanyEx copy(Company from, CompanyEx to, boolean includeAddress) {
        copy(from, (it.memelabs.smartnebula.lmm.persistence.main.dto.Company) to, includeAddress);
        if (from.getClassification() != null) {
            to.setClassificationDesc(from.getClassification().getDescription());
            to.setClassificationId(from.getClassification().getId());
        }
        if (from.getCategory() != null) {
            to.setCategoryDesc(from.getCategory().getDescription());
            to.setCategoryId(from.getCategory().getId());
        }
        if (from.getState() != null) {
            to.setStateId(from.getState().getId());
            to.setStateDesc(from.getState().getDescription());
        }
        if (includeAddress && from.getAddress() != null) {
            to.setAddress(copy(from.getAddress(), new PostalAddressEx()));
        }
        if (from.getConsortiumMembership() != null) {
            to.setConsortiumMembership(copy(from.getConsortiumMembership(), new it.memelabs.smartnebula.lmm.persistence.main.dto.Company(), false));
        }
        return to;
    }

    public CompanyComposition copy(it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyComposition from, CompanyComposition to) {
        to.setParentCompanyId(from.getParentCompanyId());
        to.setCompanyId(from.getCompanyId());
        to.setPercentage(from.getPercentage());
        to.setEmissary(from.getEmissary());
        return to;
    }

    public CompanyComposition copy(CompanyCompositionEx from, CompanyComposition to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.CompanyComposition) from, to);
        Company childCompany = new Company();
        childCompany.setId(to.getCompanyId());
        childCompany.setBusinessName(from.getBusinessName());
        childCompany.setTaxIdentificationNumber(from.getTaxIdentificationNumber());
        childCompany.setVatNumber(from.getVatNumber());
        childCompany.setCompanyCategory(from.getCompanyCategory());
        childCompany.setCompanyClassification(from.getCompanyClassification());
        childCompany.setCompanyState(from.getCompanyState());
        to.setCompany(childCompany);
        return to;
    }

    public Person copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Person from, Person to) {
        to.setId(from.getId());
        to.setTaxIdentificationNumber(from.getTaxIdentificationNumber());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setBirthDate(from.getBirthDate());
        to.setGender(from.getGender());
        to.setResidencyPermit(from.getResidencyPermit());
        to.setNote(from.getNote());
        return to;
    }

    public Person copy(PersonEx from, Person to, boolean includeAddress) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Person) from, to);
        if (includeAddress && from.getAddress() != null) {
            to.setAddress(copy((PostalAddressEx) from.getAddress(), new PostalAddress()));
        }
        if (includeAddress && from.getBirthLocation() != null) {
            to.setBirthLocation(copy((PostalAddressEx) from.getBirthLocation(), new PostalAddress()));
        }
        to.setPeCardNumber(from.getPeCardNumber());
        to.setPeBadge(from.getPeBadgeNumber());
        to.setPeStateDescription(from.getPeStateDescription());
        to.setPeStateTag(from.getPeStateTag());
        to.setCompDescription(from.getCompDescription());
        to.setSecondmentCompanyDescription(from.getSecondmentCompanyDescription());
        to.setAssumptionDate(from.getAssumptionDate());
        to.setDismissalDate(from.getDismissalDate());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());

        if (from.getRole() != null) {
            to.setRole(from.getRole().getDescription());
        }
        if (from.getJob() != null) {
            to.setJob(from.getJob().getDescription());
        }
        to.setJobDescription(from.getJobDescription());
        to.setLevel(from.getLevel());

        to.setCompanyStateDescription(from.getCompanyStateDescription());
        to.setCompanyStateTag(from.getCompanyStateTag());
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.Person copy(Person from, it.memelabs.smartnebula.lmm.persistence.main.dto.Person to) {
        to.setId(from.getId());
        to.setTaxIdentificationNumber(from.getTaxIdentificationNumber());
        to.setFirstName(from.getFirstName());
        to.setLastName(from.getLastName());
        to.setBirthDate(from.getBirthDate());
        to.setGender(from.getGender());
        to.setResidencyPermit(from.getResidencyPermit());
        to.setNote(from.getNote());
        return to;
    }

    public PersonEx copy(Person from, PersonEx to, boolean includeAddress) {
        copy(from, (it.memelabs.smartnebula.lmm.persistence.main.dto.Person) to);
        if (includeAddress && from.getAddress() != null) {
            to.setAddressId(from.getAddress().getId());
        }
        if (includeAddress && from.getBirthLocation() != null) {
            to.setBirthLocationId(from.getBirthLocation().getId());
        }
        return to;
    }

    public PersonEmployment copy(it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmployment from, PersonEmployment to) {
        to.setId(from.getId());
        to.setCardNumber(from.getCardNumber());
        to.setBadge(from.getBadgeNumber());
        to.setEmploymentStartDate(from.getStartDate());
        to.setEmploymentEndDate(from.getEndDate());
        return to;
    }

    public PersonEmployment copy(PersonEmploymentEx from, PersonEmployment to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.PersonEmployment) from, to);
        to.setState(copy(from.getState(), new EntityState()));
        to.setCompany(copy(from.getCompany(), new Company(), false));
        to.setPerson(copy(from.getPerson(), new Person()));
        if (from.getRole() != null) {
            to.setRole(copy(from.getRole(), new Identifiable()));
        }
        if (from.getJob() != null) {
            to.setJob(copy(from.getJob(), new Identifiable()));
        }
        to.setLevel(from.getLevel());
        to.setJobDescription(from.getJobDescription());
        to.setAssumptionDate(from.getAssumptionDate());
        to.setDismissalDate(from.getDismissalDate());
        if (from.getDestinationCompany() != null) {
            to.setDestinationCompany(copy(from.getDestinationCompany(), new Company(), false));
        }
        return to;
    }

    public Equipment copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Equipment from, Equipment to) {
        to.setId(from.getId());
        to.setRegistrationNumber(from.getRegistrationNumber());
        to.setBrand(from.getBrand());
        to.setModel(from.getModel());
        to.setNote(from.getNote());
        return to;
    }

    public Equipment copy(EquipmentEx from, Equipment to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Equipment) from, to);
        to.setEquipmentType(new Identifiable(from.getEquipmentTypeId(), from.getEquipmentTypeDesc()));
        to.setEquipmentCategory(new Identifiable(from.getEquipmentCategoryId(), from.getEquipmentCategoryDesc()));
        to.setPeCardNumber(from.getPeCardNumber());
        to.setPeBadge(from.getPeBadgeNumber());
        to.setCompDescription(from.getCompDescription());
        to.setEeStateDescription(from.getEeStateDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setEeStateTag(from.getEeStateTag());
        to.setCompanyStateDescription(from.getCompanyStateDescription());
        to.setCompanyStateTag(from.getCompanyStateTag());
        return to;
    }

    public EquipmentEmployment copy(it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmployment from, EquipmentEmployment to) {
        to.setId(from.getId());
        to.setCardNumber(from.getCardNumber());
        to.setBadge(from.getBadgeNumber());
        to.setEmploymentStartDate(from.getStartDate());
        to.setEmploymentEndDate(from.getEndDate());
        return to;
    }

    public EquipmentEmployment copy(EquipmentEmploymentEx from, EquipmentEmployment to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.EquipmentEmployment) from, to);
        to.setState(copy(from.getState(), new EntityState()));
        to.setCompany(copy(from.getCompany(), new Company(), false));
        to.setEquipment(copy(from.getEquipment(), new Equipment()));
        return to;
    }

    public JobOrder copy(JobOrderEx from, JobOrder to) {
        if (from.getRsp() != null)
            to.setRsp(copy(from.getRsp(), new Person()));
        return copy((it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrder) from, to);
    }

    public JobOrder copy(it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrder from, JobOrder to) {
        to.setId(from.getId());
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setNote(from.getNote());
        return to;
    }

    public JobOrderEx copy(JobOrder from, JobOrderEx to) {
        copy(from, (it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrder) to);
        if (from.getRsp() != null)
            to.setRsp(copy(from.getRsp(), new PersonEx(), true));
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrder copy(JobOrder from, it.memelabs.smartnebula.lmm.persistence.main.dto.JobOrder to) {
        to.setId(from.getId());
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setNote(from.getNote());
        return to;
    }

    public Lot copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Lot from, Lot to) {
        to.setId(from.getId());
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setNote(from.getNote());
        return to;
    }

    public Lot copy(LotEx from, Lot to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Lot) from, to);
        to.setJobOrder(from.getJobOrder() != null ? copy(from.getJobOrder(), new JobOrder()) : null);
        to.setCompanyAssigned(from.getAssignedCompany() != null ? copy(from.getAssignedCompany(), new Company(), true) : null);
        to.setWorksManager(from.getWorksManager() != null ? copy(from.getWorksManager(), new Person()) : null);
        to.setDtl(from.getDtl() != null ? copy(from.getDtl(), new Person()) : null);
        if (from.getLocations() != null && from.getLocations().size() > 0) {
            ArrayList<PostalAddress> modelLocations = new ArrayList<>();
            for (PostalAddressEx pa : from.getLocations()) {
                modelLocations.add(copy(pa, new PostalAddress()));
            }
            to.setLocations(modelLocations);
        }
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.Lot copy(Lot from, it.memelabs.smartnebula.lmm.persistence.main.dto.Lot to) {
        to.setId(from.getId());
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setNote(from.getNote());
        return to;
    }

    public LotEx copy(Lot from, LotEx to) {
        copy(from, (it.memelabs.smartnebula.lmm.persistence.main.dto.Lot) to);
        to.setJobOrderId(from.getJobOrder() != null ? from.getJobOrder().getId() : null);
        to.setAssignedCompanyId(from.getCompanyAssigned() != null ? from.getCompanyAssigned().getId() : null);
        to.setWorksManagerId(from.getWorksManager() != null ? from.getWorksManager().getId() : null);
        to.setDtlId(from.getDtl() != null ? from.getDtl().getId() : null);
        if (from.getLocations() != null && from.getLocations().size() > 0) {
            ArrayList<PostalAddressEx> dtoLocations = new ArrayList<>();
            for (PostalAddress pa : from.getLocations()) {
                dtoLocations.add(copy(pa, new PostalAddressEx()));
            }
            to.setLocations(dtoLocations);
        }
        return to;
    }

    public Attachment copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Attachment from, Attachment to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        // to.setType(toString(from.getType()));
        to.setFilename(from.getFilename());
        to.setSize(from.getSize());
        to.setCreated(from.getCreatedStamp());
        // to.setCreator(usernameOrNull(from.getCreatedByUserLogin()));
        to.setModified(from.getModifiedStamp());
        // to.setModifier(usernameOrNull(from.getModifiedByUserLogin()));
        to.setUploaded(from.getUploadedStamp());
        // to.setUploader(usernameOrNull(from.getUploadedByUserLogin()));
        to.setValidSince(from.getValidSince());
        to.setValidUntil(from.getValidUntil());
        return to;
    }

    public Attachment copy(AttachmentEx from, Attachment to) {
        to.setType(new Identifiable(from.getTypeId(), from.getTypeDescription()));
        return copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Attachment) from, to);
    }

    public Attachment copy(AttachmentEx from, AttachmentEntity entity, Attachment to) {
        to = copy(from, to);

        switch (entity) {
            case ANTIMAFIA_PROCESS:
                String description = from.getAntimafiaProcess().getCompany().getBusinessName() + SEP + DateUtil.toString(from.getAntimafiaProcess().getCreatedStamp(), "yyyy-MM-dd") + SEP + from.getAntimafiaProcess().getPrefecture().getDescription();
                if (from.getAntimafiaProcess().getLot() != null) {
                    description = from.getAntimafiaProcess().getCompany().getBusinessName() + SEP + DateUtil.toString(from.getAntimafiaProcess().getCreatedStamp(), "yyyy-MM-dd") + SEP + from.getAntimafiaProcess().getLot().getCode() + SEP + from.getAntimafiaProcess().getPrefecture().getDescription();
                }
                to.setEntityDescription(description);
                break;
            case ATI:
            case COMPANY:
            case ATI_SECURITY:
            case COMPANY_SECURITY:
                to.setEntityDescription(from.getCompany().getBusinessName());
                break;
            case CONTRACT:
            case CONTRACT_TRACEABILITY:
            case CONTRACT_REGULARITY:
                to.setEntityDescription(from.getContract().getPerformingCompany().getBusinessName() + SEP + from.getContract().getContractNumber());
                break;
            case EQUIPMENT:
                String equipDescription = from.getEquipment().getRegistrationNumber();
                if (from.getEquipment().getCompDescription() != null) {
                    equipDescription += SEP + from.getEquipment().getCompDescription();
                }
                to.setEntityDescription(equipDescription);
                break;
            case PERSON:
            case PERSON_EMPLOYMENT:
                String persDescription = from.getPerson().getLastName() + SEP + from.getPerson().getFirstName();
                if (from.getPerson().getCompDescription() != null) {
                    persDescription += SEP + from.getPerson().getCompDescription();
                }
                to.setEntityDescription(persDescription);
                break;
            default:
                break;
        }

        return to;
    }

    public Geo copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Geo from, Geo to) {
        to.setId(from.getGeoId());
        to.setDescription(from.getGeoName());
        to.setCode(from.getGeoCode());
        to.setAbbreviation(from.getAbbreviation());
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.Geo copy(Geo from, it.memelabs.smartnebula.lmm.persistence.main.dto.Geo to) {
        to.setGeoId(from.getId());
        to.setGeoName(from.getDescription());
        to.setGeoCode(from.getCode());
        return to;
    }

    public PostalAddress copy(it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress from, PostalAddress to) {
        to.setId(from.getId());
        to.setToName(from.getToName());
        to.setAttnName(from.getAttnName());
        to.setAddress(from.getAddress1());
        to.setAddress2(from.getAddress2());
        to.setDirections(from.getDirections());
        to.setCity(from.getCity());
        to.setForeignRegion(from.getForeignRegion());
        to.setForeignProvince(from.getForeignProvince());
        to.setStreetNumber(from.getStreetNumber());
        to.setVillage(from.getVillage());
        return to;
    }

    public PostalAddress copy(PostalAddressEx from, PostalAddress to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress) from, to);
        if (from.getCountryGeo() != null) {
            to.setCountry(copy(from.getCountryGeo(), new Geo()));
        }
        if (from.getMunicipalityGeo() != null) {
            to.setMunicipality(copy(from.getMunicipalityGeo(), new Geo()));
        }
        if (from.getPostalCodeGeo() != null) {
            to.setPostalCode(copy(from.getPostalCodeGeo(), new Geo()));
        }
        if (from.getProvinceGeo() != null) {
            to.setProvince(copy(from.getProvinceGeo(), new Geo()));
        }
        if (from.getRegionGeo() != null) {
            to.setRegion(copy(from.getRegionGeo(), new Geo()));
        }
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress copy(PostalAddress from, it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress to) {
        to.setId(from.getId());
        to.setToName(from.getToName());
        to.setAttnName(from.getAttnName());
        to.setAddress1(from.getAddress());
        to.setAddress2(from.getAddress2());
        to.setDirections(from.getDirections());
        to.setCity(from.getCity());
        to.setForeignRegion(from.getForeignRegion());
        to.setForeignProvince(from.getForeignProvince());
        to.setStreetNumber(from.getStreetNumber());
        to.setVillage(from.getVillage());
        return to;
    }

    public PostalAddressEx copy(PostalAddress from, PostalAddressEx to) {
        copy(from, (it.memelabs.smartnebula.lmm.persistence.main.dto.PostalAddress) to);
        if (from.getCountry() != null) {
            to.setCountryGeo(copy(from.getCountry(), new it.memelabs.smartnebula.lmm.persistence.main.dto.Geo()));
        }
        if (from.getMunicipality() != null) {
            to.setMunicipalityGeo(copy(from.getMunicipality(), new it.memelabs.smartnebula.lmm.persistence.main.dto.Geo()));
        }
        if (from.getPostalCode() != null) {
            to.setPostalCodeGeo(copy(from.getPostalCode(), new it.memelabs.smartnebula.lmm.persistence.main.dto.Geo()));
        }
        return to;
    }

    /**
     * Convert {@link it.memelabs.smartnebula.lmm.persistence.main.dto.Contract} into {@link Contract}
     *
     * @param from
     * @return Node
     */
    public Contract copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Contract from, Contract to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setAuthorizedAmount(from.getAuthorizedAmount());
        to.setContractAmount(from.getContractAmount());
        to.setContractNumber(from.getContractNumber());
        to.setMainCategory(from.getMainCategory());

        to.setCustomerAuthorization(from.getCustomerAuthorization());
        to.setSubcontractAuthorizationNumber(from.getSubcontractAutNumber());
        to.setSubcontractDocAuthorizationDate(from.getDocSubcontractAuthDate());
        to.setSigningDate(from.getSigningDate());
        to.setRevocationDate(from.getRevocationDate());

        to.setNote(from.getNote());

        to.setUniqueCode(from.getUniqueCode());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setCompanyBank(from.getCompanyBank());
        to.setCompanyIban(TokenUtil.stringTokenizer(from.getCompanyIban()));
        to.setLetterOfIndemnity(from.getLetterOfIndemnity());
        to.setLetterOfIndemnityDeliveryDate(from.getLetterOfIndemnityDeliveryDate());
        to.setSubjectToMgo(from.getSubjectToMgo());
        return to;
    }

    /**
     * Convert {@link ContractEx} into {@link Contract}
     *
     * @param from
     * @return Node
     */
    public Contract copy(ContractEx from, Contract to, boolean includeAddress) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Contract) from, to);

        if (from.getCompany() != null)
            if (from.getCompany() instanceof CompanyEx)
                to.setCompany(copy((CompanyEx) from.getCompany(), new Company(), includeAddress));
            else
                to.setCompany(copy(from.getCompany(), new Company(), includeAddress));
        if (from.getPerformingCompany() != null)
            if (from.getPerformingCompany() instanceof CompanyEx)
                to.setPerformingCompany(copy((CompanyEx) from.getPerformingCompany(), new Company(), includeAddress));
            else
                to.setPerformingCompany(copy(from.getPerformingCompany(), new Company(), includeAddress));
        if (from.getReferenceContract() != null)
            to.setReferenceContract(copy(from.getReferenceContract(), new Contract()));
        if (from.getAntimafiaProcess() != null) {
            to.setAntimafiaProcess(copy(from.getAntimafiaProcess(), new AntimafiaProcess()));
        }
        if (from.getContractType() != null)
            to.setContractType(copy(from.getContractType(), new ContractCatalogItem()));
        if (from.getLot() != null)
            to.setLot(copy(from.getLot(), new Lot()));
        if (from.getConstructionSites() != null)
            from.getConstructionSites().forEach(cs -> to.getConstructionSites().add(copy(cs, new ConstructionSite())));
        if (from.getContractExtension() != null)
            to.setContractExtension(copy(from.getContractExtension(), new Contract()));
        if (from.getState() != null)
            to.setState(copy(from.getState(), new EntityState()));
        if (from.getConstructionSites() != null && !from.getConstructionSites().isEmpty()) {
            StringBuffer buf = new StringBuffer();
            //String newLine = System.lineSeparator();
            for (it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite cs : from.getConstructionSites()) {
                buf.append(cs.getDescription()).append(" ").append(cs.getCode()).append("; ");
            }
            to.setConstructionSitesDescriptionList(buf.toString());
        }
        if (from.getContractAuthorization() != null)
            to.setAuthorization(copy(from.getContractAuthorization(), new ContractCatalogItem()));
        if (from.getContractCommitmentMode() != null)
            to.setCommitmentMode(copy(from.getContractCommitmentMode(), new ContractCatalogItem()));
        return to;
    }

    public ContractCatalogItem copy(it.memelabs.smartnebula.lmm.persistence.main.dto.ContractCatalog from, ContractCatalogItem to) {
        copy(from, (Identifiable) to);
        to.setParentId(from.getParentId());
        return to;
    }

    /**
     * Convert {@link ConstructionSiteEx} into {@link ConstructionSite}
     *
     * @param from
     * @return ConstructionSite
     */
    public ConstructionSite copy(ConstructionSiteEx from, ConstructionSite to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite) from, to);
        if (from.getAddress() != null) {
            to.setAddress(copy(from.getAddress(), new PostalAddress()));
        }
        if (from.getAssignedCompany() != null) {
            to.setAssignedCompany(copy(from.getAssignedCompany(), new Company(), false));
        }
        if (from.getCompanyManager() != null) {
            to.setCompanyManager(copy(from.getCompanyManager(), new Person()));
        }
        if (from.getCse() != null) {
            to.setCse(copy(from.getCse(), new Person()));
        }
        if (from.getWeeklyReferent() != null) {
            to.setWeeklyReferent(copy(from.getWeeklyReferent(), new Person()));
        }
        if (from.getWorksCompany() != null) {
            to.setWorksCompany(copy(from.getWorksCompany(), new Company(), false));
        }
        ArrayList<PostalAddress> dtoLocations = new ArrayList<>();
        if (from.getLocations() != null && from.getLocations().size() > 0) {
            for (PostalAddressEx pa : from.getLocations()) {
                dtoLocations.add(copy(pa, new PostalAddress()));
            }

        }
        to.setLocations(dtoLocations);
        if (from.getJobOrder() != null) {
            to.setJobOrder(copy(from.getJobOrder(), new JobOrder()));
        }
        if (from.getLot() != null) {
            to.setLot(copy(from.getLot(), new Lot()));
        }
        return to;
    }

    public ConstructionSite copy(it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite from, ConstructionSite to) {
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setId(from.getId());
        to.setNote(from.getNote());
        to.setCig(from.getCig());
        to.setCup(from.getCup());
        return to;
    }

    public ConstructionSiteEx copy(ConstructionSite from, ConstructionSiteEx to) {
        copy(from, (it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite) to);
        if (from.getAddress() != null) {
            to.setAddress(copy(from.getAddress(), new PostalAddressEx()));
        }
        if (from.getAssignedCompany() != null) {
            to.setAssignedCompany(copy(from.getAssignedCompany(), new CompanyEx(), false));
        }
        if (from.getCompanyManager() != null) {
            to.setCompanyManager(copy(from.getCompanyManager(), new PersonEx(), true));
        }
        if (from.getCse() != null) {
            to.setCse(copy(from.getCse(), new PersonEx(), true));
        }
        if (from.getWeeklyReferent() != null) {
            to.setWeeklyReferent(copy(from.getWeeklyReferent(), new PersonEx(), true));
        }
        if (from.getWeeklyReferent() != null) {
            to.setWeeklyReferent(copy(from.getWeeklyReferent(), new PersonEx(), true));
        }
        if (from.getLocations() != null && from.getLocations().size() > 0) {
            ArrayList<PostalAddressEx> dtoLocations = new ArrayList<>();
            for (PostalAddress pa : from.getLocations()) {
                dtoLocations.add(copy(pa, new PostalAddressEx()));
            }
            to.setLocations(dtoLocations);
        }
        if (from.getJobOrder() != null) {
            to.setJobOrder(copy(from.getJobOrder(), new JobOrderEx()));
            to.setJobOrderId(from.getJobOrder().getId());
        }
        if (from.getLot() != null) {
            to.setLot(copy(from.getLot(), new LotEx()));
            to.setLotId(from.getLot().getId());
        }
        return to;
    }

    public it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite copy(ConstructionSite from, it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSite to) {
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setId(from.getId());
        to.setNote(from.getNote());
        to.setCup(from.getCup());
        to.setCig(from.getCig());
        return to;
    }

    public AntimafiaProcess copy(it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcess from, AntimafiaProcess to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setExpiryDate(from.getExpiryDate());
        to.setNote(from.getNote());
        to.setCreated(from.getCreatedStamp());
        return to;
    }

    public AntimafiaProcess copy(AntimafiaProcessEx from, AntimafiaProcess to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcess) from, to);
        if (from.getCompany() != null) {
            to.setCompany(copy(from.getCompany(), new Company(), false));
        }
        if (from.getPrefecture() != null) {
            to.setPrefecture(copy(from.getPrefecture(), new Identifiable()));
        }
        if (from.getState() != null) {
            to.setState(copy(from.getState(), new EntityState()));
        }
        if (from.getCausal() != null) {
            to.setCausal(copy(from.getCausal(), new EntityState()));
        }
        if (from.getLot() != null) {
            to.setLot(copy(from.getLot(), new Lot()));
        }
        return to;
    }

    public AntimafiaProcessPhase copy(it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhase from, AntimafiaProcessPhase to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setNote(from.getNote());
        to.setProtocolNumber(from.getProtocolNumber());
        return to;
    }

    public AntimafiaProcessPhase copy(AntimafiaProcessPhaseEx from, AntimafiaProcessPhase to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhase) from, to);
        to.setState(copy(from.getState(), new EntityState()));
        to.setType(copy(from.getType(), new AntimafiaProcessPhaseType()));
        return to;
    }

    public Identifiable copy(it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseType from, Identifiable to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        return to;
    }

    public AntimafiaProcessPhaseType copy(it.memelabs.smartnebula.lmm.persistence.main.dto.AntimafiaProcessPhaseType from, AntimafiaProcessPhaseType to) {
        copy(from, (Identifiable) to);
        to.setName(from.getName());
        to.setOrdinal(from.getOrdinal());
        to.setLabelStartDate(from.getLabelStartDate());
        to.setLabelEndDate(from.getLabelEndDate());
        to.setLabelProtocolNumber(from.getLabelProtocolNumber());
        return to;
    }

    public WeeklyWorkLogCompanyPerson copy(PersonEmploymentEx from, WeeklyWorkLogCompanyPerson to) {
        to.setId(from.getId());
        if (from.getPerson() != null) {
            to.setFirstName(from.getPerson().getFirstName());
            to.setLastName(from.getPerson().getLastName());
        }
        return to;
    }

    public WeeklyWorkLogCompanyEquipment copy(EquipmentEmploymentEx from, WeeklyWorkLogCompanyEquipment to) {
        to.setId(from.getId());
        if (from.getEquipment() != null) {
            to.setRegistrationNumber(from.getEquipment().getRegistrationNumber());
        }
        return to;
    }

    public Comment copy(CommentEx from, Comment to) {
        to.setId(from.getId());
        to.setUsername(from.getUsername());
        to.setNote(from.getNote());
        to.setTimestamp(from.getModifiedStamp());
        if (from.getConstructionSiteLog() != null)
            to.setConstructionSiteLog(copy(from.getConstructionSiteLog(), new ConstructionSiteLog()));
        if (from.getCommentType() != null)
            to.setCommentType(copy(from.getCommentType(), new CommentType()));
        to.setItemType((from.getEntity() != null) ? from.getEntity().name() : null);
        return to;
    }

    public CommentType copy(it.memelabs.smartnebula.lmm.persistence.main.dto.CommentType from, CommentType to) {
        to.setDescription(from.getDescription());
        to.setId(from.getId());
        to.setOrdinal(from.getOrdinal());
        to.setEntity((from.getEntity() != null) ? from.getEntity().name() : null);
        return to;
    }


    public NotificationEvent copy(it.memelabs.smartnebula.lmm.persistence.main.dto.NotificationEvent from, NotificationEvent to) {
        copy(from.getEventId(), to);
        to.setStartDate(from.getPromiseDate());
        to.setEnabled(from.getEnabled());
        to.setExecutionInterval(from.getExecutionInterval());
        to.setNotificationInterval(from.getNotificationInterval());
        return to;
    }

    public NotificationEvent copy(NotificationEventType from, NotificationEvent to) {
        to.setId(from.toString());
        to.setCode(from.toString());
        to.setDescription(from.getDescription());
        to.setFrequencyAllowed(from.isFrequencyAllowed());
        to.setNotificationPeriodAllowed(from.isNotificationPeriodAllowed());
        to.setExecutionInterval(0);
        to.setNotificationInterval(0);
        to.setEnabled(false);
        to.setOrdinal(from.ordinal());
        return to;
    }

    public NotificationGroup copy(NotificationGroupEx from, NotificationGroup to) {
        to.setDescription(from.getDescription());
        to.setId(from.getId());
        if (from.getUsers() != null)
            to.setUsers(from.getUsers().stream().map(userLogin -> copy(userLogin, new User())).collect(Collectors.toList()));
        if (from.getEvents() != null)
            to.setEvents(from.getEvents().stream().map(event -> copy(event, new NotificationEvent())).collect(Collectors.toList()));
        return to;
    }

    public Wbs copy(it.memelabs.smartnebula.lmm.persistence.main.dto.Wbs from, Wbs to) {
        to.setCode(from.getCode());
        to.setDescription(from.getDescription());
        to.setId(from.getId());
        to.setNote(from.getNote());
        return to;
    }

    public Wbs copy(WbsEx from, Wbs to) {
        copy((it.memelabs.smartnebula.lmm.persistence.main.dto.Wbs) from, to);
        if (from.getJobOrder() != null) {
            to.setJobOrder(copy(from.getJobOrder(), new JobOrder()));
        }
        if (from.getConstructionSite() != null) {
            to.setConstructionSite(copy(from.getConstructionSite(), new ConstructionSite()));
        }

        return to;
    }

    public ConstructionSiteLog copy(ConstructionSiteLogEx from, WorkLogEx workLog, ConstructionSiteLog to) {
        copy(from, to);
        if (workLog != null) {
            to.setCompanies(workLog.getCompanyCount());
            to.setPersons(workLog.getPersonCount());
            to.setEquipments(workLog.getEquipmentCount());
        }
        return to;
    }

    public ConstructionSiteLog copy(ConstructionSiteLogEx from, ConstructionSiteLog to) {
        to.setId(from.getId());
        to.setLogDate(from.getLogDate());
        to.setNote(from.getNote());
        if (from.getState() != null) to.setState(copy(from.getState(), new EntityState()));
        if (from.getConstructionSite() != null) {
            to.setConstructionSite(copy(from.getConstructionSite(), new ConstructionSite()));
        }

        return to;
    }

    public ConstructionSiteLog copy(it.memelabs.smartnebula.lmm.persistence.main.dto.ConstructionSiteLog from, ConstructionSiteLog to) {
        to.setId(from.getId());
        to.setLogDate(from.getLogDate());
        to.setNote(from.getNote());
        return to;
    }

    public CslActivity copy(ConstructionSiteLogActivityEx from, CslActivity to) {
        to.setId(from.getId());
        to.setNote(from.getNote());
        to.setTimestamp(from.getModifiedStamp());
        if (from.getConstructionSiteLog() != null)
            to.setConstructionSiteLog(copy(from.getConstructionSiteLog(), new ConstructionSiteLog()));
        if (from.getCompany() != null)
            to.setCompany(copy(from.getCompany(), new Company(), false));
        if (from.getWbs() != null)
            to.setWbs(copy(from.getWbs(), new Wbs()));
        return to;
    }

    public WeatherData copy(ConstructionSiteLogWeatherDataEx from, WeatherData to) {
        to.setId(from.getId());
        if (from.getWeatherCondition() != null)
            to.setWeatherCondition(copy(from.getWeatherCondition(), new Identifiable()));
        to.setMinTemperature(from.getMinTemperature());
        to.setMaxTemperature(from.getMaxTemperature());
        if (from.getWindType() != null)
            to.setWindType(copy(from.getWindType(), new Identifiable()));
        to.setWindSpeed(from.getWindSpeed());
        to.setRainfall(from.getRainfall());
        if (from.getActivitySuspension() != null)
            to.setActivitySuspension(copy(from.getActivitySuspension(), new Identifiable()));
        return to;
    }

    public Accident copy(AccidentEx from, Accident to) {
        to.setId(from.getId());
        to.setDescription(from.getDescription());
        to.setDate(from.getSince());
        to.setDuration(from.getDuration());
        to.setNote(from.getNote());
        if (from.getConstructionSite() != null)
            to.setConstructionSite(copy(from.getConstructionSite(), new ConstructionSite()));
        if (from.getPersonEmployment() != null)
            to.setPersonEmployment(copy(from.getPersonEmployment(), new PersonEmployment()));
        return to;
    }
}

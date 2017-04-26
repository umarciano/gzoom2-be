package it.memelabs.smartnebula.lmm.persistence.job.util;

import it.memelabs.smartnebula.lmm.persistence.main.dto.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrea Fossi.
 * Bean to Map tranformer used to generate FTL
 */
public class EntityMapper {
    /**
     * tipo
     * data stipula
     * data inizio
     * data fine
     * descrizione
     * numero
     * impresa
     * impresa esecutrice
     *
     * @param contract
     * @return
     */
    public static Map<String, Object> map(ContractEx contract) {
        Map<String, Object> params = new HashMap<>();
        Company company = contract.getCompany();
        Company performingCompany = contract.getPerformingCompany();
        ContractCatalog type = contract.getContractType();
        if (type != null) {
            params.put("type", type.getDescription());
        }
        if (company != null) {
            params.put("companyName", company.getBusinessName());
            params.put("companyTaxIdentificationNumber", company.getTaxIdentificationNumber());
        }
        if (performingCompany != null) {
            params.put("performingCompanyName", performingCompany.getBusinessName());
            params.put("performingCompanyTaxIdentificationNumber", performingCompany.getTaxIdentificationNumber());
        }
        params.put("description", contract.getDescription());
        params.put("number", contract.getContractNumber());
        params.put("signingDate", contract.getSigningDate());
        params.put("startDate", contract.getStartDate());
        params.put("endDate", contract.getEndDate());
        return params;
    }

    public static Map<String, Object> map(AntimafiaProcessEx process) {
        Map<String, Object> params = new HashMap<>();
        Identifiable prefecture = process.getPrefecture();
        Company company = process.getCompany();
        Lot lot = process.getLot();
        EntityState causal = process.getCausal();
        if (prefecture != null) {
            params.put("prefecture", prefecture.getDescription());
        }
        if (causal != null) {
            params.put("causal", causal.getDescription());
        }
        if (company != null) {
            params.put("companyName", company.getBusinessName());
            params.put("companyTaxIdentificationNumber", company.getTaxIdentificationNumber());
        }
        if (lot != null) {
            params.put("lotDescription", lot.getDescription());
            params.put("lotCode", lot.getCode());
        } else {
            params.put("lotDescription", "");
            params.put("lotCode", "");
        }
        params.put("note", process.getNote() != null ? process.getNote() : "");
        params.put("created", process.getCreatedStamp());
        params.put("expiryDate", process.getExpiryDate());
        return params;
    }
}

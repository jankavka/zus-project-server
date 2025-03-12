package cz.kavka.dto;

import lombok.*;

@Data
public class RequiredInformationDTO {

    private Long id;

    private String establishment;

    private String organizationalStructure;

    private String contact;

    private BasicDataDTO basicData;

    private String documents;

    private String submissionsAndSuggestions;

    private String regulations;

    private String paymentsForProvidingInformation;

    private String LicenseAgreements;

    private String annualReport;


}

package cz.kavka.dto;

import lombok.*;

import java.util.Date;

@Data
public class RequiredInformationDTO {

    private String establishment;

    private String organizationalStructure;

    private String contact;

    private String documents;

    private String submissionsAndSuggestions;

    private String regulations;

    private String paymentsForProvidingInformation;

    private String LicenseAgreements;

    private String annualReport;

    private Date issuedDate;


}

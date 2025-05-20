package cz.kavka.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
public class RequiredInformationDTO {

    private String establishment;

    private List<String> organizationalStructure;

    private String officeHours;

    private String founding;

    private String telNumbers;

    private String website;

    private String documents;

    private String submissionsAndSuggestions;

    private List<String> regulations;

    private String paymentsForProvidingInformation;

    private String licenseAgreements;

    private List<String> annualReport;

    private Date issuedDate;


}

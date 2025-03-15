package cz.kavka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "required_information")
public class RequiredInformationEntity {

    @Id
    private Long id;

    private String establishment;

    private String organizationalStructure;

    private String contact;

    private String documents;

    private String submissionsAndSuggestions;

    private String regulations;

    private String paymentsForProvidingInformation;

    private String LicenseAgreements;

    private String annualReport;
}

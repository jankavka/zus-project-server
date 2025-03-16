package cz.kavka.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity(name = "required_information")
@NoArgsConstructor
@AllArgsConstructor
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

    private LocalDate issuedDate = LocalDate.now();
}

package cz.kavka.entity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequiredInformationEntity {

    private String establishment;

    private String organizationalStructure;

    private String contact;

    @OneToOne
    @JoinColumn
    private BasicDataEntity basicData;

    private String documents;

    private String submissionsAndSuggestions;

    private String regulations;

    private String paymentsForProvidingInformation;

    private String LicenseAgreements;

    private String annualReport;
}

package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "basic_data")
@NoArgsConstructor
@AllArgsConstructor
public class BasicDataEntity {

    @Id
    private Long id;

    @Column
    private String schoolName;

    @Column
    private String address;

    @Column
    private String legalForm;

    @Column
    private Integer maxNumberOfStudents;

    @ElementCollection
    @CollectionTable (name = "location_of_education")
    private List<String> locationsOfEducation;

    @Column
    private String director;

    private String telephoneNumbers;

    private String webSite;

    //zjistit jestli je třeba když je normální adresa
    private String mailingAddress;

    private String emailMailingAddress;

    private String taxIdentificationNumber;

    @Column
    private String deputyDirector;

    @Column
    private String founder;

    @Column
    private Integer idNumber;

    @Column
    private Integer organizationIdentificationMark;

    @Column
    private String dataBox;

    @Column
    private String accountNumber;

    @Column
    private LocalDate issuedDate = LocalDate.now();

}


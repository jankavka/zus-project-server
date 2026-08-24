package cz.kavka.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BasicDataDTO {

    private String schoolName;

    private String address;

    private String legalForm;

    private Integer maxNumberOfStudents;

    private List<String> locationsOfEducation;

    private String director;

    private String telephoneNumbers;

    private String webSite;

    //check whether this is still needed given the regular address above
    private String mailingAddress;

    //electronic mailroom (podatelna) address
    private String emailMailingAddress;

    //IČO (Czech company identification number)
    private String identificationNumber;

    //DIČ (Czech tax identification number)
    private String taxIdentificationNumber;

    private String deputyDirector;

    //the school's founding/establishing body
    private String founder;

    //REDIZO (national school register identifier)
    private Integer idNumber;

    //IZO (school facility identification number)
    private Integer organizationIdentificationMark;

    private String dataBox;

    private String accountNumber;

    private Date issuedDate;

}

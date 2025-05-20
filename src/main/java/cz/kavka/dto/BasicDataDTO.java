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

    //zjistit jestli je třeba když je normální adresa
    private String mailingAddress;

    //elektronická adresa podatelny
    private String emailMailingAddress;

    //IČO
    private String identificationNumber;

    //DIČ
    private String taxIdentificationNumber;

    private String deputyDirector;

    //"zřizovatel"
    private String founder;

    //REDIZO
    private Integer idNumber;

    //IZO
    private Integer organizationIdentificationMark;

    private String dataBox;

    private String accountNumber;

    private Date issuedDate;

}

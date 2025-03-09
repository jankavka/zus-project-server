package cz.kavka.dto;

import lombok.Data;

import java.util.List;

@Data
public class BasicDataDTO {

    private String schoolName;

    private String address;

    private String legalForm;

    private Integer maxNumberOfStudents;

    private List<String> locationsOfEducation;

    private String director;

    private String deputyDirector;

    private String founder;

    private Integer idNumber;

    private Integer organizationIdentificationMark;

    private String dataBox;

    private String accountNumber;
}

package cz.kavka.dto;

import lombok.*;

import java.util.Date;

@Data
public class SchoolManagementDTO {

    private Long id;

    private String name;

    private String degree;

    private String telNumber;

    private String role;

    private String email;

    private Date issuedDate;
}

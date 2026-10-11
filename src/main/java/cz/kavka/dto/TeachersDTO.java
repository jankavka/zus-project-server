package cz.kavka.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TeachersDTO {

    private Long id;

    private String name;

    private String degree;

    private String email;

    private String telNumber;

    private String subject;

    private Date issuedDate;
}

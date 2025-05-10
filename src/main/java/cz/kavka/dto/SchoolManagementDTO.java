package cz.kavka.dto;

import cz.kavka.constant.ManagementType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.Date;

@Data
public class SchoolManagementDTO {

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String degree;

    @NotBlank
    private String telNumber;

    @NotBlank
    private String email;

    private ManagementType managementType;

    private Date issuedDate;
}

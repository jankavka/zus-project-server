package cz.kavka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class TeachersDTO {

    private Long id;

    private String name;

    private String degree;

    private String email;

    private String telNumber;

    private Date issuedDate;
}

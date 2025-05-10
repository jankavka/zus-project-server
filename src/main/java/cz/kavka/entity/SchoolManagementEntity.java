package cz.kavka.entity;

import cz.kavka.constant.ManagementType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "school_management")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolManagementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String degree;

    private String telNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private ManagementType managementType;

    private LocalDate issuedDate = LocalDate.now();
}

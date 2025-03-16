package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity(name = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeachersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String degree;

    @Column
    private String email;

    @Column
    private String telNumber;

    @Column
    private LocalDate issuedDate = LocalDate.now();
}

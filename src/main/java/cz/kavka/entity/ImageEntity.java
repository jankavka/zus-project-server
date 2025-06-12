package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
public class ImageEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column
    private String fileName;

    @ManyToOne
    private AlbumEntity album;

}

package cz.kavka.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String albumName;

    @Column
    private String albumDescription;

    @OneToMany(mappedBy = "album")
    private List<ImageEntity> images;
}

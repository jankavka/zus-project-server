package cz.kavka.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Column
    private String leadPictureUrl;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<ImageEntity> images;

    @Column
    private boolean isHidden;
}

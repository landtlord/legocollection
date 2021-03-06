package be.landtlord.legocollection.inventory.sets.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "sets")
public class Set {
    @Id
    @Column(name = "set_num")
    @JsonProperty("set_num")
    private String setNumber;

    private String name;

    private int year;

    @OneToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @Column(name = "image_url")
    @JsonProperty("set_img_url")
    private String imageUrl;
}

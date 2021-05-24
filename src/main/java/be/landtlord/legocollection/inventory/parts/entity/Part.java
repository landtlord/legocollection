package be.landtlord.legocollection.inventory.parts.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "parts")
public class Part {
    @Id
    @Column(name ="part_num")
    @JsonProperty("part_num")
    private String partNumber;

    private String name;

    @OneToOne
    @JoinColumn(name = "part_cat_id", nullable = false)
    private PartCategory partCategory;

    @Column(name ="part_material")
    private String material;

    @Column(name = "image_url")
    @JsonProperty("part_img_url")
    private String imageUrl;
}

package be.landtlord.legocollection.inventory.parts.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "parts")
public class Part {
    @Id
    @Column(name ="part_num")
    private String partNumber;

    private String name;

    @ManyToOne
    private PartCategory partCategory;

    private String material;
}

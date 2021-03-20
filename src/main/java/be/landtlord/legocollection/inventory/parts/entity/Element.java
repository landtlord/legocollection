package be.landtlord.legocollection.inventory.parts.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "elements")
public class Element {
    @Id
    @GeneratedValue
    private Long id;

    private String element_id;

    @OneToOne
    @JoinColumn(name = "part_num", nullable = false)
    private Part part;

    @OneToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
}

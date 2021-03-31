package be.landtlord.legocollection.inventory.sets.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Theme parent;

}

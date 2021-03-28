package be.landtlord.legocollection.inventory.parts.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "part_categories")
public class PartCategory {
    @Id
    private Long id;

    private String name;
}

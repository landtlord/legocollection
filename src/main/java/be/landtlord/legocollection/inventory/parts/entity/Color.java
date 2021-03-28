package be.landtlord.legocollection.inventory.parts.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "colors")
public class Color {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String rgb;

    private Boolean transparent;
}

package be.landtlord.legocollection.inventory.parts.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Color {
    @Id
    @GeneratedValue
    private Long id;
    //todo
}

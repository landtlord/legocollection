package be.landtlord.legocollection.inventory.parts.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "parts")
public class Part {
    @Id
    @Column(name ="part_num")
    private String partNumber;
    //todo
}

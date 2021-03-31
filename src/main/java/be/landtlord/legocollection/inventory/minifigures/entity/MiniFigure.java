package be.landtlord.legocollection.inventory.minifigures.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "minifigs")
public class MiniFigure {
    @Id
    @Column(name ="fig_num")
    private String miniFigureNumber;

    private String name;

    @Column(name ="num_parts")
    private int numberOfParts;
}

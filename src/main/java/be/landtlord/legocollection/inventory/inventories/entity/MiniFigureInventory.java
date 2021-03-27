package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory_minifigs")
public class MiniFigureInventory {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "fig_num", nullable = false)
    private MiniFigure miniFigure;
}
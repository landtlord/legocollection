package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory_minifigs")
public class InventoryMiniFigure {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "fig_num", nullable = false)
    private MiniFigure miniFigure;

    private int quantity;
}

package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.inventory.sets.entity.Set;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory_sets")
public class InventorySet {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "set_num", nullable = false)
    private Set set;

    private int quantity;
}

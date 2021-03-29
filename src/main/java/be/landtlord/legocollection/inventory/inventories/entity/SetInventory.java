package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.inventory.sets.entity.Set;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory_sets")
public class SetInventory {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="inventory_id")
    private Inventory inventory;

    @OneToOne
    @JoinColumn(name = "set_num", nullable = false)
    private Set set;
}

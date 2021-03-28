package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.inventory.parts.entity.Color;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "inventory_parts")
public class PartsInventory {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name="inventory_id")
    private Inventory inventory;

    @OneToOne
    @JoinColumn(name = "part_num", nullable = false)
    private Part part;

    @OneToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;

    private int quantity;

    private boolean spare;
}

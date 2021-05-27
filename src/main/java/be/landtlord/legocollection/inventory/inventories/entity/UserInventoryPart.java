package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.user.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_invent_part")
public class UserInventoryPart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_invent_set_id")
    private UserInventorySet userInventorySet;

    @ManyToOne
    @JoinColumn(name = "user_invent_minifig_id")
    private UserInventoryMiniFig userInventoryMiniFig;

    @ManyToOne
    @JoinColumn(name = "inventory_parts_id", nullable = false)
    private InventoryParts inventoryParts;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private int quantity;

    private boolean sold;
}

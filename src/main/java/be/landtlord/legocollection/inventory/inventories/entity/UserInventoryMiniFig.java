package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.user.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_invent_minifig")
public class UserInventoryMiniFig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "user_invent_set_id")
    private UserInventorySet userInventorySet;

    @ManyToOne
    @JoinColumn(name = "inventory_minifig_id")
    private InventoryMiniFigure inventoryMiniFigure;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean complete;

    private int quantity;

    private boolean sold;
}

package be.landtlord.legocollection.inventory.inventories.entity;

import be.landtlord.legocollection.user.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "user_invent_set")
public class UserInventorySet {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean complete;

    private boolean sold;
}

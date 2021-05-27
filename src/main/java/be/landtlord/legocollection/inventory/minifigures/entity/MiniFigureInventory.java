package be.landtlord.legocollection.inventory.minifigures.entity;

import be.landtlord.legocollection.inventory.parts.entity.PartsInventory;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "")
    private List<PartsInventory> partsInventoryList;
}

package be.landtlord.legocollection.inventory.inventories.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "inventories")
public class Inventory {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "set_num")
    private String setNumber;

    private int version;

}

package be.landtlord.legocollection.inventory.sets.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
@Table(name = "themes")
public class Theme {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Theme parent;

    @Override
    public String toString() {
        String theme = name;

        if (Objects.nonNull(parent)) {
            theme = theme + ", onderdeel van " + parent;
        }

        return theme;
    }
}

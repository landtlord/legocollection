package be.landtlord.legocollection.inventory.minifigures.boundary;

import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MiniFiguresRepository extends CrudRepository<MiniFigure, String> {
    List<MiniFigure> findAllByMiniFigureNumberContains(String setNumber);
}

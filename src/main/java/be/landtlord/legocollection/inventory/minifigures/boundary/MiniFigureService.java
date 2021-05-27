package be.landtlord.legocollection.inventory.minifigures.boundary;

import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MiniFigureService {
    List<MiniFigure> findByMiniFigureNumberContains(String setNumber);

    MiniFigure getMiniFigureBy(String miniFigureNumber);
}

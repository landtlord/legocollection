package be.landtlord.legocollection.inventory.inventories.control;

import be.landtlord.legocollection.inventory.inventories.boundary.InventoryService;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFiguresRepository;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.sets.boundary.SetRepository;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImp implements InventoryService{
    @Autowired
    private MiniFiguresRepository miniFiguresRepository;

    @Autowired
    private SetRepository setRepository;

    @Override
    public List<Set> findBySetNumberContains(String setNumber) {
        return setRepository.findAllBySetNumberContains(setNumber);
    }

    @Override
    public Set getSetBy(String setNumber) {
         return setRepository.findById(setNumber).orElseThrow();
    }

    @Override
    public List<MiniFigure> findByMiniFigureNumberContains(String miniFigureNumber) {
        return miniFiguresRepository.findAllByMiniFigureNumberContains(miniFigureNumber);
    }
}

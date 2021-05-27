package be.landtlord.legocollection.inventory.parts.boundary;

import be.landtlord.legocollection.inventory.parts.entity.Part;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PartsService {
    List<Part> finByPartNumberContains(String partNumber);

    Part getPartBy(String partNumber);
}

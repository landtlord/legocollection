package be.landtlord.legocollection.inventory.parts.control;

import be.landtlord.legocollection.inventory.inventories.control.InventoryServiceImp;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.PartListSearchAnswer;
import be.landtlord.legocollection.inventory.parts.boundary.PartsRepository;
import be.landtlord.legocollection.inventory.parts.boundary.PartsService;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static be.landtlord.legocollection.inventory.inventories.control.InventoryServiceImp.BASE_URL_REBRICKABLE;

public class PartsServiceImpl implements PartsService {
    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Part> finByPartNumberContains(String partNumber) {
        List<Part> partList;
        try {
            partList = restTemplate.getForObject(BASE_URL_REBRICKABLE + "parts/?key=7389fd1beac9dcda170fc71e5f536512&search=" + partNumber, PartListSearchAnswer.class).getResults();
        } catch (RestClientException e) {
            partList = partsRepository.findByPartNumberContains(partNumber);
        }
        return partList;
    }

    @Override
    @Transactional
    public Part getPartBy(String partNumber) {
        Optional<Part> optionalPart = partsRepository.findById(partNumber);
        return optionalPart.map(this::updatePart)
                .orElseGet(() -> getPartFromApi(partNumber));
    }

    private Part updatePart(Part part) {
        if (Objects.isNull(part.getImageUrl())) {
            Part partFromApi = restTemplate.getForObject(BASE_URL_REBRICKABLE + "parts/" + part.getPartNumber() + "/?key=7389fd1beac9dcda170fc71e5f536512", Part.class);
            part.setImageUrl(partFromApi.getImageUrl());
        }
        return part;
    }

    private Part getPartFromApi(String partNumber) {
        Part part = restTemplate.getForObject(BASE_URL_REBRICKABLE + "parts/" + partNumber + "/?key=7389fd1beac9dcda170fc71e5f536512", Part.class);
        return partsRepository.save(part);
    }
}

package be.landtlord.legocollection.inventory.minifigures.control;

import be.landtlord.legocollection.inventory.inventories.entity.apianswer.MiniFigureListSearchAnswer;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFigureService;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFiguresRepository;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static be.landtlord.legocollection.inventory.inventories.control.InventoryServiceImp.BASE_URL_REBRICKABLE;

@Service
public class MiniFigureServiceImp implements MiniFigureService {
    @Autowired
    private MiniFiguresRepository miniFiguresRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<MiniFigure> findByMiniFigureNumberContains(String miniFigureNumber) {
        List<MiniFigure> miniFigures;
        try {
            miniFigures = restTemplate.getForObject(BASE_URL_REBRICKABLE + "minifigs/?key=7389fd1beac9dcda170fc71e5f536512&search=" + miniFigureNumber, MiniFigureListSearchAnswer.class).getResults();
        } catch (RestClientException e) {
            miniFigures = miniFiguresRepository.findAllByMiniFigureNumberContains(miniFigureNumber);
        }
        return miniFigures;
    }

    @Override
    public MiniFigure getMiniFigureBy(String miniFigureNumber) {
        Optional<MiniFigure> optionalSet = miniFiguresRepository.findById(miniFigureNumber);
        return optionalSet.map(this::updateMiniFigure)
                .orElseGet(() -> getMiniFigureFromApi(miniFigureNumber));
    }

    private MiniFigure updateMiniFigure(MiniFigure miniFigure) {
        if (Objects.isNull(miniFigure.getImageUrl())) {
            MiniFigure setFromAPI = restTemplate.getForObject(BASE_URL_REBRICKABLE + "minifigs/" + miniFigure.getMiniFigureNumber() + "/?key=7389fd1beac9dcda170fc71e5f536512", MiniFigure.class);
            miniFigure.setImageUrl(setFromAPI.getImageUrl());
        }
        return miniFigure;
    }

    private MiniFigure getMiniFigureFromApi(String miniFigureNumber) {
        MiniFigure miniFigure = restTemplate.getForObject(BASE_URL_REBRICKABLE + "minifigs/" + miniFigureNumber + "/?key=7389fd1beac9dcda170fc71e5f536512", MiniFigure.class);
        return miniFiguresRepository.save(miniFigure);
    }

}

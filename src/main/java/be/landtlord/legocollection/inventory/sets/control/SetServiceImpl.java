package be.landtlord.legocollection.inventory.sets.control;

import be.landtlord.legocollection.inventory.inventories.entity.apianswer.SetListSearchAnswer;
import be.landtlord.legocollection.inventory.sets.boundary.SetRepository;
import be.landtlord.legocollection.inventory.sets.boundary.SetService;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SetServiceImpl implements SetService {
    public static final String BASE_URL_REBRICKABLE = "https://rebrickable.com/api/v3/lego/";

    @Autowired
    private SetRepository setRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Set> findBySetNumberContains(String setNumber) {
        List<Set> sets;
        try {
            sets = restTemplate.getForObject(BASE_URL_REBRICKABLE + "sets/?key=7389fd1beac9dcda170fc71e5f536512&search=" + setNumber, SetListSearchAnswer.class).getResults();
        } catch (RestClientException e) {
            sets = setRepository.findAllBySetNumberContains(setNumber);
        }
        return sets;
    }

    @Override
    @Transactional
    public Set getSetBy(String setNumber) {
        Optional<Set> optionalSet = setRepository.findById(setNumber);
        return optionalSet.map(this::updateSet)
                .orElseGet(() -> getSetFromApi(setNumber));
    }

    private Set updateSet(Set set) {
        if (Objects.isNull(set.getImageUrl())) {
            Set setFromAPI = restTemplate.getForObject(BASE_URL_REBRICKABLE + "sets/" + set.getSetNumber() + "/?key=7389fd1beac9dcda170fc71e5f536512", Set.class);
            set.setImageUrl(setFromAPI.getImageUrl());
        }
        return set;
    }

    private Set getSetFromApi(String setNumber) {
        Set set = restTemplate.getForObject(BASE_URL_REBRICKABLE + "sets/" + setNumber + "/?key=7389fd1beac9dcda170fc71e5f536512", Set.class);
        return setRepository.save(set);
    }
}

package be.landtlord.legocollection.inventory.inventories.control;

import be.landtlord.legocollection.inventory.inventories.boundary.*;
import be.landtlord.legocollection.inventory.inventories.entity.*;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFiguresRepository;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.parts.boundary.PartsRepository;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import be.landtlord.legocollection.inventory.sets.boundary.SetRepository;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import be.landtlord.legocollection.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class InventoryServiceImp implements InventoryService {
    @Autowired
    private MiniFiguresRepository miniFiguresRepository;

    @Autowired
    private SetRepository setRepository;

    @Autowired
    private PartsRepository partsRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryPartsRepository inventoryPartsRepository;

    @Autowired
    private UserInventorySetsRepository userInventorySetsRepository;

    @Autowired
    private UserInventoryPartsRepository userInventoryPartRepository;

    @Override
    public List<Set> findBySetNumberContains(String setNumber) {
        List<Set> sets;
        try {
            RestTemplate restTemplate = new RestTemplate();
            sets = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/sets/?key=7389fd1beac9dcda170fc71e5f536512&search=" + setNumber, SetListSearchAnswer.class).getResults();
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
        RestTemplate restTemplate = new RestTemplate();
        Set setFromAPI = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/sets/" + set.getSetNumber() + "/?key=7389fd1beac9dcda170fc71e5f536512", Set.class);
        if (Objects.isNull(set.getImageUrl())) {
            set.setImageUrl(setFromAPI.getImageUrl());
        }
        return set;
    }

    private Set getSetFromApi(String setNumber) {
        RestTemplate restTemplate = new RestTemplate();
        Set set = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/sets/" + setNumber + "/?key=7389fd1beac9dcda170fc71e5f536512", Set.class);
        return setRepository.save(set);
    }

    @Override
    public List<MiniFigure> findByMiniFigureNumberContains(String miniFigureNumber) {
        List<MiniFigure> miniFigures;
        try {
            RestTemplate restTemplate = new RestTemplate();
            miniFigures = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/minifigs/?key=7389fd1beac9dcda170fc71e5f536512&search=" + miniFigureNumber, MiniFigureListSearchAnswer.class).getResults();
        } catch (RestClientException e) {
            miniFigures = miniFiguresRepository.findAllByMiniFigureNumberContains(miniFigureNumber);
        }
        return miniFigures;
    }

    @Override
    public MiniFigure getMiniFigureBy(String miniFigureNumber) {
        return miniFiguresRepository.findById(miniFigureNumber).orElseThrow();
    }

    @Override
    public List<Part> finByPartNumberContains(String partNumber) {
        List<Part> partList;
        try {
            RestTemplate restTemplate = new RestTemplate();
            partList = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/parts/?key=7389fd1beac9dcda170fc71e5f536512&search=" + partNumber, PartListSearchAnswer.class).getResults();
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
        RestTemplate restTemplate = new RestTemplate();
        Part partFromApi = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/parts/" + part.getPartNumber() + "/?key=7389fd1beac9dcda170fc71e5f536512", Part.class);
        if (Objects.isNull(part.getImageUrl())) {
            part.setImageUrl(partFromApi.getImageUrl());
        }
        return part;
    }

    private Part getPartFromApi(String partNumber) {
        RestTemplate restTemplate = new RestTemplate();
        Part part = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/parts/" + partNumber + "/?key=7389fd1beac9dcda170fc71e5f536512", Part.class);
        return partsRepository.save(part);
    }

    @Override
    @Transactional
    public List<InventoryParts> getPartsForSetNumber(String setNumber) {
        List<InventoryParts> inventoryParts;
        try {
            RestTemplate restTemplate = new RestTemplate();
            inventoryParts = restTemplate.getForObject("https://rebrickable.com/api/v3/lego/sets/" + setNumber + "/parts/?page_size=1000&key=7389fd1beac9dcda170fc71e5f536512&search=", InventoryPartsListSearchAnswer.class).getResults();
            List<Part> parts = inventoryParts.stream().map(InventoryParts::getPart).collect(Collectors.toList());
            parts.forEach(this::updateInRepoPart);
        } catch (Exception e) {
            Inventory inventory = inventoryRepository.findBySetNumber(setNumber).get(0);
            inventoryParts = inventoryPartsRepository.findByInventory(inventory);
        }
        return inventoryParts;
    }

    private void updateInRepoPart(Part part) {
        Optional<Part> optionalPart = partsRepository.findByPartNumber(part.getPartNumber());
        if (optionalPart.isPresent()){
            optionalPart.get().setImageUrl(part.getImageUrl());
        } else{
            partsRepository.save(part);
        }
    }

    @Override
    public UserInventorySet addToMyList(Set set, User user) {
        Inventory inventory = inventoryRepository.findBySetNumber(set.getSetNumber()).get(0);
        UserInventorySet userInventorySet = new UserInventorySet();
        userInventorySet.setInventory(inventory);
        userInventorySet.setUser(user);
        UserInventorySet save = userInventorySetsRepository.save(userInventorySet);
        List<InventoryParts> inventoryParts = inventoryPartsRepository.findByInventory(inventory);
        inventoryParts.stream().forEach(inventoryPart -> getUserInventoryPart(user, userInventorySet, inventoryPart));
        return save;
    }

    private void getUserInventoryPart(User user, UserInventorySet userInventorySet, InventoryParts inventoryPart) {
        UserInventoryPart userInventoryPart = new UserInventoryPart();
        userInventoryPart.setInventoryParts(inventoryPart);
        userInventoryPart.setUser(user);
        userInventoryPart.setUserInventorySet(userInventorySet);
        userInventoryPartRepository.save(userInventoryPart);
    }

    @Override
    public UserInventorySet getUserInventorySetBy(String id) {
        Long idLong = Long.parseLong(id);
        return userInventorySetsRepository.findById(idLong).get();
    }

    @Override
    public List<UserInventorySet> getUserInventorySetByUser(User user) {
        return userInventorySetsRepository.findAllByUser(user);
    }

    @Override
    public List<UserInventoryPart> getUserInventoryPartsByUserInventorySet(UserInventorySet userInventorySet) {
        return userInventoryPartRepository.findAllByUserInventorySet(userInventorySet);
    }

    @Override
    public UserInventoryPart updateUserInventoryPart(UserInventoryPart userInventoryPart) {
        return userInventoryPartRepository.save(userInventoryPart);
    }

    @Override
    public List<UserInventoryPart> getUserInventoryPartWithStockByUser(User user) {
        return  userInventoryPartRepository.findAllByUser(user)
                .stream()
                .filter(userInventoryPart -> userInventoryPart.getQuantity() != 0)
                .collect(Collectors.toList());
    }
}

package be.landtlord.legocollection.inventory.inventories.control;

import be.landtlord.legocollection.inventory.inventories.boundary.*;
import be.landtlord.legocollection.inventory.inventories.entity.*;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.InventoryPartsListSearchAnswer;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.MiniFigureListSearchAnswer;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.PartListSearchAnswer;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.SetListSearchAnswer;
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

@Service
public class InventoryServiceImp implements InventoryService {
    public static final String BASE_URL_REBRICKABLE = "https://rebrickable.com/api/v3/lego/";

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

    @Autowired
    private UserInventoryMinifigureRepository userInventoryMinifigureRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public List<InventoryParts> getPartsForSetNumber(String setNumber) {
        List<InventoryParts> inventoryParts;
        try {
            inventoryParts = restTemplate.getForObject(BASE_URL_REBRICKABLE + "sets/" + setNumber + "/parts/?page_size=1000&key=7389fd1beac9dcda170fc71e5f536512&search=", InventoryPartsListSearchAnswer.class).getResults();
            List<Part> parts = inventoryParts.stream().map(InventoryParts::getPart).collect(Collectors.toList());
            parts.forEach(this::updateInRepoPart);
        } catch (Exception e) {
            Inventory inventory = inventoryRepository.findBySetNumber(setNumber).get(0);
            inventoryParts = inventoryPartsRepository.findByInventory(inventory);
        }
        return inventoryParts;
    }

    @Override
    @Transactional
    public List<InventoryParts> getPartsForMiniFigNumber(String miniFigNumber) {
        List<InventoryParts> inventoryParts;
        try {
            inventoryParts = restTemplate.getForObject(BASE_URL_REBRICKABLE + "minifigs/" + miniFigNumber + "/parts/?page_size=1000&key=7389fd1beac9dcda170fc71e5f536512&search=", InventoryPartsListSearchAnswer.class).getResults();
            List<Part> parts = inventoryParts.stream().map(InventoryParts::getPart).collect(Collectors.toList());
            parts.forEach(this::updateInRepoPart);
        } catch (Exception e) {
            Inventory inventory = inventoryRepository.findBySetNumber(miniFigNumber).get(0);
            inventoryParts = inventoryPartsRepository.findByInventory(inventory);
        }
        return inventoryParts;
    }

    private void updateInRepoPart(Part part) {
        Optional<Part> optionalPart = partsRepository.findByPartNumber(part.getPartNumber());
        if (optionalPart.isPresent()) {
            optionalPart.get().setImageUrl(part.getImageUrl());
        } else {
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
        inventoryParts.forEach(inventoryPart -> getUserInventoryPart(user, userInventorySet, inventoryPart));
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
    public UserInventoryMiniFig addToMyList(MiniFigure set, User user) {
        Inventory inventory = inventoryRepository.findBySetNumber(set.getMiniFigureNumber()).get(0);
        UserInventoryMiniFig userInventoryMiniFig = new UserInventoryMiniFig();
        userInventoryMiniFig.setInventory(inventory);
        userInventoryMiniFig.setUser(user);
        UserInventoryMiniFig save = userInventoryMinifigureRepository.save(userInventoryMiniFig);
        List<InventoryParts> inventoryParts = inventoryPartsRepository.findByInventory(inventory);
        inventoryParts.forEach(inventoryPart -> getUserInventoryPart(user, userInventoryMiniFig, inventoryPart));
        return save;
    }

    private void getUserInventoryPart(User user, UserInventoryMiniFig userInventoryMiniFig, InventoryParts inventoryPart) {
        UserInventoryPart userInventoryPart = new UserInventoryPart();
        userInventoryPart.setInventoryParts(inventoryPart);
        userInventoryPart.setUser(user);
        userInventoryPart.setUserInventoryMiniFig(userInventoryMiniFig);
        userInventoryPartRepository.save(userInventoryPart);
    }

    @Override
    public UserInventorySet getUserInventorySetBy(String id) {
        Long idLong = Long.parseLong(id);
        return userInventorySetsRepository.findById(idLong).orElse(null);
    }

    @Override
    public UserInventoryMiniFig getUserInventoryMiniFigureBy(String id) {
        Long idLong = Long.parseLong(id);
        return userInventoryMinifigureRepository.findById(idLong).orElse(null);
    }

    @Override
    public List<UserInventorySet> getUserInventorySetByUser(User user) {
        return userInventorySetsRepository.findAllByUser(user);
    }

    @Override
    public List<UserInventoryMiniFig> getUserInventoryMiniFigureByUser(User user) {
        return userInventoryMinifigureRepository.findAllByUser(user);
    }

    @Override
    public List<UserInventoryPart> getUserInventoryPartsByUserInventorySet(UserInventorySet userInventorySet) {
        return userInventoryPartRepository.findAllByUserInventorySet(userInventorySet);
    }

    @Override
    public List<UserInventoryPart> getUserInventoryPartsByUserInventoryMiniFigure(UserInventoryMiniFig userInventoryMiniFig) {
        return userInventoryPartRepository.findAllByUserInventoryMiniFig(userInventoryMiniFig);
    }

    @Override
    public UserInventoryPart updateUserInventoryPart(UserInventoryPart userInventoryPart) {
        return userInventoryPartRepository.save(userInventoryPart);
    }

    @Override
    public List<UserInventoryPart> getUserInventoryPartWithStockByUser(User user) {
        return userInventoryPartRepository.findAllByUser(user)
                .stream()
                .filter(userInventoryPart -> userInventoryPart.getQuantity() != 0)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInventorySet> getUserInventorySetByUserAndSetNumberContains(User user, String setNumber) {
        return getUserInventorySetByUser(user).stream()
                .filter(x -> x.getInventory().getSetNumber().contains(setNumber))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInventoryMiniFig> getUserInventoryMiniFigureByUserAndFigNumberContains(User user, String setNumber) {
        return getUserInventoryMiniFigureByUser(user).stream()
                .filter(x -> x.getInventory().getSetNumber().contains(setNumber))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserInventoryPart> getUserInventoryPartByUserAndPartNumberContains(User user, String partNumber) {
        return getUserInventoryPartWithStockByUser(user).stream()
                .filter(x -> x.getInventoryParts().getPart().getPartNumber().contains(partNumber))
                .collect(Collectors.toList());
    }
}

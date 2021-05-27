package be.landtlord.legocollection.inventory.inventories.control;

import be.landtlord.legocollection.inventory.inventories.boundary.*;
import be.landtlord.legocollection.inventory.inventories.entity.*;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.InventoryPartsListSearchAnswer;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.PartListSearchAnswer;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.parts.boundary.PartsRepository;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import be.landtlord.legocollection.inventory.sets.entity.Set;
import be.landtlord.legocollection.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InventoryServiceImpTest {
    @InjectMocks
    private InventoryServiceImp inventoryService;

    @Mock
    private PartsRepository partsRepository;

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryPartsRepository inventoryPartsRepository;

    @Mock
    private UserInventorySetsRepository userInventorySetsRepository;

    @Mock
    private UserInventoryPartsRepository userInventoryPartRepository;

    @Mock
    private UserInventoryMinifigureRepository userInventoryMinifigureRepository;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void givenSetNumberAndAPIIsWorking_whenPartsForSetNumberIsInvoked_thenListOfInventoryPartsIsReturned() {
        String setNumber = "setnummer";
        List<InventoryParts> inventoryPartsList = new ArrayList<>();
        InventoryPartsListSearchAnswer inventoryPartsListSearchAnswer = new InventoryPartsListSearchAnswer();
        inventoryPartsListSearchAnswer.setResults(inventoryPartsList);

        when(restTemplate.getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class))).thenReturn(inventoryPartsListSearchAnswer);

        List<InventoryParts> actual = inventoryService.getPartsForSetNumber(setNumber);

        assertThat(actual).isEqualTo(inventoryPartsList);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class));
    }

    @Test
    void givenSetNumberAndAPIIsNotWorking_whenPartsForSetNumberIsInvoked_thenListOfInventoryPartsIsReturned() {
        String setNumber = "setnummer";
        List<InventoryParts> inventoryPartsList = new ArrayList<>();
        InventoryPartsListSearchAnswer inventoryPartsListSearchAnswer = new InventoryPartsListSearchAnswer();
        inventoryPartsListSearchAnswer.setResults(inventoryPartsList);
        Inventory inventory = new Inventory();
        List<Inventory> inventories = List.of(inventory);

        when(restTemplate.getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class))).thenThrow(new RestClientException("fault"));
        when(inventoryRepository.findBySetNumber(setNumber)).thenReturn(inventories);
        when(inventoryPartsRepository.findByInventory(inventory)).thenReturn(inventoryPartsList);

        List<InventoryParts> actual = inventoryService.getPartsForSetNumber(setNumber);

        assertThat(actual).isEqualTo(inventoryPartsList);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class));
    }

    @Test
    void givenMiniFigureNumberAndAPIIsWorking_whenPartsForSetNumberIsInvoked_thenListOfInventoryPartsIsReturned() {
        String miniFigureNumber = "miniFigureNumber";
        List<InventoryParts> inventoryPartsList = new ArrayList<>();
        InventoryPartsListSearchAnswer inventoryPartsListSearchAnswer = new InventoryPartsListSearchAnswer();
        inventoryPartsListSearchAnswer.setResults(inventoryPartsList);

        when(restTemplate.getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class))).thenReturn(inventoryPartsListSearchAnswer);

        List<InventoryParts> actual = inventoryService.getPartsForMiniFigNumber(miniFigureNumber);

        assertThat(actual).isEqualTo(inventoryPartsList);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class));
    }

    @Test
    void givenMiniFigureNumberAndAPIIsNotWorking_whenPartsForSetNumberIsInvoked_thenListOfInventoryPartsIsReturned() {
        String miniFigureNumber = "miniFigureNumber";
        List<InventoryParts> inventoryPartsList = new ArrayList<>();
        InventoryPartsListSearchAnswer inventoryPartsListSearchAnswer = new InventoryPartsListSearchAnswer();
        inventoryPartsListSearchAnswer.setResults(inventoryPartsList);
        Inventory inventory = new Inventory();
        List<Inventory> inventories = List.of(inventory);

        when(restTemplate.getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class))).thenThrow(new RestClientException("fault"));
        when(inventoryRepository.findBySetNumber(miniFigureNumber)).thenReturn(inventories);
        when(inventoryPartsRepository.findByInventory(inventory)).thenReturn(inventoryPartsList);

        List<InventoryParts> actual = inventoryService.getPartsForMiniFigNumber(miniFigureNumber);

        assertThat(actual).isEqualTo(inventoryPartsList);

        verify(restTemplate, times(1)).getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class));
    }

    @Test
    void givenSetNumberAndAPIIsWorking_whenPartsForSetNumberIsInvoked_thenTheUnknownPartsAreSaved() {
        String setNumber = "setnummer";
        InventoryParts inventoryParts = new InventoryParts();
        Part partFromAPI = new Part();
        partFromAPI.setPartNumber("partNumber");
        partFromAPI.setImageUrl("url");
        inventoryParts.setPart(partFromAPI);
        List<InventoryParts> inventoryPartsList = List.of(inventoryParts);
        InventoryPartsListSearchAnswer inventoryPartsListSearchAnswer = new InventoryPartsListSearchAnswer();
        inventoryPartsListSearchAnswer.setResults(inventoryPartsList);

        when(restTemplate.getForObject(anyString(), eq(InventoryPartsListSearchAnswer.class))).thenReturn(inventoryPartsListSearchAnswer);
        when(partsRepository.findByPartNumber("partNumber")).thenReturn(Optional.empty());

        List<InventoryParts> actual = inventoryService.getPartsForSetNumber(setNumber);

        verify(partsRepository, times(1)).save(partFromAPI);
    }

    @Test
    void givenSetAndUser_whenAddToMyListIsInvoked_thenUserInventorySetIsReturned() {

    }

    @Test
    void givenKnownUserInventorySet_whenGetUserInventorySetByIsInvoked_thenUserInventorySetIsReturned() {
        UserInventorySet userInventorySet = new UserInventorySet();
        userInventorySet.setId(1L);

        when(userInventorySetsRepository.findById(1L)).thenReturn(Optional.of(userInventorySet));

        UserInventorySet actual = inventoryService.getUserInventorySetBy("1");

        assertThat(actual).isEqualTo(userInventorySet);
        verify(userInventorySetsRepository, times(1)).findById(1L);
    }

    @Test
    void givenUnknownUserInventorySet_whenGetUserInventorySetByIsInvoked_thenNullIsReturned() {
        when(userInventorySetsRepository.findById(1L)).thenReturn(Optional.empty());

        UserInventorySet actual = inventoryService.getUserInventorySetBy("1");

        assertThat(actual).isNull();
        verify(userInventorySetsRepository, times(1)).findById(1L);
    }

    @Test
    void givenKnownUserInventoryMiniFigure_whenGetUserInventoryMiniFigureByIsInvoked_thenUserInventoryMiniFigureIsReturned() {
        UserInventoryMiniFig userInventorySet = new UserInventoryMiniFig();
        userInventorySet.setId(1L);

        when(userInventoryMinifigureRepository.findById(1L)).thenReturn(Optional.of(userInventorySet));

        UserInventoryMiniFig actual = inventoryService.getUserInventoryMiniFigureBy("1");

        assertThat(actual).isEqualTo(userInventorySet);
        verify(userInventoryMinifigureRepository, times(1)).findById(1L);
    }

    @Test
    void givenUnknownUserInventoryMiniFigure_whenGetUserInventorySetByIsInvoked_thenNullIsReturned() {
        when(userInventoryMinifigureRepository.findById(1L)).thenReturn(Optional.empty());

        UserInventoryMiniFig actual = inventoryService.getUserInventoryMiniFigureBy("1");

        assertThat(actual).isNull();
        verify(userInventoryMinifigureRepository, times(1)).findById(1L);
    }

    @Test
    void givenUseHavingUserInventoryPartWithAndWithoutStock_whenGetUserInventoryPartWithStockByUserIsInvoked_thenTheCorrectListIsReturned(){
        UserInventoryPart userInventoryPartWithStock = getUserInventoryPart(1L,1, "a");
        UserInventoryPart userInventoryPartWithoutStock = getUserInventoryPart(2L,0, "a");
        List<UserInventoryPart> userInventoryPartList = List.of(userInventoryPartWithStock, userInventoryPartWithoutStock);

        when(userInventoryPartRepository.findAllByUser(any())).thenReturn(userInventoryPartList);

        List<UserInventoryPart> actual = inventoryService.getUserInventoryPartWithStockByUser(new User());

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(userInventoryPartWithStock);
    }

    @Test
    void givenUserAndUserInventorySets_whenGetUserInventorySetByUserAndSetNumberContainsIsInvoked_thenTheCorrectListIsReturned(){
        UserInventorySet userInventorySet = getUserInventorySet("a12345");
        UserInventorySet userInventorySet2 = getUserInventorySet("b12345");
        List<UserInventorySet> userInventorySetList = List.of(userInventorySet, userInventorySet2);

        when(userInventorySetsRepository.findAllByUser(any())).thenReturn(userInventorySetList);

        List<UserInventorySet> actual = inventoryService.getUserInventorySetByUserAndSetNumberContains(new User(), "a");

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(userInventorySet);
    }

    private UserInventorySet getUserInventorySet(String setNumber) {
        UserInventorySet userInventorySet = new UserInventorySet();
        Inventory inventory = new Inventory();
        inventory.setSetNumber(setNumber);
        userInventorySet.setInventory(inventory);
        return userInventorySet;
    }

    @Test
    void givenUserAndUserInventoryMiniFigures_whenGetUserInventoryMiniFiguresByUserAndMiniFiguresNumberContainsIsInvoked_thenTheCorrectListIsReturned(){
        UserInventoryMiniFig userInventorySet = getUserInventoryMiniFig("a12345");
        UserInventoryMiniFig userInventorySet2 = getUserInventoryMiniFig("b12345");
        List<UserInventoryMiniFig> userInventorySetList = List.of(userInventorySet, userInventorySet2);

        when(userInventoryMinifigureRepository.findAllByUser(any())).thenReturn(userInventorySetList);

        List<UserInventoryMiniFig> actual = inventoryService.getUserInventoryMiniFigureByUserAndFigNumberContains(new User(), "a");

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(userInventorySet);
    }

    private UserInventoryMiniFig getUserInventoryMiniFig(String setNumber) {
        UserInventoryMiniFig userInventorySet = new UserInventoryMiniFig();
        Inventory inventory = new Inventory();
        inventory.setSetNumber(setNumber);
        userInventorySet.setInventory(inventory);
        return userInventorySet;
    }

    @Test
    void givenUserAndUserInventoryParts_whenGetUserInventoryPartsByUserAndPartsNumberContainsIsInvoked_thenTheCorrectListIsReturned(){
        UserInventoryPart userInventoryPart = getUserInventoryPart(1L,1, "a1234");
        UserInventoryPart userInventoryPart1 = getUserInventoryPart(2L,1, "b1234");
        List<UserInventoryPart> userInventoryPartList = List.of(userInventoryPart, userInventoryPart1);

        when(userInventoryPartRepository.findAllByUser(any())).thenReturn(userInventoryPartList);

        List<UserInventoryPart> actual = inventoryService.getUserInventoryPartByUserAndPartNumberContains(new User(), "a");

        assertThat(actual).isNotEmpty();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual.get(0)).isEqualTo(userInventoryPart);
    }

    private UserInventoryPart getUserInventoryPart(Long id, int quantity,String partNumber) {
        UserInventoryPart userInventoryPart = new UserInventoryPart();
        userInventoryPart.setId(id);
        userInventoryPart.setQuantity(quantity);
        InventoryParts inventoryParts = new InventoryParts();
        Part part = new Part();
        part.setPartNumber(partNumber);
        inventoryParts.setPart(part);
        userInventoryPart.setInventoryParts(inventoryParts);
        return userInventoryPart;
    }

    @Test
    void  givenSetAndUser_whenAddToMyListIsInvoked_thenUserInventorySetISReturned(){
        User user = new User();
        user.setId(1L);
        Set set = new Set();
        set.setSetNumber("1");

        Inventory inventory = new Inventory();
        inventory.setSetNumber("1");

        UserInventorySet userInventorySet = new UserInventorySet();
        userInventorySet.setInventory(inventory);
        userInventorySet.setUser(user);

        when(inventoryRepository.findBySetNumber("1")).thenReturn(List.of(inventory));
        when(userInventorySetsRepository.save(any(UserInventorySet.class))).thenReturn(userInventorySet);
        when(inventoryPartsRepository.findByInventory(inventory)).thenReturn(Collections.emptyList());

        UserInventorySet actual = inventoryService.addToMyList(set, user);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(userInventorySet);

        verify(userInventorySetsRepository, times(1)).save(any(UserInventorySet.class));
    }

    @Test
    void  givenSetWith2PartsAndUser_whenAddToMyListIsInvoked_thenSavedUserInventoryPartsIsInvoked2Times(){
        User user = new User();
        user.setId(1L);
        Set set = new Set();
        set.setSetNumber("1");

        Inventory inventory = new Inventory();
        inventory.setSetNumber("1");

        UserInventorySet userInventorySet = new UserInventorySet();
        userInventorySet.setInventory(inventory);
        userInventorySet.setUser(user);

        InventoryParts part1 = new InventoryParts();
        InventoryParts part2 = new InventoryParts();

        when(inventoryRepository.findBySetNumber("1")).thenReturn(List.of(inventory));
        when(userInventorySetsRepository.save(any(UserInventorySet.class))).thenReturn(userInventorySet);
        when(inventoryPartsRepository.findByInventory(inventory)).thenReturn(List.of(part1, part2));

        inventoryService.addToMyList(set, user);

        verify(userInventoryPartRepository, times(2)).save(any(UserInventoryPart.class));
    }

    @Test
    void  givenMiniFigureAndUser_whenAddToMyListIsInvoked_thenUserInventoryMiniFigureISReturned(){
        User user = new User();
        user.setId(1L);
        MiniFigure miniFigure = new MiniFigure();
        miniFigure.setMiniFigureNumber("1");

        Inventory inventory = new Inventory();
        inventory.setSetNumber("1");

        UserInventoryMiniFig userInventoryMiniFig = new UserInventoryMiniFig();
        userInventoryMiniFig.setInventory(inventory);
        userInventoryMiniFig.setUser(user);

        when(inventoryRepository.findBySetNumber("1")).thenReturn(List.of(inventory));
        when(userInventoryMinifigureRepository.save(any(UserInventoryMiniFig.class))).thenReturn(userInventoryMiniFig);
        when(inventoryPartsRepository.findByInventory(inventory)).thenReturn(Collections.emptyList());

        UserInventoryMiniFig actual = inventoryService.addToMyList(miniFigure, user);

        assertThat(actual).isNotNull();
        assertThat(actual).isEqualTo(userInventoryMiniFig);

        verify(userInventoryMinifigureRepository, times(1)).save(any(UserInventoryMiniFig.class));
    }

    @Test
    void  givenMiniFigureWith2PartsAndUser_whenAddToMyListIsInvoked_thenSavedUserInventoryPartsIsInvoked2Times(){
        User user = new User();
        user.setId(1L);
        MiniFigure miniFigure = new MiniFigure();
        miniFigure.setMiniFigureNumber("1");

        Inventory inventory = new Inventory();
        inventory.setSetNumber("1");

        UserInventoryMiniFig userInventoryMiniFig = new UserInventoryMiniFig();
        userInventoryMiniFig.setInventory(inventory);
        userInventoryMiniFig.setUser(user);

        InventoryParts part1 = new InventoryParts();
        InventoryParts part2 = new InventoryParts();

        when(inventoryRepository.findBySetNumber("1")).thenReturn(List.of(inventory));
        when(userInventoryMinifigureRepository.save(any(UserInventoryMiniFig.class))).thenReturn(userInventoryMiniFig);
        when(inventoryPartsRepository.findByInventory(inventory)).thenReturn(List.of(part1, part2));

        inventoryService.addToMyList(miniFigure, user);

        verify(userInventoryPartRepository, times(2)).save(any(UserInventoryPart.class));
    }


    /*
    The following test doesn't really test business logic, but only test if the correct calls to the database are made
     */

    @Test
    void getUserInventorySetByUser() {
        User user = new User();
        inventoryService.getUserInventorySetByUser(user);

        verify(userInventorySetsRepository, times(1)).findAllByUser(user);
    }

    @Test
    void getUserInventoryMiniFigureByUser() {
        User user = new User();
        inventoryService.getUserInventoryMiniFigureByUser(user);

        verify(userInventoryMinifigureRepository, times(1)).findAllByUser(user);
    }

    @Test
    void getUserInventoryPartsByUserInventorySet() {
        UserInventorySet userInventorySet = new UserInventorySet();
        inventoryService.getUserInventoryPartsByUserInventorySet(userInventorySet);

        verify(userInventoryPartRepository, times(1)).findAllByUserInventorySet(userInventorySet);
    }

    @Test
    void getUserInventoryPartsByUserInventoryMiniFigure() {
        UserInventoryMiniFig userInventoryMiniFig = new UserInventoryMiniFig();
        inventoryService.getUserInventoryPartsByUserInventoryMiniFigure(userInventoryMiniFig);

        verify(userInventoryPartRepository, times(1)).findAllByUserInventoryMiniFig(userInventoryMiniFig);
    }

    @Test
    void updateUserInventoryPart() {
        UserInventoryPart userInventoryPart = new UserInventoryPart();
        inventoryService.updateUserInventoryPart(userInventoryPart);

        verify(userInventoryPartRepository, times(1)).save(userInventoryPart);
    }
}

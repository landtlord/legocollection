package be.landtlord.legocollection.inventory.parts.control;

import be.landtlord.legocollection.inventory.inventories.entity.apianswer.PartListSearchAnswer;
import be.landtlord.legocollection.inventory.parts.boundary.PartsRepository;
import be.landtlord.legocollection.inventory.parts.entity.Part;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PartsServiceImplTest {
    @InjectMocks
    private PartsServiceImpl partService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PartsRepository partsRepository;

    @Test
    void givenTheAPIIsOnline_whenFindByPartNumberContainsIsInvoked_thenTheCorrectListIsReturned() {
        List<Part> partList = new ArrayList();
        PartListSearchAnswer partListSearchAnswer = new PartListSearchAnswer();
        partListSearchAnswer.setResults(partList);

        when(restTemplate.getForObject(anyString(), eq(PartListSearchAnswer.class))).thenReturn(partListSearchAnswer);

        List<Part> actual = partService.finByPartNumberContains("any");

        assertThat(actual).isEqualTo(partList);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(PartListSearchAnswer.class));
    }

    @Test
    void givenTheAPIIsOffline_whenFindByPartNumberContainsIsInvoked_thenTheCorrectListFromTheDatabaseIsReturned() {
        List<Part> partList = new ArrayList();

        when(restTemplate.getForObject(anyString(), eq(PartListSearchAnswer.class))).thenThrow(new RestClientException("fault"));
        when(partsRepository.findByPartNumberContains("any")).thenReturn(partList);

        List<Part> actual = partService.finByPartNumberContains("any");

        assertThat(actual).isEqualTo(partList);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(PartListSearchAnswer.class));
        verify(partsRepository, times(1)).findByPartNumberContains("any");
    }

    @Test
    void givenAKnownPartNumberWithImage_whenGetPartByIsInvoked_thenThePartFromTheDatabaseIsReturned() {
        Part part = new Part();
        part.setImageUrl("url");
        part.setPartNumber("PartNumber");

        when(partsRepository.findById("PartNumber")).thenReturn(Optional.of(part));

        Part actual = partService.getPartBy("PartNumber");

        assertThat(actual).isEqualTo(part);
        verify(restTemplate, times(0)).getForObject(anyString(), eq(Part.class));
        verify(partsRepository, times(1)).findById("PartNumber");
    }

    @Test
    void givenAKnownSetNumberWithoutImage_whenGetSetByIsInvoked_thenTheSetFromTheDatabaseIsReturned() {
        Part part = new Part();
        part.setImageUrl(null);
        part.setPartNumber("PartNumber");

        Part partFromAPI = new Part();
        partFromAPI.setImageUrl("url");
        partFromAPI.setPartNumber("PartNumber");

        when(partsRepository.findById("PartNumber")).thenReturn(Optional.of(part));
        when(restTemplate.getForObject(anyString(), eq(Part.class))).thenReturn(partFromAPI);

        Part actual = partService.getPartBy("PartNumber");

        assertThat(actual).isEqualTo(part);
        assertThat(actual.getImageUrl()).isEqualTo("url");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Part.class));
        verify(partsRepository, times(1)).findById("PartNumber");
    }

    @Test
    void givenAUnknownSetNumber_whenGetSetByIsInvoked_thenTheSetFromTheApiIsReturned() {
        Part partFromAPI = new Part();
        partFromAPI.setImageUrl("url");
        partFromAPI.setPartNumber("PartNumber");

        when(restTemplate.getForObject(anyString(), eq(Part.class))).thenReturn(partFromAPI);
        when(partsRepository.save(partFromAPI)).thenReturn(partFromAPI);

        Part actual = partService.getPartBy("PartNumber");

        assertThat(actual).isEqualTo(partFromAPI);
        assertThat(actual.getImageUrl()).isEqualTo("url");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Part.class));
        verify(partsRepository, times(1)).findById("PartNumber");
    }

}

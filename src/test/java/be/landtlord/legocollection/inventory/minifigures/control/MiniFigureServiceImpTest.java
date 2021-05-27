package be.landtlord.legocollection.inventory.minifigures.control;

import be.landtlord.legocollection.inventory.inventories.entity.apianswer.MiniFigureListSearchAnswer;
import be.landtlord.legocollection.inventory.inventories.entity.apianswer.SetListSearchAnswer;
import be.landtlord.legocollection.inventory.minifigures.boundary.MiniFiguresRepository;
import be.landtlord.legocollection.inventory.minifigures.entity.MiniFigure;
import be.landtlord.legocollection.inventory.sets.entity.Set;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MiniFigureServiceImpTest {
    @InjectMocks
    private MiniFigureServiceImp miniFigureService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private MiniFiguresRepository miniFigureRepository;

    @Test
    void givenTheAPIIsOnline_whenFindByMiniFigureNumberContainsIsInvoked_thenTheCorrectListIsReturned() {
        List<MiniFigure> miniFigureList = new ArrayList();
        MiniFigureListSearchAnswer miniFigureListSearchAnswer = new MiniFigureListSearchAnswer();
        miniFigureListSearchAnswer.setResults(miniFigureList);

        when(restTemplate.getForObject(anyString(), eq(MiniFigureListSearchAnswer.class))).thenReturn(miniFigureListSearchAnswer);

        List<MiniFigure> actual = miniFigureService.findByMiniFigureNumberContains("any");

        assertThat(actual).isEqualTo(miniFigureList);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(MiniFigureListSearchAnswer.class));
    }

    @Test
    void givenTheAPIIsOffline_whenFindByMiniFigureNumberContainsIsInvoked_thenTheCorrectListFromTheDatabaseIsReturned() {
        List<MiniFigure> miniFigureList = new ArrayList();

        when(restTemplate.getForObject(anyString(), eq(MiniFigureListSearchAnswer.class))).thenThrow(new RestClientException("fault"));
        when(miniFigureRepository.findAllByMiniFigureNumberContains("any")).thenReturn(miniFigureList);

        List<MiniFigure> actual = miniFigureService.findByMiniFigureNumberContains("any");

        assertThat(actual).isEqualTo(miniFigureList);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(MiniFigureListSearchAnswer.class));
        verify(miniFigureRepository, times(1)).findAllByMiniFigureNumberContains("any");
    }

    @Test
    void givenAKnownMiniFigureNumberWithImage_whenGetMiniFigureByIsInvoked_thenTheMiniFigureFromTheDatabaseIsReturned() {
        MiniFigure miniFigure = new MiniFigure();
        miniFigure.setImageUrl("url");
        miniFigure.setMiniFigureNumber("MiniFigureNumber");

        when(miniFigureRepository.findById("MiniFigureNumber")).thenReturn(Optional.of(miniFigure));

        MiniFigure actual = miniFigureService.getMiniFigureBy("MiniFigureNumber");

        assertThat(actual).isEqualTo(miniFigure);
        verify(restTemplate, times(0)).getForObject(anyString(), eq(MiniFigure.class));
        verify(miniFigureRepository, times(1)).findById("MiniFigureNumber");
    }

    @Test
    void givenAKnownMiniFigureNumberWithoutImage_whenGetMiniFigureByIsInvoked_thenTheMiniFigureFromTheDatabaseIsReturned() {
        MiniFigure miniFigure = new MiniFigure();
        miniFigure.setImageUrl(null);
        miniFigure.setMiniFigureNumber("MiniFigureNumber");

        MiniFigure miniFigureFromAPI = new MiniFigure();
        miniFigureFromAPI.setImageUrl("url");
        miniFigureFromAPI.setMiniFigureNumber("MiniFigureNumber");

        when(miniFigureRepository.findById("MiniFigureNumber")).thenReturn(Optional.of(miniFigure));
        when(restTemplate.getForObject(anyString(), eq(MiniFigure.class))).thenReturn(miniFigureFromAPI);

        MiniFigure actual = miniFigureService.getMiniFigureBy("MiniFigureNumber");

        assertThat(actual).isEqualTo(miniFigure);
        assertThat(actual.getImageUrl()).isEqualTo("url");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(MiniFigure.class));
        verify(miniFigureRepository, times(1)).findById("MiniFigureNumber");
    }

    @Test
    void givenAUnknownMiniFigureNumber_whenGetMiniFigureByIsInvoked_thenTheMiniFigureFromTheApiIsReturned() {
        MiniFigure miniFigureFromAPI = new MiniFigure();
        miniFigureFromAPI.setImageUrl("url");
        miniFigureFromAPI.setMiniFigureNumber("MiniFigureNumber");

        when(restTemplate.getForObject(anyString(), eq(MiniFigure.class))).thenReturn(miniFigureFromAPI);
        when(miniFigureRepository.save(miniFigureFromAPI)).thenReturn(miniFigureFromAPI);

        MiniFigure actual = miniFigureService.getMiniFigureBy("MiniFigureNumber");

        assertThat(actual).isEqualTo(miniFigureFromAPI);
        assertThat(actual.getImageUrl()).isEqualTo("url");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(MiniFigure.class));
        verify(miniFigureRepository, times(1)).findById("MiniFigureNumber");
    }

}

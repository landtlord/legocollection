package be.landtlord.legocollection.inventory.sets.control;

import be.landtlord.legocollection.inventory.inventories.entity.apianswer.SetListSearchAnswer;
import be.landtlord.legocollection.inventory.sets.boundary.SetRepository;
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

@ExtendWith(MockitoExtension.class)
class SetServiceImplTest {
    @InjectMocks
    private SetServiceImpl setService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private SetRepository setRepository;

    @Test
    void givenTheAPIIsOnline_whenFindBySetNumberContainsIsInvoked_thenTheCorrectListIsReturned() {
        List<Set> setList = new ArrayList();
        SetListSearchAnswer setListSearchAnswer = new SetListSearchAnswer();
        setListSearchAnswer.setResults(setList);

        when(restTemplate.getForObject(anyString(), eq(SetListSearchAnswer.class))).thenReturn(setListSearchAnswer);

        List<Set> actual = setService.findBySetNumberContains("any");

        assertThat(actual).isEqualTo(setList);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(SetListSearchAnswer.class));
    }

    @Test
    void givenTheAPIIsOffline_whenFindBySetNumberContainsIsInvoked_thenTheCorrectListFromTheDatabaseIsReturned() {
        List<Set> setList = new ArrayList();

        when(restTemplate.getForObject(anyString(), eq(SetListSearchAnswer.class))).thenThrow(new RestClientException("fault"));
        when(setRepository.findAllBySetNumberContains("any")).thenReturn(setList);

        List<Set> actual = setService.findBySetNumberContains("any");

        assertThat(actual).isEqualTo(setList);
        verify(restTemplate, times(1)).getForObject(anyString(), eq(SetListSearchAnswer.class));
        verify(setRepository, times(1)).findAllBySetNumberContains("any");
    }

    @Test
    void givenAKnownSetNumberWithImage_whenGetSetByIsInvoked_thenTheSetFromTheDatabaseIsReturned() {
        Set set = new Set();
        set.setImageUrl("url");
        set.setSetNumber("setNumber");

        when(setRepository.findById("setNumber")).thenReturn(Optional.of(set));

        Set actual = setService.getSetBy("setNumber");

        assertThat(actual).isEqualTo(set);
        verify(restTemplate, times(0)).getForObject(anyString(), eq(Set.class));
        verify(setRepository, times(1)).findById("setNumber");
    }

    @Test
    void givenAKnownSetNumberWithoutImage_whenGetSetByIsInvoked_thenTheSetFromTheDatabaseIsReturned() {
        Set set = new Set();
        set.setImageUrl(null);
        set.setSetNumber("setNumber");

        Set setFromAPI = new Set();
        setFromAPI.setImageUrl("url");
        setFromAPI.setSetNumber("setNumber");

        when(setRepository.findById("setNumber")).thenReturn(Optional.of(set));
        when(restTemplate.getForObject(anyString(), eq(Set.class))).thenReturn(setFromAPI);

        Set actual = setService.getSetBy("setNumber");

        assertThat(actual).isEqualTo(set);
        assertThat(actual.getImageUrl()).isEqualTo("url");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Set.class));
        verify(setRepository, times(1)).findById("setNumber");
    }

    @Test
    void givenAUnknownSetNumber_whenGetSetByIsInvoked_thenTheSetFromTheApiIsReturned() {
        Set setFromAPI = new Set();
        setFromAPI.setImageUrl("url");
        setFromAPI.setSetNumber("setNumber");

        when(restTemplate.getForObject(anyString(), eq(Set.class))).thenReturn(setFromAPI);
        when(setRepository.save(setFromAPI)).thenReturn(setFromAPI);

        Set actual = setService.getSetBy("setNumber");

        assertThat(actual).isEqualTo(setFromAPI);
        assertThat(actual.getImageUrl()).isEqualTo("url");
        verify(restTemplate, times(1)).getForObject(anyString(), eq(Set.class));
        verify(setRepository, times(1)).findById("setNumber");
    }
}

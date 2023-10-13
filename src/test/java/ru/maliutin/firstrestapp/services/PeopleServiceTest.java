package ru.maliutin.firstrestapp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.maliutin.firstrestapp.models.Person;
import ru.maliutin.firstrestapp.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {
    @Mock
    private PeopleRepository peopleRepository;
    @InjectMocks
    private PeopleService peopleService;

    @Test
    public void findOneShouldReturnPerson(){
        when(peopleRepository.findById(1)).thenReturn(Optional.of(new Person("name", 25)));
        Person person = peopleService.findOne(1);
        verify(peopleRepository).findById(1);
        assertNotNull(person);
    }

    @Test
    public void findOneShouldExceptionWhenPersonNotFound(){
        when(peopleRepository.findById(2)).thenReturn(Optional.empty());
        Exception expectException = assertThrows(IllegalArgumentException.class, () -> peopleService.findOne(2));
        String textException = "Человек с таким id не найден";
        assertEquals(textException, expectException.getMessage());
    }

    @Test
    public void findAllShouldReturnListPeople(){
        when(peopleRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new Person("Person1", 14),
                new Person("Person2", 25)
        )));
        List <Person> listPeople = peopleService.findAll();
        int expectSizeList = 2;
        assertEquals(expectSizeList, listPeople.size());
    }
}
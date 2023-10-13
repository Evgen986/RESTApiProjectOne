package ru.maliutin.firstrestapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.maliutin.firstrestapp.models.Person;
import ru.maliutin.firstrestapp.services.PeopleService;
import ru.maliutin.firstrestapp.util.PersonErrorResponse;
import ru.maliutin.firstrestapp.util.PersonNotFoundException;

import java.util.List;

@RestController  // Аннотация совмещающая в себе (@Controller + @ResponseBody), т.е. методы контроллера отдают JSON
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public List<Person> getPeople() {
        return peopleService.findAll(); // Jackson автоматически конвертирует лист объектов в JSON
    }

    /*
        Именно в данном методе при вызове метода findOne(id) класса PeopleService
        может быть выброшено исключение PersonNotFoundException при отсутствии человека
        и данное исключение будет перехвачено методом с аннотацией @ExceptionHandler
     */

    @GetMapping("/{id}")
    public Person getPerson(@PathVariable("id") int id) {
        return peopleService.findOne(id);  // Jackson автоматически конвертирует объект Person в JSON
    }

    /*
        Метод помеченный аннотацией @ExceptionHandler будет вызван если какой-либо из методов данного
        контроллера выбросит исключение типа PersonNotFoundException т.к. именно данный метод настроен
        на обработку данного исключения и принимает его в аргументах метода
     */
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        /*  В случае исключения создаем объект класса PersonErrorResponse и заполняем его поля
            используя конструктор
         */
        PersonErrorResponse response = new PersonErrorResponse(
                "Человек с таким id не найден", System.currentTimeMillis());
        // Возвращаем JSON ответ используя класс ResponseEntity, создаем его объект и в его конструктор
        // передаем объект класса PersonErrorResponse, который будет использован Jackson для сериализации JSON ответа,
        // а также передаем статус ответа, в данном случае 404 - страница не найдена.
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // NOT_FOUND - 404 ошибка
    }
}

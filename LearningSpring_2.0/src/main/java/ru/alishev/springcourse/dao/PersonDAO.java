package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    /** ID человека */
    private static int PEOPLE_COUNT;

    /** Список всех людей */
    private List<Person> people = new ArrayList<>();;
    {
        people.add(new Person(++PEOPLE_COUNT, "Tom"));
        people.add(new Person(++PEOPLE_COUNT, "Bob"));
        people.add(new Person(++PEOPLE_COUNT, "Mike"));
        people.add(new Person(++PEOPLE_COUNT, "Katy"));
    }

    /** Список всех людей */
    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        // .orElse(null) - если не нашел нужного человека, то возвращаем null
        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }
}

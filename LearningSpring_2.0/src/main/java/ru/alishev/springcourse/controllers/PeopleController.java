package ru.alishev.springcourse.controllers;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alishev.springcourse.dao.PersonDAO;
import ru.alishev.springcourse.models.Person;

@Slf4j // логирование для эксепшена
@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    @Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()   // будет переходить сюда при - 8080/people
    public String index(Model model) {
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}") // - /любое число
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

////////////////////// Добавление пользователей
    /** Возвращает html-форму на данный запрос. Через таймлиф(отправится объект) */
    @GetMapping("/newModelAttribute") // если используем таймлиф, то ему нужно передать тот объект, для которого эта форма нужна
    public String newPerson(@ModelAttribute("person") Person person) { // то же самое, что и нижний метод, ибо в обоих случаях создастся новый пустой объект
        return "people/newThymeleaf";
    }

    @GetMapping("/newPerson")
    public String newPerson(Model model) { // если используем таймлиф, то ему нужно передать тот объект, для которого эта форма нужна
        model.addAttribute("person", new Person());
        return "people/newThymeleaf";
    }

    /*  Отправка ту же анкету без таймлифа  */
    @GetMapping("/newNoThymeleaf")
    public String newPerson() { // если используем таймлиф, то ему нужно передать тот объект, для которого эта форма нужна
        return "people/newNoThymeleaf";
    }
///////////////////

    /** Берет данные из запроса и добавляет в базу новый объект с помощью DAO. Если отправляем POST через таймлиф */
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, // @Valid проверяет есть ли ошибка в данных по тем условиям, что помечены над объявлением полей
                         BindingResult bindingResult) { // если выпала ошибка, то она запишется в этот класс
        if (bindingResult.hasErrors()) return "people/newThymeleaf"; // если есть ошибка
        System.out.println("------------- Зашел в @PostMapping()");
        personDAO.save(person);
        return "redirect:/people"; // "redirect:" говорит браузеру перейти на другую страницу
    }

    /** Берет данные из запроса и добавляет в базу новый объект с помощью DAO. Если отправляем POST без таймлиф */
    @PostMapping("/POSTNoThymeleaf")
    public String createNewNoThymeleaf(@RequestParam("name") String name, Model model) {
        Person person = new Person();
        person.setName(name);
        personDAO.save(person);
        model.addAttribute("person", person);
        return "redirect:/people"; // "redirect:" говорит браузеру перейти на другую страницу
    }


    /** Редактирование человека */
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    /** Принимает запрос на редактирование человека */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id) {
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/people";
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public String handle(IllegalArgumentException e) {
        log.error("Exception controller: " + e.getMessage());
        return "Вылетел эксепшен в классе: " + this.getClass();
    }
}

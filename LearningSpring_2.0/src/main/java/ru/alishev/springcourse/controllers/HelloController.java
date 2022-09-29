package ru.alishev.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/** Запускаем MVC через XML-файл */
@Controller // создаем контроллер для XML и JAVA-class
public class HelloController {

    @GetMapping("/hello-world") // какой URL будет приходить в этот метод контроллера(по какому адресу должен перейти клиент)
    public String sayHello() {
        return "hello_world"; // Согласно Spring-MVC, метод контроллера должен вернуть имя представления(в appCont мы указали адрес "/WEB-INF/views/")
    }

    @GetMapping("/bay-world") // по сути ловит GET-запрос по HTTP от клиента
    public String sayBay() {
        return "bay_world";
    }

    @GetMapping("/HttpParametr") // @RequestParam - говорит о том, что параметр String name - является параметром url. В скобках аннотации указано, что данный параметр в url является не обязательным (required = false), в случае его отсутствия, значением параметра String name будет World (defaultValue = "World")
    public String HttpParametr(@RequestParam(name = "name", required = false, defaultValue = "Param") String name, Model model) {
        model.addAttribute("name", name); // пара ключ-значение
        return "httpParametr";
    }

    @GetMapping("/HttpParametr2")
    public String HttpParametr2(HttpServletRequest request, Model model) { // удобно, когда много параметров
        String name = request.getParameter("name");
        String id = request.getParameter("id");
        model.addAttribute("message", "Пришел GET- запрос на /HttpParametr2:  name = " + name + ", id = " + id);
        System.out.println("Пришел GET- запрос на /HttpParametr2:  name = " + name + ", id = " + id);
        return "httpParametr2";
    }
}

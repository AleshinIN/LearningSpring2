package ru.alishev.springcourse.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.alishev.springcourse.config.SpringConfig;

/** Заменяет web.xml(по сути создаем сервлеты).
 *    SpringConfig.class заменят аппКонтекст(важно создать бины для маршрутизатора таймлиф)
 *    Для работы абстрактного класса следует подгрузить библиотеку javax.servlet-api(для взаимодействия с рабочим процессом запроса/ответа.) */
public class MySpringMvcDispatcherSerlvetIntitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class}; // указываем класс конфигурации, который будет заменять аппКонтекст
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"}; //посылаем все запросы на диспетчер сервлет
    }
}

package ru.alishev.springcourse.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import ru.alishev.springcourse.config.SpringConfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

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

    /** При старте добавляем фильтр(см.ниже) */
    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerHiddenFieldFilter(aServletContext);
    }

    /** Фильтр, который будет ловить каждый http запрос и читать скрытое поле, в котором лежит реальный тип запроса(а не POST).
     * Далее он перенаправляет на правильный контроллер. В springBoot будет более удобно */
    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }
}

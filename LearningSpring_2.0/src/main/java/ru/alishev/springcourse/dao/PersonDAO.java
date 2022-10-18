package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.alishev.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    /** ID человека */
    private static int PEOPLE_COUNT;


    /*   Подключение к БД   */
    private static final String URL = "jdbc:postgresql://localhost:5432/People";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "75568489";

    /** Соединение по JDBC к БД  */
    private static Connection connection;


    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /** Список всех людей */
    public List<Person> index() {

        ArrayList<Person> people1 = new ArrayList<>();

        try {
            Statement statement = connection.createStatement(); // содержит SQL запрос
            String SQL = "SELECT * FROM person ORDER BY id";
            System.out.println("Выполняем запрос к БК для получения всех пользователей:");
            ResultSet resultSet = statement.executeQuery(SQL); // выполняется запрос к БД и возвращает строки, которые придется парсить

            while (resultSet.next()) {
//                System.out.println("    id = " + resultSet.getInt("id") + ", name = " + resultSet.getString("name"));
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people1.add(person);

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people1;
    }

    public Person show(int id) {
        // .orElse(null) - если не нашел нужного человека, то возвращаем null
//        return people.stream().filter(person -> person.getId() == id).findAny().orElse(null);

        Person person = new Person();
        try {
            Statement statement = connection.createStatement(); // содержит SQL запрос
            String SQL = "SELECT * FROM Person WHERE id=" + id;
            System.out.println("Выполняем запрос к БК для id = " + id);
            ResultSet resultSet = statement.executeQuery(SQL); // выполняется запрос к БД и возвращает строки, которые придется парсить
            if (!resultSet.next()) {
                System.out.println("Отсутствует элемент. Вызываю исключение");
                throw new RuntimeException();
            }

            System.out.println("    id = " + resultSet.getInt("id") + ", name = " + resultSet.getString("name"));
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Вылетел эксепшен в Person.show");
        }
        return person;
    }

    public void save(Person person) {
        try {
            Statement statement = connection.createStatement();

            String SQL = "INSERT INTO Person VALUES ((SELECT max(id) FROM person)+1 " +
                    ",'" + person.getName() +
                    "'," + person.getAge() +
                    ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(int id, Person updatedPerson) {
//        Person personToBeUpdated = show(id);
//
//        personToBeUpdated.setName(updatedPerson.getName());

        try {
            Statement statement = connection.createStatement();

            String SQL = "UPDATE Person SET " +
                    "name='" + updatedPerson.getName() +
                    "', age=" + updatedPerson.getAge() +
                    ", email='" + updatedPerson.getEmail() +
                    "' WHERE id=" + id;

            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void delete(int id) {
        try {
            Statement statement = connection.createStatement();
            String SQL = "DELETE FROM person WHERE id=" + id;
            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

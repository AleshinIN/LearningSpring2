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

        ArrayList<Person> people = new ArrayList<>();

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
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return people;
    }

    public Person show(int id) {
        Person person1 = null;
        try {
            PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?"); // содержит SQL запрос
            prepStatement.setInt(1, id);
            ResultSet resultSet = prepStatement.executeQuery(); // смотрим, какие данные вернулись

            if (!resultSet.next()) {
                System.out.println("Отсутствует элемент. Вызываю исключение");
                throw new RuntimeException();
            }

            person1 = new Person();
            System.out.println("    id = " + resultSet.getInt("id") + ", name = " + resultSet.getString("name"));
            person1.setId(resultSet.getInt("id"));
            person1.setName(resultSet.getString("name"));
            person1.setAge(resultSet.getInt("age"));
            person1.setEmail(resultSet.getString("email"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Вылетел эксепшен в Person.show");
        }
        return person1;
    }

    public void save(Person person) {
        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "INSERT INTO person (id, name, age, email) VALUES((SELECT max(id) FROM person)+1, ?, ?, ?)"); // содержит SQL запрос
            prepStatement.setString(1, person.getName());
            prepStatement.setInt(2, person.getAge());
            prepStatement.setString(3, person.getEmail());
            prepStatement.executeUpdate(); // выполняет запрос к БД

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void update(int id, Person updatedPerson) {

        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "UPDATE Person SET name=?, age=?, email=?  WHERE id=?"); // содержит SQL запрос
            prepStatement.setString(1, updatedPerson.getName());
            prepStatement.setInt(2, updatedPerson.getAge());
            prepStatement.setString(3, updatedPerson.getEmail());
            prepStatement.setInt(4, id);
            prepStatement.executeUpdate(); // выполняет запрос к БД

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void delete(int id) {
        try {
            PreparedStatement prepStatement = connection.prepareStatement(
                    "DELETE FROM Person WHERE id=?"); // содержит SQL запрос
            prepStatement.setInt(1, id);
            prepStatement.executeUpdate(); // выполняет запрос к БД

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

/////SQL - язык структурированных запросов
Способы связи java с БД:
    JDBC API     - сами делаем все запросы к БД и сами переводим java объекты в строки таблицы и наоборот
    JdbcTemplate - небольшая обертка вокруг JDBC API. Часть SpringFramework, который берет часть дел на себя.
    Hibernate    - практически не пишем вручную запросы к БД. Большая часть процессов автоматизирована(ORM)

В java можно прописывать SQL команды в String, потом передать его в:
    Statement - позволяет сделать SQL-запрос(возможна SQL-инъекция)
    PreparedStatement - позволяет сделать SQL-запрос(SQL-инъекция не возможна), помимо этого,
        SQL команда компилируется один раз, а потом используется, это увеличивает производительность для множества запросов
    Их основные методы:
        .executeQuery()  - для запросов, которые что-то возвращают(принимаем в ResultSet)
        .executeUpdate() - выполняем запрос для внесения данных в БД(ничего не возвращает)

DDL - команды для создания таблиц и схем
    CREATE DATABASE bd_name;   - создать БД
    DROP DATABASE bd_name;     - удаляет БД
    CREATE TABLE table_name{}; - создает таблицу
    DROP TABLE table_name;     - удаляет таблицу

DML - команды для работы с данными в таблице
    SELECT id, name FROM table_name;                - выбираем конкретные столбцы
    SELECT * FROM table_name;                       - выбираем все столбцы из таблицы
    INSERT INTO Person(id, name) VALUES (1, 'Tom'); - добавляет строки в таблицу
    UPDATE Person SET name='Tom123' WHERE id=1;     - изменяем содержимое ячейки name, для которой значение столбца id=1
    DELETE FROM Person WHERE id=1;                  - удаляем строку с id=1

INSERT - добавить
SELECT - запрос данных. Он состоит из нескольких частей: выборки (в которой перечисляются столбцы),
    списка таблиц и необязательного условия (определяющего ограничения).
FROM - из
INTO - в
WHERE - указывается логическое выражение, которое служит фильтром строк. Операторы AND(и), OR(или) и NOT(отрицание).
DISTINCT - Убирает дублирование строки в таблице или строке
ORDER BY - сортировать вывод по определенным столбцам
JOIN/ON - внутреннее соединение(таблиц). Выводит объединенную таблицу
LEFT OUTER JOIN/ON - внешнее соединение таблиц. Из таблицы в левой части оператора будут выбраны все строки,
    а из таблицы справа только те, которые удалось сопоставить каким-нибудь строкам из левой, остальные NULL
GROUP BY - позволяет группировать результаты при выборке из базы данных.
          К сгруппированным результатам можно применять любые функции
Агрегатные функции, вычисляют одно значение: count (количество), sum (сумму), avg (среднее), max (максимум) и min (минимум).
    Условие WHERE вычисляется до агрегатных функций, но можно использовать подзапрос.
HAVING - используется для агрегатных функций, позволяет сначала сгруппировать, а потом вычислять функцию.
COUNT - подсчитывает количество записей в таблице.

// можно перечислять столбцы явно(чтобы видеть куда какие данные вносим). Все указывать не обязательно
INSERT INTO weather (city, temp_lo, temp_hi, prcp, date)
    VALUES ('San Francisco', 43, 57, 0.0, '1994-11-29');
// В списке выборки вы можете писать не только ссылки на столбцы, но и выражения.
SELECT city, (temp_hi+temp_lo)/2 AS temp_avg FROM weather; // AS переименует выходной столбец, который будет сред ареф двух столбцов
// Убрать дублированные строки в столбце
SELECT DISTINCT city FROM weather;
// Чтобы упорядочить результаты, соедините предложения DISTINCT и ORDER BY
SELECT DISTINCT city FROM weather ORDER BY city;
// Можно делать сокращения в запросе(переименовать поле)
SELECT * FROM weather w JOIN cities c ON w.city = c.name;

// объединить две таблицы. Сравнить столбец city каждой строки таблицы weather со столбцом name всех строк таблицы cities и выбрать пары строк, для которых эти значения совпадают
SELECT * FROM weather JOIN cities ON city = name;
// Можно указать конкретные столбцы объединенной таблицы. Если столбцы разных таблиц имеют одинаковое название, то можно явно указать их принадлежность к таблице
SELECT weather.city, weather.temp_lo, weather.temp_hi, weather.prcp, weather.date, cities.location
    FROM weather JOIN cities ON weather.city = cities.name;
// из таблицы в левой части оператора будут выбраны все строки, а из таблицы справа только те, которые удалось сопоставить
    каким-нибудь строкам из левой. При выводе строк левой таблицы, для которых не удалось найти соответствия в правой,
    вместо столбцов правой таблицы подставляются пустые значения (NULL).
SELECT * FROM weather LEFT OUTER JOIN cities ON weather.city = cities.name;

///// Агрегатные функции
//Вернет максимальное из всего столбца
SELECT max(temp_lo) FROM weather;
// Выведет максимальную температуру для каждого города(выберет максимальную температуру для каждого города)
SELECT city, max(temp_lo) FROM weather GROUP BY city;
// Из полученных температур городов оставит только те, где < 40
SELECT city, max(temp_lo) FROM weather GROUP BY city HAVING max(temp_lo) < 40;
//Подзапрос
SELECT city FROM weather WHERE temp_lo = (SELECT max(temp_lo) FROM weather);

create table Person( -- создаем новую таблицу
    id int,
    name varchar, --varchar - String
    age int,
    email varchar
)

insert into person values(1, 'Toom', 18, 'toom@mail.ru'); -- добавить значения в таблицу
insert into person values(2, 'Rom', 22, 'rom@mail.ru');
insert into person values(3, 'For', 23, 'for@mail.ru');
insert into person values(4, 'Tim', 24, 'tim@mail.ru');



-- Все записи отсортированные по id
SELECT * FROM person ORDER BY id;

-- Добавить новую запись
INSERT INTO person (id, name, age, email) VALUES((SELECT max(id) FROM person)+1, 'reerrerer', 34, 'rrrrreeere@mail.com');

-- Удалить запись
DELETE FROM Person WHERE id=7;

-- Получить чела с id
SELECT * FROM Person WHERE id=1;

-- Обновить данные по id
UPDATE Person SET name='Tom' WHERE id=1;

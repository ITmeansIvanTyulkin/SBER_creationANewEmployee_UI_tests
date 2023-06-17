package createfieldsgenerator;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class CreateFieldsGenerator {

    // Генераторы данных для формы регистрации нового сотрудника - "Новый сотрудник".

    // Инициализирую массив данных имён.
    static final String[] names = {
            "Маша",       // 1.
            "Иван",       // 2.
            "Виктор",     // 3.
            "Сергей",     // 4.
            "Катя",       // 5.
            "Даша",       // 6.
            "Пелагея",    // 7.
            "Максим",     // 8.
            "Геннадий",   // 9.
            "Наташа"      // 10.
    };

    // Инициализирую массив данных фамилий.
    static final String[] surnames = {
            "Петрова",    // 1.
            "Сосновский", // 2.
            "Безпалова",  // 3.
            "Смирнова",   // 4.
            "Сидорова",   // 5.
            "Горбатенко", // 6.
            "Зюзин",      // 7.
            "Свинтицкий", // 8.
            "Самойлов",   // 9.
            "Айфонов"     // 10.
    };

    // Инициализирую массив данных должностей.
    static final String[] position = {
            "Менеджер",   // 1.
            "Уборщица",   // 2.
            "Директор",   // 3.
            "Курьер",     // 4.
            "Секретарша", // 5.
            "Бариста",    // 6.
            "Служанка",   // 7.
            "Ковбой",     // 8.
            "Админ",      // 9.
            "Босс"        // 10.
    };

    // Создаю генератор имени. К нему будут подставляться рандомно варианты имён из массива String[] names.
    public static String name() {
        int index = (int)Math.floor(Math.random() * names.length);
        return names[index];
    }

    // Создаю генератор фамилии. К нему будут подставляться рандомно варианты фамилий из массива String[] surnames.
    public static String surname() {
        int index = (int)Math.floor(Math.random() * surnames.length);
        return surnames[index];
    }

    // Создаю генератор должности. К нему будут подставляться рандомно варианты должностей из массива String[] position.
    public static String position() {
        int index = (int)Math.floor(Math.random() * position.length);
        return position[index];
    }

    // Создаю генератор ID (табельный номер).
    public static String id() {
        int max = 8;
        int min = 2;

        int randomInt = new SecureRandom().nextInt(max - min) + min;

        boolean useLetters = false;
        boolean useNumbers = true;

        String generatedId = RandomStringUtils.random(randomInt, useLetters, useNumbers);

        return generatedId.toString();
    }

    // Создаю генератор Login.
    public static String login() {
        Random login = new Random();
        int length = login.nextInt(10) + 3;

        boolean useLetters = true;
        boolean useNumbers = false;

        String generatedLogin = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedLogin;
    }

    // Создаю генератор Password.
    public static String password() {
        int max = 20;
        int min = 12;

        int randomInt = new SecureRandom().nextInt(max - min) + min;

        boolean useLetters = true;
        boolean useNumbers = true;

        String generatedPassword = RandomStringUtils.random(randomInt, useLetters, useNumbers);

        return generatedPassword.toString();
    }
}
package date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Random;

public class Date {

    // Метод получения случайного количества месяцев.
    public static int amountOfMonths() {
        Random random = new Random();
        int randomNumber = random.nextInt(10);
        return randomNumber;
    }

    // Метод получения текущей даты.
    public static String TodayDateAndTime() {
        Calendar calendar = new GregorianCalendar();
        java.util.Date date = calendar.getTime();
        return date.toString();
    }

    // Метод прибавления к текущей дате случайного количества месяцев.
    public static String DateAndTimePlusThreeMonth() {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, amountOfMonths());
        java.util.Date date = calendar.getTime();
        return date.toString();
    }

    // Метод для поля 'Период действия с...' календаря в форме регистрации нового сотрудника.
    public static String DateFormatting() {
        String inputDate = TodayDateAndTime();
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            java.util.Date date = inputFormat.parse(inputDate);
            String outputDate = outputFormat.format(date);
            return outputDate;
        }
        catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    // Метод для поля 'Период действия по...' календаря в форме регистрации нового сотрудника.
    public static String DateFormattingTo() {
        String inputDate = DateAndTimePlusThreeMonth();
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            java.util.Date date = inputFormat.parse(inputDate);
            String outputDate = outputFormat.format(date);
            return outputDate;
        }
        catch (Exception exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    // Проверка работы методов перевода формата в нужный формат для поля ввода.
//    public static void main(String[] args) {
//        System.out.println(DateFormattingTo());
//    }
}
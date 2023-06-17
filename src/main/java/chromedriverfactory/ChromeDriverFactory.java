package chromedriverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChromeDriverFactory {

    private static WebDriver driver;

    public static final String Base_URI_toEnterWithACertificate = "https://cpri-ci02887368.apps.ift-efsemp1-dm.delta.sbrf.ru";
    public static final String property = "webdriver.chrome.driver";
    public static final String path = "C:\\Users\\21088700\\IdeaProjects\\chromeDriver\\chromedriver.exe";

    // Метод, позволяющий убедиться, что драйвер работает и открывается браузер.
    public static void main(String[] args) {
        System.setProperty(property, path);
        ChromeDriver driver = new ChromeDriver();
        driver.get(Base_URI_toEnterWithACertificate);
    }

    // Метод для запуска браузера.
    public static void setUpDriver() {
        System.setProperty(property, path);
        //ChromeDriver driver;
    }
}
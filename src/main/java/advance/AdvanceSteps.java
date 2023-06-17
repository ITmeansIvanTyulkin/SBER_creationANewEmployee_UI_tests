package advance;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static constants.Constants.*;

public class AdvanceSteps {

// Предварительные методы.
// Данный класс описывает открытие страницы проекта с формой входа, а также ожидание заголовка формы входа, вход в систему под админом.

    private final WebDriver driver;

    public AdvanceSteps(WebDriver driver) {
        this.driver = driver;
    }

    // Локаторы для полей формы авторизации в системе под администратором.
    private final By enteringName = By.xpath("//div//span//input[@placeholder = 'Введите имя']");
    private final By password = By.xpath("//div//span//input[@placeholder = 'Пароль']");
    private final By enterButton = By.xpath("//div//form//div/button");

    // Данные пользователя типа АДМИНИСТРАТОР.
    private final String adminName = "ivtyulkin";
    private final String adminPassword = "HcH6KqKYIeXP~}h";


    // Методы.
    @Step("Открытие главной страницы проекта.")
    public void openHomePage() {
        driver.get(BASE_URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Step("Ввод данных пользователя типа АДМИНИСТРАТОР в поля формы и нажатие кнопки ВОЙТИ")
    public void shouldEnterTheSystem() {
        openHomePage();
        waitForTheEnteringForm();
        driver.findElement(enteringName).sendKeys(adminName);
        driver.findElement(password).sendKeys(adminPassword);
        driver.findElement(enterButton).click();
    }

    // Ожидания.
    @Step("Ожидание видимости на экране заголовка формы входа - ВХОД В СИСТЕМУ СТОРИ.")
    public void waitForTheEnteringForm() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text() = 'Вход в систему СТОРИ']")));
    }

    @Step("Ожидание видимости на экране раздела УЧЁТНЫЕ ЗАПИСИ")
    public void waitForTheContent() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[text() = 'Учетные записи']")));
    }
}
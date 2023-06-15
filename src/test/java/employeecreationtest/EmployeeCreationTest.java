package employeecreationtest;

import administrationpage.AdministrationPage;
import advance.AdvanceSteps;
import employees.Employees;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import io.qameta.allure.junit4.DisplayName;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;

import static chromedriverfactory.ChromeDriverFactory.setUpDriver;
import static constants.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

public class EmployeeCreationTest {

    private WebDriver driver;

    /**
     * @Author Ivan Tyulkin, QA;
     * @Date 07.06.2023;
     *
     * Сьют всего package. : Создание сотрудника.
     * Предварительные действия до начала работы (описание методов произведено в package advance классе AdvanceSteps):
     * 1. Авторизация под администратором;
     * 2. Зайти в раздел администрирование;
     * 3. Зайти в раздел Сотрудники.
     */

    public EmployeeCreationTest() {}

    @Before
    public void setUp() {
        setUpDriver();
        ChromeOptions options = new ChromeOptions();
        options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.addArguments("--ignore-certificate-errors");
        driver = new ChromeDriver(options);
        //driver.manage().window().maximize(); // Запуск браузера в полном окне.
    }

    @Test
    @DisplayName("Тест на открытие URL страницы проекта.")
    @Description("Проверка, что происходит успешное открытие нужного URL страницы проекта.")
    @Severity(SeverityLevel.MINOR)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void homePageOpenedSuccessfulTest() {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.openHomePage();
        assertEquals(BASE_URL_ENTERING_FORM, driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Тест на проверку отображения заголовка формы входа.")
    @Description("Проверка, что на экране виден заголовок формы входа 'ВХОД В СИСТЕМУ СТОРИ'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void homePageEnteringFormIsDisplayedTest() {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.openHomePage();
        advanceSteps.waitForTheEnteringForm();
        assertThat(true, equalTo(driver.findElement(By
                .xpath("//div/h4[contains(text(),'Вход в систему СТОРИ')]")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку успешного входа в систему.")
    @Description("Проверка, что осуществляется успешный вход в систему под АДМИНИСТРАТОРОМ.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldEnterTheSystemByAdminTest() {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();
        assertEquals(HOME_PAGE_URL, driver.getCurrentUrl());                                            // Проверка соответствия URI ожидаемому.
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения ожидаемого элемента на ожидаемом URI.
                .xpath("//div//section/a[1][contains(text(),'Учетные записи')]")).isDisplayed()));
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения ожидаемого элемента на ожидаемом URI.
                .xpath("//div//section/a[3][contains(text(),'Сотрудники')]")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку успешного перехода к списку сотрудников.")
    @Description("Проверка, что осуществляется успешный переход к списку сотрудников для последующего создания нового сотрудника.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldEnterTheEmployeesListTest() {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения ожидаемого элемента на ожидаемом URI.
                .xpath(".//div//button[@title='Добавить сотрудника']")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку, что кнопка 'Добавить сотрудника' активирована.")
    @Description("Проверка, что осуществляется успешная активация кнопки 'Добавить сотрудника'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldTheButtonOfAddEmployeeIsActivatedTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        assertThat(true, equalTo(driver.findElement(By                                            // Проверка, что кнопка 'Добавить сотрудника' активировалась.
                .xpath(".//div//button[@title='Добавить сотрудника']")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку, что чекбокс активирован.")
    @Description("Проверка, что чекбокс активирован (стоит флажок).")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldTheCheckboxIsEnabledTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        assertThat(true, equalTo(driver.findElement(By                                            // Проверка, что чекбокс активирован.
                .xpath(".//div//input[@type='checkbox']")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку успешного заполнения поля.")
    @Description("Проверка, что осуществляется успешное заполнение поля: 'Организация'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldFilledTheFieldOfOrganizationTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения 'ПАО Сбербанк' в поле.
                .xpath(".//div[@class='ant-select-item-option-content'][contains(text(),'ПАО Сбербанк')]")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку успешного заполнения поля.")
    @Description("Проверка, что осуществляется успешное заполнение поля: 'Территориальное подразделение'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldFilledTheFieldOfTerritoryDepartmentTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения 'Байкальский банк' в поле.
                .xpath(".//div[@class='ant-select-item-option-content'][contains(text(),'Байкальский банк')]")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку успешного заполнения поля.")
    @Description("Проверка, что осуществляется успешное заполнение поля: 'Головное отделение'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldFilledTheFieldOfHeadDepartmentTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения 'Бурятское отделение №8601' в поле.
                .xpath(".//div[@class='ant-select-item-option-content'][contains(text(),'Бурятское отделение №8601')]")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку успешного открытия формы создания.")
    @Description("Проверка, что осуществляется успешное открытие формы содания нового сотрудника.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldOpenANewEmployeeAdditionFormTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        // Нажимаю на кнопку 'Добавить сотрудника' и жду появление всплывающей формы.
        employees.addANewEmployeeButtonClick();
        employees.waitForTheTitleOfTheForm();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения заголовка 'Новый сотрудник' в форме.
                .xpath(".//div//span/strong[contains(text(),'Новый сотрудник')]")).isDisplayed()));
    }

    @Test
    @DisplayName("Тест на проверку, что кнопка 'Сохранить' не активна.")
    @Description("Проверка, что в пустой форме добавления нового сотрудника, кнопка 'Сохранить' не активна.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldBeTheButtonNotActiveTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        // Нажимаю на кнопку 'Добавить сотрудника' и жду появление всплывающей формы.
        employees.addANewEmployeeButtonClick();
        employees.waitForTheTitleOfTheForm();
        assertThat(false, equalTo(driver.findElement(By                                            // Проверка НЕ отображения ожидаемого элемента.
                .xpath(".//div/button[@class='Button_button__dxrcN'][contains(text(),'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку, что после корректного заполнения формы кнопка 'Сохранить' активируется.")
    @Description("Проверка, что происходит активация кнопки 'Сохранить' после корректного заполнения всех полей формы добавления нового сотрудника.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldTheButtonEnabledTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        // Нажимаю на кнопку 'Добавить сотрудника' и жду появление всплывающей формы.
        employees.addANewEmployeeButtonClick();
        employees.waitForTheTitleOfTheForm();

        // Заполняю форму для создания нового сотрудника.
        employees.fillTheFormOfAddingANewEmployee();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка отображения активного элемента.
                .xpath(".//div/button[@class='Button_button__dxrcN'][contains(text(),'Сохранить')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку, что можно добавить нового сотрудника.")
    @Description("Проверка, что происходит добавление нового сотрудника после корректного заполнения всех полей формы и нажатию кнопки 'Сохранить'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldAddANewEmployeeTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        // Нажимаю на кнопку 'Добавить сотрудника' и жду появление всплывающей формы.
        employees.addANewEmployeeButtonClick();
        employees.waitForTheTitleOfTheForm();

        // Заполняю форму для создания нового сотрудника.
        employees.fillTheFormOfAddingANewEmployeeAndSave();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка информационного сообщения, что сотрудник успешно создан.
                .xpath(".//div/*[contains(text(), 'Сотрудник создан')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку сообщения WARNING вида: 'Отсутствует учётная запись с указанным табельным номером'.")
    @Description("Проверка, что если не имеется учётная запись пользователя с тем же табельным номером, то появлятся сообщение WARNING вида: 'Отсутствует учётная запись с указанным табельным номером'.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldAWarningMessageBeDisplayedTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        // Нажимаю на кнопку 'Добавить сотрудника' и жду появление всплывающей формы.
        employees.addANewEmployeeButtonClick();
        employees.waitForTheTitleOfTheForm();

        // Заполняю форму для создания нового сотрудника и нажимаю кнопку 'Связать'.
        employees.fillTheFormOfAddingANewEmployeeAndPressATieButton();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка сообщения WARNING вида.
                .xpath(".//div/*[contains(text(), 'Ошибка')]")).isEnabled()));
    }

    @Test
    @DisplayName("Тест на проверку кнопки 'Связать'.")
    @Description("Проверка, что если имеется учётная запись пользователя с тем же табельным номером, то запись пользователя связывается с табельным номером.")
    @Severity(SeverityLevel.NORMAL)
    @TmsLink("https://jira.sberbank.ru/secure/Tests.jspa#/testCase/CPRI-T444")
    public void shouldTieTheIDTest() throws InterruptedException {
        AdvanceSteps advanceSteps = new AdvanceSteps(driver);
        advanceSteps.shouldEnterTheSystem();
        advanceSteps.waitForTheContent();

        AdministrationPage administrationPage = new AdministrationPage(driver);
        administrationPage.employeeButtonClick();

        Employees employees = new Employees(driver);
        employees.waitForTheAddANewEmployeeButtonIsDisplayed();

        Thread.sleep(5000);

        // Заполнение полей.
        employees.chooseTheOrganization();
        employees.chooseTheBank();
        employees.chooseTheDepartment();

        // Нажимаю на кнопку 'Добавить сотрудника' и жду появление всплывающей формы.
        employees.addANewEmployeeButtonClick();
        employees.waitForTheTitleOfTheForm();

        // Заполняю форму для создания нового сотрудника и нажимаю кнопку 'Связать'.
        employees.fillTheFormOfAddingANewEmployeeAndPressTheTieButton();
        assertThat(true, equalTo(driver.findElement(By                                            // Проверка сообщения успешного связывания.
                .xpath(".//div/*[contains(text(), 'Связывание прошло успешно')]")).isEnabled()));
    }

    @After
    public void teardown() {
        this.driver.quit();
    }
}
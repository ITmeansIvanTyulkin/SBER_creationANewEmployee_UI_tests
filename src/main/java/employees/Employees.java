package employees;

import createfieldsgenerator.CreateFieldsGenerator;
import date.Date;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Employees {

    CreateFieldsGenerator createFieldsGenerator;
    // Генератор каждый раз создаёт новые данные, поэтому, чтобы ввести уже сгенерированные второй раз, делаю это через переменную.


    private final WebDriver driver;

    public Employees(WebDriver driver) {
        this.driver = driver;
    }

    /*
   Локаторы для элементов страницы https://cpri-ci02887368.apps.ift-efsemp1-dm.delta.sbrf.ru/cpri/admin/employees
   После нажатия на кнопку 'Сотрудники' осуществляется переход на страницу списка всех сотрудников, на которой есть
   кнопка 'Добавить сотрудника' - её локатор ниже 'addANewEmployeeButton'.
    */
    private final By addANewEmployeeButton = By.xpath("//div//button[@title='Добавить сотрудника']");

    // Локаторы для полей - 'Организация', 'Территориальное подразделение', 'Головное отделение'.
    private final By organization = By.xpath("//div[@name='organization']/div[@class='ant-select-selector']");
    private final By territoryDepartment = By.xpath("//div[@name='department']/div[@class='ant-select-selector']");
    private final By headDepartment = By.xpath("//div[@name='office']/div[@class='ant-select-selector']");

    // Локатор проверки чекбокса - 'Только действующие' - что он активирован.
    private final By checkBoxIsEnabled = By.xpath("//div//input[@type='checkbox']");

    // Локатор выпадающего 'ПАО Сбербанк'.
    private final By PAOSberbank = By.xpath("//div[@class='ant-select-item-option-content'][contains(text(),'ПАО Сбербанк')]");

    // Локатор выпадающего Территориального отделения 'Байкальский банк'.
    private final By baykalskiyBank = By.xpath("//div[@class='ant-select-item-option-content'][contains(text(),'Байкальский банк')]");

    // Локатор выпадающего отделения 'Бурятское отделение №8601'.
    private final By buryatskoeOtdelenie8601 = By.xpath("//div[@class='ant-select-item-option-content'][contains(text(),'Бурятское отделение №8601')]");

    // Локаторы для формы создания нового сотрудника (всплывает форма после нажатия на кнопку 'Добавить сотрудника').
    private final By newEmployeeFormTitle = By.xpath("//div//span/strong[contains(text(),'Новый сотрудник')]");
    private final By saveButton = By.xpath("//div/button[@class='Button_button__dxrcN'][contains(text(),'Сохранить')]");
    private final By positionOfTheEmployeeField = By.xpath("//div//form//div/input[@name='positionName']");
    private final By roleOfTheEmployeeField = By.xpath("//div//form//div/span/input[@role='combobox']");
    private final By rolesEmployeeList = By.xpath("//div/*[contains(text(),'Бизнес-администратор')]");
    private final By employeeIDField = By.xpath("//div//form//div//input[@name='personnelNumber']");
    private final By tieButton = By.xpath("//div/button[@class='Button_button__dxrcN'][contains(text(),'Связать')]");
    private final By employeeSurnameField = By.xpath("//div/form//div/input[@name='lastName']");
    private final By employeeNameField = By.xpath("//div/form//div/input[@name='firstName']");
    private final By employeeFatherNameField = By.xpath("//div/form//div/input[@name='patronymic']");
    private final By periodFieldFrom = By.xpath("//div/form/div[7]/div[1]/div[2]/div/div/div/input"); // К сожалению, только такой локатор доступен.
    private final By periodFieldTo = By.xpath("//div/form/div[7]/div[2]/div[2]/div/div/div/input"); // Путь и названия элементов абсолютно одинаковые.
    // Локатор кнопки 'Сегодня' в календарике 'Период действия с...'.
    private final By periodFieldFromCalendar = By.xpath("//div[@class='ant-picker-footer']/a");

    // Методы.
    @Step("Выбираю организацию 'ПАО Сбербанк'.")
    public void chooseTheOrganization() {
        driver.findElement(organization).click();
        waitForThePAOSberbankItem();
        driver.findElement(PAOSberbank).click();
    }

    @Step("Выбираю территориальное отделение 'Байкальский банк'.")
    public void chooseTheBank() {
        driver.findElement(territoryDepartment).click();
        waitForTheBaykalskiyBankItem();
        driver.findElement(baykalskiyBank).click();
    }

    @Step("Выбираю головное отделение 'Бурятское отделение №8601'.")
    public void chooseTheDepartment() {
        driver.findElement(headDepartment).click();
        waitForTheBuryatskoeOtdelenie8601Item();
        driver.findElement(buryatskoeOtdelenie8601).click();
    }

    @Step("Нажимаю кнопку 'Добавить сотрудника'.")
    public void addANewEmployeeButtonClick() {
        driver.findElement(addANewEmployeeButton).click();
    }

    @Step("Заполняю поле 'Должность'.")
    public void fillingThePositionField() {
        driver.findElement(positionOfTheEmployeeField).isEnabled();
        driver.findElement(positionOfTheEmployeeField).clear();
        driver.findElement(positionOfTheEmployeeField).sendKeys(CreateFieldsGenerator.position());
    }

    @Step("Выбираю 'Роль' для будущего сотрудника.")
    public void chooseTheRole() {
        driver.findElement(roleOfTheEmployeeField).click();
        waitForTheRole();
        driver.findElement(rolesEmployeeList).isDisplayed();
        driver.findElement(rolesEmployeeList).click();
    }

    @Step("Присваиваю сотруднику табельный номер.")
    public void assignAnIDtoTheNewEmployee() {
        driver.findElement(employeeIDField).isEnabled();
        driver.findElement(employeeIDField).clear();
        driver.findElement(employeeIDField).sendKeys(0 + CreateFieldsGenerator.id());
    }

    @Step("Присваиваю сотруднику заранее созданный табельный номер для проверки кнопки 'Связать'.")
    public void assignTheIDtoCheckTheTieButton() {
        driver.findElement(employeeIDField).isEnabled();
        driver.findElement(employeeIDField).clear();
        driver.findElement(employeeIDField).sendKeys("06082023");
    }

    @Step("Заполняю поле 'Фамилия'.")
    public void fillingTheSurnameField() {
        driver.findElement(employeeSurnameField).isEnabled();
        driver.findElement(employeeSurnameField).clear();
        driver.findElement(employeeSurnameField).sendKeys(CreateFieldsGenerator.surname());
    }

    @Step("Заполняю поле 'Имя'.")
    public void fillingTheNameField() {
        driver.findElement(employeeNameField).isEnabled();
        driver.findElement(employeeNameField).clear();
        driver.findElement(employeeNameField).sendKeys(CreateFieldsGenerator.name());
    }

    @Step("Заполняю поле 'Отчество'.")
    public void fillingThePatronymicField() {
        driver.findElement(employeeFatherNameField).isEnabled();
        driver.findElement(employeeFatherNameField).clear();
        driver.findElement(employeeFatherNameField).sendKeys("Без отчества");
    }

    @Step("Выбираю в календаре сегодняшнюю дату.")
    public void chooseTheDateFrom() {
        driver.findElement(periodFieldFrom).isEnabled();
        driver.findElement(periodFieldFrom).clear();
        driver.findElement(periodFieldFrom).sendKeys(Date.DateFormatting());
    }

    @Step("Выбираю в календаре дату через - случайное количество месяцев методом amountOfMonths().")
    public void chooseTheDateTo() {
        driver.findElement(periodFieldTo).isEnabled();
        driver.findElement(periodFieldTo).clear();
        driver.findElement(periodFieldTo).sendKeys(Date.DateFormattingTo());
    }

    @Step("Нажмаю на кнопку 'Связать'.")
    public void pressTheTieButton() {
        driver.findElement(tieButton).isEnabled();
        driver.findElement(tieButton).click();
    }

    @Step("Нажимаю кнопку 'Сохранить'.")
    public void pressSaveButton() {
        driver.findElement(saveButton).isEnabled();
        driver.findElement(saveButton).click();
    }

    @Step("Единый шаг заполнения формы добавления нового сотруника БЕЗ нажатия кнопки 'Сохранить'.")
    public void fillTheFormOfAddingANewEmployee() {
        // Заполняю поле 'Должность'.
        fillingThePositionField();
        // Выбираю 'Роль' для будущего сотрудника.
        chooseTheRole();
        // Присваиваю сотруднику табельный номер.
        assignAnIDtoTheNewEmployee();
        // Заполняю поле 'Фамилия'.
        fillingTheSurnameField();
        // Заполняю поле 'Имя'.
        fillingTheNameField();
        // Заполняю поле 'Отчество'.
        fillingThePatronymicField();
        // Выбираю в календаре сегодняшнюю дату.
        chooseTheDateFrom();
        // Выбираю в календаре дату через - случайное количество месяцев методом amountOfMonths().
        chooseTheDateTo();
    }

    @Step("Единый шаг заполнения формы добавления нового сотруника и нажатие кнопки 'Связать' для проверки сообщения WARNING вида.")
    public void fillTheFormOfAddingANewEmployeeAndPressATieButton() {
        // Заполняю поле 'Должность'.
        fillingThePositionField();
        // Выбираю 'Роль' для будущего сотрудника.
        chooseTheRole();
        // Присваиваю сотруднику табельный номер.
        assignAnIDtoTheNewEmployee();
        // Заполняю поле 'Фамилия'.
        fillingTheSurnameField();
        // Заполняю поле 'Имя'.
        fillingTheNameField();
        // Заполняю поле 'Отчество'.
        fillingThePatronymicField();
        // Выбираю в календаре сегодняшнюю дату.
        chooseTheDateFrom();
        // Выбираю в календаре дату через - случайное количество месяцев методом amountOfMonths().
        chooseTheDateTo();
        // Нажмаю на кнопку 'Связать'.
        pressTheTieButton();
    }

    @Step("Единый шаг заполнения формы добавления нового сотруника с нажатием кнопки 'Сохранить'.")
    public void fillTheFormOfAddingANewEmployeeAndSave() {
        // Заполняю поле 'Должность'.
        fillingThePositionField();
        // Выбираю 'Роль' для будущего сотрудника.
        chooseTheRole();
        // Присваиваю сотруднику табельный номер.
        assignAnIDtoTheNewEmployee();
        // Заполняю поле 'Фамилия'.
        fillingTheSurnameField();
        // Заполняю поле 'Имя'.
        fillingTheNameField();
        // Заполняю поле 'Отчество'.
        fillingThePatronymicField();
        // Выбираю в календаре сегодняшнюю дату.
        chooseTheDateFrom();
        // Выбираю в календаре дату через - случайное количество месяцев методом amountOfMonths().
        chooseTheDateTo();
        // Нажимаю кнопку 'Сохранить'.
        pressSaveButton();
    }

    @Step("Единый шаг заполнения формы добавления нового сотруника с нажатием кнопки 'Связать'.")
    public void fillTheFormOfAddingANewEmployeeAndPressTheTieButton() {
        // Заполняю поле 'Должность'.
        fillingThePositionField();
        // Выбираю 'Роль' для будущего сотрудника.
        chooseTheRole();
        // Присваиваю сотруднику табельный номер.
        assignTheIDtoCheckTheTieButton();
        // Нажмаю на кнопку 'Связать'.
        pressTheTieButton();
    }

    // Ожидания.
    @Step("Ожидание видимости на экране кнопки - 'Добавить сотрудника'.")
    public void waitForTheAddANewEmployeeButtonIsDisplayed() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(addANewEmployeeButton));
    }

    @Step("Ожидание видимости на экране выпадающего списка с названием 'ПАО Сбербанк'.")
    public void waitForThePAOSberbankItem() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-select-item-option-content'][contains(text(),'ПАО Сбербанк')]")));
    }

    @Step("Ожидание видимости на экране выпадающего списка Территориального отделения 'Байкальский банк'.")
    public void waitForTheBaykalskiyBankItem() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-select-item-option-content'][contains(text(),'Байкальский банк')]")));
    }

    @Step("Ожидание видимости на экране выпадающего списка 'Бурятское отделение №8601'.")
    public void waitForTheBuryatskoeOtdelenie8601Item() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='ant-select-item-option-content'][contains(text(),'Бурятское отделение №8601')]")));
    }

    @Step("Ожидание видимости на экране заголовка всплывающей формы 'Новый сотрудник'.")
    public void waitForTheTitleOfTheForm() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(newEmployeeFormTitle));
    }

    @Step("Ожидание видимости на экране роли 'Бизнес-администратор'.")
    public void waitForTheRole() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div/*[contains(text(),'Бизнес-администратор')]")));
    }

    @Step("Ожидание видимости на экране информационного сообщения 'Сотрудник создан'.")
    public void waitForTheInformationWindowIsDisplayed() {
        new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div/*[contains(text(), 'Сотрудник создан')]")));
    }
}
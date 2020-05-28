package integrationtests.cucumbers.mvideoSearch;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class MvideoSearch {
    WebDriver webDriver;
    ChromeOptions options = new ChromeOptions();
    WebDriverWait wait;

    @When("^Запускаем браузер Google Chrome$")
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/diana.sadikova/Downloads/chromedriver_win32/chromedriver.exe");
        options.addArguments("--disable-notifications");
        webDriver = new ChromeDriver(options);
    }

    @When("^Вводим адрес поисковой страницы \"(.*)\"$")
    public void url(String url) {
        webDriver.get(url);
    }

    @Then("^Отображается название вкладки \"(.*)\"$")
    public void title(String nameTitle) {
        Assert.assertEquals(webDriver.getTitle(), nameTitle);
    }

    @When("^Закрываем браузер$")
    public void close() {
        webDriver.quit();
    }

    @When("^Выполняем запрос \"(.*)\" в поисковой строке интернет-магазина")
    public void searchInquiry(String inquiry) {
        webDriver.findElement(By.id("frm-search-text")).clear();
        webDriver.findElement(By.id("frm-search-text")).sendKeys(inquiry);
        webDriver.findElement(By.cssSelector(".header-search-btn")).click();
    }

    @Then("^В поисковой строке интернет-магазина отображается запрос \"(.*)\"$")
    public void nameSearchInquiry(String inquiry) {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header-search-input_new")));

        Assert.assertEquals(webDriver.findElement(By.cssSelector(".header-search-input_new")).getAttribute("value"), inquiry);
    }

    @Then("^Проверяем изменение адресной строки после выполнения поиска \"(.*)\"$")
    public void mvideoSearchUrl(String searchUrl) {
        Assert.assertEquals(webDriver.getCurrentUrl(), searchUrl);
    }

    @Then("^На первой странице отображается 12 результатов поиска$")
    public void mvideoSearch() {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".search-results-area")));

        Assert.assertEquals(webDriver.findElement(By.cssSelector(".search-results-area"))
                .findElements(By.cssSelector(".c-product-tile"))
                .size(), 12);
    }

    @Then("^Найдено 2 товара по запросу$")
    public void mvideoSearchResults() {
        Assert.assertEquals(webDriver.findElement(By.cssSelector(".search-results-area"))
                .findElements(By.cssSelector(".c-product-tile"))
                .size(), 2);
    }

    @Then("^По запросу найдено \"(.*)\" результата$")
    public void quantityResults(String quantity) {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".search-results-cluster-subtitle__quantity")));

        Assert.assertEquals(webDriver.findElement(By.cssSelector(".search-results-cluster-subtitle__quantity")).getText(), quantity);
    }

    @When("^Выбираем из списка товар с наименованием \"(.*)\"$")
    public void openMvideoSearch(String nameResult) {
        List<WebElement> results = webDriver.findElement(By.id("js-product-tile-list"))
                .findElements(By.cssSelector(".sel-product-tile-main"));

        for (WebElement result : results) {
            WebElement resultLink = result.findElement(By.tagName("a"));
            if (resultLink.getAttribute("title").contains(nameResult)) {
                resultLink.click();
                break;
            }
        }

        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".e-h1")));
    }

    @When("^Выбираем первый найденый результат поиска$")
    public void clickInquiry() {
        webDriver.findElement(By.cssSelector(".sel-product-tile-title")).click();
    }

    @When("^Выбираем товар из списка найденых результатов поиска$")
    public void choiceMvideoSearch() {
        webDriver.findElements(By.cssSelector(".sel-product-tile-title")).get(0).click();
    }

    @Then("^Отображается товар с наименованием \"(.*)\"$")
    public void nameProduct(String productName) {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".e-h1")));

        Assert.assertEquals(webDriver.findElement(By.cssSelector(".e-h1")).getText(), productName);
    }

    @Then("^Отображается код товара \"(.*)\"$")
    public void codeProduct(String productCode) {
        Assert.assertEquals(webDriver.findElement(By.cssSelector(".c-product-code")), productCode);
    }

    @When("^Переходим на главную страницу магазина$")
    public void homePage() {
        webDriver.findElements(By.cssSelector(".c-breadcrumbs__item-link")).get(0).click();

        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header-search-input_new")));
    }

    @When("^Фильтруем результаты поиска по Бренду - Samsung$")
    public void filterBrand() {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".facets-holder")));

        if (!webDriver.findElement(By.id("vendor¿Samsung")).isSelected()) {
            webDriver.findElement(By.id("vendor¿Samsung")).click();
        }
        Selenide.sleep(1000);
    }

    @When("^Фильтруем результаты поиска по Категориям - LED 8K телевизоры и 4K телевизоры, Минимальной стоимости - \"(.*)\"$")
    public void filterCriterion(String coast) {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".facets-holder")));

        webDriver.findElement(By.id("cat3¿LED 8K телевизоры")).click();
        webDriver.findElement(By.id("cat3¿4K (UHD) телевизоры")).click();
        webDriver.findElement(By.cssSelector(".facet-price-min")).clear();
        webDriver.findElement(By.cssSelector(".facet-price-min")).sendKeys(coast);
        webDriver.findElement(By.cssSelector(".facet-price-min")).sendKeys(Keys.TAB);

        JavascriptExecutor jse = (JavascriptExecutor) webDriver;
        jse.executeScript("scroll(0, -250);");
        Selenide.sleep(10000);
    }

    @Then("^Проверяем название первого результата поиска - \"(.*)\"$")
    public void nameFirstProduct(String productFirstName) {
        Assert.assertEquals(webDriver.findElement(By.cssSelector(".sel-product-tile-title")).getText(), productFirstName);
    }

    @When("^Выполняем запрос \"(.*)\" в поисковой строке интернет-магазина и выбираю подсказку из выпадающего списка")
    public void searchInquiryList(String inquiry) {
        webDriver.findElement(By.id("frm-search-text")).clear();
        webDriver.findElement(By.id("frm-search-text")).sendKeys(inquiry);
        Selenide.sleep(2000);
        webDriver.findElement(By.cssSelector(".suggested-search-text")).click();
    }

    @Then("^Проверяем стоимость первого результата поиска - \"(.*)\"$")
    public void valueFirstProduct(String productFirstValue) {
        Assert.assertEquals(webDriver.findElement(By.cssSelector(".c-pdp-price__current")).getText(), productFirstValue);
    }

    @When("^Сортируем результаты поиска по возрастанию цены")
    public void sortingResults() {
        wait = new WebDriverWait(webDriver, 30, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".search-results-sort-popularity")));

        webDriver.findElement(By.cssSelector(".select-button")).click();
        webDriver.findElement(By.linkText("по цене")).click();
        Selenide.sleep(2000);
    }

    @Then("^Результаты поиска отсортированы \"(.*)\"$")
    public void nameSorting(String sortingName) {
        Assert.assertEquals(webDriver.findElement(By.cssSelector(".center")).getText(), sortingName);
    }

    @Then("^Отображается стоимость товара \"(.*)\"$")
    public void costProduct(String cost) {
        Assert.assertEquals(webDriver.findElement(By.cssSelector(".c-pdp-price__offers")).getText(), cost);
    }
}

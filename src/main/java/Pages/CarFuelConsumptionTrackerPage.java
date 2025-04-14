package Pages;

import Helpers.ConfigReader;
import Helpers.Helper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static Helpers.Helper.getDriver;

public class CarFuelConsumptionTrackerPage {
    List<String> ActualcompanyIds;
    int retries;
    boolean success;
    Helper help = new Helper();
    private WebDriver driver;
    private WebDriverWait wait;
    String ActualCompanyId;
    ConfigReader reader = new ConfigReader();
    By Pageheader_Locator = By.tagName(reader.getProperty("PageHeader_Locator"));
    By CarNumber_Input = By.xpath(reader.getProperty("CardNumber_Locator"));
    By FuelInLiters_Input = By.xpath(reader.getProperty("fuelInLiters_Locator"));
    By FuelCost_Input = By.xpath(reader.getProperty("fuelCost_Locator"));
    By FuelType_Input = By.xpath(reader.getProperty("fuelType_Locator"));
    By CompanyId_Input = By.xpath(reader.getProperty("companyId_Locator"));
    By Add_Btn = By.xpath(reader.getProperty("Add_Locator"));
    By Refilldate_Locator = By.cssSelector(reader.getProperty("DatePickr_Locator"));
    By SavedRecords_Table = By.cssSelector(reader.getProperty("Grid_Locator"));
    By Table_rows = By.tagName(reader.getProperty("Table_rows_locator"));
    By Table_data = By.tagName(reader.getProperty("Table_data_locator"));
    By Delete_Locators = By.xpath(reader.getProperty("DeleteBtn_Locator"));

    public CarFuelConsumptionTrackerPage(WebDriver driver) {
        this.driver = driver;
    }

    public void validateFields(String FieldName) {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(Long.parseLong(reader.getProperty("waitTime"))))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CarNumber_Input));
            switch (FieldName) {
                case "Page header":
                    help.findElement(Pageheader_Locator).isDisplayed();
                    Assert.assertTrue(help.findElement(Pageheader_Locator).getText().equals(reader.getProperty("Header")));
                    break;
                case "Car Number":
                    help.findElement(CarNumber_Input).isDisplayed();
                    break;
                case "Fuel in Liters":
                    help.findElement(FuelInLiters_Input).isDisplayed();
                    break;
                case "Fuel Cost":
                    help.findElement(FuelCost_Input).isDisplayed();
                    break;

                case "Refill Date Picker":
                    help.findElement(Refilldate_Locator).isDisplayed();
                    break;
                case " Customer Company ID":
                    help.findElement(CompanyId_Input).isDisplayed();
                    break;


            }

        } catch (Exception e) {

        }
    }

    public void EnterCarInputFields(String CarNumber, double FuelInLiters, double FuelCost, String FuelType, String CustomerId) {
        try {
            new WebDriverWait(getDriver(), Duration.ofSeconds(Long.parseLong(reader.getProperty("waitTime"))))
                    .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(CarNumber_Input));
            help.SendKeys(CarNumber_Input, CarNumber);
            help.SendKeys(FuelInLiters_Input, String.valueOf(FuelInLiters));
            help.SendKeys(FuelCost_Input, String.valueOf(FuelCost));
            help.SendKeys(FuelType_Input, FuelType);
            help.SendKeys(CompanyId_Input, CustomerId);

        } catch (Exception e) {
            throw e;
        }
    }

    public void ClickOnAddButton() {
        try {
            help.ClickOn(Add_Btn, true);
        } catch (Exception e) {
            throw e;
        }
    }

    public void SelectRefillDate() throws InterruptedException {
        try {
            help.SelectfromRefilldate(Refilldate_Locator);
        } catch (Exception e) {
            throw e;
        }
    }

    public void ValidateSavedRecords( List<String> ExpectedcompanyIds) throws Exception {
        try {
            ActualcompanyIds=new ArrayList<>();
            WebElement table = help.findElement(SavedRecords_Table); // Replace with the actual table ID or selector

            // Find all rows in the table
            List<WebElement> rows = table.findElements(Table_rows);

            // Iterate through the rows to find the desired row
            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(Table_data);
                if (!cells.isEmpty()) {
                    ActualcompanyIds.add(cells.get(5).getText());
                }
            }
            boolean AllRecordsAdded = ActualcompanyIds.containsAll(ExpectedcompanyIds);
            if (AllRecordsAdded){
                System.out.println("All Car records for is added).");
            }
            else {
                System.out.println("Car record for Company ID is not added).");
                throw new Exception("Records not added");
            }

        } catch (Exception e) {
            throw e;
        }
    }
    public void ValidateDeletionOfRecords() throws Exception {
        try {
            WebElement table = help.findElement(SavedRecords_Table); // Replace with the actual table ID or selector
            List<WebElement> rows = table.findElements(Table_rows);
            for (WebElement row : rows) {
                // Find all cells in the current row
                List<WebElement> cells = row.findElements(Table_data);
                    Assert.assertTrue(cells.isEmpty());
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteAddedRecord() throws Exception {

             retries = 5; // Number of retries
             success = false;
            while (retries > 0 && !success) {
                try {
                    List<WebElement> DeleteBtns = help.findElements(Delete_Locators);
                    for (WebElement deleteButton : DeleteBtns) {
                        deleteButton.click();
                        System.out.println("Stale element. Retries left: " + retries);
                    }
                    success = true;
                }

         catch (StaleElementReferenceException e) {
            retries--;
            System.out.println("Stale element. Retries left: " + retries);
        }
            }

        if (!success) {
        System.out.println("Failed to interact with the element after retries.");
            throw new Exception("Failed to delet all records");
    }


}

}

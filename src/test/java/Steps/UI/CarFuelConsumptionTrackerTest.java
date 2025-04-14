package Steps.UI;

import Helpers.*;
import Pages.CarFuelConsumptionTrackerPage;
import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static Helpers.Helper.getDriver;


public class CarFuelConsumptionTrackerTest {
    CarFuelConsumptionTrackerPage carFuel = new CarFuelConsumptionTrackerPage(getDriver());
    static ConfigReader reader = new ConfigReader();
    static Helper help = new Helper();
    static String Browser;
    String FullCarNo;
    String CarNumber_CharPrefix;
    double FuelLiters;
    double FuelCost;
    String FuelType;
    String CompanyId;
    List<String> addedCompanyIds;
    static File allure_results = new File("test-outputs/allure-results");
    static File logs = new File("test-outputs/Logs");
    static File screenshots = new File("test-outputs/screenshots");

    @BeforeAll
    public static void SetupDriver() throws Exception {
        try {
            LogsUtil.info("Test Execution started");
            FilesUtils.deleteFiles(allure_results);
            FilesUtils.cleanDirectory(logs);
            FilesUtils.cleanDirectory(screenshots);
            FilesUtils.createDirectory(allure_results);
            FilesUtils.createDirectory(logs);
            FilesUtils.createDirectory(screenshots);
            Browser = reader.getProperty("Browser");
            Helper.getDriver(Browser);
        } catch (Exception e) {
            throw e;
        }
    }

    @Given("User Open Car Fuel Webpage")
    public void OpenCarFuelWebpage() throws Exception {
        try {
            help.OpenuRL(reader.getProperty("CarFuelConsumptionTracker-URL"));
        } catch (Exception e) {
            throw e;
        }

    }

    @And("Add {int} car record")
    public void EnterCarInputFields(Integer NumberOfRecords) throws InterruptedException {
        try {
            addedCompanyIds = new ArrayList<>();
            for (int i = 0; i < NumberOfRecords; i++) {

                CarNumber_CharPrefix = help.RandomChar(4);
                FullCarNo = CarNumber_CharPrefix + "-" + help.randomNumbers();
                FuelLiters = help.RandomdoubleNo();
                FuelCost = help.RandomdoubleNo();
                FuelType = String.valueOf(help.RandomChar(8));
                CompanyId = reader.getProperty("CompanyId_Prefix") + help.randomNumbers();
                addedCompanyIds.add(CompanyId);
                carFuel.EnterCarInputFields(FullCarNo, FuelLiters, FuelCost, FuelType, CompanyId);
                carFuel.SelectRefillDate();
                carFuel.ClickOnAddButton();

            }
        } catch (Exception e) {
            throw e;
        }

    }


    @Then("validate that car records added properly")
    public void ValidateThatRecordAdded() throws Exception {
        try {
            carFuel.ValidateSavedRecords(addedCompanyIds);

        } catch (Exception e) {
            throw e;
        }
    }

    @Then("Delete car records from Grid")
    public void BulkdeleteRecords() throws Exception {
        try {
            carFuel.deleteAddedRecord();

        } catch (Exception e) {
            throw e;
        }
    }

    @And("Validate that {string} field is displayed properly on UI")
    public void ValidteUiFields(String fieldName) {
        carFuel.validateFields(fieldName);

    }

    @And("validate that all records deleted properly")
    public void ValidateRecordsDeletion() throws Exception {
        carFuel.ValidateDeletionOfRecords();

    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            String screenshotName = scenario.getName();
            byte[] Screenshot = ((TakesScreenshot) Helper.getDriver()).getScreenshotAs(OutputType.BYTES);
            switch (scenario.getStatus()) {
                case UNDEFINED:
                case PENDING:
                case SKIPPED:
                case FAILED:
                case PASSED:
                    Allure.addAttachment(screenshotName, new ByteArrayInputStream(Screenshot));
                    break;

            }
            //AllureOpen();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to serve Allure report", e);
        }
    }

    @AfterAll
    public static void KillSession() throws Exception {

        Helper.getDriver().quit();
        AllureUtils.opnAllureReportAfterExecution();
    }

}


package Helpers;

import io.cucumber.java.Scenario;
import lombok.Getter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Helper {

    char randomChar;
    @Getter
    private static WebDriver driver;
    @Getter
    String Url;

    public static WebDriver getDriver(String BrowserName) throws Exception {
        try {
            if (driver == null) {
                switch (BrowserName) {
                    case "Chrome":
                        driver = new ChromeDriver();
                        break;
                    case "Firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        driver = new FirefoxDriver(firefoxOptions);
                        break;
                }
                assert driver != null;
                driver.manage().window().maximize();
            }

        } catch (Exception e) {
            throw new Exception(e + "Failure during intialize driver");
        }
        return driver;
    }

    public void OpenuRL(String Url) {
        try {
            //driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            driver.get(Url);
        } catch (Exception e) {
            throw e;

        }
    }


    public void takeScreenShot(Scenario scenario) {
        try {
            String screenshotName = scenario.getName().replaceAll("", "_");
            if (scenario.isFailed()) {
                TakesScreenshot ts = (TakesScreenshot) driver;
                byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "img/png", screenshotName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //findElement
    public WebElement findElement(By locator) {
        {
            try {
            } catch (Exception e) {
                throw e;
            }
        }
        return driver.findElement(locator);
    }

    public List<WebElement> findElements(By locator) {
        {
            try {
            } catch (Exception e) {
                throw e;
            }
            return driver.findElements(locator);
        }
    }

    //ClickOn
    public void ClickOn(By locator, boolean usingJavascript) {
        try {
            if (usingJavascript) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", findElement(locator));
            } else {
                findElement(locator).click();
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public void SendKeys(By locator, String value) {
        try {
            findElement(locator).sendKeys(value);
        } catch (Exception e) {
            throw e;
        }
    }

    public void scrollIntoView(By locator) {
        try {
            WebElement elem = findElement(locator);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            jsExecutor.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth' });", elem);
        } catch (Exception e) {
            throw e;
        }
    }

    public void SelectfromRefilldate(By locator) throws InterruptedException {

        // Create the Actions object
        Actions actions = new Actions(getDriver());

        // Perform keyboard events
        actions.click(findElement(locator)) // Click on the input field
                .sendKeys(Keys.SPACE) // Type "WORLD" (in uppercase due to SHIFT)
                .pause(1000)
                .sendKeys(Keys.ARROW_UP) // Release the SHIFT key
                .pause(1000)
                .sendKeys(Keys.ENTER) // Press the ENTER key
                .pause(1000)
                .build() // Build the action
                .perform(); // Perform the action
    }

    public double RandomdoubleNo() {
       // double randomDouble = ThreadLocalRandom.current().nextDouble();
        Random random = new Random();
        double min = 100.50;
        double max = 10000.99;
        double randomDouble = min + (max - min) * random.nextDouble(); // Range: 10.0 to 50.0
        double roundedDouble = Math.round(randomDouble * 100.0) / 100.0;
        return roundedDouble;
    }

    public String RandomChar(int length) {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder randomString = new StringBuilder();

        // Generate the random string
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(ThreadLocalRandom.current().nextInt(characters.length()));
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

public Integer randomNumbers(){

    int randomNumber = ThreadLocalRandom.current().nextInt(100);
    System.out.println("Random Integer: " + randomNumber);

    // Generate a random integer within a specific range (e.g., 10 to 50)
    int min = 1;
    int max = 5000;
    int randomInRange = ThreadLocalRandom.current().nextInt(min, max + 1);
    System.out.println("Random Integer in Range: " + randomInRange);
    return randomInRange;
}
}


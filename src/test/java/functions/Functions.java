package functions;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import variables.Variables;

import java.text.MessageFormat;
import java.time.Duration;

public class Functions {
    public static void swipeToBottom(AndroidDriver<MobileElement> driver)
    {
        MobileElement top = driver.findElement(Variables.topStationElem);
        MobileElement middle = driver.findElement(Variables.middleStationElem);
        MobileElement bottom = driver.findElement(Variables.bottomStationElem);

        int midOfX = top.getLocation().x + (top.getSize().width/2);
        int topY = top.getLocation().y;
        int middleY = middle.getLocation().y;
        int bottomY = bottom.getLocation().y;

        System.out.println("Scrolling down");
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(midOfX, bottomY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(midOfX, topY)).release().perform();
    }

    public static int swipeToRight(AndroidDriver<MobileElement> driver) throws InterruptedException {
        String firstStation = MessageFormat.format(Variables.sliderStationElem, 1);
        String lastStation = MessageFormat.format(Variables.sliderStationElem, 6);
        MobileElement left = driver.findElement(By.xpath(firstStation));
        MobileElement right = driver.findElement(By.xpath(lastStation));

        int midOfY = left.getLocation().y + (left.getSize().height/2);
        int leftX = 28;
        // int leftX = left.getLocation().x;
        int rightX = 915;
        // int rightX = right.getLocation().x;

        System.out.println("Scrolling right");
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(rightX, midOfY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(leftX, midOfY)).release().perform();
        Thread.sleep(2000);
        return 1;
    }

    public static void goToHomePage(AndroidDriver<MobileElement> driver, WebDriverWait wait) {
        String proceedText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.proceedElem)).getText();
        String proceed = "PROCEED";
        checkText(proceedText, proceed);
        waitAndClick(wait, Variables.proceedElem, proceed);
        // driver.findElement(Variables.proceedElem).click();

        String languageTitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.languageTitleElem)).getText();
        checkText(languageTitleText, "Choose your language");

        // Select Bahasa
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Variables.languageElem)).get(2).click();

        String nextText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.nextElem)).getText();
        String next = "NEXT";
        checkText(nextText, next);
        waitAndClick(wait, Variables.nextElem, next);
        // driver.findElement(Variables.nextElem).click();

        String radioSelectTitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.radioSelectTitleElem)).getText();
        checkText(radioSelectTitleText, "Listen Now");

        // Select first radio in list
        driver.findElements(Variables.radioListElem).get(0).click();

        // Check for internet connection pop-up
        try {
            if (wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.okButtonElem)).isDisplayed()) {
                Functions.waitAndClick(wait, Variables.okButtonElem, "OK button");
            }
        } catch (TimeoutException e) {
            System.out.println("Internet connection pop-up didn't display");
        }

        waitAndClick(wait, Variables.miniPlayerElem, "Mini Player");

        // Check for AA notification and close
        try {
            waitAndClick(wait, Variables.aaInfoElem, "Android Auto pop-up");
        } catch (TimeoutException e) {
            System.out.println("AA notification didn't display");
        }

        // Check for advert and close
        for (int i = 1; i < 4; i++) {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.advertElem)).isDisplayed()) {
                    waitAndClick(wait, Variables.closeAdvertElem, "Close advert button");
                }
            } catch (TimeoutException e) {
                System.out.println("Advert " + i + " didn't display");
            }
        }
    }

    public static void waitAndClick(WebDriverWait wait, By element, String e) {
        System.out.print("\tPressing element: " + e);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element)).click();
            System.out.println(" - OK");
        } catch (TimeoutException error) {
            System.out.println(" - NOK");
        }
    }

    public static void checkText(String actualText, String expectedText) {
        System.out.print("\t- Checking text: " + expectedText);
        Assert.assertEquals(actualText, expectedText);
        System.out.println(" - OK");
    }
}

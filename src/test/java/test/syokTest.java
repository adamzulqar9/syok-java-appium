package test;

import functions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import variables.Variables;

import java.text.MessageFormat;

public class syokTest extends BaseTest{
    @Test
    public void radioTest() throws InterruptedException {
        String proceedText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.proceedElem)).getText();
        Assert.assertEquals(proceedText, "PROCEED");
        driver.findElement(Variables.proceedElem).click();

        String languageTitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.languageTitleElem)).getText();
        Assert.assertEquals(languageTitleText, "Choose your language");

        // Select Bahasa
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(Variables.languageElem)).get(2).click();

        String nextText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.nextElem)).getText();
        Assert.assertEquals(nextText, "NEXT");
        driver.findElement(Variables.nextElem).click();

        String radioSelectTitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.radioSelectTitleElem)).getText();
        Assert.assertEquals(radioSelectTitleText, "Listen Now");

        // Select first radio in list
        driver.findElements(Variables.radioListElem).get(0).click();

        // Check for internet connection pop-up
        try {
            if (wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.okButtonElem)).isDisplayed()) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.okButtonElem)).click();
            }
        } catch (TimeoutException e) {
            System.out.println("Internet connection pop-up didn't display");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.miniPlayerElem)).click();

        // Check for AA notification and close
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.aaInfoElem)).click();
        } catch (TimeoutException e) {
            System.out.println("AA notification didn't display");
        }

        // Check for advert and close
        for (int i = 0; i < 3; i++) {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.advertElem)).isDisplayed()) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.closeAdvertElem)).click();
                }
            } catch (TimeoutException e) {
                System.out.println("Advert " + i + " didn't display");
            }
        }

        // Iterate through all available radio stations and perform verifications
        int count = 0;
        for (int i = 0; i < Variables.totalStation; ) {
            try {
                String stationText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationTitleElem)).getText();
                Assert.assertEquals(stationText, Variables.expectedRadioList.get(count));

                boolean liveIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationLiveIndicator)).isDisplayed();
                Assert.assertTrue(liveIndicator);

                String liveIndicatorText = driver.findElement(Variables.stationLiveIndicator).getText();
                Assert.assertEquals(liveIndicatorText, "LIVE");

                // Couldn't figure out how to verify Play or Pause was selected
                boolean playPauseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationPauseElem)).isDisplayed();
                Assert.assertTrue(playPauseButton);

                wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.closeStationElem)).click();

                i++;
                System.out.println(count);
                if (i >= 6) {
                    i = Functions.swipeToRight(driver);
                }
                String position = MessageFormat.format(Variables.sliderStationElem, i + 1);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.miniPlayerElem)).click();
            } catch (AssertionError e) {
                e.printStackTrace();
                // Handle failed scenario during assertion
                wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.closeStationElem)).click();
                i++;
                String position = MessageFormat.format(Variables.sliderStationElem, i + 1);
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.miniPlayerElem)).click();
            }
            // Check if iteration exceed list of stations, it will break for loop
            count += 1;
            if (count > Variables.totalStation) {
                break;
            }

        }
    }
}
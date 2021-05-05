package test;

import functions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import variables.Variables;

import java.text.MessageFormat;

public class RadioTest extends BaseTest{
    @Test
    public void radioTest() throws InterruptedException {
        Functions.goToHomePage(driver, wait);

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

                Functions.waitAndClick(wait, Variables.closeStationElem, "Close station button");

                i++;
                if (i >= 6) {
                    i = Functions.swipeToRight(driver);
                }
                // Workaround so that test will select the correct station at the last swipe
                if (count == 26) {
                    i = 6;
                }
                System.out.println("Station number: " + (count + 2));
                String position = MessageFormat.format(Variables.sliderStationElem, i + 1);
                String selectStation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).getText();
                Functions.waitAndClick(wait, By.xpath(position), selectStation);

                Functions.waitAndClick(wait, Variables.miniPlayerElem, "Mini Player");
            } catch (AssertionError e) {
                e.printStackTrace();
                // Handle failed scenario during assertion
                Functions.waitAndClick(wait, Variables.closeStationElem, "Close station button");
                i++;
                String position = MessageFormat.format(Variables.sliderStationElem, i + 1);
                Functions.waitAndClick(wait, By.xpath(position), "Next station");

                Functions.waitAndClick(wait, Variables.miniPlayerElem, "Mini Player");
            }
            // Check if iteration exceed list of stations, it will break for loop
            count++;
            if (count > Variables.totalStation) {
                break;
            }
        }
    }
}
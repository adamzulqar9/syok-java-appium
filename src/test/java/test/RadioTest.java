package test;

import functions.Functions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.MessageFormat;

public class RadioTest extends BaseTest{
    @Test
    public void radioTest() throws InterruptedException {
        Functions.goToHomePage(driver, wait);

        // Iterate through all available radio stations and perform verifications
        boolean status = true;
        int count = 0;
        for (int swipe_checker = 0; swipe_checker < Variables.totalStation; ) {
            try {
                String stationText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationTitleElem)).getText();
                Functions.checkText(stationText, Variables.expectedRadioList.get(count));

                boolean liveIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationLiveIndicator)).isDisplayed();
                Assert.assertTrue(liveIndicator);

                String liveIndicatorText = driver.findElement(Variables.stationLiveIndicator).getText();
                Functions.checkText(liveIndicatorText, "LIVE");

                // Couldn't figure out how to verify Play or Pause was selected
                boolean playPauseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationPauseElem)).isDisplayed();
                Assert.assertTrue(playPauseButton);

                Functions.waitAndClick(wait, Variables.closeStationElem, "Close station button");

                swipe_checker++;
                if (swipe_checker >= 6) {
                    swipe_checker = Functions.swipeToRight(driver);
                }
                // Workaround so that test will select the correct station at the last swipe
                if (count == 25) {
                    swipe_checker = 5;
                }
                // System.out.println("swipe_checker: " + swipe_checker);
                // System.out.println("count: " + count);
                System.out.println("Station number: " + (count + 2));
                String position = MessageFormat.format(Variables.sliderStationElem, swipe_checker + 1);
                String selectStation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).getText();
                Functions.waitAndClick(wait, By.xpath(position), selectStation);
                // FIXME: checking doesnt work
                // Check that station was selected after press
                // String selected = MessageFormat.format(Variables.selectedElem, swipe_checker + 1);
                // boolean isSelected =  wait.until(ExpectedConditions.elementToBeSelected(By.xpath(selected)));
                // Assert.assertTrue(isSelected);

                Functions.waitAndClick(wait, Variables.miniPlayerElem, "Mini Player");
            } catch (AssertionError e) {
                e.printStackTrace();
                // Handle failed scenario during assertion
                Functions.waitAndClick(wait, Variables.closeStationElem, "Close station button");
                swipe_checker++;
                String position = MessageFormat.format(Variables.sliderStationElem, swipe_checker + 1);
                Functions.waitAndClick(wait, By.xpath(position), "Next station");

                Functions.waitAndClick(wait, Variables.miniPlayerElem, "Mini Player");
                status = false;
            }
            // Check if iteration exceed list of stations, it will break for loop
            count++;
            if (count >= Variables.totalStation - 1) {
                // Needed to perform check on the last radio station
                String stationText = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationTitleElem)).getText();
                Functions.checkText(stationText, Variables.expectedRadioList.get(count));

                boolean liveIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(Variables.stationLiveIndicator)).isDisplayed();
                Assert.assertTrue(liveIndicator);

                String liveIndicatorText = driver.findElement(Variables.stationLiveIndicator).getText();
                Functions.checkText(liveIndicatorText, "LIVE");
                break;
            }
        }
        // Check if there's any failures
        Assert.assertTrue(status);
    }
}
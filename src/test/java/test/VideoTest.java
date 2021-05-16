package test;

import functions.Functions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VideoTest extends BaseTest{
    @Test
    public void videoTest() throws InterruptedException {
        Functions.goToHomePage(driver, wait);

        Functions.waitAndClick(wait, Variables.closeStationElem, "Close station button");

        Functions.waitAndClick(wait, Variables.searchButtonElem, "Search button");

        Assert.assertTrue(driver.findElement(Variables.backElem).isDisplayed());

        MobileElement searchVideo = driver.findElement(Variables.searchBarElem);
        searchVideo.sendKeys(Variables.searchFor);

        String videoText = driver.findElement(Variables.searchBarElem).getText();
        Functions.checkText(videoText, Variables.searchFor);
        Assert.assertTrue(driver.findElement(Variables.closeSearchElem).isDisplayed());

        Functions.waitAndClick(wait, Variables.videoTabElem, "Video tab");

        Assert.assertTrue(driver.findElement(Variables.firstVideoTitleElem).isDisplayed());
        Assert.assertTrue(driver.findElement(Variables.firstVideoInfoElem).isDisplayed());

        Functions.waitAndClick(wait, Variables.firstVideoElem, "First video");

        // TODO: video playing verification

        driver.pressKey(new KeyEvent(AndroidKey.BACK));

        Assert.assertTrue(driver.findElement(Variables.secondVideoTitleElem).isDisplayed());
        Assert.assertTrue(driver.findElement(Variables.secondVideoInfoElem).isDisplayed());

        Functions.waitAndClick(wait, Variables.secondVideoElem, "Second video");

        // TODO: video playing verification

        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}

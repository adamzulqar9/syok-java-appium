package test;

import functions.Functions;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.testng.annotations.Test;
import variables.Variables;

public class VideoTest extends BaseTest{
    @Test
    public void videoTest() throws InterruptedException {
        Functions.goToHomePage(driver, wait);

        Functions.waitAndClick(wait, Variables.closeStationElem, "Close station button");

        Functions.waitAndClick(wait, Variables.searchButtonElem, "Search button");

        MobileElement searchVideo = driver.findElement(Variables.searchBarElem);
        searchVideo.sendKeys(Variables.searchFor);

        Functions.waitAndClick(wait, Variables.videoTabElem, "Video tab");

        Functions.waitAndClick(wait, Variables.firstVideoElem, "First video");

        // TODO: verification

        driver.pressKey(new KeyEvent(AndroidKey.BACK));

        Functions.waitAndClick(wait, Variables.secondVideoElem, "Second video");

        // TODO: verification

        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }
}

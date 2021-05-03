import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class syokTest {
    public AndroidDriver<MobileElement> driver;
    public WebDriverWait                wait;
    private TouchAction _touchAction;
    private TouchAction touchAction() {
        if (_touchAction == null) {
            _touchAction = new TouchAction(driver);
        }
        return _touchAction;
    }

    //Elements By
    By proceedElem          = By.id("net.amp.era:id/btnPrivacyPolicyAgree");
    By languageTitleElem    = By.id("net.amp.era:id/tvOnboardingPrefLanguageTitle");
    By languageElem         = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout");
    By nextElem             = By.id("net.amp.era:id/btnOnboardingPrefLanguageNext");
    By radioSelectTitleElem = By.id("net.amp.era:id/tvPrefLanguageTitle");
    By radioListElem        = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout");
    By advertElem           = By.id("net.amp.era:id/interstitial_image");
    By closeAdvertElem      = By.xpath("//android.widget.FrameLayout/android.widget.ImageView");
    By okButtonElem         = By.id("android:id/button1");
    By miniPlayerElem       = By.id("net.amp.era:id/ivRadioStationIcon");
    By aaInfoElem           = By.id("net.amp.era:id/half_interstitial_image");
    By stationTitleElem     = By.id("net.amp.era:id/tvRadioPlayerStationTitle");
    By stationLiveIndicator = By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView");
    By stationPauseElem     = By.id("net.amp.era:id/ivRadioPlayerPauseButton");
    By closeStationElem     = By.id("net.amp.era:id/ibCloseButton");
    By viewAllStationsElem  = By.id("net.amp.era:id/tvHomeRadioStationsSeeAll");
    By selectedStationElem  = By.id("net.amp.era:id/tvHomeRadioStationTitleSelected");
    String radioListTextElem  = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[{0,number,#}]/android.widget.TextView";
    String selectStationElem  = "//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[{0,number,#}]/android.widget.TextView";

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        //caps.setCapability("deviceName", "rp9");
        //caps.setCapability("udid", "bc3d16b3");
        caps.setCapability("deviceName", "Pixel_3a_API_30_x86");
        caps.setCapability("udid", "emulator-5554");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "net.amp.era");
        caps.setCapability("appActivity", "my.com.astro.radiox.presentation.screens.launch.LaunchActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 15);
    }

    public void swipeToBottom()
    {
        // FIXME: Not working currently
        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight();
        int width = dim.getWidth();
        int x = width/2;
        int top_y = (int)(height*0.80);
        int bottom_y = (int)(height*0.20);
        System.out.println("Coordinates :" + x + "  "+ top_y + " "+ bottom_y);
        WaitOptions waitOptions = new WaitOptions().withDuration(Duration.ofMillis(200));
        touchAction().press(PointOption.point(x, bottom_y)).waitAction(waitOptions).moveTo(PointOption.point(x, top_y)).waitAction().release().perform();
    }

    @Test
    public void radioTest() throws InterruptedException {
        String proceedText = wait.until(ExpectedConditions.visibilityOfElementLocated(proceedElem)).getText();
        Assert.assertEquals(proceedText, "PROCEED");
        driver.findElement(proceedElem).click();

        String languageTitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(languageTitleElem)).getText();
        Assert.assertEquals(languageTitleText, "Choose your language");

        // Select Bahasa
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(languageElem)).get(2).click();

        String nextText = wait.until(ExpectedConditions.visibilityOfElementLocated(nextElem)).getText();
        Assert.assertEquals(nextText, "NEXT");
        driver.findElement(nextElem).click();

        String radioSelectTitleText = wait.until(ExpectedConditions.visibilityOfElementLocated(radioSelectTitleElem)).getText();
        Assert.assertEquals(radioSelectTitleText, "Listen Now");

        // Get all the listed radio text to be used in assertion later
        List<String> radioList = new ArrayList<String>();
        List<WebElement> selectRadioText = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(radioListElem));
        for (int i = 1; i <= selectRadioText.size(); i++) {
            String position = MessageFormat.format(radioListTextElem, i);
            String radioText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).getText();
            radioList.add(radioText);
        }

        // Select first radio in list
        driver.findElements(radioListElem).get(0).click();

        // Check for internet connection pop-up
        try {
            if (wait.until(ExpectedConditions.visibilityOfElementLocated(okButtonElem)).isDisplayed()) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(okButtonElem)).click();
            }
        } catch (TimeoutException e) {
            System.out.println("Internet connection pop-up didn't display");
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(miniPlayerElem)).click();

        // Check for AA notification and close
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(aaInfoElem)).click();
        } catch (TimeoutException e) {
            System.out.println("AA notification didn't display");
        }

        // Check for advert and close
        for (int i = 0; i < 3; i++) {
            try {
                if (wait.until(ExpectedConditions.visibilityOfElementLocated(advertElem)).isDisplayed()) {
                    wait.until(ExpectedConditions.visibilityOfElementLocated(closeAdvertElem)).click();
                }
            } catch (TimeoutException e) {
                System.out.println("Advert didn't display");
            }
        }

        // Iterate through all available radio stations and perform verifications
        for (int i = 0; i < selectRadioText.size(); ) {
            try {
                String stationText = wait.until(ExpectedConditions.visibilityOfElementLocated(stationTitleElem)).getText();
                Assert.assertEquals(stationText, radioList.get(i).toUpperCase());

                boolean liveIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(stationLiveIndicator)).isDisplayed();
                Assert.assertTrue(liveIndicator);

                String liveIndicatorText = driver.findElement(stationLiveIndicator).getText();
                Assert.assertEquals(liveIndicatorText, "LIVE");

                // Couldn't figure out how to verify Play or Pause was selected
                boolean playPauseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(stationPauseElem)).isDisplayed();
                Assert.assertTrue(playPauseButton);

                wait.until(ExpectedConditions.visibilityOfElementLocated(closeStationElem)).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllStationsElem)).click();
                i++;
                String position = MessageFormat.format(selectStationElem, i + 1);
                if (i > 12) {
                    swipeToBottom();
                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(miniPlayerElem)).click();
            } catch (AssertionError e) {
                e.printStackTrace();
                // Handle failed scenario during assertion
                wait.until(ExpectedConditions.visibilityOfElementLocated(closeStationElem)).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(viewAllStationsElem)).click();
                i++;
                String position = MessageFormat.format(selectStationElem, i + 1);
                if (i > 12) {
                    swipeToBottom();
                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(position))).click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(miniPlayerElem)).click();
            }
        }
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class syokTest {

    public AndroidDriver<MobileElement> driver;
    public WebDriverWait                wait;

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
    By miniPlayer           = By.xpath("//android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.LinearLayout/android.widget.FrameLayout[1]");
    By aaInfo               = By.id("net.amp.era:id/half_interstitial_image");

    String radioListTextElem  = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[{0,number,#}]/android.widget.TextView";

    @BeforeMethod
    public void setup() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Pixel_3a_API_30_x86");
        caps.setCapability("udid", "emulator-5554"); //DeviceId from "adb devices" command
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "11.0");
        caps.setCapability("skipUnlock", "true");
        caps.setCapability("appPackage", "net.amp.era");
        caps.setCapability("appActivity", "my.com.astro.radiox.presentation.screens.launch.LaunchActivity");
        caps.setCapability("noReset", "false");
        driver = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
        wait = new WebDriverWait(driver, 15);
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
            Reporter.log(radioList.toString());
        }

        // Select first radio in list
        driver.findElements(radioListElem).get(0).click();

        // Check for advert
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(advertElem)).isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(closeAdvertElem)).click();
        }

        // Check for internet connection pop-up
        if (wait.until(ExpectedConditions.visibilityOfElementLocated(okButtonElem)).isDisplayed()) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(okButtonElem)).click();
        }

        Set<String> contextNames = driver.getContextHandles();
        // Set context to webview
        driver.context((String) contextNames.toArray()[1]);

    }

    @AfterMethod
    public void teardown() {
        driver.quit();
    }
}
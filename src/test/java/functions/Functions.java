package functions;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
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
        int leftX = left.getLocation().x;
        int rightX = right.getLocation().x;

        System.out.println("Scrolling right");
        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(rightX, midOfY)).waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(leftX, midOfY)).release().perform();
        Thread.sleep(1000);
        return 1;
    }
}

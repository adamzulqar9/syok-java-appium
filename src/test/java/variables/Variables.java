package variables;

import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.List;

public class Variables {
    //Elements By
    public static By proceedElem          = By.id("net.amp.era:id/btnPrivacyPolicyAgree");
    public static By languageTitleElem    = By.id("net.amp.era:id/tvOnboardingPrefLanguageTitle");
    public static By languageElem         = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout");
    public static By nextElem             = By.id("net.amp.era:id/btnOnboardingPrefLanguageNext");
    public static By radioSelectTitleElem = By.id("net.amp.era:id/tvPrefLanguageTitle");
    public static By radioListElem        = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout");
    public static By advertElem           = By.id("net.amp.era:id/interstitial_image");
    public static By closeAdvertElem      = By.xpath("//android.widget.FrameLayout/android.widget.ImageView");
    public static By okButtonElem         = By.id("android:id/button1");
    public static By miniPlayerElem       = By.id("net.amp.era:id/ivRadioStationIcon");
    public static By aaInfoElem           = By.id("net.amp.era:id/half_interstitial_image");
    public static By stationTitleElem     = By.id("net.amp.era:id/tvRadioPlayerStationTitle");
    public static By stationLiveIndicator = By.xpath("//android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.TextView");
    public static By stationPauseElem     = By.id("net.amp.era:id/ivRadioPlayerPauseButton");
    public static By closeStationElem     = By.id("net.amp.era:id/ibCloseButton");
    public static By viewAllStationsElem  = By.id("net.amp.era:id/tvHomeRadioStationsSeeAll");
    public static By topStationElem       = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[1]");
    public static By middleStationElem    = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[6]");
    public static By bottomStationElem    = By.xpath("//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[12]");
    public static By selectedStationElem  = By.id("net.amp.era:id/tvHomeRadioStationTitleSelected");

    public static String radioListTextElem  = "//androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[{0,number,#}]/android.widget.TextView";
    public static String selectStationElem  = "//androidx.recyclerview.widget.RecyclerView/android.widget.RelativeLayout[{0,number,#}]/android.widget.TextView";
    public static String sliderStationElem   = "//android.widget.LinearLayout[1]/android.widget.RelativeLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[{0,number,#}]/android.widget.TextView";
    public static List<String> expectedRadioList = Arrays.asList("ERA", "HITZ", "MIX", "MY", "LITE", "SINAR", "RAAGA", "GEGAR", "MELODY", "ZAYAN", "GOXUAN",
            "ERA (SABAH)", "HITZ (SABAH)", "MY (SABAH)", "ERA (SARAWAK)", "HITZ (SARAWAK)", "MY (SARAWAK)", "ASTRO AWANI", "SYOK BAYU",
            "SYOK GOLD", "SYOK INDIA BEAT", "SYOK JAZZ", "SYOK KENYALANG", "SYOK OPUS", "SYOK OSAI", "SYOK CLASSIC ROCK", "ASTRO ARENA");
    public static int totalStation = 27;
}

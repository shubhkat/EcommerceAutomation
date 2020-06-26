package utility;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
	public static String screenshot(WebDriver driver, String screenshotname) {

		String path= "../EcommerceAutomation/images/"+screenshotname+".png";
        TakesScreenshot screenShot =(TakesScreenshot)driver;

        File Src = screenShot.getScreenshotAs(OutputType.FILE);
        
        File Dest=new File(path);

        try {
			FileUtils.copyFile(Src, Dest);
			//Reporter.log("Able to capture Screenshot",true);
		} catch (IOException e) {
			//Reporter.log("Unble to capture Screenshot",true);
			//e.printStackTrace();
		}
        return path;
	}
}

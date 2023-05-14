package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	RequestSpecification reqspec;
	public static Response response;

	static WebDriver driver;
	static Alert alt;
	TakesScreenshot ts;
	
	public String getProjectPath() {
		String path = System.getProperty("user.dir");
		return path;
	}

	public String getPropertyFileValue(String key) throws FileNotFoundException, IOException {
		Properties properties = new Properties();
		properties.load(new FileInputStream(getProjectPath() + "\\config\\config.properties"));
		Object object = properties.get(key);
		String value = (String) object;
		return value;
	}
	
	public void screenshot(){
		TakesScreenshot screenShot=(TakesScreenshot)driver;
		byte[]b=screenShot.getScreenshotAs(OutputType.BYTES);	
	}
	
	// AddHeader
	public void addHeader(String key, String value) {
		reqspec = RestAssured.given().header(key, value);
	}

	// AddHeaders
	public void addHeaders(Headers headers) {
		reqspec = RestAssured.given().headers(headers);
	}

	// AddPathParameter
	public void addPathParam(String key, String value) {
		reqspec = reqspec.pathParam(key, value);
	}

	// AddQueryParameter
	public void addQueryParam(String key, String value) {
		reqspec = reqspec.queryParam(key, value);
	}

	// AddBodyRequest
	public void addBody(String body) {
		reqspec = reqspec.body(body);
	}

	// AddBodysRequest
	public void addBody(Object body) {
		reqspec = reqspec.body(body);
	}

	// AddReqType
	public Response addReqType(String type, String endpoint) {
		switch (type) {
		case "GET":
			response = reqspec.log().all().get(endpoint);
			break;
		case "POST":
			response = reqspec.log().all().post(endpoint);
			break;
		case "PUT":
			response = reqspec.log().all().put(endpoint);
			break;
		case "DELETE":
			response = reqspec.log().all().delete(endpoint);
			break;
		case "PATCH":
			response = reqspec.log().all().patch(endpoint);
			break;
		default:
			break;
		}
		return response;
	}

	// GetStatusCode

	public int getStatusCode(Response response) {
		int statusCode = response.getStatusCode();
		return statusCode;
	}

	// GetResponseBodyAsString

	public String getResponeBodyAsString(Response response) {
		String asString = response.asString();
		return asString;
	}

	// GetResponeBodyAsPrettyString

	public String getResBodyAsPerttyString(Response response) {

		String asPrettyString = response.asPrettyString();
		return asPrettyString;

	}

	// BasicAuthentication
	public void addBasicAuth(String username, String password) {

		reqspec = reqspec.auth().preemptive().basic(username, password);
	}

	// To GetDriver

	public static void getDriver(String browserName) {
		switch (browserName) {
		case "Chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;

		case "Edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;

		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;

		default:
			System.out.println("Invalid Browser Name");
			break;
		}
		driver.manage().window().maximize();
	}

	// To LaunchUrl

	public static void launchurl(String url) {
		driver.get(url);
	}

	// To TypeText

	public void typetext(WebElement element, String data) {
		element.sendKeys(data);
	}

	// To ButtonClick

	public void buttonClick(WebElement element) {
		element.click();
	}

	// To getAttribute

	public String getAttribute(WebElement element, String data) {
		String attribute = element.getAttribute(data);
		return attribute;
	}

	// To getText

	public String gettingText(WebElement element) {
		String text = element.getText();
		System.out.println(text);
		return text;
	}

	// To Close

	public void close() {
		driver.close();
	}

	// To Quit

	private void Quit() {
		driver.quit();
	}

	// To MousehOverActions

	public void mouseaction(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).perform();
	}

	// To RightClick

	public void rightClick(WebElement element) {
		Actions act = new Actions(driver);
		act.contextClick(element).perform();
	}

	// To doubleClick

	public void doubleClick(WebElement element) {
		Actions act = new Actions(driver);
		act.doubleClick(element).perform();
	}

	// To DragandDrop

	public void dragandDrop(WebElement element) {
		Actions act = new Actions(driver);
		act.dragAndDrop(element, element).perform();
	}

	// To accept

	public void accept(WebElement element) {
		alt = driver.switchTo().alert();
		alt.accept();
	}

	// To dismiss

	public void dismiss(WebElement element) {
		alt = driver.switchTo().alert();
		alt.dismiss();
	}

	// To getText

	public String gettext(WebElement element) {
		alt = driver.switchTo().alert();
		String text = alt.getText();
		return text;
	}

	// To sendKeys

	public void sendKeys(String element) {
		alt = driver.switchTo().alert();
		alt.sendKeys(element);
	}

	// To sendingtextValue

	public void sendingTextValue(String data, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('value','" + data + "')", element);
	}

	/// To gettingTextValue

	public void gettingTextValue(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Object executeScript = js.executeScript("return arguments[0].getAttribute('value')", element);
		String text = (String) executeScript;
		System.out.println(text);
		String String = null;

	}

	// To Button click

	public void buttonClickjs(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", element);
	}

	// To ScrollUp

	public void scrollUp(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(false)", element);
	}

	// To ScrollDown

	public void scrollDown(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true)", element);
	}

	// TakesScreenShot

	public Object takeScreenShot(String Filename) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshotAs = ts.getScreenshotAs(OutputType.FILE);
		File file = new File("C:\\Users\\sanka\\OneDrive\\Pictures\\takeScreenShot\\ " + Filename + ".png");
		FileUtils.copyFile(screenshotAs, file);

		return file;
	}
	// To selectByVissibleText

	public void selectByVisibleText(WebElement element, String Text) {
		Select slt = new Select(element);
		slt.selectByVisibleText(Text);
	}

	// ToSelect By value

	public void selectByValue(WebElement element, String Value) {
		Select slt = new Select(element);
		slt.selectByValue(Value);
	}

	// ToSelect by Index

	public void selectByIndex(WebElement element, int index) {
		Select slt = new Select(element);
		slt.selectByIndex(index);
	}

	// To Deselect by visibleText

	public void deselectByVisibleText(WebElement element, String Text) {
		Select slt = new Select(element);
		slt.deselectByVisibleText(Text);
	}

	// To Deselect by Visible value

	public void deselectByVisibleValue(WebElement element, String Value) {
		Select slt = new Select(element);
		slt.deselectByValue(Value);
	}

	// To Deselect by Visible Index

	public void deselectByVisibleIndex(WebElement element, int index) {
		Select slt = new Select(element);
		slt.deselectByIndex(index);
	}

	// To Get Options

	public void getOptions(WebElement element) {
		Select slt = new Select(element);
		slt.getOptions();
	}

	// To Get First Selected Option

	public void getFirstSelectedOption(WebElement element) {
		Select slt = new Select(element);
		slt.getFirstSelectedOption();
	}

	// To Get All Selected Options

	// public void getallSelectedOptions(WebElement element) {
	// Select slt = new Select(element);
	// slt.getAllSelectedOptions();
	// }

	public List<WebElement> getOptionsFromDropDown(WebElement element) {
		Select slt = new Select(element);
		List<WebElement> Options = slt.getOptions();
		return Options;
	}

	// To Check isMultiple

	public void isMultiple(WebElement element) {
		Select slt = new Select(element);
		slt.isMultiple();
	}

	// To deSelectAll

	public void deSelectAll(WebElement element) {
		Select slt = new Select(element);
		slt.deselectAll();
	}

	// To WindowHandle

	public String parentwinid() {
		return driver.getWindowHandle();

	}

	// To WindowHandles

	public Set<String> getallwinid() {
		Set<String> windowHandles = driver.getWindowHandles();
		return windowHandles;
	}

	// To switchToCurrentWindow

	public void switchToCurrentWindow() {

		String parentwindow = driver.getWindowHandle();
		System.out.println(parentwindow);

		Set<String> allwindow = driver.getWindowHandles();
		System.out.println(allwindow);

		for (String winid : allwindow) {
			if (!parentwindow.equals(winid)) {
				driver.switchTo().window(winid);
			}
		}

	}

	// To selectDropDown

	public void selectDropDown(WebElement element, String data, String value) {
		Select slt = new Select(element);
		switch (data) {
		case "value":
			slt.selectByValue(value);
			break;
		case "text":
			slt.selectByVisibleText(value);
			break;
		case "index":
			slt.selectByIndex(Integer.parseInt(value));
			break;

		default:
			System.out.println("spelling mistak");
			break;
		}
	}

	// To findElementByLocator

	public WebElement findElementByLocator(String locator, String value) {

		WebElement element = null;
		switch (locator) {
		case "id":
			element = driver.findElement(By.id(value));
			break;
		case "name":
			element = driver.findElement(By.name(value));
			break;
		case "xpath":
			element = driver.findElement(By.xpath(value));
			break;
		default:
			break;
		}
		return element;
	}

}

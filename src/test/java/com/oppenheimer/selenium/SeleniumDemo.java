package com.oppenheimer.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.xml.dom.Tag;

import com.oppenheimer.config.TestTags;

public class SeleniumDemo {
	WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");

		// Instantiate a ChromeDriver class.
		driver = new ChromeDriver();
		driver.manage().deleteAllCookies();
		wait = new WebDriverWait(driver, 20);
		driver.navigate().refresh();
		driver.manage().window().maximize();

	}

	@AfterTest
	public void cleanup() {
		driver.close();
		driver.quit();
	}

	@Tag(name =TestTags.UI_AUTOMATION)
	@Test
	
	public void dispenseAmountTest() throws Exception {
		driver.get("http://localhost:8080/");
		try {
			String dispenseNowbutton_xpath = "//*[@id=\"contents\"]/a[2]";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dispenseNowbutton_xpath)));
			WebElement dispenseNowbutton = driver.findElement(By.xpath(dispenseNowbutton_xpath));
			Assert.assertNotNull(dispenseNowbutton);
			Assert.assertEquals(dispenseNowbutton.getText(), "Dispense Now");
			Assert.assertEquals(dispenseNowbutton.getCssValue("background-color"), "rgba(220, 53, 69, 1)");
			dispenseNowbutton.click();

			String dispenseDone_xpath = "//*[@id=\"app\"]/div/main/div/div/div";
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dispenseDone_xpath)));
			WebElement dispenseDone = driver.findElement(By.xpath(dispenseDone_xpath));
			Assert.assertNotNull(dispenseDone);
			Assert.assertEquals(dispenseDone.getText(), "Cash dispensed");

		} catch (Exception e) {

			throw e;
		}

	}
	@Tag(name =TestTags.UI_AUTOMATION)
	@Test
	public void validateNatidSplAndAmountCheck() {
		this.driver.get("http://localhost:8080/");
		String table_xpath_natid = "//*[@id=\"contents\"]/div[2]/table/tbody/tr";
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(table_xpath_natid)));

		List<WebElement> tableRow = driver.findElements(By.xpath(table_xpath_natid));
		Assert.assertNotNull(tableRow);
		for (int i = 0; i < tableRow.size(); i++) {
			List<WebElement> columns = tableRow.get(i).findElements(By.tagName("td"));
			Assert.assertNotNull(tableRow);
			Assert.assertEquals(columns.size(), 2);
			WebElement columnOne = columns.get(0);
			String netId = columnOne.getText();
			//Assert.assertEquals(netId.length(), 11);
			Assert.assertTrue(netId.endsWith("$$$$$$"));
			WebElement columnTwo = columns.get(1);
			String amount = columnTwo.getText();
			Assert.assertTrue(amount.matches("^[0-9]+.[0-9]{2}"));

		}

	}
}
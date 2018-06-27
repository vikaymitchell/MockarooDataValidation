package com.cybertek;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MockarooDataValidation {
	
	
WebDriver driver;
List<String> cities=new ArrayList<>();
List<String> countries=new ArrayList<>();;
Set<String> citiesSet=new HashSet<>();
Set<String> countriesSet=new HashSet<>();

int lineCount;

	@BeforeClass
	public void setUp() {	
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		System.out.println("Navigating to homepage in @BeforeMethod....");
		driver.get("https://mockaroo.com/");
	}
	
	@BeforeMethod	//runs once for the whole tests
	public void navigateToHomePage() {
			
	}
	
	@Test (priority=1)//Step 3
	public void assertTitle() {
		Assert.assertTrue(driver.getTitle().contains("Mockaroo"));
	}
	
	@Test (priority=2) //Step 4
	public void ifMockarooIsDisplayed() {

		String expected1 = "mockaroo";
		String expected2 = "realistic data generator";

		String actual1 = driver.findElement(By.xpath("//div[@class='brand']")).getText();
		String actual2 = driver.findElement(By.xpath("//div[@class='tagline']")).getText();

		assertEquals(actual1, expected1);
		assertEquals(actual2, expected2);
	}

	@Test (priority=3)//Step 5
	public void removeX() {

		List<WebElement> el = driver.findElements(By.xpath("//a[@class='close remove-field remove_nested_fields']"));

		for (WebElement webElement : el) {
			webElement.click();
		}
	}
	@Test (priority=4)//Step 6
    public void areDisplayed() {
        String actual = driver.findElement(By.xpath("//div[@class='column column-header column-name']")).getText();
        String expected = "Field Name";
        Assert.assertEquals(actual,expected);
        
    Assert.assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-type']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@class='column column-header column-options']")).isDisplayed());
    }
	
	@Test(priority=5)
	//Step7
	public void addAnother() {
		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).isEnabled());
		
	}
	
	@Test (priority=6)//Step 8
	public void assertDefaultNumberOfRows() {
		
		Assert.assertTrue(driver.findElement(By.xpath("//input[@value='1000']")).isDisplayed());
	}
	
	@Test(priority = 7) // Step 9
    public void ifDefaultIsCVS() {
        String expected = "CSV";
        String actual = driver.findElement(By.xpath("//select[@id='schema_file_format']/option[1]")).getText();
        assertEquals(actual, expected);
	}
	
	@Test(priority=8)
    //step 10
    public void lineEnding() {
        String actual = driver.findElement(By.xpath("//*[.='Unix (LF)']")).getText();
        String expected = "Unix (LF)";
        assertEquals(actual, expected);
	}

	@Test (priority=9) //Step 11
    public void checkedUnchecked() {
        
    WebElement header = driver.findElement(By.xpath("//*[@id=\"schema_include_header\"]"));
    WebElement bom = driver.findElement(By.xpath("//input[@name='schema[bom]']"));
       
        Assert.assertTrue(header.isSelected());
        Assert.assertFalse(bom.isSelected());
        
    }
	
	@Test (priority=10)  // Step 12
	public void addAnotherFieldAndTypeSomething() {
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("(//input[@placeholder='enter name...'])[7]")).sendKeys("City");
	}
	
	@Test (priority=11)//Step 13
	public void assertChooseTypeIsDisplayed() throws InterruptedException {
		Thread.sleep(2000);

	    driver.findElement(By.xpath("(//input[@class='btn btn-default'])[7]")).click();
	    Thread.sleep(1000);
	
		Thread.sleep(2000);
		String choose = driver.findElement(By.xpath("//h3[@class='modal-title']")).getText();
		String expected = "Choose a Type";
		Assert.assertEquals(choose, expected);
		  Thread.sleep(2000);
		    driver.findElement(By.xpath("(//input[@id='type_search_field'])")).clear();
		    driver.findElement(By.xpath("(//input[@id='type_search_field'])")).sendKeys("city");
		  
	}
	
	//step 14
		//Search for "city" and click on City on search results.	
		@Test(priority=12)
		public void searchCity() throws InterruptedException { 
    driver.findElement(By.xpath("//div[@class='examples']")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(//input[@class='column-name form-control'])[8]")).sendKeys("Country");
    driver.findElement(By.xpath("(//input[@class='btn btn-default'])[8]")).click();
    Thread.sleep(2000);
    driver.findElement(By.xpath("(//input[@id='type_search_field'])")).clear();
    driver.findElement(By.xpath("(//input[@id='type_search_field'])")).sendKeys("country");
    driver.findElement(By.xpath("//div[.='Country']")).click();
    		}

	
	@Test(priority=13)
	//step 16
	public void download() throws InterruptedException {	
	Thread.sleep(2000);
		driver.findElement(By.xpath("//button[@class='btn btn-success']")).click();	
	}
	
	@Test (priority=14)
	  public void verifyDownloadedData() throws IOException {
	    loadLists();
	    assertEquals(lineCount, 1000);
	    sortCities();
	    findCountries();
	    loadSets();
	    
	    int actual = removeDuplicates(cities);
	    assertEquals(actual, citiesSet.size());
	    
	    actual=removeDuplicates(countries);
	    
	    assertEquals(actual, countriesSet.size());
	  }
	
	@Test (priority=16)
	  public boolean verifyStrings() {
	    List<WebElement> actual = driver.findElements(By.xpath("//div[@class='table-header']//div"));
	    List<String> expected = new ArrayList<>();
	    expected.add("Field Name");
	    expected.add("Type");
	    expected.add("Options");
	    String eachActual;
	    for (int i = 0; i < actual.size(); i++) {
	      eachActual = actual.get(i).getText();
	      if (!eachActual.equals(expected.get(i))) {
	        return false;
	      }
	    }
	    return true;
	  }
	
	@Test (priority=17)
	  public void sortCities() {
	    Collections.sort(cities);
	    int max = cities.get(0).length();
	    for (String string : cities) {
	      if (string.length() > max)
	        max = string.length();
	    }
	    int min = cities.get(0).length();
	    for (String string : cities) {
	      if (string.length() < min)
	        min = string.length();
	    }
	    System.out.println("City-Name: Maximum Length is " + max);
	    System.out.println("City_Name: Minimum Length is " + min);
	  }
	
	@Test (priority=18)
	  public void findCountries() {
	    int count = 0;
	    Set<String> k = new HashSet<>(countries);
	    for (String outer : k) {
	      for (String inner : countries) {
	        if (inner.equals(outer))
	          count++;
	      }
	      System.out.println(outer + "-" + count);
	      count = 0;
	    }
	  }
	
	@Test (priority=19)
	  public void loadLists() throws IOException {
	    FileReader reader = new FileReader("C:/Users/Vika/Downloads/MOCK_DATA (13).csv");
	    BufferedReader breader = new BufferedReader(reader);
	    String temp = breader.readLine();
	    assertEquals(temp, "City,Country");
	    lineCount = 0;
	    temp = breader.readLine();
	    String[] something = new String[2];
	    while (temp != null) {
	      something = temp.split(",");
	      cities.add(something[0]);
	      countries.add(something[1]);
	      lineCount++;
	      temp = breader.readLine();
	      
	    }
	      reader.close();
	      breader.close();
	  }
	  
	@Test (priority=20)
	  public void loadSets() throws IOException {
	    
	    FileReader reader = new FileReader("C:/Users/Vika/Downloads/MOCK_DATA (13).csv");
	    BufferedReader breader = new BufferedReader(reader);
	    String temp = breader.readLine();
	    temp = breader.readLine();
	    String[] something = new String[2];
	    while (temp != null) {
	      something = temp.split(",");
	      citiesSet.add(something[0]);
	      countriesSet.add(something[1]);
	      lineCount++;
	      temp = breader.readLine();
	  }
	    
	    reader.close();
	    breader.close();
	}
	
	@Test (priority=21)
	  public int removeDuplicates(List<String> myList) {
	    Iterator<String> myIterator = myList.iterator();
	    List<String> localList = new ArrayList<>();
	    String each = new String();
	    boolean add = true;
	    while (myIterator.hasNext()) {
	      each = myIterator.next();
	      add = true;
	      for (String string : localList) {
	        if (each.equals(string)) {
	          add = false;
	        }
	      }
	      if (add) {
	        localList.add(each);
	      }
	    }
	    return localList.size();
	  }
	
	@AfterClass
	public void tearDown() {
		driver.close();
	}
}

package StepDefinitions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class todoSteps {

	WebDriver driver = null;
	
	@Given("the todoMVC site is open in the browser")
	public void the_todo_mvc_site_is_open_in_the_browser() {
		
		String projectPath = System.getProperty("user.dir");
		
		String webdriver = "chrome";
		
		if (webdriver.equalsIgnoreCase("chrome")){        
			System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/resources/Drivers/chromedriver.exe");		
			driver = new ChromeDriver();
		}
		else if (webdriver.equalsIgnoreCase("firefox")){        
			System.setProperty("webdriver.gecko.driver", projectPath + "/src/test/resources/Drivers/geckodriver.exe");		
			driver = new FirefoxDriver();
		}
		else
		{
			throw new RuntimeException("Unsupported webdriver: " + webdriver);
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		driver.navigate().to("http://todomvc.com/examples/vue/");
		driver.manage().window().maximize();
	}
	
	@After
	public void stopBrowser(Scenario scenario) throws InterruptedException{
		if (scenario.isFailed()) {
		    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
		    scenario.attach(screenshot, "image/png", "name");
		}
		
		driver.quit();
		Thread.sleep(5000);
	} 
	
	@Given("a list with {int} tasks")
	public void a_list_with_tasks(Integer int1) throws InterruptedException {
		for (int i = 1; i <= int1 ; i++) {
			  String taskName = "Example Task " + i;
			  driver.findElement(By.className("new-todo")).sendKeys(taskName);
			  Thread.sleep(1000);
			  driver.findElement(By.className("new-todo")).sendKeys(Keys.ENTER);	
		}
	}
	
	@When("a task is added")
	public void a_task_is_added() throws InterruptedException {
			Integer iCount = new Integer(0);
			iCount = driver.findElements(By.xpath("//ul[@class=\"todo-list\"]/li")).size();
		    iCount++;
			
		    String taskName = "Example Task "+ iCount ;
			driver.findElement(By.className("new-todo")).sendKeys(taskName);
			Thread.sleep(1000);
			driver.findElement(By.className("new-todo")).sendKeys(Keys.ENTER);	
		
	}
	
	@Then("the number of items counter is set to {int}")
	public void the_number_of_items_counter_is_set_to(Integer int1) {
		
		Integer iCount = new Integer( driver.findElement(By.xpath("//span[@class=\"todo-count\"]/strong")).getText());
		Assert.assertEquals(iCount, int1);
	}
	
	@Then("{int} task is visible in the list")
	public void task_is_visible_in_the_list(Integer int1) {
		for (int i = 1; i <= int1 ; i++) {
			  String taskName = "Example Task " + i;
			  boolean disp = driver.findElement(By.xpath("//ul[@class='todo-list']//li//label[text()='"+ taskName + "']")).isDisplayed();
			  Assert.assertTrue(disp);
		}		
	}

	@Then("the filters are displayed")
	public void the_filters_are_displayed() {
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()=\"All\"]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()=\"Active\"]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[text()=\"Completed\"]")).isDisplayed());
	}

	@Then("the option to clear completed tasks is available")
	public void the_option_to_clear_completed_tasks_is_available() {
		Assert.assertTrue(driver.findElement(By.className("clear-completed")).isDisplayed());		
	}
	
	@When("the task is edited")
	public void the_task_is_edited() throws InterruptedException {
		Actions act = new Actions(driver);
		WebElement ele = driver.findElement(By.xpath("//ul[@class='todo-list']//li[1]"));
		act.doubleClick(ele).perform();
		Thread.sleep(1000);

		driver.findElement(By.xpath("//li/input[@class='edit']")).sendKeys("- Edited");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//li/input[@class='edit']")).sendKeys(Keys.ENTER);	
	}

	@Then("the edited task is visible in the list")
	public void the_edited_task_is_visible_in_the_list() {
		boolean disp = driver.findElement(By.xpath("//ul[@class='todo-list']//li//label[text()='Example Task 1- Edited']")).isDisplayed();
		Assert.assertTrue(disp);
	}



	@When("{int} task is marked as completed")
	public void task_is_marked_as_completed(Integer int1) throws InterruptedException {
		WebElement ele = driver.findElement(By.xpath("//ul[@class='todo-list']//li["+ int1 + "]//input[@type='checkbox']"));
		ele.click();
		Thread.sleep(1000);
	}

	@Then("the task is checked off from the list")
	public void the_task_is_checked_off_from_the_list() {
		String classname = driver.findElement(By.xpath("//ul[@class='todo-list']//li[1]")).getAttribute("class");
		Integer flag = new Integer(0);
		for (String i : classname.split(" ")) {
	         //check the class for specific value
	         if (i.equals("completed")) {
	               flag=1;
	         } 
		}
		Assert.assertEquals(flag, new Integer(1));
	}


	@When("{string} filter is selected")
	public void filter_is_selected(String string) {
		WebElement ele = driver.findElement(By.xpath("//a[text()=\"" + string + "\"]"));
		ele.click();
	}

	@Then("{string} tasks are displayed")
	public void tasks_are_displayed(String string) {
		if (string.equals("All")) {
			Assert.assertTrue(driver.findElement(By.xpath("//li[@class='todo completed']//label[text()='Example Task 1']")).isDisplayed());
			Assert.assertTrue(driver.findElement(By.xpath("//li[@class='todo']//label[text()='Example Task 2']")).isDisplayed());
		}
		else if (string.equals("Active")) {
			Assert.assertEquals(driver.findElements(By.className("completed")).size(), 0);
			Assert.assertTrue(driver.findElement(By.xpath("//li[@class='todo']//label[text()='Example Task 2']")).isDisplayed());
		}
		else if (string.equals("Completed")){
			Integer iCount = new Integer(0);
			iCount = driver.findElements(By.xpath("//ul[@class=\"todo-list\"]/li")).size();
			
			Integer iCount2 = new Integer(0);
			iCount2 = driver.findElements(By.className("completed")).size(); 
			Assert.assertEquals(iCount2, iCount);
			Assert.assertTrue(driver.findElement(By.xpath("//li[@class='todo completed']//label[text()='Example Task 1']")).isDisplayed());
			
		}
	}

	@When("completed tasks are cleared")
	public void completed_tasks_are_cleared() {
		WebElement ele = driver.findElement(By.className("clear-completed"));
		ele.click();		
	}

	@Then("the completed tasks are not displayed")
	public void the_completed_tasks_are_not_displayed() {
		Assert.assertEquals(driver.findElements(By.className("completed")).size(), 0);
	}

	@Then("the active tasks are displayed")
	public void the_active_tasks_are_displayed() {
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='todo']//label[text()='Example Task 2']")).isDisplayed());
	}

	@When("{string} task is deleted")
	public void task_is_deleted(String string) throws InterruptedException {
		Thread.sleep(1000);
		if (string.equals("Completed")) {
			WebElement ele = driver.findElement(By.xpath("//li[@class='todo completed']//button[@class='destroy']"));
			ele.click();
		}
		else if (string.equals("Active")) {	
			WebElement ele = driver.findElement(By.xpath("//li[@class='todo']//button[@class='destroy']"));			
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);    

		}
	}

	@Then("{string} tasks is not displayed")
	public void tasks_is_not_displayed(String string) throws InterruptedException {
		if (string.equals("Completed")) {
			Assert.assertEquals(driver.findElements(By.className("completed")).size(), 0);

		}
		else if (string.equals("Active")) {
			Assert.assertEquals(driver.findElements(By.xpath("//li[@class='todo']")).size(), 0);

		}
		Thread.sleep(1000);
	}

	@When("All the tasks are marked completed")
	public void all_the_tasks_are_marked_completed() {
		WebElement ele = driver.findElement(By.xpath("//label[@for=\"toggle-all\"]"));
		//ele.sendKeys(Keys.ENTER);
		Actions actions = new Actions(driver);
		actions.moveToElement(ele).click().perform();
	}

	@Then("the tasks are checked off from the list")
	public void the_tasks_are_checked_off_from_the_list() {
		Assert.assertEquals(driver.findElements(By.xpath("//li[@class='todo']")).size(), 0);
		Assert.assertEquals(driver.findElements(By.className("completed")).size(), 2);
	}


}

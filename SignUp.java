/**
 * @author Deep
 */
package sanityTests;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SignUp{
	public static WebDriver driver;
	
	@BeforeMethod
	public void NavigateToApplication(){
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Deep\\Downloads\\chromedriver.exe");
		driver = new ChromeDriver(); //Launches chrome browser with blank URL

		//Load the park-now web page
		driver.get("https://eu.park-now.com/");
		driver.manage().window().maximize();
		driver.findElement(By.linkText("Sign up")).click();
	}
	
	@Test(dataProvider = "testdata")
	public void register(String TestCase, String TestType, String country, String Package, String Purpose, String companyname, String	brn, String	firstname, String lastname, String email, String password, String repeatpassword, String mobilenumber, String licenseplate, String Errorcompanyname, String	Errorbrn, String Errorfirstname, String Errorlastname, String Erroremail, String Errorpassword, String Errorrepeatpassword, String Errormobilenumber, String Errorlicenseplate) throws InterruptedException{
		Boolean CompareString;
		//In case of no error expected or no data has been passed then <Blank> is used in data provider 
		String ConstantBlank = "<Blank>";
		//In case of personal use, Company name and BRN should be marked as unavailable in data provider 
		String ConstantNotAvailable = "<Unavailable>";

		int n = selectCountry(country);
		selectPackage(Package);
		
		WebDriverWait registrationWait = new WebDriverWait(driver,10);
		registrationWait.until(ExpectedConditions.presenceOfElementLocated(By.name("firstName")));
		
		//Select to use parknow for personal or company use
		selectSwitch(Purpose, country);
		
		//Now entering data to the web form using data provider
		CompareString = ConstantBlank.equals(firstname);
		if (CompareString) {
			driver.findElement(By.name("firstName")).clear();
		} else {
			driver.findElement(By.name("firstName")).sendKeys(firstname);
		}
		
		CompareString = ConstantBlank.equals(lastname);
		if (CompareString) {
			driver.findElement(By.name("lastName")).clear();
		} else {
			driver.findElement(By.name("lastName")).sendKeys(lastname);
		}

		CompareString = ConstantBlank.equals(email);
		if (CompareString) {
			driver.findElement(By.name("email")).clear();
		} else {
			driver.findElement(By.name("email")).sendKeys(email);
		}
		
		CompareString = ConstantBlank.equals(password);
		if (CompareString) {
			driver.findElement(By.name("password")).clear();
		} else {
			driver.findElement(By.name("password")).sendKeys(password);
		}
		
		CompareString = ConstantBlank.equals(repeatpassword);
		if (CompareString) {
			driver.findElement(By.name("confirmPassword")).clear();
		} else {
			driver.findElement(By.name("confirmPassword")).sendKeys(repeatpassword);
		}
		
		CompareString = ConstantBlank.equals(mobilenumber);
		if (CompareString) {
			driver.findElement(By.name("mobileNumber")).clear();
		} else {
			driver.findElement(By.name("mobileNumber")).sendKeys(mobilenumber);
		}

		CompareString = ConstantBlank.equals(licenseplate);
		if (CompareString) {
			driver.findElement(By.name("vrn1")).clear();
		} else {
			driver.findElement(By.name("vrn1")).sendKeys(licenseplate);
		}
		
		//First check if content not available
		CompareString = ConstantNotAvailable.equals(companyname);
		if (!(CompareString)){
			CompareString = ConstantBlank.equals(companyname);
			if (CompareString) {
				driver.findElement(By.name("companyName")).clear();
			} else {
				driver.findElement(By.name("companyName")).sendKeys(companyname);
			}
		}
		
		CompareString = ConstantNotAvailable.equals(brn);
		if (!(CompareString)){
			CompareString = ConstantBlank.equals(brn);
			if (CompareString) {
				driver.findElement(By.name("companyRegistrationNumber")).clear();
			} else {
				driver.findElement(By.name("companyRegistrationNumber")).sendKeys(brn);
			}
		}

		//Click Credit card option as a payment method
		clickCreditcard(country);
		
		//Validating the checkpoints using data provider expected result
		//Verify First name
		int ActualFirstNameError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[2]/div[1]/div/div[3]/div")).size();
		CompareString = ConstantBlank.equals(Errorfirstname);    
		if (CompareString) {
			Assert.assertEquals(0, ActualFirstNameError);
		} else {
			Assert.assertEquals(1, ActualFirstNameError);
			String ActualFirstNameMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[2]/div[1]/div/div[3]/div/span/span")).getText();
			Assert.assertEquals(Errorfirstname, ActualFirstNameMessage);
		}
		
		//Verify Last name
		int ActualLastNameError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[2]/div[2]/div/div[3]/div")).size();
		CompareString = ConstantBlank.equals(Errorlastname);
		if (CompareString) {
			Assert.assertEquals(0, ActualLastNameError);
		} else {
			Assert.assertEquals(1, ActualLastNameError);
			String ActualLastNameMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[2]/div[2]/div/div[3]/div/span/span")).getText();
			Assert.assertEquals(Errorlastname, ActualLastNameMessage);
		}
		
		//Verify Email
		int ActualEmailError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[3]/div/div/div[3]/div")).size();
		CompareString = ConstantBlank.equals(Erroremail);
		if (CompareString) {
			Assert.assertEquals(0, ActualEmailError);
		} else {
			Assert.assertEquals(1, ActualEmailError);
			String ActualEmailMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[3]/div/div/div[3]/div/span/span")).getText();
			Assert.assertEquals(Erroremail, ActualEmailMessage);
		}
		
		//Verify Password
		int ActualPasswordError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[4]/div[1]/div/div[3]/div")).size();
		CompareString = ConstantBlank.equals(Errorpassword);
		if (CompareString) {
			Assert.assertEquals(0, ActualPasswordError);
		} else {
			Assert.assertEquals(1, ActualPasswordError);
			String ActualPasswordMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[4]/div[1]/div/div[3]/div/span/span")).getText();
			Assert.assertEquals(Errorpassword, ActualPasswordMessage);
		}

		//Verify Repeat Password
		int ActualRepeatPassError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[4]/div[2]/div/div[3]/div")).size();
		CompareString = ConstantBlank.equals(Errorrepeatpassword);
		if (CompareString) {
			Assert.assertEquals(0, ActualRepeatPassError);
		} else {
			Assert.assertEquals(1, ActualRepeatPassError);
			String ActualRepeatPassMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[4]/div[2]/div/div[3]/div/span/span")).getText();
			Assert.assertEquals(Errorrepeatpassword, ActualRepeatPassMessage);
		}
		
		//Verify Mobile Number
		int ActualMobileNoError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[5]/div/div/div/data-pn-phone-number-component/div/div[2]/div[2]/div")).size();
		CompareString = ConstantBlank.equals(Errormobilenumber);
		if (CompareString) {
			Assert.assertEquals(0, ActualMobileNoError);
		} else {
			Assert.assertEquals(1, ActualMobileNoError);
			String ActualMobileNoMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[5]/div/div/div/data-pn-phone-number-component/div/div[2]/div[2]/div/span/span")).getText();
			Assert.assertEquals(Errormobilenumber, ActualMobileNoMessage);
		}	

		//Verify License plate
		int ActualLicensePlateError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[6]/div/div/div/data-pn-licence-plate-component/div/div[2]/div/div[2]/div")).size();
		CompareString = ConstantBlank.equals(Errorlicenseplate);
		if (CompareString) {
			Assert.assertEquals(0, ActualLicensePlateError);
		} else {
			Assert.assertEquals(1, ActualLicensePlateError);
			String ActualLicenseMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[6]/div/div/div/data-pn-licence-plate-component/div/div[2]/div/div[2]/div/span/span")).getText();
			Assert.assertEquals(Errorlicenseplate, ActualLicenseMessage);
		}	
		
		//Verify Company Name
		int ActualCompanyNameError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[1]/div[1]/div/div[3]/div")).size();
		CompareString = ConstantBlank.equals(Errorcompanyname);
		if (CompareString) {
			Assert.assertEquals(0, ActualCompanyNameError);
		} else {
			Assert.assertEquals(1, ActualCompanyNameError);
			String ActualCompanyNameMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[1]/div[1]/div/div[3]/div/span/span")).getText();
			Assert.assertEquals(Errorcompanyname, ActualCompanyNameMessage);
		}	

		//Verify Brn
		int ActualBrnError = driver.findElements(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[1]/div[2]/div/div[2]/div")).size();
		CompareString = ConstantBlank.equals(Errorbrn);
		if (CompareString) {
			Assert.assertEquals(0, ActualBrnError);
		} else {
			Assert.assertEquals(1, ActualBrnError);
			String ActualBrnMessage = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[" + n +"]/form/div[1]/div[2]/div/div[2]/div/span/span")).getText();
			Assert.assertEquals(Errorbrn, ActualBrnMessage);
		}	
		
	}

	@AfterMethod
	public void closeBrowser(){
		driver.close();
	}

	//Defining excel file for data driven testing as a data provider
	@DataProvider(name = "testdata")
	public Object [] [] readExcel() throws BiffException, IOException {

		//JXL is used to work with xls sheets and Apache POI can be used for xlsx worksheets.
		File inputFile = new File("InputData/input.xls");
		Workbook w = Workbook.getWorkbook(inputFile);
		Sheet s = w.getSheet("Sheet1");
		int rows = s.getRows();
		int columns = s.getColumns();
		rows = rows - 1;

		String inputData [] [] = new String [rows] [columns];
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				Cell c = s.getCell(j, i+1);
				inputData [i][j] = c.getContents();
			}
		}
		return inputData;
		}

//Select different countries according to the input data
	public int selectCountry(String country){
		// sequence number for xpath - different for different country / package
		int n;
		//wait until element is located once page is fully loaded
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.name("country")));
		//Select countries from dropdown
		driver.findElement(By.name("country")).click();
		switch(country) {
		case "AT":
			driver.findElement(By.xpath("//*[@id='reg-country-select']/div[2]/div/div/div[2]/div")).click();
			n = 3;
			break;
		case "CH":
			driver.findElement(By.xpath("//*[@id='reg-country-select']/div[2]/div/div/div[5]/div")).click();
			n = 2;
			break;
		case "DE":
			driver.findElement(By.xpath("//*[@id='reg-country-select']/div[2]/div/div/div[4]/div")).click();
			n = 3;
			break;
		case "FR":
			driver.findElement(By.xpath("//*[@id='reg-country-select']/div[2]/div/div/div[3]/div")).click();
			n = 2;
			break;
		default :
			driver.findElement(By.xpath("//*[@id='reg-country-select']/div[2]/div/div/div[4]/div")).click();
			n = 3;
			break;
		}
		return n;
	}
	
//Select different packages according to the input data
	public void selectPackage(String package1){
		//Default processing in case of Silver and Discovery package
		switch(package1) {
		case "Gold":
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/form/pn-package-table/div/div/table/tbody/tr[7]/td[3]/button")).click();
			break;
		default :
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[1]/div/form/pn-package-table/div/div/table/tbody/tr[7]/td[2]/button")).click();
			break;
		}
	}
	
//Click credit card payment option
	public void clickCreditcard(String country){
		switch(country) {
		case "FR":
		case "CH":
			driver.findElement(By.name("firstName")).sendKeys();
			//driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[3]/div/div[2]/form/div[3]/div/div/div")).click();
			break;
		default :
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[3]/div/div[2]/form/div[3]/div/div/div[3]")).click();
			driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[3]/div/div[4]/form/div[5]/div/button")).click();
			break;
		}
	}
	
//Select the purpose of use - By default 'personal'
	public void selectSwitch(String purpose, String country) {
		switch(purpose) {
		case "C":
			if (country.equals("DE") || country.equals("AT")) {
				driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[3]/div[3]/div[2]/div/div/div[2]")).click();
			} else {
				driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[2]/div/div[2]/div[3]/div[2]/div/div/div[2]")).click();
			}
			break;
		}
	}
}
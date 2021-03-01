package com.oppenheimer;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.Assert;
import org.testng.annotations.Ignore;

import com.oppenheimer.config.BaseAPI;
import com.oppenheimer.config.ConfigurationManager;
import com.oppenheimer.config.TestTags;

import io.qameta.allure.Epic;
import io.restassured.http.ContentType;

@Epic("EPIC-YYY : Calculator API ")
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class OppenHeimerCalculatorTest extends BaseAPI {

	@BeforeEach
	public void setup() {
		configuration = ConfigurationManager.getConfiguration();
		basePath = configuration.health();
	}

	@AfterEach
	void tearDown() {
		basePath = configuration.basePath();
	}

	@Test
	@Tag(TestTags.HEALTH)
	@DisplayName("Working class hero details")
	@Order(1)
	// @DisplayName("Should be able to hit the tax relief ; dispense end-point")

	void healthCheck() {
		when().get("/health").then().statusCode(SC_OK).body("status", is("UP"));
	}

	@Test
	@Tag(TestTags.CLERK)
	@Tag(TestTags.CALCULATOR)
	@DisplayName("insert person")
	@Order(2)

	void insertPersonPost() {
		PersonBulider personBulider = new PersonBulider(20);
		basePath = configuration.basePath();
		given().contentType(ContentType.JSON).body(personBulider).when().post("/insert").then().statusCode(SC_ACCEPTED)
				.body(is("Alright"));

	}

	@Test
	@Tag(TestTags.CLERK)
	@Tag(TestTags.CALCULATOR)
	@DisplayName("insert person with worng DOB")
	@Order(8)

	void insertPersonWithInvalidDobPost() {
		PersonBulider personBulider = new PersonBulider(20);
		personBulider.setBirthday("30021989");
		basePath = configuration.basePath();
		given().contentType(ContentType.JSON).body(personBulider).when().post("/insert").then()
				.statusCode(SC_BAD_REQUEST);
	}

	@Test
	@Tag(TestTags.CALCULATOR)
	@Tag(TestTags.CLERK)

	@DisplayName("insert multiple person")
	@Order(3)
	void insertPersonMultiplePost() {
		List persons = new ArrayList();
		PersonBulider personBulider1 = new PersonBulider(21);

		persons.add(personBulider1);
		PersonBulider personBulider2 = new PersonBulider(22);
		persons.add(personBulider2);

		basePath = configuration.basePath();
		given().contentType(ContentType.JSON).body(persons).when().post("/insertMultiple").then()
				.statusCode(SC_ACCEPTED).body(is("Alright"));

	}

	@Ignore
	@Test
	@Tag(TestTags.CLERK)
	@Tag(TestTags.CALCULATOR)
	@DisplayName("insert from CSV")
	@Order(5)
	void insertPersonMultiUsingCSVFile() {
		URL url = this.getClass().getResource("/UploadFile.csv");
		File csvFile = new File(url.getFile());
		basePath = configuration.basePath();
		given().multiPart("file", csvFile, "application/octet-stream").when()
				.post("/uploadLargeFileForInsertionToDatabase").then().statusCode(SC_OK)
				.body(is("Successfully uploaded"));

	}

	@Ignore
	@Test
	@Tag(TestTags.CLERK)
	@Tag(TestTags.CALCULATOR)
	@DisplayName("insert from CSV with invalid data")
	@Order(5)
	void insertPersonMultiUsingCSVFileWithInvalidData() {
		URL url = this.getClass().getResource("/InvalidData.csv");
		File csvFile = new File(url.getFile());
		basePath = configuration.basePath();
		given().multiPart("file", csvFile, "application/octet-stream").when()
				.post("/uploadLargeFileForInsertionToDatabase").then().statusCode(SC_BAD_REQUEST)
				.body(is("Successfully uploaded"));

	}

	@Ignore
	@Test
	@Tag(TestTags.CLERK)
	@Tag(TestTags.CALCULATOR)
	@DisplayName("invalid file type")
	@Order(5)
	void inserWithDeffFileType() {
		URL url = this.getClass().getResource("/api.properties");
		File csvFile = new File(url.getFile());
		basePath = configuration.basePath();
		given().multiPart("file", csvFile, "application/octet-stream").when()
				.post("/uploadLargeFileForInsertionToDatabase").then().statusCode(SC_BAD_REQUEST)
				.body(is("Successfully uploaded"));

	}
	
	@Test
	@Tag(TestTags.BOOKKEEPER)
	@Tag(TestTags.GOVERNER)
	@DisplayName("ValidateTaxRelief: Fetch from API the TexReleif calculated.")
	@Order(4)
	void ValidateTaxReliefGet() {
		basePath = configuration.basePath();
		TaxRelief[] taxReliefs = when().get("/taxRelief").then().statusCode(SC_OK).extract().as(TaxRelief[].class);
		Assert.assertTrue(taxReliefs.length > 0);
	}
}

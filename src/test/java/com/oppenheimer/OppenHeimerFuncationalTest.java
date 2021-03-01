package com.oppenheimer;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testng.Assert;

import com.oppenheimer.config.BaseAPI;
import com.oppenheimer.config.ConfigurationManager;
import com.oppenheimer.config.TestTags;

import io.qameta.allure.Epic;
import io.restassured.http.ContentType;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@Epic("EPIC-XXX : Functional Testing ")
public class OppenHeimerFuncationalTest extends BaseAPI {

	@BeforeEach
	public void setup() {
		configuration = ConfigurationManager.getConfiguration();
		basePath = configuration.basePath();
	}

	@AfterEach
	void tearDown() {
		basePath = configuration.basePath();
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@Tag(TestTags.DEV)
	@DisplayName("Test person tax relief age < 18 & gender F")
	@Order(1)
	void taxReliefPersonAgeLessThen18YearsGenderF() {
		PersonBulider person = new PersonBulider(17);
		basePath = configuration.basePath();
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@DisplayName("Test person tax relief age < 18 & gender M")
	@Order(2)
	void taxReliefPersonAgeLessThen18YearsGenderM() {
		PersonBulider person = new PersonBulider(16);
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@DisplayName("Test person tax relief age = 18 & Gender F")
	@Order(3)
	void taxReliefPersonAge18YearsGenderF() {
		PersonBulider person = new PersonBulider(18);
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@DisplayName("Test person tax relief age= 18 & Gender M")
	@Order(4)
	void taxReliefPersonAge18YearsPost() {
		PersonBulider person = new PersonBulider(18, "M");
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@DisplayName("Test person tax relief age < 35 & gender F")
	@Order(5)
	void taxReliefPersonAgeLessThen35YearsGenderF() {
		PersonBulider person = new PersonBulider(34);
		basePath = configuration.basePath();
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@DisplayName("Test person tax relief age < 35 & gender M")
	@Order(6)
	void taxReliefPersonAgeLessThen35YearsGenderM() {
		PersonBulider person = new PersonBulider(33);
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@DisplayName("Test person tax relief age = 35 & Gender M")
	@Order(7)
	void taxReliefPersonAge35YearsGenderM() {
		PersonBulider person = new PersonBulider(35);
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@Tag(TestTags.E2E)
	@DisplayName("Test person tax relief age= 35 & Gender F")
	@Order(8)
	void taxReliefPersonAge35YearsF() {
		PersonBulider person = new PersonBulider(35, "F");
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@Tag(TestTags.E2E)
	@DisplayName("Test person tax relief age= 50 & Gender F")
	@Order(9)
	void taxReliefPersonAge50YearsF() {
		PersonBulider person = new PersonBulider(50, "F");
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@Tag(TestTags.E2E)
	@DisplayName("Test person tax relief age= 50 & Gender M")
	@Order(10)
	void taxReliefPersonAge50YearsM() {
		PersonBulider person = new PersonBulider(50, "M");
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@Tag(TestTags.E2E)
	@DisplayName("Test person tax relief age= 75 & Gender M")
	@Order(11)
	void taxReliefPersonAge75YearsM() {
		PersonBulider person = new PersonBulider(75, "M");
		insertHero(person);
		validateTaxRelief(person);
	}

	@Test
	@Tag(TestTags.FUNCTIONAL)
	@Tag(TestTags.E2E)
	@DisplayName("Test person tax relief age= 75 & Gender F")
	@Order(12)
	void taxReliefPersonAge75YearsF() {
		PersonBulider person = new PersonBulider(75, "F");
		insertHero(person);
		validateTaxRelief(person);
	}

	private void validateTaxRelief(PersonBulider person) {
		boolean recodeFound = false;

		TaxRelief[] taxReliefs = when().get("/taxRelief").then().statusCode(SC_OK).extract().as(TaxRelief[].class);
		if (taxReliefs.length > 0) {
			for (int i = 0; i < taxReliefs.length; i++) {
				if (person != null && taxReliefs[i].getName().equals(person.getName())) {
					String taxRel = PersonBulider.getTaxRelief(person.getBirthday(), person.getSalary(),
							person.getTax(), person.getGender());
					Assert.assertEquals(taxRel, taxReliefs[i].relief);
					recodeFound = true;
				}
			}
			Assert.assertTrue(recodeFound);

		}
	}

	private void insertHero(PersonBulider person) {
		given().contentType(ContentType.JSON).body(person).when().post("/insert").then().statusCode(SC_ACCEPTED)
				.body(is("Alright"));
	}

}

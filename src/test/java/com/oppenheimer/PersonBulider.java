package com.oppenheimer;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PersonBulider {
	String birthday = "26011950";
	String gender = "M";
	String name = "Modi";
	String natid = "C1234567892";
	String salary = "5000.95";
	String tax = "345.75";
	@JsonIgnore
	private static final DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern("ddMMYYYY");

	public PersonBulider(int seed) {
		this.setBirthday(seed);
		if (seed % 2 == 0) {
			this.setGender("M");
			setName("Person" + seed);
		} else {
			this.setGender("F");
		}
		setName("Person" + seed + "-" + getGender() + "-" + System.nanoTime());
		// setName("Person"+seed+"+getGender()+"+"+System.nanoTime();");

		setNatid(seed);
		setSalary(seed);
		setTax(getSalary());
	}

	public PersonBulider(int seed, String gender) {
		new PersonBulider(seed);
		setGender(gender);
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(int seed) {
		// Local date time instance
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusYears(seed);
		// Get formatted String
		String ldtString = FOMATTER.format(localDateTime);
		this.birthday = ldtString;
	}

	public void setBirthday(String dob) {

		this.birthday = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNatid() {
		return natid;
	}

	public void setNatid(int seed) {
		String natId = ("C3" + getGender() + getBirthday());
		this.natid = natId;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(int seed) {
		int sal = new Random().nextInt(99999) + seed;
		this.salary = sal + "";
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String sal) {
		Double taxCal = Integer.parseInt(sal) * 0.08;
		DecimalFormat df = new DecimalFormat("0.00");
		/// rounding off to two decimal points
		// val n = BigDecimal(taxCal)
		// n.setScale(2, BigDecimal.RoundingMode.HALF_UP)

		this.tax = df.format(taxCal);
	}

	public static String getTaxRelief(String dob, String salary, String tax, String gender) {
		Period period = Period
				.between(LocalDate.parse(dob.substring(4) + "-" + dob.substring(2, 4) + "-" + dob.substring(0, 2),
						DateTimeFormatter.ofPattern("yyyy-MM-dd")), LocalDate.now());
		Double salaryInt = Double.parseDouble(salary);
		Double taxPaid = Double.parseDouble(tax);
		if (period.getYears() <= 18) {
			return caluculatTaxRelief(salaryInt, taxPaid, gender, 1);
		} else if (period.getYears() <= 35) {
			return caluculatTaxRelief(salaryInt, taxPaid, gender, 0.8);
		} else if (period.getYears() <= 50) {
			return caluculatTaxRelief(salaryInt, taxPaid, gender, 0.5);
		} else if (period.getYears() <= 75) {
			return caluculatTaxRelief(salaryInt, taxPaid, gender, 0.367);
		} else if (period.getYears() > 75) {
			return caluculatTaxRelief(salaryInt, taxPaid, gender, 0.05);
		}
		return "";
	}

	private static String caluculatTaxRelief(Double salaryInt, Double taxPaid, String gender, double var) {
		Double taxRel = 0d;
		DecimalFormat df = new DecimalFormat("0.00");

		if (gender.equalsIgnoreCase("M")) {
			taxRel = ((salaryInt - taxPaid) * var);
		} else {
			taxRel = (salaryInt - taxPaid) * var + 500;
		}
		BigDecimal bd = new BigDecimal(Double.toString(taxRel));
		bd = bd.setScale(4, BigDecimal.ROUND_HALF_DOWN);
		String taxRelStr = df.format(bd);
		String[] dec = taxRelStr.split("\\.");
		if (Integer.parseInt(dec[1]) < 50) {
			return dec[0] + ".00";
		} else {
			return taxRelStr;
		}
	}

	@Override
	public String toString() {
		return "{" + "birthday:'" + birthday + '\'' + ", gender:'" + gender + '\'' + ", name:'" + name + '\''
				+ ", natid:'" + natid + '\'' + ", salary:'" + salary + '\'' + ", tax:'" + tax + '\'' + '}';
	}

	public static void main(String[] args) {

		for (int i = 1; i < 10; i++) {
			PersonBulider personBulider = new PersonBulider(i);
			System.out.println(personBulider);
		}

	}
}

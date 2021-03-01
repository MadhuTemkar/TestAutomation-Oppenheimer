#run below commad in project directory 
##To generate html report and automatically open it in a web browser, run the following command:
________________________________________________________________________________________________________________________

    mvn clean test -D maven.test.failure.ignore=true allure:report 

	mvn allure:serve
__________________________________________________________________________________________
##Run the following command to open the generated reports in browser.
__________________________________________________________________________________________
mvn clean test -Dmaven.test.failure.ignore=true allure:report  allure:serve

___________________________________________________________________________________________
##Run test case by tag name
__________________________________________________________________________________________

mvn  test -D groups=calculator,health -D maven.test.failure.ignore=true allure:report  allure:serve

mvn  test -D groups=functional -Dmaven.test.failure.ignore=true allure:report allure:serve

##Only selenium Test case run 

Step 1: Open pom.xml
Step 2: un-comment below code  in pom.xml
'''
 <!--   <plugin>
		    <groupId>org.apache.maven.plugins</groupId>
		    <artifactId>maven-surefire-plugin</artifactId>
		    <version>2.18.1</version>
		    <configuration>
		     <suiteXmlFiles>
		      <suiteXmlFile>testreport.xml</suiteXmlFile>
		     </suiteXmlFiles>
		    </configuration>
		   </plugin> -->
'''
Step 3: run below commad in project directory 
mvn  test -D groups=ui-automation  -Dmaven.test.failure.ignore=true allure:report allure:serve
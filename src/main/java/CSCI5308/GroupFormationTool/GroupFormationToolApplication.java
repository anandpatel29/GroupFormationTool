package CSCI5308.GroupFormationTool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.PropertyConfigurator;

@SpringBootApplication
public class GroupFormationToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupFormationToolApplication.class, args);
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
	}

}

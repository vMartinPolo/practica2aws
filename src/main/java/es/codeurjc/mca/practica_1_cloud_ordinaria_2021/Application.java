package es.codeurjc.mca.practica_1_cloud_ordinaria_2021;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;

@SpringBootApplication
@EnableRdsInstance(dbInstanceIdentifier="${cloud.aws.rds.dbInstanceIdentifier}", password="${cloud.aws.rds.events.password}")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}

}

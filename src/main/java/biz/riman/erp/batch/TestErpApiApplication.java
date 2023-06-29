package biz.riman.erp.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class TestErpApiApplication {

	public static void main(String[] args) {
		final var context = SpringApplication.run(TestErpApiApplication.class, args);
		System.exit(SpringApplication.exit(context));
	}

}

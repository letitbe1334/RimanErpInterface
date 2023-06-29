package biz.riman.erp.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableBatchProcessing
public class TestErpApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(TestErpApiApplication.class);

	public static void main(String[] args) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
//	    SpringApplication.run(TestErpApiApplication.class, args);
//		final var context = SpringApplication.run(TestErpApiApplication.class, args);
//		System.exit(SpringApplication.exit(context));
        

        SpringApplication app = new SpringApplication(TestErpApiApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        ConfigurableApplicationContext ctx = app.run(args);
        JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);

        if (args.length > 0) {
            for (String arg : args) {
                logger.error("## JOB NAME ARGUMENTS {}", arg);
                Job job = ctx.getBean(arg, Job.class);
                jobLauncher.run(job, new JobParameters());
            }
        } else {
            logger.error("배치 잡 이름을 입력해 주세요. EX) {}", "java -jar app.jar updateTop100CoinPriceJob");
        }

        System.exit(0);
	}

}

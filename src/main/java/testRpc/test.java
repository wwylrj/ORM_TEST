package testRpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class test {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(
				testRpc.class, args);
		testRpc test = run.getBean(testRpc.class);
		test.printMessage();

	}
}

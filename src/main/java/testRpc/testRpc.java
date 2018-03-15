package testRpc;

import org.spring.springboot.DealCountService;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

@Component
public class testRpc {

	@Reference(version = "1.0.0")
	DealCountService h;

	public void printMessage() {
		int message = h.addNumber(5, 4);
		System.out.println(message);
	}

}

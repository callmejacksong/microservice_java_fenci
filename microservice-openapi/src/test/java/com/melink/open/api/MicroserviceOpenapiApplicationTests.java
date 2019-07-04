package com.melink.open.api;


import com.melink.open.api.feignClient.PicturePromotionClient;
import com.melink.open.api.service.PlusEmoticionPromotionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroserviceOpenapiApplicationTests {

	@Autowired
	private PicturePromotionClient picturePromotionClient;

	@Autowired
	private PlusEmoticionPromotionService plusEmoticionPromotionService;
	@Test
	public void contextLoads() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

	}

	@Test
	public void fanshe() throws NoSuchFieldException, IllegalAccessException {
		ArrayList<String> list = new ArrayList<>();
		list.add("854d6bf2124211e999326c92bf61e2d8");
		list.add("74aa21409c7411899326c92bf61e2d8");

		System.out.println("aaa");


	}


}


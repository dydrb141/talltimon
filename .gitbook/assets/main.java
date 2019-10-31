package com.designpattern.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

	/*	for (int i = 0; i < 10; i++) {
			executorService.submit(() -> {
				System.out.println(SoccerCoach.getInstance());
			});
		}*/

		for (int i = 0; i < 10; i++) {
			executorService.submit(() -> {
				System.out.println(SoccerCoarchLazyHolder.getInstance());
			});
		}



		executorService.shutdown();

		System.out.println("감독은 하나다" +  SoccerCoarchLazyHolder.getInstance());
	}

}

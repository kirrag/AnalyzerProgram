package ru.netology;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
	public static String generateText(String letters, int length) {
    	Random random = new Random();
		StringBuilder text = new StringBuilder();
    	for (int i = 0; i < length; i++) {
			text.append(letters.charAt(random.nextInt(letters.length())));
		}
		return text.toString();
  	}

	public static BlockingQueue<String> queueA = new ArrayBlockingQueue<>(100);
	public static BlockingQueue<String> queueB = new ArrayBlockingQueue<>(100);
	public static BlockingQueue<String> queueC = new ArrayBlockingQueue<>(100);

	public static void main(String[] args) {
		Thread getText = new Thread(() -> {
			String text;
			for(int i = 0; i < 10_000; i++) {
				text = generateText("abc", 100_000);

				try{
					queueA.put(text);
					System.out.println(text);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					queueB.put(text);
					System.out.println(text);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				try {
					queueC.put(text);
					System.out.println(text);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread analyzeA = new Thread(() -> {
			String text;
			int count = 0;
			int max = 0;
			while (true) {
				try {
					text = queueA.take();
					for (int i = 0; i < text.length(); i++) {
						if (text.charAt(i) == 'a') count++;
					}
					if (count > max) max = count;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(max);
		});

		Thread analyzeB = new Thread(() -> {
			String text;
			int count = 0;
			int max = 0;
			while (true) {
				try {
					text = queueA.take();
					for (int i = 0; i < text.length(); i++) {
						if (text.charAt(i) == 'b') count++;
					}
					if (count > max) max = count;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(max);
		});

		Thread analyzeC = new Thread(() -> {
			String text;
			int count = 0;
			int max = 0;
			while (true) {
				try {
					text = queueA.take();
					for (int i = 0; i < text.length(); i++) {
						if (text.charAt(i) == 'c') count++;
					}
					if (count > max) max = count;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(max);
		});

		getText.start();
		analyzeA.start();
		analyzeB.start();
		analyzeC.start();
		/*	
		try {
			getText.join();
			analyzeA.join();
			analyzeB.join();
			analyzeC.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
}

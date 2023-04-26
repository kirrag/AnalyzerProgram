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

	public static int maxA = 0;
	public static int maxB = 0;
	public static int maxC = 0;

	public static void main(String[] args) throws InterruptedException {

		Thread getText = new Thread(() -> {
			String text;
			for (int i = 0; i < 10_000; i++) {
				text = generateText("abc", 100_000);

				try {
					queueA.put(text);
				} catch (InterruptedException e) {
					return;
				}

				try {
					queueB.put(text);
				} catch (InterruptedException e) {
					return;
				}

				try {
					queueC.put(text);
				} catch (InterruptedException e) {
					return;
				}

			}
		});

		Thread analyzeA = new Thread(() -> {
			String text;
			try {
				for (int i = 0; i < 10_000; i++) {
					int count = 0;
					text = queueA.take();
					for (int j = 0; j < text.length(); j++) {
						if (text.charAt(j) == 'a') count++;
					}
					if (count > maxA) maxA = count;
				}
			} catch (InterruptedException e) {
				return;
			}
		});

		Thread analyzeB = new Thread(() -> {
			String text;
			try {
				for (int i = 0; i < 10_000; i++) {
					int count = 0;
					text = queueB.take();
					for (int j = 0; j < text.length(); j++) {
						if (text.charAt(j) == 'b') count++;
					}
					if (count > maxB) maxB = count;
				}
			} catch (InterruptedException e) {
				return;
			}
		});

		Thread analyzeC = new Thread(() -> {
			String text;
			try {
				for (int i = 0; i < 10_000; i++) {
					int count = 0;
					text = queueC.take();
					for (int j = 0; j < text.length(); j++) {
						if (text.charAt(j) == 'c') count++;
					}
					if (count > maxC) maxC = count;
				}
			} catch (InterruptedException e) {
				return;
			}
		});

		getText.start();
		analyzeA.start();
		analyzeB.start();
		analyzeC.start();

		try {
			getText.join();
			analyzeA.join();
			analyzeB.join();
			analyzeC.join();
		} catch (InterruptedException e) {
			return;
		}

		System.out.println(maxA);
		System.out.println(maxB);
		System.out.println(maxC);
	}
}

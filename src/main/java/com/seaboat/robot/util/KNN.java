package com.seaboat.robot.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class KNN {

	public final static int K = 5;

	// TODO reader should be cached in order to speed up.
	private static int[] readArray(String fileName) {
		int arr[] = new int[32 * 32];
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader buffer = new BufferedReader(reader);
			for (int index = 0; index < 32; index++) {
				String str = buffer.readLine();
				int length = str.length();
				for (int i = 0; i < length; i++) {
					String c = str.substring(i, i + 1);
					arr[32 * index + i] = Integer.parseInt(c);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arr;
	}

	private static double calDistance(int[] a, int[] b) {
		double result = 0.0;
		int temp = 0;
		for (int i = 0; i < a.length; i++) {
			temp += (a[i] - b[i]) * (a[i] - b[i]);
		}
		result = Math.sqrt(temp);
		return result;
	}

	private static int[] transformImage(BufferedImage buffer) {
		int[] rgb = new int[3];
		int width = buffer.getWidth();
		int height = buffer.getHeight();
		int[] array = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int i = buffer.getRGB(x, y);
				rgb[0] = (i & 0xff0000) >> 16;
				rgb[1] = (i & 0xff00) >> 8;
				rgb[2] = (i & 0xff);
				if (rgb[0] < 10 && rgb[1] < 10 && rgb[2] < 10)
					array[y * width + x] = 1;
				else
					array[y * width + x] = 0;
			}
		}
		return array;
	}

	public static int predict(BufferedImage buffer, String path) {
		int[] array = transformImage(buffer);
		int result = 0;
		double dis[] = new double[K];
		int num[] = new int[K];
		for (int index = 0; index < K; index++) {
			dis[index] = 32;
			num[index] = -1;
		}
		for (int i = 0; i <= 9; i++) {
			for (int j = 0; j < 100; j++) {
				int temp_arr[] = readArray(path + "/TrainingData/" + i + "_" + j + ".txt");
				double temp_dis = calDistance(array, temp_arr);
				for (int k = 0; k < K; k++) {
					if (temp_dis < dis[k]) {
						dis[k] = temp_dis;
						num[k] = i;
						break;
					}
				}
			}
		}
		int count[] = new int[10];
		for (int i = 0; i < 10; i++)
			count[i] = 0;
		for (int i = 0; i < K; i++) {
			if (num[i] != -1)
				count[num[i]]++;
		}
		int max = 0;
		for (int i = 0; i < 10; i++) {
			if (count[i] > max) {
				max = count[i];
				result = i;
			}
		}
		return result;
	}
}

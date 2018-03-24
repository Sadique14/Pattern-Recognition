package partitionalClustering;

import java.io.File;
import java.util.Scanner;
/**
 * Pattern Recognition Laboratory
 * 
 * @author Md. Farhan Sadique
 * Student ID: 130238
 */
public class ClusteringMain {

	public static void main(String[] args) {
		File file = new File("samples.txt");
		try {
			Scanner scanner = new Scanner(file);
			int numOfSamples = scanner.nextInt();
			Sample samples[] = new Sample[numOfSamples];
			for (int i = 0; i < numOfSamples; i++) {
				samples[i] = new Sample(scanner.nextDouble(), scanner.nextDouble());
			}
			scanner.close();
			
			System.out.println("Partitional Clustering");
			System.out.println(".......................");
			System.out.println("Enter 1: Forgy's Algorithm");
			System.out.println("Enter 2: K-means Algorithm");
			Scanner scanner2 = new Scanner(System.in);
			int flag = scanner2.nextInt();
			System.out.print("k = ");
			int k = scanner2.nextInt();
			if (k > numOfSamples) {
				System.out.println("Error...");
			}
			else {
				if(flag == 1) {
					ForgyAlgorithm forgyAlgorithm = new ForgyAlgorithm(samples, numOfSamples);
					forgyAlgorithm.clustering(k);
				}
				else if(flag == 2) {
					KmeansAlgorithm kmeansAlgorithm = new KmeansAlgorithm(samples, numOfSamples);
					kmeansAlgorithm.clustering(k);
				}
			}
			scanner2.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

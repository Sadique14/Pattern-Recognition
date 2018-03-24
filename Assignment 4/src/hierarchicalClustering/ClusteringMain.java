package hierarchicalClustering;
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
			System.out.println("Hierarchical Clustering");
			System.out.println(".......................");
			System.out.println("Enter 1: Single-Linkage Algorithm");
			System.out.println("Enter 2: Complete-Linkage Algorithm");
			System.out.println("Enter 3: Average-Linkage Algorithm");
			System.out.println("Enter 4: Ward's Method");
			Scanner scanner2 = new Scanner(System.in);
			int flag = scanner2.nextInt();
			scanner2.close();
			if(flag == 1) {
				SingleLinkageAlgorithm singleLinkageAlgorithm = new SingleLinkageAlgorithm(samples, numOfSamples);
				singleLinkageAlgorithm.clustering();
			}
			else if(flag == 2) {
				CompleteLinkageAlgorithm completeLinkageAlgorithm = new CompleteLinkageAlgorithm(samples, numOfSamples);
				completeLinkageAlgorithm.clustering();
			}
			else if(flag == 3) {
				AverageLinkageAlgorithm averageLinkageAlgorithm = new AverageLinkageAlgorithm(samples, numOfSamples);
				averageLinkageAlgorithm.clustering();
			}
			else if(flag == 4) {
				WardMethod wardMethod = new WardMethod(samples, numOfSamples);
				wardMethod.clustering();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
package partitionalClustering;
import java.util.Scanner;
/**
 * Pattern Recognition Laboratory
 * 
 * @author Md. Farhan Sadique
 * Student ID: 130238
 */
public class ForgyAlgorithm {

	Sample[] samples;
	int numOfSamples;
	public ForgyAlgorithm(Sample[] samples, int numOfSamples)
	{
		this.samples = samples;
		this.numOfSamples = numOfSamples;
	}
	//main algorithm
	public void clustering(int k) {
		boolean stop = false;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 1: Random seed points (first k samples)\nEnter 2: Choose seed points");
		int flag = scanner.nextInt();
		//initialization of clusters
		Cluster[] clusters = new Cluster[k];
		double[] oldCentroidX = new double[k];
		double[] oldCentroidY = new double[k];
		if (flag == 1) {
			for (int i = 0; i < k; i++) {
				clusters[i] = new Cluster(numOfSamples);
				clusters[i].setCentroid(new Sample(samples[i].getX(), samples[i].getY()));
				oldCentroidX[i] = clusters[i].getCentroid().getX();
				oldCentroidY[i] = clusters[i].getCentroid().getY();
			}
		}
		else if (flag == 2) {
			for (int i = 0; i < k; i++) {
				int seedPoint = scanner.nextInt();
				if (seedPoint > numOfSamples) {
					System.out.println("Error...");
					stop = true;
				}
				else {
					seedPoint--;
					clusters[i] = new Cluster(numOfSamples);
					clusters[i].setCentroid(new Sample(samples[seedPoint].getX(), samples[seedPoint].getY()));
					oldCentroidX[i] = clusters[i].getCentroid().getX();
					oldCentroidY[i] = clusters[i].getCentroid().getY();
				}
			}
		}
		scanner.close();
		if (!stop) {
			System.out.println("\nForgy's Algorithm");
			System.out.println("....................");
			int step = 1;
			while(true) {
				System.out.println("Step "+step);
				step++;
				for (int i = 0; i < numOfSamples; i++) {
					int index = -1;
					double minDistance = 999999.0;
					for (int j = 0; j < k; j++) {
						double distance = Math.sqrt(Math.pow((samples[i].getX() - clusters[j].getCentroid().getX()), 2) + Math.pow((samples[i].getY() - clusters[j].getCentroid().getY()), 2));
						if (distance < minDistance) {
							minDistance = distance;
							index = j;
						}
					}
					clusters[index].add(samples[i]);
				}
				for (int i = 0; i < k; i++) {
					oldCentroidX[i] = clusters[i].getCentroid().getX();
					oldCentroidY[i] = clusters[i].getCentroid().getY();
					clusters[i].printCluster();
				}
				System.out.println();
				int flag2 = 0;
				for (int i = 0; i < k; i++) {
					double sumX = 0;
					double sumY = 0;
					for (int j = 0; j < clusters[i].getLength(); j++) {
						Sample temp = clusters[i].getElementsAt(j);
						sumX += temp.getX();
						sumY += temp.getY();
					}
					double meanX = sumX/clusters[i].getLength();
					double meanY = sumY/clusters[i].getLength();
					clusters[i].setCentroid(new Sample(meanX, meanY));
					if (oldCentroidX[i] == clusters[i].getCentroid().getX() && oldCentroidY[i] == clusters[i].getCentroid().getY()) {
						flag2++;
					}
				}
				if (flag2 == k)
				{
					break;
				}
				for (int i = 0; i < k; i++) {
					clusters[i].clear();
				}
			}
		}
	}
}

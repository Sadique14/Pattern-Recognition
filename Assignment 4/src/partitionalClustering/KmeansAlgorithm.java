package partitionalClustering;

import java.util.Scanner;

/**
 * Pattern Recognition Laboratory
 * 
 * @author Md. Farhan Sadique
 * @StudentID: 130238
 */
public class KmeansAlgorithm {

	Sample[] samples;
	int numOfSamples;
	public KmeansAlgorithm(Sample[] samples, int numOfSamples)
	{
		this.samples = samples;
		this.numOfSamples = numOfSamples;
	}
	//main algorithm
	public void clustering(int k) {
		boolean stop = false;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter 1: Random centroids (first k samples)\nEnter 2: Choose centroids");
		int flag = scanner.nextInt();
		//initialization of clusters
		Cluster[] clusters = new Cluster[k];
		if (flag == 1) {
			for (int i = 0; i < k; i++) {
				clusters[i] = new Cluster(numOfSamples);
				clusters[i].setCentroid(new Sample(samples[i].getX(), samples[i].getY()));
			}
		}
		else if (flag == 2) {
			for (int i = 0; i < k; i++) {
				int centroidPoint = scanner.nextInt();
				if (centroidPoint > numOfSamples) {
					System.out.println("Error...");
					stop = true;
				}
				else {
					centroidPoint--;
					clusters[i] = new Cluster(numOfSamples);
					clusters[i].setCentroid(new Sample(samples[centroidPoint].getX(), samples[centroidPoint].getY()));
				}	
			}
		}
		scanner.close();
		if (!stop) {
			System.out.println("\nk-means Algorithm");
			System.out.println("...................");
			//step 1 of the algorithm
			System.out.println("Step 1");
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
				//updating clusters
				double sumX = 0;
				double sumY = 0;
				for (int j = 0; j < clusters[index].getLength(); j++) {
					Sample temp = clusters[index].getElementsAt(j);
					sumX += temp.getX();
					sumY += temp.getY();
				}
				double meanX = sumX/clusters[index].getLength();
				double meanY = sumY/clusters[index].getLength();
				clusters[index].setCentroid(new Sample(meanX, meanY));
			}
			for (int i = 0; i < k; i++) {
				clusters[i].printCluster();
				clusters[i].clear();
			}
			//step 2 of the algorithm
			System.out.println("\nStep 2");
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
				clusters[i].printCluster();
			}
		}
	}
}

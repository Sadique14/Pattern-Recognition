package hierarchicalClustering;
/**
 * Pattern Recognition Laboratory
 * 
 * @author Md. Farhan Sadique
 * Student ID: 130238
 */
public class WardMethod {

	Sample[] samples;
	int numOfSamples;
	public WardMethod(Sample[] samples, int numOfSamples)
	{
		this.samples = samples;
		this.numOfSamples = numOfSamples;
	}
	//main algorithm
	public void clustering() {
		//initialization of clusters
		Cluster[] clusters = new Cluster[numOfSamples];
		for (int i = 0; i < clusters.length; i++) {
			clusters[i] = new Cluster(numOfSamples);
			clusters[i].insert(i);
		}
		int numOfClusters = numOfSamples;
		//calculating initial squared error
		double[][] tempDistances = new double[numOfClusters][numOfClusters];
		for (int i = 0; i < numOfClusters; i++) {
			for (int j = 0; j < numOfClusters; j++) { 
				if (j > i) {
					tempDistances[i][j] = distanceBetweenClusters(clusters, i, j, numOfClusters);
				}
				else {
					tempDistances[i][j] = 999999;
				}
			}
		}
		
		System.out.println("\nWard's Method");
		while(true) {
			//printing current clusters
			System.out.println("\nStep "+((numOfSamples - numOfClusters) + 1));
			for (int i = 0; i < numOfClusters; i++) {
				clusters[i].printCluster();
			}
			if (numOfClusters == 1)
				break;
			//finding minimum distance in current matrix of distances
			double minDistance = 99999.0;
			int clusterIndex1 = -1;
			int clusterIndex2 = -1;
			for (int i = 0; i < numOfClusters; i++) {
				for (int j = 0; j < numOfClusters; j++) {
					if (minDistance > tempDistances[i][j])
					{
						minDistance = tempDistances[i][j];
						clusterIndex1 = i;
						clusterIndex2 = j;
					}
				}
			}
			//adjusting clusters (adding a cluster to another)
			clusters[clusterIndex1].add(clusters[clusterIndex2].getCluster(), clusters[clusterIndex2].getLength());
			//moving the clusters one step ahead from to fill the gap
			for (int i = clusterIndex2; i < (numOfClusters-1); i++) {
				clusters[i] = clusters[i+1];
			}
			numOfClusters--;
			//update distance (new distance matrix for new set of clusters)
			tempDistances = new double[numOfClusters][numOfClusters];
			for (int i = 0; i < numOfClusters; i++) {
				for (int j = 0; j < numOfClusters; j++) { 
					if (j > i) {
						tempDistances[i][j] = distanceBetweenClusters(clusters, i, j, numOfClusters);
					}
					else {
						tempDistances[i][j] = 999999;
					}
				}
			}
			System.out.println();
		}
	}
	//calculating distance between two clusters
	public double distanceBetweenClusters (Cluster[] clusters, int index1, int index2, int numOfClusters) {
		double sumCluster1X = 0;
		double sumCluster1Y = 0;
		for (int i = 0; i < clusters[index1].getLength(); i++) {
			sumCluster1X += samples[clusters[index1].getElementsAt(i)].getX();
			sumCluster1Y += samples[clusters[index1].getElementsAt(i)].getY();
		}
		double sumCluster2X = 0;
		double sumCluster2Y = 0;
		for (int i = 0; i < clusters[index2].getLength(); i++) {
			sumCluster2X += samples[clusters[index2].getElementsAt(i)].getX();
			sumCluster2Y += samples[clusters[index2].getElementsAt(i)].getY();
		}
		double sumX = sumCluster1X + sumCluster2X;
		double sumY = sumCluster1Y + sumCluster2Y;
		double meanX = sumX/(clusters[index1].getLength() + clusters[index2].getLength());
		double meanY = sumY/(clusters[index1].getLength() + clusters[index2].getLength());
		double squaredError = 0.0;
		for (int i = 0; i < clusters[index1].getLength(); i++) {
			squaredError += Math.pow((samples[clusters[index1].getElementsAt(i)].getX() - meanX),2);
			squaredError += Math.pow((samples[clusters[index1].getElementsAt(i)].getY() - meanY),2);
		}
		for (int i = 0; i < clusters[index2].getLength(); i++) {
			squaredError += Math.pow((samples[clusters[index2].getElementsAt(i)].getX() - meanX),2);
			squaredError += Math.pow((samples[clusters[index2].getElementsAt(i)].getY() - meanY),2);
		}
		for (int i = 0; i < numOfClusters; i++) {
			if (i != index1 && i != index2) {
				sumX = 0;
				sumY = 0;
				for (int j = 0; j < clusters[i].getLength(); j++) {
					sumX += samples[clusters[i].getElementsAt(j)].getX();
					sumY += samples[clusters[i].getElementsAt(j)].getY();
				}
				meanX = sumX/clusters[i].getLength();
				meanY = sumY/clusters[i].getLength();
				for (int j = 0; j < clusters[i].getLength(); j++) {
					squaredError += Math.pow((samples[clusters[i].getElementsAt(j)].getX() - meanX),2);
					squaredError += Math.pow((samples[clusters[i].getElementsAt(j)].getY() - meanY),2);
				}
			}
		}
		return squaredError;
	}
}

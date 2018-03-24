package hierarchicalClustering;
/**
 * Pattern Recognition Laboratory
 * 
 * @author Md. Farhan Sadique
 * Student ID: 130238
 */
public class CompleteLinkageAlgorithm {

	Sample[] samples;
	int numOfSamples;
	public CompleteLinkageAlgorithm(Sample[] samples, int numOfSamples)
	{
		this.samples = samples;
		this.numOfSamples = numOfSamples;
	}
	//main algorithm
	public void clustering() {
		double[][] distances = new double[numOfSamples][numOfSamples];
		//calculating euclidean distances
		for (int i = 0; i < numOfSamples; i++) {
			for (int j = 0; j < numOfSamples; j++) { 
				if (j > i) {
					distances[i][j] = Math.sqrt(Math.pow((samples[i].getX() - samples[j].getX()), 2) + Math.pow((samples[i].getY() - samples[j].getY()), 2));
				}
				else {
					distances[i][j] = 99999;
				}
			}
		}
		//initialization of clusters
		Cluster[] clusters = new Cluster[numOfSamples];
		for (int i = 0; i < clusters.length; i++) {
			clusters[i] = new Cluster(numOfSamples);
			clusters[i].insert(i);
		}
		int numOfClusters = numOfSamples;
		double[][] tempDistances = distances;
		System.out.println("\nSingle-Linkage Algorithm");
		
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
						tempDistances[i][j] = distanceBetweenClusters(clusters[i], clusters[j], distances);
					}
					else {
						tempDistances[i][j] = 999999;
					}
				}
			}
		}
	}
	//calculating distance between two clusters (maximum distance of two points of the two clusters)
	public double distanceBetweenClusters (Cluster cluster1, Cluster cluster2, double[][] distances) {
		double maxDistance = -1;
		for (int i = 0; i < cluster1.getLength(); i++) {
			for (int j = 0; j < cluster2.getLength(); j++) {
				int index1 = cluster1.getElementsAt(i);
				int index2 = cluster2.getElementsAt(j);
				if (index2 < index1)
				{
					index1 = cluster2.getElementsAt(j);
					index2 = cluster1.getElementsAt(i);
				}
				if (maxDistance < distances[index1][index2]) {
					maxDistance = distances[index1][index2];
				}
			}
		}
		return maxDistance;
	}
}

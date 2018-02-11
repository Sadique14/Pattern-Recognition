package hierarchicalClustering;
/**
 * Pattern Recognition Laboratory
 * 
 * @author Md. Farhan Sadique
 * Student ID: 130238
 */
public class Cluster {

	int[] clusterElements;
	int count;
	public Cluster(int numOfElements) {
		clusterElements = new int[numOfElements];
		for (int i = 0; i < numOfElements; i++) {
			clusterElements[i] = -1;
		}
		count = 0;
	}
	public void insert (int x) {
		this.clusterElements[count] = x;
		count++;
	}
	public int getLength() {
		return count;
	}
	public int getElementsAt (int index) {
		return this.clusterElements[index];
	}
	public void add(int[] otherCluster, int otherLength) {
		for (int i = 0; i < otherLength; i++) {
			this.clusterElements[count] = otherCluster[i];
			count++;
		}
	}
	public void printCluster() {
		System.out.print("{ ");
		for (int i = 0; i < count; i++) {
			System.out.print((clusterElements[i]+1)+" ");
		}
		System.out.print("} ");
	}
	public int[] getCluster() {
		return this.clusterElements;
	}
}

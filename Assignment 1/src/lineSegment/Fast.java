/**
 *  Problem: Drawing every line segment that connects 4 or more distinct points from a set of N points.
 *  Input: A set of N points from file.
 *  Output: number of collinear points in the line segment, followed by the points. Both in console and graphical display.
 *  
 *  Md. Farhan Sadique
 *  Student ID: 130238
 **/
package lineSegment;
import java.io.File;
import java.util.Scanner;
import library.StdDraw;

public class Fast {

	static int[] numOfCollinearPoints;
	public static void main(String[] args){
		File file = new File("input.txt");
		try {
			//Drawing window configuration
			StdDraw.setXscale(0, 32768);
	        StdDraw.setYscale(0, 32768);  //scaling the coordinate system between 0 and 32,767 fit in the graphics window.
	        StdDraw.setPenRadius(0.01);   //setting up point radius
	        
			Scanner sc = new Scanner(file);
			int numOfPoints = sc.nextInt();
			numOfCollinearPoints = new int[numOfPoints];
			Point[] point = new Point[numOfPoints];
			Point[] tempPoint = new Point[numOfPoints];
			for (int i = 0; i < numOfPoints; i++) {       //reading points from file
				int x = sc.nextInt();
				int y = sc.nextInt();
				point[i] = new Point(x, y);
				StdDraw.point(x, y);                      //plotting the points
			}
			sc.close();
			
			//calculating angles
			int index1 = 0;
			int index2 = 0;
			Point[][] output = new Point[numOfPoints*2][numOfPoints];  //2d array for saving collinear points
			for (int i = 0; i < numOfPoints; i++) {
				point[i].setAngle(0.0);                  //angle with the point itself is 0
				for(int j = 0; j < numOfPoints; j++) {
					if(i != j) {                        //finding angle of each point with the current origin point.
						double angle = getAngle(point[i].getX(), point[i].getY(), point[j].getX(), point[j].getY());
						point[j].setAngle(angle);
					}
					tempPoint[j] = new Point(point[j].getX(), point[j].getY());
					tempPoint[j].setAngle(point[j].getAngle());
				}
				Point temp = tempPoint[0];
				tempPoint[0] = tempPoint[i];
				tempPoint[i] = temp;                 //taking the current origin point to the first index.
				
				sort(tempPoint);                     //sorting the points according to angle.
				
				//finding similar angles
				int count = 1;
				for(int m = 1; m < numOfPoints; m++) {
					double currentAngle = tempPoint[m].getAngle();
					for(int l = m + 1; l<numOfPoints; l++) {
						if(currentAngle == tempPoint[l].getAngle()) {
							count++;                        //counting the number of same angles.
						}
						else {
							if(count >= 3) {              //if number of same angles is greater than or equal to 3
								output[index1][index2] = new Point(tempPoint[0].getX(), tempPoint[0].getY());
								index2++;
								for(int n = m; n < (m + count); n++) {  //saving the collinear points to output
									output[index1][index2] = new Point(tempPoint[n].getX(), tempPoint[n].getY());
									index2++;
								}
								index1++;
								index2 = 0;
							}
							count = 1;
							m = l - 1;
							break;
						}
					}
				}
			}
			
			removeDuplicates(output, index1);  //removing duplicate set of collinear points. Every point was
			                                   //considered as origin. So, there are several combinations of points
                                               //for a single straight line.			
			StdDraw.setPenRadius(0.001);   //setting up point radius for drawing straight lines.
			
			//Printing output
			index2 = 0;
			boolean space = false;
			Point previousPoint = null;
			int numOfOutputPrinted = 0;
			for(int i = 0; i < index1; i++) {
				while (true) {
					if(output[i][index2] != null) {
						if(numOfOutputPrinted == 0)
							System.out.print(numOfCollinearPoints[i]+": ");
						System.out.print("("+output[i][index2].getX()+", "+output[i][index2].getY()+") -> ");
						if(numOfOutputPrinted > 0)    //drawing straight line.
							StdDraw.line(previousPoint.getX(), previousPoint.getY(), output[i][index2].getX(), output[i][index2].getY()); //drawing the line in graphics window
						previousPoint = output[i][index2];
						numOfOutputPrinted++;
						index2++;
						space = true;
					}	
					else {
						index2 = 0;
						numOfOutputPrinted = 0;
						break;
					}
				}
				if (space) {
					System.out.println();
					space = false;
				}
			}
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	public static void sort(Point[] a)  //function for sorting points according to angle it makes with origin point.
	{
		for(int i=2; i<a.length; i++)
	    {
	        int j=i-1;
	        Point key = a[i];
	        while(j>=1 && a[j].angle>key.angle)
	        {
	            a[j+1]=a[j];
	            j--;
	        }
	        a[j+1] = key;
	    }
	}
	public static void sortOutput(Point[] a)  //function for sorting collinear points according to X coordinates.
	{
		int i = 1;
		while(a[i] != null)
	    {
	        int j=i-1;
	        Point key = a[i];
	        while(j>=0 && a[j].getX()>key.getX())
	        {
	            a[j+1]=a[j];
	            j--;
	        }
	        a[j+1] = key;
	        i++;
	    }
	}
	public static double getAngle(int x1, int y1, int x2, int y2)   //function for calculating angle
	{
	  return Math.toDegrees(Math.atan((y2-y1)/(double)(x2-x1)));
	}

	public static void removeDuplicates(Point[][] array, int numOfRows) {  //function for removing duplicate set of collinear points.
		for(int i = 0; i < numOfRows; i++) {
			sortOutput(array[i]);
		}
		int index = 0;
		boolean equal = true;
		for (int i = 0; i < numOfRows; i++) {
			for (int j = i + 1; j < numOfRows; j++) {
				while (array[i][index] != null && array[j][index] != null) {
	                  if (array[i][index].getX() != array[j][index].getX() || array[i][index].getY() != array[j][index].getY()) {
	                      equal = false; 
	                      break;
	                  }
	                  index++;
	            }
				index = 0;
				if (equal && array[i][0] != null && array[j][0] != null) {
					for (int j2 = 0; j2 < array[j].length; j2++) {
						array[j][j2] = null;
					}
				}
				equal = true;
			}
		}
		for(int i = 0; i < numOfRows; i++) {
			if(array[i][0] != null) {
				for (int j = 0; j < array[i].length; j++) {
					if(array[i][j] != null) 
						numOfCollinearPoints[i] += 1;           //calculating the number of points on a straight line.
					else
						break;
				}
			}
		}
	}
}

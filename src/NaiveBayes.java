import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

class NaiveBayes {
	public static void main(String args[]) throws IOException {
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter the number of attributes: ");
		int attr = sc.nextInt();
		sc.nextLine();

		String[][] attrValues = new String[attr][];
		float[][][] probTable = new float[attr][][]; //Achievement unlocked: 3D Array!

		for(int i=0; i<attr; i++) {
			System.out.print("Enter the possible values for attribute " + (i+1) + ": ");
			attrValues[i] = sc.nextLine().split(" ");
			probTable[i] = new float[attrValues[i].length][2];
		}
		
		System.out.print("Enter the name of the CSV file: ");
		String fileName = sc.nextLine();
		
		int yesCount = 0, noCount = 0;
		
		BufferedReader br = new BufferedReader(new FileReader(fileName + ".csv"));
		String line;
		int objects = 0;

		while ((line = br.readLine()) != null) {
			String[] currentAttr = line.split(",");
			
			for(int j=0; j<attr; j++)
				for(int k=0; k<attrValues[j].length; k++)
					if(currentAttr[j].equals(attrValues[j][k])) {
						if(currentAttr[attr].equals("Y"))
							probTable[j][k][0]++;
						else
							probTable[j][k][1]++;
						break;
					}
			
			if(currentAttr[attr].equals("Y"))
				yesCount++;
			else
				noCount++;
			
			objects++;
		}
		
		br.close();
		
		for(int i=0; i<attr; i++)
			for(int j=0; j<probTable[i].length; j++) {
				probTable[i][j][0] /= yesCount;
				probTable[i][j][1] /= noCount;
			}
			
		System.out.print("\nEnter the attributes of the unknown sample: ");
		String[] unknownAttr = sc.nextLine().split(" ");
		
		float yesProb = 1, noProb = 1;
		
		for(int i=0; i<unknownAttr.length; i++)
			for(int j=0; j<attrValues[i].length; j++)
				if(unknownAttr[i].equals(attrValues[i][j])) {
					yesProb *= probTable[i][j][0];
					noProb *= probTable[i][j][1];
					break;
				}
		
		yesProb *= yesCount*1.0/objects;
		noProb *= noCount*1.0/objects;
		
		System.out.println("P(Y) = " + yesProb + "\tP(N) = " + noProb);
		System.out.println("Thus, the class of the unknown sample will be " + (yesProb>noProb? "Y":"N"));
		
		sc.close();
	}
}

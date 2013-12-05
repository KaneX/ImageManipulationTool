package readAndWriteImage;

import java.io.*;

public class PGM implements readAndWriteImage{
	
	private int picWidth;
	private int picHeight;
	private int[][] data2D;
	
	@SuppressWarnings("deprecation")
    /**
     * Read image
     */
	public void readImage(String filePath) throws IOException {
		
		FileInputStream fileInputStream = new FileInputStream(filePath);
		DataInputStream dis = new DataInputStream(fileInputStream);

		 // look for 4 lines (i.e.: the header) and discard them
		 int numnewlines = 4;
		 String data;
		 String[] tmp;
		 while (numnewlines > 0) {
		     
			 data=dis.readLine();
			 System.out.println(data);
			 if(numnewlines==2){
				 tmp=data.split(" ");
				 picWidth=Integer.parseInt(tmp[0]);
				 picHeight=Integer.parseInt(tmp[1]);
			 }
			 
		     numnewlines--;
		 }
		 // read the image data
		 data2D = new int[picHeight][picWidth];
		 
		 for (int row = 0; row < picHeight; row++) {
			 data=dis.readLine();
			 tmp=data.split(" ");
			 for (int col = 0; col < picWidth; col++) {
				 data2D[row][col] =Integer.parseInt(tmp[col]);
		     }
		 }
		 fileInputStream.close();
	  }

    /**
     * Write image
     */
	public void writeImage(String filePath) throws IOException{
		FileWriter fileWriter = new FileWriter(filePath);
		BufferedWriter bw = new BufferedWriter(fileWriter);
		
		bw.write("P2\n# edited using Image Manipulation Tool.\n"+picWidth+" "+picHeight+"\n"+1+"\n");
		
		for (int row = 0; row < picHeight; row++) {
			 for (int col = 0; col < picWidth; col++) {
				 bw.write(data2D[row][col]+" ");
		     }
			 bw.write('\n');
		 }
		
		bw.close();
	}

    /**
     * Methods for reading image
     */
	public int getHeight() { return picHeight;}
	public int getWidth() { return picWidth;}
	public int[][] getData() { return data2D; }

    /**
     * Methods for writing image
     */
	public void setHeight(int height){ picHeight = height;}
	public void setWidth(int width){ picWidth = width;}
	/*
	 * Fill data2D with a new matrix. If the new matrix has different size, use
	 * setHeight and setWidth first.
	 */
	public void setData(int[][] newMatrix){
		data2D = new int[picHeight][picWidth];
		
		for (int row = 0; row < picHeight; row++)
			for (int col = 0; col < picWidth; col++)
				data2D[row][col] = newMatrix[row][col];
	}
	
}

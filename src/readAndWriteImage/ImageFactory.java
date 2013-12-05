package readAndWriteImage;

import java.io.IOException;

public class ImageFactory {

	readAndWriteImage imageFormat;
	  
	/*
	 * Set image format at run time
	 */
	public void setImageFormat(readAndWriteImage imageFormat) {
		this.imageFormat = imageFormat;  
	}

	/*
	 * Use this image format
	 */
	// Methods for reading this image format
	public void readImage(String filePath) throws IOException{
		imageFormat.readImage(filePath);
	}

    public int getHeight(){
    	int height = imageFormat.getHeight();
    	return height;
    }
    public int getWidth(){
    	int width = imageFormat.getWidth();
    	return width;
    }
    public int[][] getData(){
    	int height = imageFormat.getHeight();
    	int width = imageFormat.getWidth();
    	int[][] data = new int[height][width];
    	data = imageFormat.getData();
    	return data;
    }
	
	// Methods for writing this image format
	public void writeImage(String filePath) throws IOException{
		imageFormat.writeImage(filePath);
	}
    public void setHeight(int height){
    	imageFormat.setHeight(height);
    }
    public void setWidth(int width){
    	imageFormat.setWidth(width);
    }
    public void setData(int[][] newMatrix){
    	imageFormat.setData(newMatrix);
    }
}

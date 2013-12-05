package readAndWriteImage;

import java.io.IOException;

public interface readAndWriteImage {
	
	// Methods for read image
    void readImage(String filePath) throws IOException;
    public int getHeight();
    public int getWidth();
    public int[][] getData();
    
    // Methods for write image
    void writeImage(String filePath) throws IOException;
    public void setHeight(int height);
    public void setWidth(int width);
    public void setData(int[][] newMatrix);
}
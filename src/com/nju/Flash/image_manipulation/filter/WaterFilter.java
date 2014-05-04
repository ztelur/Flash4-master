package com.nju.Flash.image_manipulation.filter;

import android.graphics.Bitmap;

public class WaterFilter {
	private double wave;
	private double period;
	
	public WaterFilter() {
		wave = 15.0;
		period = 84.0;
	}
	
	public WaterFilter(double w,double p) {
		wave = w;
		period = p;
	}
	
    public Bitmap filter(Bitmap src) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap dest = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        Point[][] ssPixels = new Point[height][width];
        for(int row=0;row<height;row++) {
        	for(int col=0;col<width;col++) {
        		inPixels[row*width+col] = src.getPixel(col, row);
        	}
        }
        int index = 0, index2 = 0;
        int xoffset = 0, yoffset = 0;
        for(int row=0; row<height; row++) {
            for(int col=0; col<width; col++) {
                xoffset = (int)((double)wave * Math.sin(2.0 * Math.PI * (float)row / period));
                yoffset = (int)((double)wave * Math.cos(2.0 * Math.PI * (float)col / period));
                xoffset = xoffset + col;
                yoffset = yoffset + row;
                if(xoffset < 0 || xoffset >=width) {
                    xoffset = 0;
                }
                if(yoffset < 0 || yoffset >=height) {
                    yoffset = 0;
                }
                
                ssPixels[row][col] = new Point(xoffset, yoffset); 
            }
        }
        
        // coordinate 2D result and fill the pixels data.
        for(int row=0; row<height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for(int col=0; col<width; col++) {
                index = row * width + col;
                index2 = ssPixels[row][col].getRow() * width + ssPixels[row][col].getCol();
                ta = (inPixels[index2] >> 24) & 0xff;
                tr = (inPixels[index2] >> 16) & 0xff;
                tg = (inPixels[index2] >> 8) & 0xff;
                tb = inPixels[index2] & 0xff;
                outPixels[index] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
            }
        }

        for(int row=0;row<height;row++) {
        	for(int col=0;col<width;col++) {
        		dest.setPixel(col, row, outPixels[row*width+col]);
        	}
        }

        return dest;
    }
    
    class Point {
        private int x;
        private int y;
        
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        public int getRow() {
            return this.y;
        }
        
        public int getCol() {
            return this.x;
        }
        
    }
}

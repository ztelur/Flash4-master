package com.nju.Flash.image_manipulation.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

public class HahajingFilter {
	double coefficient = 1.5d;
	double radius = 50.0;
	
	public HahajingFilter() {
		
	}
	
	public HahajingFilter(double c, double r) {
		coefficient = c;
		radius = r;
	}
	
	public Bitmap filter(Bitmap src, int x, int y) {
		int width = src.getWidth();
		int height = src.getHeight();
		
		Bitmap dest = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        for(int row=0;row<height;row++) {
        	for(int col=0;col<width;col++) {
        		inPixels[row*width+col] = src.getPixel(col, row);
        		outPixels[row*width+col] = src.getPixel(col, row);
        	}
        }

//        int centerX = width*5/8;
//        int centerY = height*21/32;
        int centerX = x;
        int centerY = y;
        
        for(int col=0;col<width;col++) {
        	 for(int row=0;row<height;row++) {
        		int distance = (int)((centerX-col)*(centerX-col)+(centerY-row)*(centerY-row));
        		int newR=Color.red(inPixels[row*width+col]);
        		int newG=Color.green(inPixels[row*width+col]);;
        		int newB=Color.blue(inPixels[row*width+col]);;
        		int newA=Color.alpha(inPixels[row*width+col]);; 
        		
        		if (distance < radius*radius) {
        			int newX = (int)((float)(col-centerX)/coefficient);
        			int newY = (int)((float)(row-centerY)/coefficient);
        			newX = (int)(newX*(2*Math.sqrt(distance)/radius));
        			newY = (int)(newY*(2*Math.sqrt(distance)/radius));
        			newX = newX+centerX;
        			newY = newY+centerY;
        			int color = inPixels[newY*width+newX];
        			newR = Color.red(color);
        			newG = Color.green(color);
        			newB = Color.blue(color);
        			newA = Color.alpha(color);
        		}
        		newR = Math.min(255, Math.max(0, newR));
        		newG = Math.min(255, Math.max(0, newG));
        		newB = Math.min(255, Math.max(0, newB));
        		newA = Math.min(255, Math.max(0, newA));
        		int newColor = Color.argb(newA, newR, newG, newB);
        		outPixels[row*width+col] = newColor;
        	}
        }
        for(int row=0;row<height;row++) {
        	for(int col=0;col<width;col++) {
        		dest.setPixel(col, row, outPixels[row*width+col]);
        	}
        }
		
		return dest;
	}
}

package com.nju.Flash.image_manipulation.filter;

import android.graphics.Bitmap;

public class MagicMirrorFilter {
//    private double factor = 15.0d; // default value
	private double factor = 10.0d;

    public MagicMirrorFilter() {
        
    }
    
    public MagicMirrorFilter(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
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
        	}
        }
        int index = 0, outIndex = 0;
//        int centerX = width/2;
//        int centerY = height/2;
        int centerX = x;
        int centerY = y;
        
        double theta, radius;
        double newX, newY;
        int offsetX = 0, offsetY = 0;
        for(int row=0; row<height; row++) {
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for(int col=0; col<width; col++) {

                int trueX = col - centerX;
                int trueY = row - centerY;
                theta = Math.atan2((trueY),(trueX));
                radius = Math.sqrt(trueX*trueX + trueY*trueY);
                double newRadius = Math.sqrt(radius) * factor;
                newX = centerX + (newRadius * Math.cos(theta));
                newY = centerY + (newRadius * Math.sin(theta));
                
                if (newX > 0 && newX < width) {
                    offsetX = (int)newX;
                } else {
                    newX = 0;
                }
                
                if (newY > 0 && newY < height) {
                    offsetY = (int)newY;
                } else {
                    newY = 0;
                }
                
                index = offsetY * width + offsetX;
                ta = (inPixels[index] >> 24) & 0xff;
                tr = (inPixels[index] >> 16) & 0xff;
                tg = (inPixels[index] >> 8) & 0xff;
                tb = inPixels[index] & 0xff;
                
                // use newX, newY and fill the pixel data now...
                outIndex = row * width + col;
                outPixels[outIndex] = (ta << 24) | (tr << 16) | (tg << 8) | tb;
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
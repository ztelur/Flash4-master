package com.nju.Flash.image_manipulation;

/**
 * Created by randy on 14-3-3.
 */
public enum OperationEnum {
    a,b,c,d,e,pen,eraser;
    public static OperationEnum getEnum(int number) {
        switch (number) {
            case 1:
                return a;
            case 2:
                return b;
            case 3:
                return c;
            case 4:
                return d;
            case 5:
                return e;
            case 6:
                return pen;
            case 10:
                return eraser;
            default:
                return a;
        }
    }
}

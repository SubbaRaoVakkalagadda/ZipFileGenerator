package com.imaginea;

import com.imaginea.file.zipper.LZ4Zipper;
import com.imaginea.file.zipper.Zipper;

import java.io.IOException;

/**
 * Created by subbaraov on 18/11/16.
 */
public class ZipFileDemo {
    public static void main(String[] args) throws IOException {

        long startTime = System.currentTimeMillis();
        Zipper zipper = new LZ4Zipper();
        zipper.zipAndCopy(args[0], args[1], Long.valueOf(args[2]), Integer.valueOf(args[3]));
        System.out.println("Time Taken: " + (System.currentTimeMillis()-startTime));
    }

}

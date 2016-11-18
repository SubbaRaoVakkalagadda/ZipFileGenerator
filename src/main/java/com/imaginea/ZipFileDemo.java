package com.imaginea;

import com.imaginea.file.utils.ZipperFactory;
import com.imaginea.file.utils.ZipperType;
import com.imaginea.file.zipper.Zipper;

import java.io.IOException;
import java.util.stream.Stream;

/**
 * Created by subbaraov on 18/11/16.
 */
public class ZipFileDemo {
    public static void main(String[] args) throws IOException {

        //start time
        long startTime = System.currentTimeMillis();

        //get zipper type
        ZipperType zipperType = Stream.of(ZipperType.values()).filter(z -> z.name().equalsIgnoreCase(args[4])).findFirst().orElse(ZipperType.LZ4);
        //create zipper
        Zipper zipper = ZipperFactory.getZipper(zipperType);

        //zip & copy
        zipper.zipAndCopy(args[0], args[1], Long.valueOf(args[2]), Integer.valueOf(args[3]));

        //print the time taken
        System.out.println("Time Taken: " + (System.currentTimeMillis()-startTime));
    }

}

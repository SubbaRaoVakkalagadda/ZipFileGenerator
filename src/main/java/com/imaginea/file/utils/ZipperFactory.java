package com.imaginea.file.utils;

import com.imaginea.file.zipper.GzipZipper;
import com.imaginea.file.zipper.Java7Zipper;
import com.imaginea.file.zipper.LZ4Zipper;
import com.imaginea.file.zipper.Zipper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by subbaraov on 18/11/16.
 */
public class ZipperFactory {
    private static final Map<ZipperType, Zipper> zippers = new HashMap<>();

    public static Zipper getZipper(ZipperType zipperType) {
        if(zipperType == null) {
            throw new IllegalArgumentException("ZipperType cannot be null");
        }

        if(!zippers.containsKey(zipperType)) {
            synchronized (zippers) {
                if(!zippers.containsKey(zipperType)) {
                    switch (zipperType) {
                        case GZIP:
                            zippers.put(zipperType, new GzipZipper());
                            break;
                        case JAVA7:
                            zippers.put(zipperType, new Java7Zipper());
                            break;
                        case LZ4:
                            zippers.put(zipperType, new LZ4Zipper());
                    }
                }
            }
        }

        return zippers.get(zipperType);
    }
}

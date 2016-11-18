package com.imaginea.file.zipper;

import com.imaginea.file.utils.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by subbaraov on 17/11/16.
 */
public interface Zipper {
    void zipAndCopy(String inFilePath, String outFilePath, long uncompressedSize, int noOfCopies) throws IOException;

    default void duplicate(String filePath, int noOfCopies) {
        FileUtils.duplicate(filePath, noOfCopies);
    }
}

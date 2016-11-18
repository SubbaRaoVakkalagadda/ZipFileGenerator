package com.imaginea.file.zipper;

import java.io.File;
import java.io.IOException;

/**
 * Created by subbaraov on 17/11/16.
 */
public interface Zipper {
    void zip(String inFilePath, String outFilePath, long uncompressedSize) throws IOException;
}

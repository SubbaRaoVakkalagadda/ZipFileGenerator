package com.imaginea.file.zipper;

import com.imaginea.file.utils.FileUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by subbaraov on 18/11/16.
 */
public class GzipZipper implements Zipper {
    @Override
    public void zipAndCopy(String inFilePath, String outFilePath, long uncompressedSize, int noOfCopies) throws IOException {
        try (GZIPOutputStream zipper = new GZIPOutputStream(new FileOutputStream(outFilePath))) {

            //read all the content of the file in to ByteBuffers
            ByteBuffer[] byteBuffs = FileUtils.readFileToByteBuffers(inFilePath);
            //calculate no of times we need to add input content to zipAndCopy file
            long maxCount = uncompressedSize / Stream.of(byteBuffs).map(buf -> buf.capacity()).collect(Collectors.summingInt(Integer::intValue));

            //write to zipAndCopy file
            for (int i = 0; i < maxCount; i++) {
                for (int j = 0; j < byteBuffs.length; j++) {
                    zipper.write(byteBuffs[j].array());
                }
            }

        }

        if(noOfCopies > 1) {
            duplicate(outFilePath, noOfCopies-1);
        }
    }
}

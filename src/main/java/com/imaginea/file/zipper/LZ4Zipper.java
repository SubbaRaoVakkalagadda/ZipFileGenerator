package com.imaginea.file.zipper;

import com.imaginea.file.utils.FileUtils;
import net.jpountz.lz4.LZ4BlockOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by subbaraov on 18/11/16.
 */
public class LZ4Zipper implements Zipper {
    @Override
    public void zip(String inFilePath, String outFilePath, long uncompressedSize) throws IOException {

        //TODO: validations pending

        //create file channel for input file & zip output stream for output file
        try (LZ4BlockOutputStream zipper = new LZ4BlockOutputStream(new FileOutputStream(outFilePath), 33554432/*this is the max buffer size allowed*/)) {

            //read all the content of the file in to ByteBuffers
            ByteBuffer[] byteBuffs = FileUtils.readFileToByteBuffers(inFilePath);

            //calculate no of times we need to add input content to zip file
            long maxCount = uncompressedSize / Stream.of(byteBuffs).map(buf -> buf.capacity()).collect(Collectors.summingInt(Integer::intValue));

            //write to zip file
            for (int i = 0; i < maxCount; i++) {
                for (int j = 0; j < byteBuffs.length; j++) {
                    zipper.write(byteBuffs[j].array());
                }
            }

        }
    }
}

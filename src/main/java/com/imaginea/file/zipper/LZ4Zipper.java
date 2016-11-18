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
    public void zipAndCopy(String inFilePath, String outFilePath, long uncompressedSize, int noOfFiles) throws IOException {

        //TODO: validations pending

        //create file channel for input file & zipAndCopy output stream for output file
        try (LZ4BlockOutputStream zipper = new LZ4BlockOutputStream(new FileOutputStream(outFilePath), 33554432/*this is the max buffer size allowed*/)) {

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

        if(noOfFiles > 1) {
            duplicate(outFilePath, noOfFiles-1);
        }
    }
}

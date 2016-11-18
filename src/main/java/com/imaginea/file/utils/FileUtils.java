package com.imaginea.file.utils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by subbaraov on 18/11/16.
 */
public class FileUtils {
    public static ByteBuffer[] readFileToByteBuffers(String filePath) throws IOException {
        Path inPath = Paths.get(filePath);
        try (FileChannel inChannel = FileChannel.open(inPath, StandardOpenOption.READ)) {
            //calculate number of byte buffers needed due to size constraints (only int size)
            int sizeOfLastArray = (int) (inChannel.size() % Integer.MAX_VALUE);
            long noOfArraysNeeded;
            if(sizeOfLastArray != 0) {
                noOfArraysNeeded = (inChannel.size() / Integer.MAX_VALUE) + 1;
            } else {
                noOfArraysNeeded = (inChannel.size() / Integer.MAX_VALUE);
            }


            //create number of bytebuffers needed with appropriate size
            ByteBuffer[] byteBuffs = new ByteBuffer[(int) noOfArraysNeeded];
            for (int i = 0; i < byteBuffs.length; i++) {
                if (i == byteBuffs.length-1) {
                    if(sizeOfLastArray > 0) {
                        byteBuffs[i] = ByteBuffer.wrap(new byte[sizeOfLastArray]);
                    } else {
                        byteBuffs[i] = ByteBuffer.wrap(new byte[Integer.MAX_VALUE]);
                    }
                } else {
                    byteBuffs[i] = ByteBuffer.wrap(new byte[Integer.MAX_VALUE]);
                }
            }

            //read all the content into bytebuffers
            inChannel.read(byteBuffs);
            return  byteBuffs;
        }
    }
}

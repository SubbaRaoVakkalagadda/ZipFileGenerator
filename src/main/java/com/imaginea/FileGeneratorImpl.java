package com.imaginea;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * Created by subbaraov on 16/11/16.
 */
public class FileGeneratorImpl implements FileGenerator {

    @Override
    public void generateFileUsingFileChannelTransferTo(Path source, Path destination, int sizeInBytes) throws IOException {
        try (FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);
             FileChannel destChannel = FileChannel.open(destination, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND)) {
            while (destChannel.size() < sizeInBytes) {
                long p = 0;
                while (p < sourceChannel.size()) {
                    p += sourceChannel.transferTo(p, sourceChannel.size(), destChannel);
                }
            }
        }

    }

    @Override
    public void generateFileUsingMemoryMappedBuffer(Path source, Path destination, int sizeInBytes) throws IOException {
        try (FileChannel sourceChannel = FileChannel.open(source, StandardOpenOption.READ);
             RandomAccessFile destFile = new RandomAccessFile(destination.toFile(), "rw");
             FileChannel destChannel = destFile.getChannel()) {

            long iterations = sizeInBytes / sourceChannel.size();

            MappedByteBuffer inMapBuf = sourceChannel.map(FileChannel.MapMode.READ_ONLY, 0, sourceChannel.size());
            MappedByteBuffer outMappedBuf = destChannel.map(FileChannel.MapMode.READ_WRITE, 0, sizeInBytes);
            for (int i = 0; i < iterations; i++) {
                outMappedBuf.put(inMapBuf);
                inMapBuf.position(0);
            }
        }
    }

    @Override
    public void generateFileUsingSingleChannel(Path source, Path destination, int sizeInBytes) throws IOException {

        Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

        try (RandomAccessFile sourceFile = new RandomAccessFile(destination.toFile(), "rwd");
             FileChannel destFile = sourceFile.getChannel();) {

            ByteBuffer byteBuff = ByteBuffer.allocateDirect(1024 * 1024 * 32);
            while (destFile.size() < sizeInBytes) {
                destFile.read(byteBuff);
                byteBuff.flip();
                destFile.write(byteBuff);
                destFile.position(destFile.position() - byteBuff.position());
                byteBuff.compact();
            }
        }
    }
}

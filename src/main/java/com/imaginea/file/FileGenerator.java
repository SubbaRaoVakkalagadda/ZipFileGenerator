package com.imaginea.file;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by subbaraov on 16/11/16.
 */
public interface FileGenerator {
    void generateFileUsingFileChannelTransferTo(Path source, Path destination, int sizeInBytes) throws IOException;
    void generateFileUsingMemoryMappedBuffer(Path source, Path destination, int sizeInBytes) throws IOException;
    void generateFileUsingSingleChannel(Path source, Path destination, int sizeInBytes) throws IOException;
}

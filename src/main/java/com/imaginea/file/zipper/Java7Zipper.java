package com.imaginea.file.zipper;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by subbaraov on 18/11/16.
 */
public class Java7Zipper implements Zipper {

    @Override
    public void zip(String inFilePath, String outFilePath, long uncompressedSize) throws IOException {
        // convert the filename to a URI
        try(FileSystem zipFileSystem = createZipFileSystem(outFilePath)) {
            final Path src = Paths.get(inFilePath);
            final Path pathInZipFile = zipFileSystem.getPath("/" + src.getFileName().toString());
            Files.copy(src, pathInZipFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static FileSystem createZipFileSystem(String outFilePath) throws IOException {
        final Path path = Paths.get(outFilePath);
        final URI uri = URI.create("jar:file:" + path.toUri().getPath());

        final Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        return FileSystems.newFileSystem(uri, env);
    }

}

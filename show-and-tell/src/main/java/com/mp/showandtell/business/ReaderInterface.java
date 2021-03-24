package com.mp.showandtell.business;

import java.io.IOException;
import java.nio.file.Path;

public interface ReaderInterface {
    String readFile(Path tempFile) throws IOException;
    void writeFile();
}

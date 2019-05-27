package com.matrix.call.demo.core.demo007TryFinally;

import java.io.*;

public class BufferedTestReader extends FileReader {


    public BufferedTestReader(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @Override
    public void close() throws IOException {
        System.out.println("i am in file reader close");
        super.close();

    }
}

package ca.encodeous.pong.system;

import java.io.InputStream;

public class Utils {
    public static InputStream getFileAsIOStream(final String fileName)
    {
        InputStream ioStream = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
}

package com.spidey01.sxe.core;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;

/** Loads an InputStream out of a file path */
public class PathResourceLoader implements ResourceLoader {
    private final static String TAG = "PathResourceLoader";

    public InputStream getInputStream(File path)
        throws IOException
    {
        return new FileInputStream(path);
    }

    public InputStream getInputStream(String path)
        throws IOException
    {
        return new FileInputStream(path);
    }
}



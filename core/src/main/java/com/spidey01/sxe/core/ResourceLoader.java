package com.spidey01.sxe.core;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;

/** ABC for loading a Resource */
public interface ResourceLoader {
    InputStream getInputStream(File path) throws IOException;
    InputStream getInputStream(String path) throws IOException;
}


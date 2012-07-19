package com.spidey01.sxe.core;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Utils {

    /** InputStream to something with .readLine() :-).
     *
     * Often I need to deal with InputStream but of course, all I reallly want
     * to do is get data out of it line by line.
     */
    public static BufferedReader makeBufferedReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }

}


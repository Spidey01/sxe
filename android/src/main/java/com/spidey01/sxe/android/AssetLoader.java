package com.spidey01.sxe.android;

import com.spidey01.sxe.core.ResourceLoader;

import android.content.res.AssetManager;

import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;

/** Loads an InputStream out of a Zip Archive */
public class AssetLoader implements ResourceLoader {
    private final static String TAG = "AssetLoader";
    private AssetManager mAssetManager;

    public AssetLoader(AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    public InputStream getInputStream(File path)
        throws IOException
    {
        return getInputStream(path.getPath());
    }

    public InputStream getInputStream(String path)
        throws IOException
    {
        String assetPath = path.substring(path.indexOf(":")+1);

        // wtf, AssetManager.open gives cannot find symbol!?
        // return mAssetManager.open(assertPath, AssetManager.ACCESS_UNKNOWN);
        return null;
    }
}



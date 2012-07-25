package com.spidey01.sxe.core;

import java.io.InputStream;

/** A resource bucket.
 *
 * A resource will consist of either an InputStream that can be used for
 * accessing it's data, or an Object of some relevant type.
 *
 *  Resources always have a file name. Note however that it may not be a valid
 *  file namer in the conventional sense, i.e. it's what ResourceManager.load()
 *  uses not what the file system necessarily uses.
 */
public class Resource {
    public enum Type {
        UNKNOWN,
        TEXT_FILE,
        VERTEX_SHADER,
        FRAGMENT_SHADER
    };

    private final Type mType;
    private final String mFileName;
    private final InputStream mInputStream;
    private final Object mObject;
    private static final String TAG = "Resource";

    /** Initializes a resource.
     *
     * Note that only one of is or obj may be null.
     *
     * @param type What kindof Resource.Type this is.
     * @param fileName the file name of this resource, as suitable for ResourceManager.load().
     * @param is An InputStream representing this. May be null.
     * @param obj Object representing this resource. May be null.
     *
     * @see ResourceManager
     */
    Resource(Type type, String fileName, InputStream is, Object obj) {

        if (is == null && obj == null) {
            throw new IllegalArgumentException("Both is and obj cannot be null!");
        }

        mType = type;
        mFileName = fileName;
        mInputStream = is;
        mObject = obj;
    }

    public String getFileName() {
        return mFileName;
    }

    public InputStream getInputStream() {
        return mInputStream;
    }

    public Object getObject() {
        return mObject;
    }
}


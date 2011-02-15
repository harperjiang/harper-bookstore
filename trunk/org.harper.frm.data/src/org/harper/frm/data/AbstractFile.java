package org.harper.frm.data;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author harper
 * 
 */
public abstract class AbstractFile implements IFile {

    private int mode;

    private String mimeType;

    private String name;

    private String absolutePath;

    private String extension;

    private String encoding = "utf8";

    private Map<String, String> properties;

    private long size;

    public AbstractFile() {
        super();
        properties = new HashMap<String, String>();
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType.fullName();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    /**
     * Always try to return a name compliant with abc.xyz format.
     */
    public String getName() {
        if (name != null && extension != null && !name.endsWith(getExtension()))
            name = MessageFormat.format(NAME_8_3, name, extension);
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (null != name && name.contains(".")) {
            setExtension(name.substring(name.indexOf(".") + 1));
            try {
                setMimeType(Extension.valueOf(getExtension()).getType());
            } catch (Exception e) {
                // No corresponding mimetype
                setMimeType(null);
            }
        }
    }

    protected static final String NAME_8_3 = "{0}.{1}";

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    private Map<String, String> metaInfo;

    public Map<String, String> getMetaInfo() {
        if (metaInfo == null)
            metaInfo = new HashMap<String, String>();
        return metaInfo;
    }

}

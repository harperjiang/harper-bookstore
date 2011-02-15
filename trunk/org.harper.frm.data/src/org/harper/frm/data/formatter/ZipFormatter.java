package org.harper.frm.data.formatter;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.harper.frm.data.Extension;
import org.harper.frm.data.IFile;
import org.harper.frm.data.MemoryFile;
import org.harper.frm.data.MimeType;


public class ZipFormatter extends AbstractFormatter {

    public IFile format(Object input) throws IOException,
            UnsupportedOperationException {
        if (!(input instanceof IFile))
            throw new IllegalArgumentException(
                    "Could only compress file content");

        IFile inputFile = (IFile) input;

        MemoryFile output = new MemoryFile();
        output.setName(inputFile.getName());
        output.setMimeType(MimeType.application_zip);
        output.setExtension(Extension.zip.getText());

        ZipOutputStream zipos = new ZipOutputStream(output
                .getOutputStream(true));

        fireBegin();
        // IO

        ZipEntry entry = new ZipEntry(inputFile.getName());
        zipos.putNextEntry(entry);
        IOUtils.copy(inputFile.getInputStream(), zipos);
        inputFile.getInputStream().close();
        zipos.closeEntry();
        zipos.close();
        fireEnd();

        return output;
    }

}

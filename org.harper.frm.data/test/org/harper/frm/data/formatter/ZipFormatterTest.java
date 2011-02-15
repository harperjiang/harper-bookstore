package org.harper.frm.data.formatter;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.harper.frm.data.IFile;
import org.harper.frm.data.MemoryFile;
import org.harper.frm.data.MimeType;
import org.harper.frm.data.formatter.ZipFormatter;
import org.junit.Test;


public class ZipFormatterTest {

    @Test
    public void testFormat() throws IOException {
        MemoryFile file = new MemoryFile();
        file.setName("test.txt");
        file.setMimeType(MimeType.text_plain);
        OutputStream os =file.getOutputStream(true);
        
        os.write("This is a test file".getBytes());
        os.close();
        
        IFile zipfile = new ZipFormatter().format(file);
        assertTrue(file.getInputStream().available()!=0);
        FileOutputStream fos = new FileOutputStream(zipfile.getName());
        IOUtils.copy(zipfile.getInputStream(), fos);
        zipfile.getInputStream().close();
        fos.close();
    }

}

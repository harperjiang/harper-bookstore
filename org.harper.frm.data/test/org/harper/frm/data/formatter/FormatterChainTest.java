package org.harper.frm.data.formatter;

import static org.junit.Assert.assertEquals;

import java.io.FileOutputStream;

import org.apache.commons.io.IOUtils;
import org.harper.frm.data.IFile;
import org.harper.frm.data.MimeType;
import org.harper.frm.data.formatter.FormatterChain;
import org.harper.frm.data.formatter.ZipFormatter;
import org.harper.frm.data.formatter.excel.XLSFormatter2;
import org.junit.Test;


public class FormatterChainTest {

    @Test
    public void testFormat() throws Exception {

        XLSFormatter2 xls = new XLSFormatter2("Temp", new String[] { "SheetX" });
        xls.setContentProvider(new SampleTableContentProvider2());
        xls.setUsecolor(true);

        ZipFormatter zip = new ZipFormatter();

        FormatterChain chain = new FormatterChain(xls, zip);

        IFile file = chain.format(new Object[] { "abcdef" });
        assertEquals(MimeType.application_zip.fullName(), file.getMimeType());

        FileOutputStream test = new FileOutputStream("output.zip");
        IOUtils.copy(file.getInputStream(), test);
        test.close();
    }

}

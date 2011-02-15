package org.harper.frm.data.formatter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.harper.frm.data.IFile;
import org.harper.frm.data.MappedFile;
import org.harper.frm.data.MemoryFile;
import org.harper.frm.data.formatter.supp.Decoration;
import org.harper.frm.data.formatter.supp.FontBean;


/**
 * <p>
 * This abstract class provides the most basic function, such as paging, that
 * will be used by a formatter. It also handles the job of sending notification
 * to listeners.
 * </p>
 * 
 * <p>
 * Subclasses of this formatter supports input(s) contains info from different
 * datasource and would like to be integrated into a single output. By set the
 * input as an Object[] could this function be enabled.
 * </p>
 * 
 * <p>
 * The following methods should be implemented to get a concrete formatter,
 * which comprise a complete lifecycle of a formatter.
 * <li><code>{@link #createFile()}</code> : start of formatting.
 * <li><code>{@link #newSection(String[])}</code> : each input is called a
 * section. Input parameter is the column name for this section.
 * <li><code>{@link #newPage()}</code> : the page size is determined by the
 * {@link #shouldStartNewPage()} method.
 * <li><code>{@link #newRow(Object, ITableContentProvider)}</code> : format each
 * row.
 * <li><code>{@link #finalizeFile()}</code> : end of formatting
 * </p>
 * 
 * <p>
 * If a IFile instance is provided via {@link #setFile(IFile)}, it will be used
 * as the output target. If not, a {@link MemoryFile} or a {@link MappedFile}
 * will be created depending on the option {@link #isUseMemoryFile()}. The
 * default value is true. For small file, using memory file is enough. But for
 * large files, it is preferred to use a mapped disk file.
 * </p>
 * 
 * @author Harper Jiang
 * @since ips.frm.component 1.0
 * @version 1.0 Jun 15, 2009
 * @see ITableContentProvider
 */
public abstract class DefaultFormatter extends AbstractFormatter {

    public static final int NO_PAGING = -1;
    /**
     * -1 means no paging
     */
    private int pageSize = NO_PAGING;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
	 * 
	 */
    private ITableContentProvider<? extends Object> contentProvider;

    public ITableContentProvider<? extends Object> getContentProvider() {
        return contentProvider;
    }

    public void setContentProvider(
            ITableContentProvider<? extends Object> contentProvider) {
        this.contentProvider = contentProvider;
    }

    protected transient int currentRow;

    protected transient int currentPage;

    protected transient int currentSection;

    /**
     * Generally subclasses need not to override this method. Otherwise they
     * should take full advantage of handing all issues.
     */
    public IFile format(Object input) throws IOException,
            UnsupportedOperationException {
        Validate.notNull(contentProvider);

        Object[] inputArray = null;
        if (null != input && input.getClass().isArray()) {
            inputArray = (Object[]) input;
        } else
            inputArray = new Object[] { input };

        // Begin
        createFile();
        fireBegin();

        for (int i = 0; i < inputArray.length; i++) {
            fireSectionBegin(i);
            // Start a new Section
            newSection(getContentProvider().getColumnNames(inputArray[i]));
            // Iterate to format each row
            currentRow = 0;
            currentPage = 0;

            newPage();
            firePageBegin(currentPage);
            Iterator<? extends Object> iterator = getContentProvider()
                    .iterator(inputArray[i]);
            while (iterator.hasNext()) {
                // Row Limit
                if (getRowLimit() > 0 && currentRow >= getRowLimit())
                    throw FormatterException.exceedRowLimit();
                newRow(iterator.next(), contentProvider);
                currentRow++;
                if (shouldStartNewPage()) {
                    firePageEnd(currentPage);
                    currentPage++;
                    currentRow = 0;
                    firePageBegin(currentPage);
                    newPage();
                }
            }
            fireSectionEnd(i);
            currentSection++;
        }

        // End
        finalizeFile();
        reset();
        fireEnd();
        getDescriber().describe(file);
        return file;
    }

    protected void reset() {
        currentRow = 0;
        currentSection = 0;
        currentPage = 0;
    }

    /**
     * Default Implementation for Paging Control. Subclass can override this
     * method to indicate it's own paging method.
     * 
     * @return
     */
    protected boolean shouldStartNewPage() {
        return getPageSize() != NO_PAGING && currentRow == getPageSize();
    }

    /**
     * Invoked when a new format operation starts.
     * 
     * @throws IOException
     */
    protected abstract void createFile() throws IOException;

    /**
     * Invoked when all content had been formatted and the operation is about to
     * exit.
     * 
     * @throws IOException
     */
    protected abstract void finalizeFile() throws IOException;

    /**
     * Invoked each time the formatter come to a new input.
     * 
     * @param sectionHeader
     * @throws IOException
     */
    protected abstract void newSection(String[] sectionHeader)
            throws IOException;

    /**
     * Invoked when the formatter come to a new page. The page size is
     * determined by {@link #shouldStartNewPage()}. By default, the
     * implementation use the value of {@link #pageSize} to determine whether to
     * start a new page.
     * 
     * @throws IOException
     */
    protected abstract void newPage() throws IOException;

    /**
     * Invoked on each element extracted from the input.
     * 
     * @param input
     * @param provider
     * @throws IOException
     */
    protected abstract void newRow(Object input,
            ITableContentProvider<?> provider) throws IOException;

    /**
     * True to use memory file. False to use disk file. Only take effect when an
     * external file was not provided via {@link #setFile(IFile)}.
     */
    private boolean useMemoryFile = true;

    public boolean isUseMemoryFile() {
        return useMemoryFile;
    }

    public void setUseMemoryFile(boolean useMemoryFile) {
        this.useMemoryFile = useMemoryFile;
    }

    /**
     * The file to be used for output
     */
    private IFile file;

    public IFile getFile() {
        return file;
    }

    public void setFile(IFile file) {
        this.file = file;
    }

    /**
     * Get an OutputStream to write the formatted content in. WARNING: Don't
     * call the OutputStream's close method in formatter.
     * 
     * @return OutputStream fetched from file.
     * @throws IOException
     */
    protected OutputStream getFileOutputStream() throws IOException {
        if (file == null) {
            // Create File
            if (isUseMemoryFile()) {
                file = new MemoryFile();
            } else {
                file = new MappedFile(true);
            }
        }
        return file.getOutputStream(true);
    }

    /**
     * Decoration Info
     */
    private Decoration deco;

    public Decoration getDeco() {
        return deco;
    }

    public void setDeco(Decoration deco) {
        this.deco = deco;
    }

    /**
     * Method to get Default Font
     * 
     * @return
     */
    protected FontBean getFont() {
        if (getDeco() != null)
            return getDeco().getBasic().font;
        return null;
    }
}

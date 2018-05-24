package com.eray.util.file;

import java.io.File;
import java.util.Comparator;

public class FileComparator implements Comparator<File>
{

    @Override
    public int compare(File file1, File file2)
    {
        return file1.lastModified()>file2.lastModified()?1:(file1.lastModified()==file2.lastModified()?0:-1);
    }

}

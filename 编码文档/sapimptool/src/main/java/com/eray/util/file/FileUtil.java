package com.eray.util.file;

import java.io.File;

public class FileUtil
{
    private static boolean success;
    public static boolean moveFile(String filePath, File file){
        success=file.renameTo(new File(filePath));
        file.delete();
        return success;
    }
}

package apiutility;

import java.io.*;

public class FileOpHelper {
    public static String Read(String fileName) {
        String userdir = System.getProperty("user.dir");
        fileName = userdir + "\\src\\TestData\\Demo\\" +fileName;
        String encoding = "UTF-8";
        File file = new File(fileName);
        if(!file.exists()) {
            CommonTool.ThrowNewException(fileName+"文件不存在！");
        }
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

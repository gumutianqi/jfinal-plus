/**
 * Copyright (c) 2009-2016, LarryKoo 老古 (gumutianqi@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.plus.kit;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class FileKit {

    /**
     * 遍历文件夹中文件
     *
     * @param filepath 文件路径
     * @return 返回file［］ 数组
     */
    public static File[] getFileList(String filepath) {
        File d;
        File list[] = null;
        /** 建立当前目录中文件的File对象 **/
        try {
            d = new File(filepath);
            if (d.exists()) {
                list = d.listFiles();
            }
        } catch (Exception e) {
            log.debug("Other Exception", e);
        }
        /** 取得代表目录中所有文件的File对象数组 **/
        return list;
    }

    /**
     * 读取文本文件内容
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding        文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public static String readTxt(String filePathAndName, String encoding) throws IOException {
        encoding = encoding.trim();
        StringBuffer str = new StringBuffer("");
        String st;
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data;
                while ((data = br.readLine()) != null) {
                    str.append(data);
                }
            } catch (Exception e) {
                str.append(e.toString());
            }
            st = str.toString();
            if (st != null && st.length() > 1)
                st = st.substring(0, st.length() - 1);
        } catch (IOException es) {
            log.debug("IO Exception", es);
            st = "";
        }
        return st;
    }

    /**
     * 读取文本文件内容
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding        文本文件打开的编码方式
     * @return 返回文本文件的内容
     */
    public static List<String> readTxtList(String filePathAndName, String encoding) throws IOException {
        encoding = encoding.trim();
        List<String> listStr = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream(filePathAndName);
            InputStreamReader isr;
            if (encoding.equals("")) {
                isr = new InputStreamReader(fs);
            } else {
                isr = new InputStreamReader(fs, encoding);
            }
            BufferedReader br = new BufferedReader(isr);
            try {
                String data = "";
                while ((data = br.readLine()) != null) {
                    listStr.add(data);
                }
            } catch (Exception e) {
                listStr.add(e.toString());
            }
        } catch (IOException es) {
            log.debug("IO Exception", es);
        }
        return listStr;
    }

    /**
     * 新建目录
     *
     * @param folderPath 目录
     * @return 返回目录创建后的路径
     */
    public static Boolean createFolder(String folderPath) {
        String dir = folderPath;
        boolean bool = true;
        try {
            File myFilePath = new File(dir);
            if (!myFilePath.exists()) {
                bool = myFilePath.mkdirs();
            }
        } catch (Exception e) {
            bool = false;
            log.debug("Other Exception", e);
        }
        return bool;
    }

    /**
     * 新建文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return
     */
    public static Boolean createFile(String filePathAndName) {
        boolean bool = true;
        try {
            File myFileAndName = new File(filePathAndName);
            if (!myFileAndName.exists()) {
                bool = myFileAndName.createNewFile();
            }
        } catch (IOException e) {
            bool = false;
            log.debug("IOException", e);
        } catch (Exception e) {
            bool = false;
            log.debug("Other Exception", e);
        } finally {
            return bool;
        }
    }

    /**
     * 新建文件，并写入内容
     *
     * @param filePath    文本文件完整绝对路径
     * @param fileName    文件名
     * @param fileContent 文本文件内容
     * @return
     */
    public static Boolean createFile(String filePath, String fileName, String fileContent) {
        boolean bool = false;
        try {
            filePath = filePath.toString();
            fileName = fileName.toString();

            if (!filePath.endsWith("/")) {
                filePath += "/";
            }

            String filePathAndName = filePath + fileName;

            if (createFolder(filePath) && createFile(filePathAndName)) {
                File myFilePath = new File(filePathAndName);
                FileWriter resultFile = new FileWriter(myFilePath);
                PrintWriter myFile = new PrintWriter(resultFile);
                String strContent = fileContent;
                myFile.println(strContent);
                myFile.close();
                resultFile.close();
                bool = true;
            }
        } catch (IOException e) {
            log.debug("IO Exception", e);
        } catch (Exception e) {
            log.debug("Other Exception", e);
        } finally {
            return bool;
        }
    }

    /**
     * 新建文件，并写入内容
     *
     * @param filePath        文本文件完整绝对路径
     * @param fileName        文件名
     * @param fileContentList 文本文件内容集合
     * @return
     */

    public static Boolean createFile(String filePath, String fileName, List<String> fileContentList) {
        boolean bool = false;
        try {
            filePath = filePath.toString();
            fileName = fileName.toString();

            if (!filePath.endsWith("/")) {
                filePath += "/";
            }

            String filePathAndName = filePath + fileName;

            if (createFolder(filePath) && createFile(filePathAndName)) {
                File myFilePath = new File(filePathAndName);
                FileWriter resultFile = new FileWriter(myFilePath);
                PrintWriter myFile = new PrintWriter(resultFile);
                for (String strContent : fileContentList) {
                    myFile.println(strContent);
                }
                myFile.close();
                resultFile.close();
                bool = true;
            }
        } catch (IOException e) {
            log.debug("IO Exception", e);
        } catch (Exception e) {
            log.debug("Other Exception", e);
        } finally {
            return bool;
        }
    }

    /**
     * 有编码方式的文件创建和内容写入
     *
     * @param filePath    文本文件完整绝对路径
     * @param fileName    文件名
     * @param fileContent 文本文件内容
     * @param encoding    编码方式 例如 GBK 或者 UTF-8
     * @return
     */
    public static Boolean createFile(String filePath, String fileName, String fileContent, String encoding) {
        boolean bool = false;
        try {
            filePath = filePath.toString();
            fileName = fileName.toString();

            if (!filePath.endsWith("/")) {
                filePath += "/";
            }

            String filePathAndName = filePath + fileName;

            if (createFolder(filePath) && createFile(filePathAndName)) {
                File myFilePath = new File(filePathAndName);
                PrintWriter myFile = new PrintWriter(myFilePath, encoding);
                String strContent = fileContent;
                myFile.println(strContent);
                myFile.close();
                bool = true;
            }
        } catch (IOException e) {
            log.debug("IO Exception", e);
        } catch (Exception e) {
            log.debug("Other Exception", e);
        } finally {
            return bool;
        }
    }


    /**
     * 删除文件
     *
     * @param filePathAndName 文本文件完整绝对路径及文件名
     * @return Boolean 成功删除返回true遭遇异常返回false
     */
    public static Boolean delFile(String filePathAndName) {
        boolean bea = false;
        try {
            String filePath = filePathAndName;
            File myDelFile = new File(filePath);
            if (myDelFile.exists()) {
                myDelFile.delete();
                bea = true;
            } else {
                bea = false;
            }
        } catch (Exception e) {
            log.debug("Other Exception", e);
        }
        return bea;
    }

    /**
     * 删除文件
     *
     * @param folderPath 文件夹完整绝对路径
     * @return
     */
    public static void delFolder(String folderPath) {
        try {
            /**删除完里面所有内容**/
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            /**删除空文件夹**/
            myFilePath.delete();
        } catch (Exception e) {
            log.debug("Other Exception", e);
        }
    }

    /**
     * 删除指定文件夹下所有文件
     *
     * @param path 文件夹完整绝对路径
     * @return
     */
    public static Boolean delAllFile(String path) {
        boolean bea = false;
        File file = new File(path);
        if (!file.exists()) {
            return bea;
        }
        if (!file.isDirectory()) {
            return bea;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                /**先删除文件夹里面的文件**/
                delAllFile(path + "/" + tempList[i]);
                /**再删除空文件**/
                delFolder(path + "/" + tempList[i]);
                bea = true;
            }
        }
        return bea;
    }

    /**
     * 复制单个文件
     *
     * @param oldPathFile 准备复制的文件源
     * @param newPathFile 拷贝到新绝对路径带文件名
     * @return
     */
    public static void copyFile(String oldPathFile, String newPathFile) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPathFile);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPathFile);
                FileOutputStream fs = new FileOutputStream(newPathFile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (IOException e) {
            log.debug("IO Exception", e);
        } catch (Exception e) {
            log.debug("Other Exception", e);
        }
    }

    /**
     * 复制整个文件夹的内容
     *
     * @param oldPath 准备拷贝的目录
     * @param newPath 指定绝对路径的新目录
     * @return
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            /**如果文件夹不存在 则建立新文件**/
            new File(newPath).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath
                            + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                /**如果是子文件**/
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (IOException e) {
            log.debug("IO Exception", e);
        } catch (Exception e) {
            log.debug("Other Exception", e);
        }
    }

    /**
     * 移动文件
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    /**
     * 移动目录
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    /**
     * 取得文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 截取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoExt(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 截取文件名
     *
     * @param filename
     * @return
     */
    public static String getPathNoFileName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('/');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 建立一个可以追加的BufferedReader
     *
     * @param fileDir
     * @param fileName
     * @return
     */
    public static BufferedWriter getWriter(String fileDir, String fileName) {
        try {
            File f1 = new File(fileDir);
            if (!f1.exists()) {
                f1.mkdirs();
            }
            f1 = new File(fileDir, fileName);
            if (!f1.exists()) {
                f1.createNewFile();
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter(f1.getPath(), true));
            return bw;
        } catch (IOException e) {
            log.debug("IO Exception", e);
            return null;
        } catch (Exception e) {
            log.debug("Other Exception", e);
            return null;
        }
    }

    /**
     * 得到一个BufferedReader
     *
     * @param fileDir
     * @param fileName
     * @param encoding
     * @return
     */
    public static BufferedReader getReader(String fileDir, String fileName, String encoding) {
        try {
            File file = new File(fileDir, fileName);
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), encoding);
            BufferedReader br = new BufferedReader(read);
            return br;

        } catch (FileNotFoundException ex) {
            log.debug("File Exception", ex);
            return null;
        } catch (IOException e) {
            log.debug("IO Exception", e);
            return null;
        } catch (Exception e) {
            log.debug("Other Exception", e);
            return null;
        }

    }
}

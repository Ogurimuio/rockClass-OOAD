package com.example.rockclass.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileService {

    //上传文件
    public int upload(MultipartFile file,String url){
        if (Objects.isNull(file) || file.isEmpty()) {
            return 404;
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(url + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(url));
            }
            //文件写入指定路径
            Files.write(path, bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return 400;
        }
        return 200;
    }

    //更新文件
    //url:新文件上传路径
    //filePath:原文件绝对路径+文件名
    public int update(MultipartFile file,String url,String filePath) {
        if (Objects.isNull(file) || file.isEmpty()) {
            return 404;
        }
        try {
            File file1 = new File(filePath);
            if (file1.isFile() && file1.exists()) {
                file1.delete();
                return upload(file, url);
            } else {
                return 404;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 400;
        }
    }


    public void download(HttpServletResponse response,String url, String fileName) throws IOException {
        response.setContentType("application/json;charset=utf-8");

        try {
            response.setContentType("content-type:octet-stream");
            //response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));// 设置文件名
            File tempFile = new File(url);

            InputStream inputStream = new FileInputStream(tempFile);
            OutputStream out = response.getOutputStream();
            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                out.write(b, 0, length);
            }
            out.flush();
            out.close();
            inputStream.close();
            response.setStatus(200);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            response.setStatus(404);
            response.getWriter().write("服务器没有该文件");

        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(400);
            response.getWriter().write("文件下载失败");

        }
    }

















    /*  *//**
     * 压缩当前目录path下的所有文件 , 生成文件名称为 zipname , 放在路径zippath下 ;
     * 有异常则抛出 ;
     * @param path
     * @param zippath
     * @param zipname
     * @throws IOException
     *//*
    public static void folder2zip(String path, String zippath, String zipname) {
        File src = new File(path);
        ZipOutputStream zos = null;


        if (src == null  || !src.exists() || !src.isDirectory()) {
            // 源目录不存在 或不是目录 , 则异常
            throw new RuntimeException("压缩源目录不存在或非目录!" + path);
        }

        File destdir = new File(zippath);

        if (!destdir.exists()) {
            // 创建目录
            destdir.mkdirs();
        }

        File zipfile = new File(zippath+"\\"+zipname);


        File[] srclist = src.listFiles();

        if (srclist == null || srclist.length == 0) {
            // 源目录内容为空,无需压缩
            throw new RuntimeException("源目录内容为空,无需压缩下载!" + path);
        }

        try {
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipfile)));

            // 递归压缩目录下所有的文件  ;
            compress(zos, src, src.getName());

            zos.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException("压缩目标文件不存在!" + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("压缩文件IO异常!" + e.getMessage());
        }
        finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }
        }

    }

    *//**
     * 递归压缩文件
     * @param zos
     * @param src
     * @throws IOException
     *//*
    private static void compress(ZipOutputStream zos, File src, String name) throws IOException {
        if (src == null || !src.exists()) {
            return ;
        }
        if (src.isFile()) {
            byte[] bufs = new byte[10240];

            ZipEntry zentry = new ZipEntry(name);
            zos.putNextEntry(zentry);

            FileInputStream in = new FileInputStream(src);

            BufferedInputStream bin = new BufferedInputStream(in, 10240);

            int readcount = 0 ;

            while( (readcount = bin.read(bufs, 0 , 10240)) != -1) {
                zos.write(bufs, 0 , readcount);
            }

            zos.closeEntry();
            bin.close();
        } else {
            // 文件夹
            File[] fs = src.listFiles();

            if (fs == null || fs.length == 0 ) {
                zos.putNextEntry(new ZipEntry(name + File.separator ));
                zos.closeEntry();
                return ;
            }

            for (File f : fs) {
                compress(zos, f, name + File.separator + f.getName());
            }
        }
    }

*/
}

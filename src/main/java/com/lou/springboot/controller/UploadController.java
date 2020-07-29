package com.lou.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
public class UploadController {
    // 文件保存路径
    private final static String FILE_UPLOAD_PATH = "upload/";

    @RequestMapping(value="/upload", method = RequestMethod.POST)

    public String upload(@RequestParam("file") MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        // 生成文件名通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

        Random r = new Random();

        StringBuffer tempName = new StringBuffer();

        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);

        String newFileName = tempName.toString();

        try {
            // 保存文件
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FILE_UPLOAD_PATH + newFileName);
            Files.write(path, bytes);

            return "files/" + newFileName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "上传失败";
    }

}

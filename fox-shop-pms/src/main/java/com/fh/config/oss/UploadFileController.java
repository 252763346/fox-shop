package com.fh.config.oss;

import com.fh.utils.CommonsReturn;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName UploadFileController
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/3 11:38
 **/
@RestController
@RequestMapping("/UploadFileController")
public class UploadFileController {

    @PostMapping
    public CommonsReturn UploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String url= OssUtil.UploadFile("user",file);
        return CommonsReturn.success(url);
    }

}

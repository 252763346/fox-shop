package com.fh.config.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName OssUtil
 * @Deacription TODO
 * @Author 路东生
 * @Date 2020/12/3 11:25
 **/
public class OssUtil {

    // Endpoint以杭州为例，其它Region请按实际情况填写。
    public static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    public static final String accessKeyId = "LTAI4G2kPxMqDpfYpusfQ1ec";
    public static final String accessKeySecret = "O29ePSErQ41v1TYWCgoWIrejWhfaUV";
    public static final String bucketName = "2005one";
    public static final String photoUrl="https://2005one.oss-cn-beijing.aliyuncs.com/";



    public static String UploadFile(String mkdir,MultipartFile file) throws IOException {
        // <yourObjectName>上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String oldFileName = file.getOriginalFilename();
        String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
        String newFileName=UUID.randomUUID().toString()+suffix;
        String path = mkdir+"/"+newFileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(bucketName, path,file.getInputStream());

        // 关闭OSSClient。
        ossClient.shutdown();

        return photoUrl+"/"+path;
    }

}

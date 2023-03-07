package com.ymk.health.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Slf4j
public class QiniuUtil {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    private static volatile UploadManager uploadManager;

    /**
     * 设置上传管理器
     */
    public static UploadManager getUploadManager() {
        // 构造一个带指定Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        if (uploadManager == null) {
            synchronized (UploadManager.class) {
                if (uploadManager == null) {
                    uploadManager = new UploadManager(cfg);
                }
            }
        }
        return uploadManager;
    }

    /**
     * 上传本地文件
     *
     * @param filePath 路径
     * @param fileName 文件名
     */
    public String uploadQiniu(String filePath, String fileName) {
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String key = this.genKey(fileName);
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("Qiniu上传成功key-->{}", putRet.key);
            log.info("Qiniu上传成功hash-->{}", putRet.hash);
            return key;
        } catch (QiniuException e) {
            try {
                log.error("Qiniu错误-->{}", e.response.bodyString());
            } catch (QiniuException ex) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 字节数组上传
     *
     * @param bytes    字节数组
     * @param fileName 文件名
     */
    public String uploadQiniu(byte[] bytes, String fileName) {
        //构造一个带指定 Region 对象的配置类
        UploadManager uploadManager = getUploadManager();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = this.genKey(fileName);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("Qiniu上传成功key-->{}", putRet.key);
            log.info("Qiniu上传成功hash-->{}", putRet.hash);
            return key;
        } catch (QiniuException e) {
            try {
                log.error("Qiniu错误-->{}", e.response.bodyString());
            } catch (QiniuException ex) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 数据流上传
     *
     * @param inputStream 数据流
     * @param fileName    文件名
     */
    public String uploadQiniu(InputStream inputStream, String fileName) {
        //构造一个带指定 Region 对象的配置类
        UploadManager uploadManager = getUploadManager();
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = this.genKey(fileName);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("Qiniu上传成功key-->{}", putRet.key);
            log.info("Qiniu上传成功hash-->{}", putRet.hash);
            return key;
        } catch (QiniuException e) {
            try {
                log.error("Qiniu错误-->{}", e.response.bodyString());
            } catch (QiniuException ex) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 根据文件名删除文件
     *
     * @param fileName 文件名
     */
    public void deleteFileForQiniu(String fileName) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException e) {
            //如果遇到异常，说明删除失败
            log.error("Qiniu错误码-->{}", e.code());
            log.error("Qiniu错误-->{}", e.response.toString());
        }
    }

    /**
     * 根据文件名生成key
     *
     * @param fileName 文件名
     * @return key
     */
    private String genKey(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date()) + fileName;
    }
}

package com.eum.haetsal.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.eum.haetsal.common.DTO.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {
    private final AmazonS3Client amazonS3Client;

    private String bucketName="k-eum";

//    private String filePath = "marketpost";

    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID() + "_" + ext;
    }
    public FileDto uploadFile(MultipartFile multipartFile, String filePath){
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);
        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        String keyName ="" ;
        try (InputStream inputStream = multipartFile.getInputStream()) {

            keyName = filePath + "/" + uploadFileName;

            // S3에 폴더 및 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            // S3에 업로드한 폴더 및 파일 URL
            uploadFileUrl = "https://kr.object.ncloudstorage.com/"+ bucketName + "/" + keyName;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileDto.builder().originalFileName(originalFileName)
                .uploadFileName(uploadFileName)
                .uploadFilePath(filePath)
                .uploadFileUrl(uploadFileUrl)
                .build();

    }
    public void deleteFile(String filePath, String fileName){
        String keyName = filePath + "/" + fileName;
        try{
            amazonS3Client.deleteObject(bucketName,keyName);
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }

    }
    public List<FileDto> uploadFiles(List<MultipartFile> multipartFiles,String filePath) {


        List<FileDto> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            FileDto fileDto = uploadFile(multipartFile,filePath);
            s3files.add(fileDto);
        }

        return s3files;
    }
    public FileDto uploadFileFromBase64(byte[] base64String, String originalFileName, String filePath) {
//        byte[] decodedBytes = Base64.getDecoder().decode(base64String);

        // 파일 이름에서 확장자 추출
        String fileExtension = StringUtils.getFilenameExtension(originalFileName);
        if (fileExtension == null) {
            // 확장자가 없을 경우 기본적으로 jpg로 설정
            fileExtension = "jpg";
        }

        // 새로운 파일 이름 생성 (UUID와 확장자 조합)
        String uploadFileName = getUuidFileName(originalFileName) + "." + fileExtension;

        String keyName = filePath + "/" + uploadFileName;
        String uploadFileUrl = "";

        // base64 문자열을 파일로 변환하여 업로드
        try (InputStream inputStream = new ByteArrayInputStream(base64String)) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(base64String.length);
            objectMetadata.setContentType("image/" + fileExtension); // 이미지인 경우에는 확장자에 따라 Content-Type 설정

            // S3에 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            // S3에 업로드한 파일 URL
            uploadFileUrl = "https://kr.object.ncloudstorage.com/" + bucketName + "/" + keyName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return FileDto.builder()
                .originalFileName(originalFileName)
                .uploadFileName(uploadFileName)
                .uploadFilePath(filePath)
                .uploadFileUrl(uploadFileUrl)
                .build();
    }


    // 스토리지에 파일 업로드
    private String uploadFileToStorage(Path filePath, String storagePath) {
        String uploadFileUrl = "";

        try (InputStream inputStream = Files.newInputStream(filePath)) {
            String keyName = storagePath + "/" + filePath.getFileName().toString();

            // S3에 폴더 및 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, new ObjectMetadata())
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            // S3에 업로드한 폴더 및 파일 URL
            uploadFileUrl = "https://kr.object.ncloudstorage.com/"+ bucketName + "/" + keyName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadFileUrl;
    }

    // 파일 이름에 UUID 추가
}

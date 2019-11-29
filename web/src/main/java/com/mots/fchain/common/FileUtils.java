package com.mots.fchain.common;

import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.User;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileUtils {

    public Evidence parseFileInfo(FileItem file, User user, String path) throws Exception{
        if(ObjectUtils.isEmpty(file)){
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();

        String newFileName, originalFileName, fileExt, documentId;

        originalFileName = file.getName();

        int pos = originalFileName.lastIndexOf(".");

        fileExt = originalFileName.substring(pos+1);

        Evidence uploadEvidence = new Evidence();

        //newFileName = Long.toString(System.nanoTime()) + fileExt;
        documentId = bytesToHex(sha256(originalFileName));

        uploadEvidence.setDocumentId(documentId);
        uploadEvidence.setFileSize(file.getSize());
        uploadEvidence.setOriginalFileName(file.getName());
        uploadEvidence.setStoredFiledPath(path);
        uploadEvidence.setCreateDate(current.format(format));
        uploadEvidence.setCreatorName(member.getName());
        uploadEvidence.setDeleteStatus('N');

        uploadEvidence.setSecurityLevel(securityLevel);
        return uploadDocument;
    }


    /* 파일 이름으로 해쉬 함수 이용해 문서고유아이디 만들기 */
    private static byte[] sha256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());

        return md.digest();
    }
    /* 바이트를 핵사값으로 변환 */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}

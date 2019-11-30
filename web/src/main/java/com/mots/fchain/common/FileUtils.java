package com.mots.fchain.common;

import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.User;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class FileUtils {

    /* 업로드시 블록체인 넣을 데이터 생성 */
    public Evidence parseFileInfo(File file, String caseId, User user, String evidenceId,  int fileSize, String description, String path, String fileHash) throws Exception{

        String fileName;
        String registerTime;
        /* 증거 번호 생성 - 증거 부호는 '증거'로 고정 */

        fileName = file.getName();
        Evidence uploadEvidence = new Evidence();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm:SS");
        ZonedDateTime current = ZonedDateTime.now();
        registerTime = current.format(format);


        /* 파일 정보 생성 - 블록체인 저장 */
        uploadEvidence.setRegisterId(user.getUserId());
        uploadEvidence.setRegisterTime(registerTime);
        uploadEvidence.setCaseId(caseId);
        uploadEvidence.setEvidenceId(evidenceId);
        uploadEvidence.setFileName(fileName);
        uploadEvidence.setFileSize(fileSize);
        uploadEvidence.setFilePath(path);
        uploadEvidence.setDescription(description);
        uploadEvidence.setHash(fileHash);

        return uploadEvidence;
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

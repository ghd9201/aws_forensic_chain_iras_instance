package com.mots.fchain.common;

import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.User;

import com.mots.fchain.service.CaseProfileService;
import com.mots.fchain.service.EvidenceService;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileUtils {

    @Autowired
    private CaseProfileService caseService;

    @Autowired
    private EvidenceService evidenceService;


    /* 업로드시 블록체인 넣을 데이터 생성 */
    public Evidence parseFileInfo(FileItem file, String caseId, User user, String description,  String path, String fileHash) throws Exception{
        if(ObjectUtils.isEmpty(file)){
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
        ZonedDateTime current = ZonedDateTime.now();

        String fileName, hash;

        int evidenceNo = 0;

        if(caseId == null){
            /* 사건 번호 생성 - 사건 부호는 '형제'로 고정 */
            caseId = current.format(format)+"-형제-"+ caseService.getCaseCnt();
        }

        /* 증거 번호 생성 - 증거 부호는 '증거'로 고정 */
        evidenceNo = (evidenceService.getEvidenceNo(caseId) + 1);

        fileName = file.getName();
        Evidence uploadEvidence = new Evidence();


        /* 파일 정보 생성 - 블록체인 저장 */
        uploadEvidence.setRegisterId(user.getUserId());
        uploadEvidence.setCaseId(caseId);
        uploadEvidence.setEvidenceNo(evidenceNo);
        uploadEvidence.setFileName(fileName);
        uploadEvidence.setFileSize(file.getSize());
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

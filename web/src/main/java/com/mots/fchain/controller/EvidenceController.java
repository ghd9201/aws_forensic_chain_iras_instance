package com.mots.fchain.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import com.mots.fchain.common.FileUtils;
import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.User;
import com.mots.fchain.service.CaseProfileService;
import com.mots.fchain.service.EvidenceService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@MultipartConfig(fileSizeThreshold = 1024*1024*10, maxFileSize = 1024*1024*30)
public class EvidenceController {
    @Autowired
    private static EvidenceService evidenceService;

    @Autowired
    private static CaseProfileService caseService;

    @Autowired
    private static FileUtils fileUtils;

    private static final String UPLOAD_DIR = "upload";

    private User user;
    private Evidence evidence;
    private List<Evidence> entireFiles;

    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "/MobileTop/capture/";
    private static final String TEMP_FILE_NAME = "UploadedFileName.xls";

    /* fchain hyperledger network restFUL API 서버 URL */
    private static final String HyperledgerSendURL = "http://54.219.173.243:4000/api/v1/fchain";

    /* fchain 증거 기록 추가를 위한 추가 URL */
    private static final String HyperledgerAddEvidenceRecord = "/addEvidenceRecord";

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @GetMapping(value = "/mypage/uploadDocument.do")
    public ModelAndView getUpload() throws Exception{
        ModelAndView mv = new ModelAndView("/fchain/mypage");
        return mv;
    }

    @PostMapping(value = "/mypage/uploadDocument.do")
    @ResponseBody
    public void UploadDocument(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("############### FileUpload - START ##########");
        String returnValueJSON = "";

        String[] fileNameArray;
        int fileNameNo = 0;

        /* 행위자 정보 */
        User user = (User)request.getSession().getAttribute("user");

        /* 증거 이벤트 객체 */
        Evidence evidence;

        /* 사건 번호 */
        String caseId = null;

        /* 증거 이벤트 행위 */
        String eventType = null;

        /* 증거 이벤트 행위자 */
        String userId = null;

        /* 이벤트 행위자에 대한 설명 */
        String description = null;

        /* 증거물 해시값 */
        String fileHash;

        /* 업로드 폴더 날짜명 생성 - 날짜 데이터 생성 */
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "upload/"+current.format(format);
        File file = new File(path);
        if(file.exists() == false){
            file.mkdirs();
        }

        /* 사건 번호가 이미 있는 경우와 없는 경우 구분 */
        if(caseId == null){
            /* 사건 번호 생성 - 사건 부호는 '형제'로 고정 */
            caseId = current.format(format)+"-형제-"+ caseService.getCaseCnt();
        }


        try {
            /* 업로드된 파일 리스트를 확인 */
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);

            fileNameArray = new String[items.size()];
            fileNameNo = 0;
            for (FileItem item : items) {

                if (item.isFormField()) {
                    // 이곳은 File 이외의 다른 값을 설정 했을 때만 실행된다.
                } else {
                    String fileName = item.getName();	// 업로드된 파일네임 (확장자 포함)

                    File uploadedFile = new File(path+"/"+fileName);
                    item.write( uploadedFile );

                    response.setContentType("text/plain");
                    response.setCharacterEncoding("UTF-8");

                    fileHash = sha(path+"/"+fileName);

                    /* 증거 데이터 생성 */
                    evidence = fileUtils.parseFileInfo(item, caseId, user, description, path+"/"+fileName, fileHash);

                    /* 테스트 출력을 위한 문장 */
                    returnValueJSON += "{ \"fileName\" : \"" + item.getName() + "\" }";

                    /* 파일이 여러 개일 경우를 말하는 것 같음 */
                    fileNameArray[fileNameNo++] = evidence.getFileName();

                    evidenceService.upload(evidence);

                    JSONObject blockData = createBlockdata(fileHash, evidence, eventType, userId, description);

                    sendBlockchainData(blockData);
                }
                response.getWriter().print(gson.toJson(fileNameArray));
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* 테스트 출력을 위한 문장 */
        response.getWriter().print(returnValueJSON);

        System.out.println("############### FileUpload - End ##########");
        /*

        Member member = (Member)req.getAttribute("member");

        documentService.upload(file, member);
*/
        //return mv;
    }


    /* Hyperledger Fabric Data Send */
    private static void sendBlockchainData(JSONObject blockData) throws IllegalStateException{
        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        try {
            System.out.println("############### Send Hyperledger Network ###############");

            URL url = new URL(HyperledgerSendURL+HyperledgerAddEvidenceRecord);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept-Charset","UTF-8");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);

            OutputStream os = conn.getOutputStream();
            os.write(blockData.toString().getBytes("UTF-8"));
            os.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            while ((inputLine = in.readLine())!=null){
                outResult.append(inputLine);
            }

            conn.disconnect();

            System.out.println("############### Finished Hyperledger Network ###############");
        }catch (Exception e){

            e.printStackTrace();
        }
    }

    private static String md(String a) throws Exception{
        MessageDigest md = MessageDigest.getInstance("MD5");
        FileInputStream fis = new FileInputStream(a);

        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        }
        byte[] mdbytes = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private static String sha(String a) throws Exception{
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        FileInputStream fis = new FileInputStream(a);

        byte[] dataBytes = new byte[1024];

        int nread = 0;
        while ((nread = fis.read(dataBytes)) != -1) {
            md.update(dataBytes, 0, nread);
        };
        byte[] mdbytes = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    private static JSONObject createBlockdata(String fileHash, Evidence evidence, String eventType, String userId, String description) throws Exception{
        JSONObject jsonObject = new JSONObject();

        /* 이벤트 발생 시간 구하기 */
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
        ZonedDateTime current = ZonedDateTime.now();

        String evidenceId = evidence.getCaseId() +"-증거-"+evidence.getEvidenceNo();


        String timestamp = current.format(format);


        jsonObject.put("objectId", evidence.getObjectId());
        jsonObject.put("timestamp", timestamp);


        if(eventType =="upload"){
            /* 증거에 발생한 이벤트가 새로운 증거 등록일 경우 */
            jsonObject.put("registerTime", timestamp);
        }
        else{
            /* 증거에 발생한 이벤트가 등록 이외의 이벤트일 경우 */
            jsonObject.put("registerTime", evidence.getRegisterTime());
        }

        jsonObject.put("caseId", evidence.getCaseId());
        jsonObject.put("evidenceId", evidenceId);
        jsonObject.put("fileName", evidence.getFileName());
        jsonObject.put("fileSize", evidence.getFileSize());
        jsonObject.put("eventType", eventType);
        jsonObject.put("eventUserId", userId);
        jsonObject.put("description", description);
        jsonObject.put("evidenceHash", fileHash);

        System.out.println("jsonObject :"+jsonObject);

        return jsonObject;
    }
}
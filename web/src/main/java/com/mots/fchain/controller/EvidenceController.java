package com.mots.fchain.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import com.mots.fchain.common.FileUtils;
import com.mots.fchain.model.CaseInfo;
import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.MoveInfo;
import com.mots.fchain.model.User;
import com.mots.fchain.service.EvidenceService;

import com.mots.fchain.service.EvidenceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

@Controller
@MultipartConfig(fileSizeThreshold = 1024*1024*10, maxFileSize = 1024*1024*30)
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private static FileUtils fileUtils = new FileUtils();

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

    private String caseId;
    private String evidenceId;
    private String eventType;
    private String description;

    private MoveInfo move;

    @GetMapping(value = "/mypage/uploadEvidence.do")
    public ModelAndView getUpload() throws Exception{
        ModelAndView mv = new ModelAndView("/fchain/mypage");
        return mv;
    }

    @GetMapping(value = "/mypage/evidenceInfo.do")
    public ModelAndView getInfo() throws Exception{
        ModelAndView mv = new ModelAndView("/fchain/mypage");
        return mv;
    }

    @PostMapping(value = "/mypage/evidenceInfo.do")
    @ResponseBody
    public void getEvidenceInfo(@RequestBody CaseInfo caseInfo) throws Exception{
        System.out.println("caseInfo"+caseInfo);

        caseId = caseInfo.getCaseId();
        evidenceId = caseInfo.getEvidenceId();
        eventType = caseInfo.getEventType();
        description = caseInfo.getDescription();
    }


    @GetMapping(value = "/mypage/move.do")
    public ModelAndView getMove() throws Exception{
        ModelAndView mv = new ModelAndView("/fchain/mypage");
        return mv;
    }

    @PostMapping(value = "/mypage/move.do")
    @ResponseBody
    public void postMove(@RequestBody MoveInfo moveInfo, HttpServletResponse resposne, HttpServletRequest req) throws Exception{
        User user = (User)req.getSession().getAttribute("user");

        Evidence before;

        System.out.println("MoveInfo"+moveInfo);

        move = new MoveInfo();

        move.setMoveDescription(moveInfo.getMoveDescription());
        move.setMoveEvidence(moveInfo.getMoveEvidence());
        move.setMoveOrg(moveInfo.getMoveOrg());

        before = evidenceService.selectEvidence(moveInfo.getMoveEvidence());

        JSONObject blockData = createBlock(before, "증거 이관",user.getUserId(), move.getMoveOrg(), move.getMoveDescription());

        sendBlockchainData(blockData);
    }

    @PostMapping(value = "/mypage/uploadEvidence.do")
    @ResponseBody
    public ModelAndView UploadEvidence(MultipartHttpServletRequest request, HttpServletResponse response) throws Exception{
        ModelAndView mv = new ModelAndView("/mypage");

        String attach_path = "upload/";

        String pull_path = null;

        System.out.println("############### FileUpload - START ##########");

        String returnValueJSON = "";

        String uploadCaseId = "2019-형제-"+caseId;
        String uploadEvidenceId = uploadCaseId +"-증거-"+evidenceId;

        String[] fileNameArray;
        int fileNameNo = 0;

        /* 행위자 정보 */
        User user = (User)request.getSession().getAttribute("user");

        /* 증거 이벤트 객체 */
        Evidence evidence = new Evidence();

        int fileSize = 0;

        /* 증거 이벤트 행위자 */
        String userId = user.getUserId();
        String userOrg = user.getOrg();

        /* 증거물 해시값 */
        String fileHash = null;

        /* 증거물 파일 이름 */
        String fileName = null;

        /* 업로드 폴더 날짜명 생성 - 날짜 데이터 생성 */
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        attach_path = "upload/"+current.format(format);
        File file = new File(attach_path);
        if(file.exists() == false){
            file.mkdirs();
        }

        Iterator<String> itr = request.getFileNames();
        if(itr.hasNext()) {
            MultipartFile mpf = request.getFile(itr.next());
            fileName = mpf.getOriginalFilename();

            pull_path = attach_path+"/"+fileName;

            File uploadFile = new File(pull_path);

            uploadFile.createNewFile();

            //mpf.transferTo(uploadFile);
            System.out.println("file name : " + fileName);

            System.out.println(mpf.getOriginalFilename() +" uploaded!");
            try { //just temporary save file info into ufile
                System.out.println("file length : " + mpf.getBytes().length);
                fileSize = mpf.getBytes().length;

                response.setContentType("text/plain");
                response.setCharacterEncoding("UTF-8");

                fileHash = bytesToHex(sha256(fileName));

                /* 증거 데이터 생성 */
                evidence = fileUtils.parseFileInfo(uploadFile, uploadCaseId, user, uploadEvidenceId, fileSize, description, pull_path, fileHash);


                System.out.println(evidence);

                evidenceService.upload(evidence);

                /* 테스트 출력을 위한 문장 */
                returnValueJSON += "{ \"fileName\" : \"" + fileName + "\" }";

                JSONObject blockData = createBlockdata(fileHash, evidence, uploadEvidenceId, eventType, userId, userOrg, description);

                sendBlockchainData(blockData);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /* 테스트 출력을 위한 문장 */
        response.getWriter().print(returnValueJSON);

        System.out.println("############### FileUpload - End ##########");

        return mv;
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

    private static JSONObject createBlockdata(String fileHash, Evidence evidence, String evidenceId, String eventType, String userId, String userOrg, String description) throws Exception{
        JSONObject jsonObject = new JSONObject();

        /* 이벤트 발생 시간 구하기 */
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
        ZonedDateTime current = ZonedDateTime.now();

        String timestamp = current.format(format);

        jsonObject.put("objectId",  Integer.toString(evidence.getObjectId()));
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
        jsonObject.put("fileSize", Long.toString(evidence.getFileSize()));
        jsonObject.put("eventType", eventType);
        jsonObject.put("eventUserId", userId);
        jsonObject.put("eventUserOrg", userOrg);
        jsonObject.put("description", description);
        jsonObject.put("evidenceHash", fileHash);

        System.out.println("jsonObject :"+jsonObject);

        return jsonObject;
    }

    private static JSONObject createBlock(Evidence evidence,String eventType, String userId, String userOrg, String description) throws Exception{
        JSONObject jsonObject = new JSONObject();

        /* 이벤트 발생 시간 구하기 */
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
        ZonedDateTime current = ZonedDateTime.now();

        String timestamp = current.format(format);

        jsonObject.put("objectId",  Integer.toString(evidence.getObjectId()));
        jsonObject.put("timestamp", timestamp);

        jsonObject.put("registerTime", evidence.getRegisterTime());

        jsonObject.put("caseId", evidence.getCaseId());
        jsonObject.put("evidenceId", evidence.getEvidenceId());
        jsonObject.put("fileName", evidence.getFileName());
        jsonObject.put("fileSize", Long.toString(evidence.getFileSize()));
        jsonObject.put("eventType", eventType);
        jsonObject.put("eventUserId", userId);
        jsonObject.put("eventUserOrg", userOrg);
        jsonObject.put("description", description);
        jsonObject.put("evidenceHash", evidence.getEvidenceId());

        System.out.println("jsonObject :"+jsonObject);

        return jsonObject;
    }
}
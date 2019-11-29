package com.mots.fchain.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import com.mots.fchain.common.FileUtils;
import com.mots.fchain.model.Evidence;
import com.mots.fchain.model.User;
import com.mots.fchain.service.EvidenceService;
import com.mots.fchain.service.UserService;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@MultipartConfig(fileSizeThreshold = 1024*1024*10, maxFileSize = 1024*1024*30)
public class EvidenceController {
    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private FileUtils fileUtils;

    private static final String UPLOAD_DIR = "upload";

    private User user;
    private Evidence evidence;
    private List<Evidence> entireFiles;

    private static final long serialVersionUID = 1L;

    private static final String UPLOAD_DIRECTORY = "/MobileTop/capture/";
    private static final String TEMP_FILE_NAME = "UploadedFileName.xls";

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
        User user = (User)request.getSession().getAttribute("user");

        Evidence evidence;

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "upload/"+current.format(format);
        File file = new File(path);
        if(file.exists() == false){
            file.mkdirs();
        }

        try {
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

                    evidence = fileUtils.parseFileInfo(item, user, path+"/"+fileName);

                    returnValueJSON += "{ \"fileName\" : \"" + item.getName() + "\" }";

                    fileNameArray[fileNameNo++] = evidence.getFileName();

                    evidenceService.upload(evidence);
                }
                response.getWriter().print(gson.toJson(fileNameArray));
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.getWriter().print(returnValueJSON);

        System.out.println("############### FileUpload - End ##########");
        /*

        Member member = (Member)req.getAttribute("member");

        documentService.upload(file, member);
*/
        //return mv;
    }
}
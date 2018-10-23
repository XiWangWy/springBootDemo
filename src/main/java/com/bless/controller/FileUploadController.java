package com.bless.controller;

import com.bless.Utils.FileHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by wangxi on 18/7/3.
 */
@Controller
public class FileUploadController {

    /**
     * 上传单个文件
     * @return
     */
    @RequestMapping(value = "/upload",method = {RequestMethod.GET})
    public ModelAndView upload() {
        return new ModelAndView("uploading");
    }

    @RequestMapping(value = "/downloadhtml",method = RequestMethod.GET)
    public ModelAndView download(){
        return new ModelAndView("downloading");
    }

    /**
     * 上传单个文件
     * @return
     */
//    @RequestMapping(value = "/simpleupload",method = {RequestMethod.GET})
//    public ModelAndView multupload() {
//        return new ModelAndView("multiUploading");
//    }

//    @RequestMapping(value = "/simpleupload",method = RequestMethod.POST)
//    public @ResponseBody String simpleUpload(@RequestParam("file") MultipartFile file){
//        String targetPath = "./uploadFiles/";
//        String msg = FileHelper.writeClientDataToPath(file,targetPath);
//        return msg;
//    }

    /**
     * 上传文件即可以单个也可以多个同时上传
     * @param request
     * @return
     */
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public String upload(HttpServletRequest request){
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            String targetPath = "./uploadFiles/";
            FileHelper.writeClientDataToPath(file,targetPath);
        }
        return "upload successful";
    }

    /**
     * 下载文件
     * @param res
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void Download(HttpServletResponse res) throws UnsupportedEncodingException {
        String path = "./uploadFiles/hzw.jpeg";
        String fileName = path.replaceAll(".*/","");
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os;

        File downLoadFile = new File(path);
        if (!downLoadFile.exists()){
            try {
                downLoadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            os = res.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(downLoadFile));
            int nRead;
            while ((nRead = bis.read(buff, 0, buff.length)) != -1) {
                os.write(buff, 0, nRead);
                os.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("success");
    }


}

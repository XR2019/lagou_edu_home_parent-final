package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {

    //注入service层对象
    @Autowired
    private PromotionAdService promotionAdService;

    /*
        广告分页查询
     */
    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllAdByPage(PromotionAdVO promotionAdVO) {
        PageInfo<PromotionAd> pageInfo = promotionAdService.findAllPromotionAdByPage(promotionAdVO);

        ResponseResult responseResult = new ResponseResult(true,200,"广告分页查询成功",pageInfo);
        return responseResult;
    }

    /*
      课程图片上传
   */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        //1.判断接收到的上传文件是否为null
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
        //2.获取项目的部署路径
        //D:\apache-tomcat-8.5.56\webapps\ssm-web\
        String realPath = request.getServletContext().getRealPath("/");
        //D:\apache-tomcat-8.5.56\webapps\
        String substring = realPath.substring(0, realPath.indexOf("ssm-web"));

        //获取源文件名
        String filename = file.getOriginalFilename();

        //生成一个新的文件名
        String newFileName = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
        //文件上传
        String uploadPath = substring + "upload\\";
        File filePath = new File(uploadPath, newFileName);
        //如果目录不存在就创建该目录
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录：" + filePath);
        }
        //图片就进行了真正的上传
        file.transferTo(filePath);
        //将文件名和文件路径进行返回，进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", newFileName);
        map.put("filePath", "http://localhost:8080/upload/" + newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "图片上传成功", map);

        return responseResult;
    }

    /*
        新增&更新广告信息
     */
    @RequestMapping("/saveOrUpdatePromotionAd")
    public ResponseResult saveOrUpdatePromotionAd(@RequestBody PromotionAd promotionAd) {
        try {
            if (promotionAd.getId() == null) {
                //新建广告信息
                Date date = new Date();
                promotionAd.setCreateTime(date);
                promotionAd.setUpdateTime(date);
                promotionAdService.savePromotionAd(promotionAd);
                ResponseResult responseResult = new ResponseResult(true,200,"新建广告信息成功",null);
                return responseResult;
            } else {
                //修改广告信息
                Date date = new Date();
                promotionAd.setUpdateTime(date);

                promotionAdService.updatePromotionAd(promotionAd);
                ResponseResult responseResult = new ResponseResult(true,200,"修改广告位信息成功",null);
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        广告位置动态上下线
     */
    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(@RequestParam int id ,@RequestParam int status) {
        //执行修改操作
        if (status == 1) {
            promotionAdService.updatePromotionAdStatus(id,status);
        } else {
            promotionAdService.updatePromotionAdStatus(id,0);
        }
        //保存修改后的状态，并返回
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status",status);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",map);
        return responseResult;
    }
}

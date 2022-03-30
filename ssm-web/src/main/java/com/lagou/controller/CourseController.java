package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController //相当于@Controller + @ResponseBody
@RequestMapping("/course")
public class CourseController {

    /*
        多条件课程列表查询
     */

    @Autowired
    private CourseService courseService;

//    @RequestMapping("/findCourseByCondition")
    @RequestMapping("/findAllCourse")
    public ResponseResult findCourseByCondition(@RequestBody CourseVo courseVo) {
        //调用service
        List<Course> courses = courseService.findCourseByCondition(courseVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", courses);

        return responseResult;
    }

    /*
        课程图片上传
     */
    @RequestMapping("/courseUpload")
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
        新增课程信息及讲师信息的方法
        新增课程信息和修改课程信息要写在同一个方法中！！
     */
//    @RequestMapping("/saveOrUpdateCourse")
//    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
//
//        //调用service
//        courseService.saveCourseOrTeacher(courseVo);
//
//        ResponseResult responseResult = new ResponseResult(true, 200, "响应成功", null);
//
//        return responseResult;
//    }

    /*
        根据id获取课程信息及关联的讲师信息
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(@RequestParam(value = "id", required = true) int id) {
        CourseVo courseVo = courseService.findCourseById(id);
        ResponseResult responseResult = new ResponseResult(true, 200, "根据id查询课程信息成功", courseVo);
        return responseResult;
    }

    /*
        保存&更新课程信息接口
     */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) {

        try {
            if (courseVo.getId() == null) {
                courseService.saveCourseOrTeacher(courseVo);
                ResponseResult responseResult = new ResponseResult(true, 200, "保存课程信息成功", null);
                return responseResult;
            } else {
                courseService.updateCourseOrTeacher(courseVo);
                ResponseResult responseResult = new ResponseResult(true, 200, "修改课程信息成功", null);
                return responseResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
        修改课程状态
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(@RequestParam int id , @RequestParam int status) {
        //执行修改操作
        courseService.updateCourseStatus(id,status);
        //保存修改后的状态，并返回
        HashMap<String,Integer> map = new HashMap<>();
        map.put("status",status);
        ResponseResult responseResult = new ResponseResult(true,200,"响应成功",map);

        return responseResult;
    }
}

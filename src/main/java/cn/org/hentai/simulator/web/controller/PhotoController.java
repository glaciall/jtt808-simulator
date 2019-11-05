package cn.org.hentai.simulator.web.controller;

import cn.org.hentai.simulator.util.Configs;
import cn.org.hentai.simulator.web.entity.DriverPhoto;
import cn.org.hentai.simulator.web.service.XDriverPhotoService;
import cn.org.hentai.simulator.web.service.XRouteService;
import cn.org.hentai.simulator.web.vo.Page;
import cn.org.hentai.simulator.web.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by houcheng on 2018/11/25.
 * 驾驶员照片控制器
 */
@Controller
@RequestMapping("/photo")
public class PhotoController
{
    @Autowired
    XRouteService routeService;

    @Autowired
    private XDriverPhotoService driverPhotoService;

    /**
     * 批量保存照片
     * @param request
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("/addPhotos")
    public String addPhotos(HttpServletRequest request) throws IllegalStateException, IOException {

        Long driverId = Long.parseLong(request.getParameter("driverId"));

        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;

            List<MultipartFile> fileList = multiRequest.getFiles("file");

            for (MultipartFile file : fileList) {


                // 取得当前上传文件的文件名称
                String myFileName = file.getOriginalFilename();
                // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                if(!StringUtils.isEmpty(myFileName.trim())) {

                    // 重命名上传后的文件名
                    String fileName =  file.getOriginalFilename();
                    // 存放文件夹
                    String folder = Configs.get("file.upload.path") + File.separator;
                    File folderFile = new File(folder);
                    if (!folderFile.exists()) {
                        folderFile.mkdir();
                    }

                    folder += "driver" + File.separator;
                    folderFile = new File(folder);
                    if (!folderFile.exists()) {
                        folderFile.mkdir();
                    }

                    // 定义上传路径
                    String path = folder + fileName;

                    File localFile = new File(path);
                    if (!localFile.exists()) {
                        localFile.createNewFile();
                    }
                    file.transferTo(localFile);

                    // 将路径保存到数据库
                    this.driverPhotoService.create(new DriverPhoto(driverId, File.separator + "driver" + File.separator +  fileName));

                }
            }
        }
        return "redirect:/photo/toPhotoList";
    }
}

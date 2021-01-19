package demo.demo.api.hospital.facade;

import com.cmrh.indigo.common.util.CommonUtils;
import com.cmrh.nuw.healthExamination.app.biz.hospital.UwExamHospitalItemService;
import com.cmrh.nuw.healthExamination.app.biz.hospital.UwExamHospitalService;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.HospitalItemClassifyDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.QueryHospitalParam;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalItemDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.entity.UwExamHospitalItem;
import com.cmrh.nuw.healthExamination.infra.utils.common.WebResponse;
import com.cmrh.nuw.healthExamination.infra.utils.constant.SystemConstant;
import com.cmrh.sf.store.RFFile;
import com.cmrh.sf.store.RFFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 核保体检 - 医院信息维护相关
 * 
 * @author ex-zrh001 2019/12/31
 * */

@RequestMapping("uwExam/ExamHospital")
@Controller
public class UwExamHospitalController {
    
	private static final Logger logger = LoggerFactory.getLogger(UwExamHospitalController.class);
    @Autowired
    private UwExamHospitalService uwExamHospitalService;

    @Autowired
    private UwExamHospitalItemService uwExamHospitalItemService;

    /**
     * 医院列表
     *
     * @param queryHospitalParam
     * @return
     */
    @RequestMapping(value = "/branchHospitalList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBranchHospitalList(HttpServletRequest request,QueryHospitalParam queryHospitalParam) {
    	logger.info("uwExam/ExamHospital/branchHospitalList:queryHospitalParam:"+queryHospitalParam);
    	return WebResponse.create(uwExamHospitalService.getBranchHospitalList(queryHospitalParam)).status(HttpStatus.OK);
    }
    
    /**
     * 新增/更新医院信息
     *
     * @param uwExamHospitalDTO
     * @return
     */
    @RequestMapping(value = "/NewHospital", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createNewHospital (HttpServletRequest request,@RequestBody UwExamHospitalDTO uwExamHospitalDTO) {
    	logger.info("uwExam/ExamHospital/NewHospital:uwExamHospitalDTO:"+uwExamHospitalDTO);
    	String user = request.getRemoteUser();
    	uwExamHospitalDTO.setCreatedUser(user);
    	uwExamHospitalDTO.setUpdatedUser(user);
    	//新增or更新
    	return WebResponse.create(uwExamHospitalService.createNewHospital(uwExamHospitalDTO)).status(HttpStatus.OK);
    }
    
    
    /**
     * 医院模板选择
     *
     * @return
     */
    @RequestMapping(value = "/hospital", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getHospitalNameAndId() {
    	logger.info("uwExam/ExamHospital/hospital");
        return WebResponse.create(uwExamHospitalService.getHospitalNameAndId(SystemConstant.HospitalStatus.IN_COOPERATION)).status(HttpStatus.OK);
    }
    
    /**
     * 机构下拉选择
     *
     * @return
     */
    @RequestMapping(value = "/branch", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getBranchNameAndCode() {
    	logger.info("uwExam/ExamHospital/branch");
        return WebResponse.create(uwExamHospitalService.getBranchNameAndCode()).status(HttpStatus.OK);
    }
    
    /**
     * 医院项目基表上传回显
     * 
     * **/
    @RequestMapping(value = "/importExcel/hospitalItem", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadHospitalItemShow(HttpServletRequest request, @RequestParam("file") MultipartFile file,String gender) throws IOException {

        logger.info("uploadHospitalItem start.");

        String fileName = file.getOriginalFilename();
        logger.info("uploadHospitalItem fileName:"+fileName);

        String fileSuffix = fileName.substring(fileName.lastIndexOf("."));

        //判断文件是否是excel
        if (!StringUtils.equals(".xlsx", fileSuffix)
                && !StringUtils.equals(".xls", fileSuffix)) {
            logger.info(fileSuffix + " is not support file type");
            throw new RuntimeException(fileSuffix + " is not support file type");
        }

        //解决中文文件名问题,中文名称的MultipartFile在转Workbook会异常
        RFFilePath fileDestinationPath = RFFilePath.newTempFilePath();
        fileDestinationPath.setFileName(CommonUtils.formatDate(new Date(), "yyyyMMddHHmmss") + "hospitalItem"+fileSuffix);
        RFFile rfFile = new RFFile(fileDestinationPath);
        FileUtils.copyInputStreamToFile(file.getInputStream(),rfFile);
        
        List<UwExamHospitalItemDTO> uwExamHospitalItems = uwExamHospitalItemService.importHospitalItem(rfFile,fileSuffix);
        List<HospitalItemClassifyDTO> dataList = HospitalItemClassifyDTO.handleArrayData(uwExamHospitalItems);
        List<Map<String,String>> titleList = UwExamHospitalItem.getTitle();
        Map<String,Object> resultMap = new HashMap<String, Object>();
        resultMap.put("titleList", titleList);
        resultMap.put("dataMap", dataList);
        request.getSession().setAttribute("uwExamHospitalItems" + gender, uwExamHospitalItems);
        return  WebResponse.create(resultMap).status(HttpStatus.OK);
    }

    /**
     * 体检基表确认提交
     * */
    @RequestMapping(value = "/importExcel/hospitalItemCommit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadHospitalItemCommit(HttpServletRequest request,@RequestBody UwExamHospitalItemDTO requestParam) {
    	HttpSession session = request.getSession();
    	//取出对应性别模板
    	ArrayList<UwExamHospitalItemDTO> list = (ArrayList<UwExamHospitalItemDTO>) session.getAttribute("uwExamHospitalItems"+requestParam.getApplyGender());
    	if(CollectionUtils.isNotEmpty(list)) {
    		String user = request.getRemoteUser();
        	requestParam.setUpdatedUser(user);
        	uwExamHospitalItemService.importHospitalItemCommit(requestParam, list);
        	session.setAttribute("uwExamHospitalItems"+requestParam.getApplyGender(), null);
    	}
    	return WebResponse.create().status(HttpStatus.OK);
    }
    
    /**
     * 导出医院体检项
     * @throws IOException 
     * 
     * **/
    @RequestMapping(value = "/expotExcel/baseMoudle", method = RequestMethod.GET)
    public void downloadHospitalExamItem(HttpServletResponse response,String hospitalId,String applyGender) throws IOException {
    	String fileName = "项目基表.xls";
    	response.reset();
		fileName = new String(fileName.getBytes(), "ISO-8859-1");
		response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
		response.setContentType("application/octet-stream;charset=UTF-8");
    	OutputStream os = response.getOutputStream();
    	HSSFWorkbook wb = uwExamHospitalItemService.generateHospitalItemWorkbook(hospitalId, applyGender);
    	wb.write(os);
    	os.flush();
    }
    
}

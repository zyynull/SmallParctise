package demo.demo.biz.hospital.impl;

import com.cmrh.nuw.healthExamination.app.biz.hospital.UwExamHospitalItemService;
import com.cmrh.nuw.healthExamination.domain.hospital.dao.UwExamHospitalItemDao;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.HospitalItemClassifyDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalItemDTO;
import com.cmrh.nuw.healthExamination.infra.utils.util.ExcelUtils;
import com.cmrh.sf.store.RFFile;
import com.cmrh.uw.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service("UwExamBaseHospitalServiceImpl")
public class UwExamHospitalItemServiceImpl implements UwExamHospitalItemService {
    private static final Logger logger = LoggerFactory.getLogger(UwExamHospitalItemServiceImpl.class);

    @Autowired
    private UwExamHospitalItemDao uwExamHospitalItemDao;


    @Override
    @Transactional
    public void hospitalItemListInsert(List<UwExamHospitalItemDTO> dtoList, String hospitalId, String user) {
        //将集合写入
        for (UwExamHospitalItemDTO uwExamHospitalItemDTO : dtoList) {
            uwExamHospitalItemDTO.setHospitalId(hospitalId);
            uwExamHospitalItemDTO.setCreatedUser(user);
            uwExamHospitalItemDao.insert(uwExamHospitalItemDTO);
        }
    }


    @Override
    @Transactional
    public void hospitalItemleListUpdate(List<UwExamHospitalItemDTO> dtoList, String hospitalId, String gender, String user) {
        if (!CollectionUtils.isEmpty(dtoList)) {
            //先设为无效, 再逐条update为有效
            //将医院项目全部置为无效
            Map<String, String> map = new HashMap<>();
            map.put("hospitalId", hospitalId);
            map.put("gender", gender);
            uwExamHospitalItemDao.deleteItem(map);
            for (UwExamHospitalItemDTO uwExamHospitalItemDTO : dtoList) {
                //判断数据库中是否有该数据, 有更新,无新增
                uwExamHospitalItemDTO.setHospitalId(hospitalId);
                uwExamHospitalItemDTO.setCreatedUser(user);
                uwExamHospitalItemDTO.setUpdatedUser(user);
                uwExamHospitalItemDao.mergeHospitalItem(uwExamHospitalItemDTO);
            }
        }
    }

    /**
     * 导入医院体检项目数据
     * @param file
     * @param uwExamHospitalItemDTO
     * @return
     */
    @Override
    @Transactional
    public List<UwExamHospitalItemDTO> importHospitalItem(RFFile file,String fileSuffix) {
        logger.info("importHospitalItem start");
        List<UwExamHospitalItemDTO> uwExamHospitalItems = null;
        try {
            Workbook workbook = ExcelUtils.getWorkbook(new FileInputStream(file), fileSuffix);
            uwExamHospitalItems = readExcel(workbook, 0, 1, 0);
            logger.info("importHospitalItem end:" + uwExamHospitalItems);
        } catch (IOException e) {
            logger.error("importHospitalItem error", e);
        }

        return uwExamHospitalItems;
    }

    /**
     * 读取excel文件
     *
     * @param wb
     * @param sheetIndex    sheet页下标：从0开始
     * @param startReadLine 开始读取的行:从0开始
     * @param tailLine      去除最后读取的行
     */
    public List<UwExamHospitalItemDTO> readExcel(Workbook wb, int sheetIndex, int startReadLine, int tailLine) {


        List<UwExamHospitalItemDTO> uwExamHospitalItems = new ArrayList<>();

        Sheet sheet = wb.getSheetAt(sheetIndex);
        Row row = null;
        Cell cell = null;
        int last = sheet.getLastRowNum();
        for (int i = startReadLine; i < last - tailLine + 1; i++) {
        	 row = sheet.getRow(i);
             int rowNum = row.getRowNum();
             UwExamHospitalItemDTO uwExamHospitalItem = new UwExamHospitalItemDTO();
             cell = row.getCell(0);
             uwExamHospitalItem.setItemClassify((String) ExcelUtils.getCellValue(sheet, rowNum, cell));
             cell = row.getCell(1);
             String itemNo = (String) ExcelUtils.getCellValue(sheet, rowNum, cell);
             if(StringUtil.isEmpty(itemNo)) {
            	 continue;
             }
             uwExamHospitalItem.setItemNo(itemNo);
             cell = row.getCell(2);
             uwExamHospitalItem.setItemDescription((String) ExcelUtils.getCellValue(sheet, rowNum, cell));
             cell = row.getCell(3);
             Object sortNo = ExcelUtils.getCellValue(sheet, rowNum, cell);
             if (StringUtil.isEmpty(sortNo) ) continue;
             ExcelUtils.getCellValue(sheet, rowNum, cell);
             uwExamHospitalItem.setSortNo((Double)sortNo);
             cell = row.getCell(4);
             uwExamHospitalItem.setReferenceType(getTypeCode(ExcelUtils.getCellValue(sheet, rowNum, cell) + ""));

             //判断定性还是定量
             if (StringUtils.equals(uwExamHospitalItem.getReferenceType(), ReferenceType.FIXED_QUALITY.typeCode)) {
                 cell = row.getCell(5);
                 Object referenceText =  ExcelUtils.getCellValue(sheet, rowNum, cell);
                 if(StringUtil.isEmpty(referenceText))continue;
                 uwExamHospitalItem.setReferenceText( referenceText+"");
             } else {
                 cell = row.getCell(5);
                 Object referenceStart =  ExcelUtils.getCellValue(sheet, rowNum, cell);
                 if(StringUtil.isEmpty(referenceStart))continue;
                 uwExamHospitalItem.setReferenceStart(referenceStart + "");
                 cell = row.getCell(6);
                 Object referenceEnd =  ExcelUtils.getCellValue(sheet, rowNum, cell);
                 if(StringUtil.isEmpty(referenceEnd))continue;
                 uwExamHospitalItem.setReferenceEnd( referenceEnd + "");
             }

             cell = row.getCell(7);
             uwExamHospitalItem.setUnit((String) ExcelUtils.getCellValue(sheet, rowNum, cell));
             cell = row.getCell(8);
             uwExamHospitalItem.setPrize( ExcelUtils.getCellValue(sheet, rowNum, cell)+"");
             cell = row.getCell(9);
             String prizeGroupDesc = (String) ExcelUtils.getCellValue(sheet, rowNum, cell);

             if (StringUtils.isNotBlank(prizeGroupDesc)) {
                 uwExamHospitalItem.setPrizeGroupCode(prizeGroupDesc.substring(0,prizeGroupDesc.indexOf("_")));
                 uwExamHospitalItem.setPrizeGroupDesc(prizeGroupDesc);
             }

             uwExamHospitalItems.add(uwExamHospitalItem);

        }
        return uwExamHospitalItems;
    }


    /**
     * 更加寄送结果的中文描述，获取状态的Code
     *
     * @param typeDesc
     * @return
     */
    private String getTypeCode(String typeDesc) {

        if (org.apache.commons.lang.StringUtils.isBlank(typeDesc)) return "";

        ReferenceType[] referenceTypes = ReferenceType.values();

        //定位状态的code
        for (ReferenceType referenceType : referenceTypes) {
            if (org.apache.commons.lang.StringUtils.equals(typeDesc.trim(), referenceType.getTypeDesc()))
                return referenceType.getTypeCode();
        }

        return "";
    }


    private enum ReferenceType {
        FIXED_QUANTUM("0", "定量"),
        FIXED_QUALITY("1", "定性");

        private String typeCode;
        private String typeDesc;

        ReferenceType(String typeCode, String typeDesc) {
            this.typeCode = typeCode;
            this.typeDesc = typeDesc;
        }

        public String getTypeCode() {
            return typeCode;
        }

        public void setTypeCode(String typeCode) {
            this.typeCode = typeCode;
        }

        public String getTypeDesc() {
            return typeDesc;
        }

        public void setTypeDesc(String typeDesc) {
            this.typeDesc = typeDesc;
        }
    }


    /**
     * 不需要校验基表数据的编号
     */
    private final static List<String> NON_VERIFICATION_ITEM_NO = new ArrayList<>();
    static {
        NON_VERIFICATION_ITEM_NO.add("NC13");
        NON_VERIFICATION_ITEM_NO.add("NC14");
        NON_VERIFICATION_ITEM_NO.add("NC15");
        NON_VERIFICATION_ITEM_NO.add("NC16");
        NON_VERIFICATION_ITEM_NO.add("NC17");
        NON_VERIFICATION_ITEM_NO.add("NC18");
        NON_VERIFICATION_ITEM_NO.add("NC19");
        NON_VERIFICATION_ITEM_NO.add("NC20");
        NON_VERIFICATION_ITEM_NO.add("NC21");
        NON_VERIFICATION_ITEM_NO.add("NC22");
        Collections.unmodifiableList(NON_VERIFICATION_ITEM_NO);
    }

    /**
     * 检查数据是否存在基表且性别符合
     *
     * @param uwExamHospitalItemDTO
     * @return
     */
    private boolean validItem(UwExamHospitalItemDTO uwExamHospitalItemDTO) {

        //如果是不需要校验基表的编号，可以任务符合条件
        if (NON_VERIFICATION_ITEM_NO.contains(uwExamHospitalItemDTO.getItemNo()))
            return true;

        return 0 < uwExamHospitalItemDao.checkItem(uwExamHospitalItemDTO);
    }


	@Override
	@Transactional
	public boolean importHospitalItemCommit(UwExamHospitalItemDTO uwExamHospitalItemDTO,List<UwExamHospitalItemDTO> list) {
		logger.info("importHospitalItemCommit -- start");
		String hospitalId = uwExamHospitalItemDTO.getHospitalId();
        String updateUser = uwExamHospitalItemDTO.getUpdatedUser();
        String applyGender = uwExamHospitalItemDTO.getApplyGender();
        
        //先把所有项目置为无效
        Map<String,String> map = new HashMap<>();
        map.put("hospitalId",hospitalId);
        map.put("applyGender",applyGender);
        uwExamHospitalItemDao.deleteItem(map);
        
        for (UwExamHospitalItemDTO uwExamHospitalItem : list) {
        	uwExamHospitalItem.setHospitalId(hospitalId);
            uwExamHospitalItem.setUpdatedUser(updateUser);
            uwExamHospitalItem.setCreatedUser(updateUser);
            uwExamHospitalItem.setApplyGender(applyGender);
            uwExamHospitalItemDTO.setItemNo(uwExamHospitalItem.getItemNo());
            if (validItem(uwExamHospitalItemDTO)) {
                uwExamHospitalItemDao.mergeHospitalItem(uwExamHospitalItem);
            }
		}
        logger.info("importHospitalItemCommit -- end");
		return true;
	}
	
	@Override
	public HSSFWorkbook generateHospitalItemWorkbook(String hospitalId,String applyGender) {
		Map<String,String> map = new HashMap<>();
		map.put("hospitalId", hospitalId);
		map.put("applyGender", applyGender);
		List<UwExamHospitalItemDTO> uwExamHospitalItemDTOList = uwExamHospitalItemDao.getExportExamResult(map);
		List<HospitalItemClassifyDTO> resultList = HospitalItemClassifyDTO.handleArrayData(uwExamHospitalItemDTOList);
		LinkedHashMap<String, Object> titleMap = new LinkedHashMap<String, Object>();
		titleMap.put("itemClassify", "项目名称");
		titleMap.put("itemNo", "项目编号");
		titleMap.put("itemDescription", "项目明细");
		titleMap.put("sortNo", "项目明细排序");
		titleMap.put("referenceType", "参考值类型");
		titleMap.put("reference", "参考值");
		titleMap.put("unit", "单位");
		titleMap.put("prize", "价格");
		titleMap.put("prizeGroupDesc", "价格组合");
		return ExcelUtils.produceHSSFWorkbook("基表", titleMap, resultList);
	}
}

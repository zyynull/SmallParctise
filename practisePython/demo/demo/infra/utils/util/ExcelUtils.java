package demo.demo.infra.utils.util;

import com.cmrh.nuw.healthExamination.domain.hospital.dto.HospitalItemClassifyDTO;
import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalItemDTO;
import com.cmrh.nuw.healthExamination.infra.enums.ReferenceTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date: 2020/1/13 10:01
 * @Modified: Last modified by
 */
public class ExcelUtils {

	/**-----------------------------导入读取-------------------------------------**/
    public static Workbook getWorkbook(InputStream inputStream, String fileSuffix) throws IOException {
        if (fileSuffix.equals(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else if (fileSuffix.equals(".xls")) {
            return new HSSFWorkbook(inputStream);
        }
        throw new RuntimeException("not support file type");
    }

    public static Sheet getSheet(Workbook workbook, int numSheet) {
        return workbook.getSheetAt(numSheet);
    }

    public static Row getRow(Sheet sheet, int numRow) {
        return sheet.getRow(numRow);
    }

    public static Cell getCell(Row row, int numCell) {
        return row.getCell(numCell);
    }

    /**
     * 获取某一单元格的值
     *
     * @param cell
     * @return
     */
    public static Object getCellValue(Cell cell) {
        if (cell == null)
            return "";
        Object value = null;
        switch (cell.getCellType()) {
            //字符串
            case STRING:
                value = StringUtils.trim(cell.getStringCellValue());
                break;
            //时间与数字
            case NUMERIC:
                if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                    value = cell.getDateCellValue();
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double val = cell.getNumericCellValue();
                    value = org.apache.poi.ss.usermodel.DateUtil
                            .getJavaDate(val);
                } else {
                    value = cell.getNumericCellValue();
                }
                break;
            //空值
            case BLANK:
                value = "";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }

    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    public static boolean isMergedRegion(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    public static Object getMergedRegionValue(Sheet sheet, int row, int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }
        return null;
    }

    public static Object getCellValue(Sheet sheet, int rowNum, Cell cell) {
        if (sheet==null||cell==null)return "";
        boolean isMerge = ExcelUtils.isMergedRegion(sheet, rowNum, cell.getColumnIndex());
        //判断是否具有合并单元格
        if (isMerge) {
            return ExcelUtils.getMergedRegionValue(sheet, rowNum, cell.getColumnIndex());
            //       returnStr = rs;
        }
        return ExcelUtils.getCellValue(cell);
        // returnStr = c.getRichStringCellValue().getString();
    }
    
    /**--------------------------------导出写入--------------------------------------**/
    
    /**
     * 只用于导出医院项目
     *
     * @param sheetName sheet名称
     * @param titleMap  标题
     * @param dataList 分类处理后的数据 
     * @return
     */
    public static HSSFWorkbook produceHSSFWorkbook(String sheetName, LinkedHashMap<String, Object> titleMap,List<HospitalItemClassifyDTO> dataList) {

        // 第一步，创建一个HSSFWorkbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();

        // 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        wb = setReportBodyByList(titleMap, dataList, wb, sheet, 0);
        return wb;
    }
    
    /**
     * 设置报表内容
     *
     * @param titleMap
     * @param values
     * @param wb
     * @param sheet
     * @param index
     */
    private static HSSFWorkbook setReportBodyByList(LinkedHashMap<String, Object> titleMap, List<HospitalItemClassifyDTO> dataList, HSSFWorkbook wb, HSSFSheet sheet, int index) {
        HSSFRow row;
        HSSFCellStyle style;
        HSSFCell cell;

        row = sheet.createRow(index);
        //设置列标题字体
        HSSFFont hssfFont = wb.createFont();
        hssfFont.setFontName(com.cmrh.uw.uwquery.utils.ExcelUtils.FontAttribute.DEFAULT_FONT);
        hssfFont.setFontHeightInPoints(com.cmrh.uw.uwquery.utils.ExcelUtils.FontAttribute.BODY);
        //设置字体粗体
        hssfFont.setBold(true);

        //设置格式
        style = wb.createCellStyle();
        style.setFont(hssfFont);

        //创建列标题
        int cellIndex = 0;
        List<String> column = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : titleMap.entrySet()) {
            column.add(entry.getKey());
            cell = row.createCell(cellIndex);
            cell.setCellValue((String) entry.getValue());
            if("参考值".equals((String)entry.getValue())) {
            	sheet.addMergedRegion(new CellRangeAddress(0, 0, 5, 6));
            	//被合并单元格要跳过
            	cellIndex++;
            }
            cell.setCellStyle(style);
            sheet.autoSizeColumn(cellIndex,true);
            cellIndex++;
        }

        //设置数据字体
        hssfFont = wb.createFont();
        hssfFont.setFontName(com.cmrh.uw.uwquery.utils.ExcelUtils.FontAttribute.DEFAULT_FONT);
        hssfFont.setFontHeightInPoints(com.cmrh.uw.uwquery.utils.ExcelUtils.FontAttribute.BODY);

        //设置数据格式
        style = wb.createCellStyle();
        style.setFont(hssfFont);
        style.setAlignment(HorizontalAlignment.CENTER_SELECTION);
        
        //将合并数据分开
        for (HospitalItemClassifyDTO outerDTO : dataList) {
        	//outList.getName();  上下合并的单元格内容
        	//outList.getList().size(); 上下合并的单元格size
        	
        	List<UwExamHospitalItemDTO> innerlist = outerDTO.getList();
        	int size = innerlist.size();
        	
        	for (int i = 0; i < innerlist.size(); i++) {
        		//设置自适应
        		sheet.autoSizeColumn(i+index+1,true);
        		UwExamHospitalItemDTO innerDto = innerlist.get(i);
        		//创建内容
        		row = sheet.createRow(i+index+1);
        		String referenceType = innerDto.getReferenceType();
        		//column.size+1是因为参考值类型占了2列,总共应该是Size+1列
        		for (int j = 0; j < column.size()+1; j++) {
        			cell = row.createCell(j);
        			if(j == 0 && i==0) {
        				//第一列要合并
        				//合并Size这么多 
        				cell.setCellValue(outerDTO.getItemClassify());
        				//由于有标题, 所以index要+1
        				//只有一行不合并
        				if(size>1) {
        					sheet.addMergedRegion(new CellRangeAddress(index+1, size+index, 0, 0));
        				}
        			}
        			if(j==1) {
        				cell.setCellValue(innerDto.getItemNo());
        			}
        			if(j==2) {
        				cell.setCellValue(innerDto.getItemDescription());
        			}
        			if(j==3) {
        				Double sortNo = innerDto.getSortNo();
        				if(sortNo!=null) {
        					cell.setCellValue(sortNo);
        				}
        			}
        			if(j==4) {
        				//referenceType = 0 定量  1定性(合并)
        				cell.setCellValue(ReferenceTypeEnum.getName(referenceType));
        			}
        			if(j==5) {
        				if(ReferenceTypeEnum.QUANTITY.getIndex().equals(referenceType)) {
        					cell.setCellValue(innerDto.getReferenceStart());
        				}else if(ReferenceTypeEnum.QUALITY.getIndex().equals(referenceType)) {
        					cell.setCellValue(innerDto.getReferenceText());
        					sheet.addMergedRegion(new CellRangeAddress(index+1+i, index+1+i, 5, 6));
        				}
        			}
        			if(j==6) {
        				if(ReferenceTypeEnum.QUANTITY.getIndex().equals(referenceType)) {
        					cell.setCellValue(innerDto.getReferenceEnd());	
        				}
        			}
        			if(j==7) {
        				cell.setCellValue(innerDto.getUnit());	
        			}
        			if(j==8) {
        				cell.setCellValue(innerDto.getPrize());	
        			}
        			if(j==9) {
        				cell.setCellValue(innerDto.getPrizeGroupDesc());	
        			}
        		}
			}
        	index = index+size;
        }
        
        return wb;
    }
}

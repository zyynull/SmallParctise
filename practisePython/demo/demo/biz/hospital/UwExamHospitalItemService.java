package demo.demo.biz.hospital;

import com.cmrh.nuw.healthExamination.domain.hospital.dto.UwExamHospitalItemDTO;
import com.cmrh.sf.store.RFFile;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
 * 医院项目信息接口
 * @author ex-zrh001 2019/12/31
 * */
public interface UwExamHospitalItemService {
	
	/**
	 * 集合插入
	 * */
	void hospitalItemListInsert(List<UwExamHospitalItemDTO> dtoList, String hospitalId, String user);

	/**
	 * 集合更新
	 * */
	void hospitalItemleListUpdate(List<UwExamHospitalItemDTO> dtoList, String hospitalId, String gender, String user);

	/**
	 * 导入医院体检项目数据回显
	 * @param file
	 * @param uwExamHospitalItemDTO
	 * @return
	 */
	List<UwExamHospitalItemDTO> importHospitalItem(RFFile file, String fileSuffix);

	/**
	 * 导入医院体检项目数据提交
	 *
	 *
	 * **/
	boolean importHospitalItemCommit(UwExamHospitalItemDTO uwExamHospitalItemDTO, List<UwExamHospitalItemDTO> list);

	/**
	 * 查出医院体检项目,并转为wb格式
	 * */
	HSSFWorkbook generateHospitalItemWorkbook(String hospitalId, String applyGender);
	
}

package demo.demo.domain.hospital.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检项目导入后分类DTO
 * */
@Data
public class HospitalItemClassifyDTO {

	/**项目分类*/
	private String itemClassify;
	
	/**分类下项目子集*/
	private List<UwExamHospitalItemDTO> list;
	
	public HospitalItemClassifyDTO(String itemClassify,List<UwExamHospitalItemDTO> list) {
		this.itemClassify = itemClassify;
		this.list = list;
	}
	
	/**
	 * 处理报表数据 将集合分类转化为类似Map的实体类输出 
	 * */
	public static List<HospitalItemClassifyDTO> handleArrayData(List<UwExamHospitalItemDTO> list){
		Map<String,HospitalItemClassifyDTO> dataMap = new LinkedHashMap<>();
		for (UwExamHospitalItemDTO uwExamHospitalItemDTO : list) {
			String itemClassify = uwExamHospitalItemDTO.getItemClassify();
			HospitalItemClassifyDTO dto = dataMap.get(itemClassify);
			if(dto==null) {
				dto = new HospitalItemClassifyDTO(itemClassify,new ArrayList<>());
			}
			List<UwExamHospitalItemDTO> innerlist = dto.getList();
			innerlist.add(uwExamHospitalItemDTO);
			dto.setList(innerlist);
			dataMap.put(itemClassify, dto);
		}
		List<HospitalItemClassifyDTO> resultList = new ArrayList<HospitalItemClassifyDTO>(dataMap.values());
		return resultList;
	}
}

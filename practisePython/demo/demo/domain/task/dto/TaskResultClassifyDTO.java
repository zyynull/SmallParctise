package demo.demo.domain.task.dto;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * 体检项目导入后分类DTO
 * */
@Data
public class TaskResultClassifyDTO {

	/**项目分类*/
	private String itemClassfy;
	
	/**分类下项目子集*/
	private List<UwExamTaskResultDTO> list;
	
	public TaskResultClassifyDTO(String itemClassfy,List<UwExamTaskResultDTO> list) {
		this.itemClassfy = itemClassfy;
		this.list = list;
	}
	
	/**
	 * 将集合分类转化为按分类的实体类输出
	 * */
	public static List<TaskResultClassifyDTO> handleArrayData(List<UwExamTaskResultDTO> list){
		Map<String,TaskResultClassifyDTO> dataMap = new LinkedHashMap<>();
		for (UwExamTaskResultDTO uwExamTaskResultDTO : list) {
			String itemClassify = uwExamTaskResultDTO.getItemClassfy();
			TaskResultClassifyDTO dto = dataMap.get(itemClassify);
			if(dto==null) {
				dto = new TaskResultClassifyDTO(itemClassify,new ArrayList<>());
			}
			List<UwExamTaskResultDTO> innerlist = dto.getList();
			innerlist.add(uwExamTaskResultDTO);
			dto.setList(innerlist);
			dataMap.put(itemClassify, dto);
		}
		//尿常规分两次查询, 会影响排序, 需要重新排
		TaskResultClassifyDTO sortDto = dataMap.get("尿常规");
		if(sortDto!=null) {
			List<UwExamTaskResultDTO> sortList = sortDto.getList();
			if(CollectionUtils.isNotEmpty(sortList)) {
				Collections.sort(sortList);
			}
		}
		List<TaskResultClassifyDTO> resultList = new ArrayList<TaskResultClassifyDTO>(dataMap.values());
		return resultList;
	}
}

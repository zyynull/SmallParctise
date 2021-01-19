package demo.demo.api.task.facade;

import com.cmrh.indigo.basic.dto.WebResponse;
import com.cmrh.nuw.healthExamination.app.biz.task.UwExamTaskManageService;
import com.cmrh.nuw.healthExamination.domain.task.dto.QueryTaskParam;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskInfoDTO;
import com.cmrh.nuw.healthExamination.infra.utils.constant.SystemConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


/**
 * @Description:
 * @Date: 2020/4/20 14:41
 * @Modified: Last modified by
 */
@RequestMapping("uwExam/examTask/management")
@Controller
public class UwExamTaskManageController {

    @Autowired
    private UwExamTaskManageService uwExamTaskManageService;


    @RequestMapping("/tasks")
    public ResponseEntity getTaskList(QueryTaskParam queryTaskParam){
       return WebResponse.create(uwExamTaskManageService.getTaskList(queryTaskParam)).status(HttpStatus.OK);
    }

    @RequestMapping(value = "/allocateTasks",method = RequestMethod.PUT)
    public ResponseEntity allocateTasks(HttpServletRequest request,@RequestBody UwExamTaskInfoDTO uwExamTaskInfoDTO){
        String user = request.getRemoteUser();
        uwExamTaskInfoDTO.setUpdatedUser(user);
        uwExamTaskManageService.allocateTasks(uwExamTaskInfoDTO);
        return WebResponse.create().message(SystemConstant.SUCCESS).status(HttpStatus.CREATED);
    }


    @RequestMapping(value = "/modifyTasks",method = RequestMethod.PUT)
    public ResponseEntity modifyTasks(HttpServletRequest request, @RequestBody UwExamTaskInfoDTO uwExamTaskInfoDTO){

        String user = request.getRemoteUser();
        uwExamTaskInfoDTO.setOperatedUser(user);
        uwExamTaskInfoDTO.setUpdatedUser(user);
        uwExamTaskManageService.modifyTasks(uwExamTaskInfoDTO);
        return WebResponse.create().message(SystemConstant.SUCCESS).status(HttpStatus.CREATED);
    }


}

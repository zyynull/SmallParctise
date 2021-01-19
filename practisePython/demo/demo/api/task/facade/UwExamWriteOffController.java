package demo.demo.api.task.facade;

import com.cmrh.nuw.healthExamination.app.biz.task.UwExamTaskInfoService;
import com.cmrh.nuw.healthExamination.domain.task.dto.UwExamTaskInfoDTO;
import com.cmrh.nuw.healthExamination.infra.utils.common.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author : lig001@cmrh.com
 * @date : Created in 2020/3/4 9:27
 * @description :
 * @modified By :
 */

@RequestMapping("/uwExam/migrate")
@Controller
@Slf4j
public class UwExamWriteOffController {

    @Autowired
    private UwExamTaskInfoService uwExamTaskInfoService;

    /**
     * 手动调用函件回销接口
     *
     * @param clientNo
     * @return
     */
    @RequestMapping(value = "/writeOff", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity writeOffTask(String clientNo,String noteSeq) {
        String key = String.format("ClientNo:%s, NoteSeq:%s",clientNo,noteSeq);
        log.info("Mannual trigger writeOff task for old data:" + key);

        uwExamTaskInfoService.writeOffPhysicalExamNote(clientNo, noteSeq);
        return WebResponse.create(key).status(HttpStatus.OK);
    }

    /**
     * 手动调用函件回销接口
     *
     * @param uwExamTaskInfoDTO
     * @return
     */
    @RequestMapping(value = "/writeOffEmptyTask", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity writeOffEmptyTask(@RequestBody @Valid UwExamTaskInfoDTO uwExamTaskInfoDTO, HttpServletRequest request) {
        String key = String.format("ClientNo:%s, NoteSeq:%s",uwExamTaskInfoDTO.getClientNo(),uwExamTaskInfoDTO.getNoteSeq());
        log.info("Mannual trigger writeOff task for old data:" + key);
        String user = request.getRemoteUser();
        //5 空回销
        uwExamTaskInfoService.cancelTask(uwExamTaskInfoDTO.getTaskNo(), uwExamTaskInfoDTO.getRemark(), user,"5");
        uwExamTaskInfoService.writeOffPhysicalExamNote(uwExamTaskInfoDTO.getClientNo(),uwExamTaskInfoDTO.getNoteSeq()+"");
        return WebResponse.create(key).status(HttpStatus.OK);
    }
}

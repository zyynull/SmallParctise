package demo.demo.infra.utils.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @Author: Mo Zhipeng
 * @Description:
 * @Date: 2019/8/8 21:07
 * @Modified: Last modified by
 */
public abstract class AbstractResponseStatus {

    /**
     * 产生 ResponseEntity
     *
     * @param status 状态
     * @return ResponseEntity
     */
    public ResponseEntity<?> status(HttpStatus status) {
        return new ResponseEntity(this, status);
    }


    /**
     * 产生 ResponseEntity OK(200)
     *
     * @return ResponseEntity
     */
    public ResponseEntity<?> ok() {
        return status(HttpStatus.OK);
    }

    /**
     * 产生 ResponseEntity Unauthorized(401)
     *
     * @return ResponseEntity
     */
    public ResponseEntity<?> unauthorized() {
        return status(HttpStatus.UNAUTHORIZED);
    }

    /**
     * 产生 ResponseEntity Forbidden(403)
     *
     * @return ResponseEntity
     */
    public ResponseEntity<?> forbidden() {
        return status(HttpStatus.FORBIDDEN);
    }

    /**
     * 产生 ResponseEntity Conflict(409)
     *
     * @return ResponseEntity
     */
    public ResponseEntity<?> conflict() {
        return status(HttpStatus.CONFLICT);
    }

    /**
     * 产生 ResponseEntity Bad Request(400)
     *
     * @return ResponseEntity
     */
    public ResponseEntity<?> badRequest() {
        return status(HttpStatus.BAD_REQUEST);
    }

    /**
     * 产生 ResponseEntity Internal Server Error(500)
     *
     * @return ResponseEntity
     */
    public ResponseEntity<?> internalError() {
        return status(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

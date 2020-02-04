package com.observer.pattern.controller;

import com.observer.pattern.model.Locker;
import com.observer.pattern.model.RedisObject;
import com.observer.pattern.model.RedisValue;
import com.observer.pattern.model.RequestModel;
import com.observer.pattern.service.DBService;
import com.observer.pattern.service.RedisService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class TestController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private DBService dbService;

    private static final String SUCCESS_CODE = "0X200";
    private static final String SUCCESS_MESSAGE = "Request completed successfully";

    //API to for bulk insert:
    @GetMapping("/v1/bulkinsert")
    public @ResponseBody ResponseEntity<?> bulkInsert() {
        try {
            String status = dbService.bulkInsert();
            CustomResponse customResponse = getCustomResponse(status);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //API to reserve a locker:
    @PostMapping("/v1/locker")
    public @ResponseBody ResponseEntity<?> getLocker(@Valid @RequestBody RequestModel requestModel) {
        try {
            String status = dbService.getLocker(requestModel.getLockerId());
            CustomResponse customResponse = getCustomResponse(status);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/v1/db/insert")
    public @ResponseBody
    ResponseEntity<?> insert(@RequestBody Locker locker) {
        try {
            Locker lockerRes = dbService.insert(locker);
            CustomResponse customResponse = getCustomResponse(lockerRes);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/v1/db/update")
    public @ResponseBody
    ResponseEntity<?> update(@RequestBody Locker locker) {
        try {
            Locker lockerRes = dbService.update(locker);
            CustomResponse customResponse = getCustomResponse(lockerRes);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/v1/db/delete")
    public @ResponseBody
    ResponseEntity<?> delete(@RequestBody Locker locker) {
        try {
              dbService.delete(locker);
            CustomResponse customResponse = getCustomResponse("Successfully Deleted!!!");
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/v1/set_redis")
    public @ResponseBody
    ResponseEntity<?> setRedis(@RequestBody RedisValue redisValue) {
        log.info("Printing key and value for setting in redis  {} {} ", redisValue.getKey(), redisValue.getValue());
        try {
            redisService.putData(redisValue.getKey(), redisValue.getValue());
            CustomResponse customResponse = getCustomResponse(redisValue.getValue());
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/v1/get_redis/{key}")
    public @ResponseBody
    ResponseEntity<?> getRedisValue(@PathVariable Long key) {
        log.info("Printing data for getting value from from redis  {}", key);
        try {
            List<RedisObject> redisObjects = redisService.getFromRedis(key);
            CustomResponse customResponse = getCustomResponse(redisObjects);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/v1/delete_redis/{key}")
    public @ResponseBody
    ResponseEntity<?> deleteRedisKey(@PathVariable String key) {
        log.info("Printing data for deleting value from from redis  {}", key);
        try {
            redisService.remove(key);
            CustomResponse customResponse = getCustomResponse(key);
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error occured : {}", ex.toString());
            return new ResponseEntity<>("ERROR_OCCURRED", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CustomResponse getCustomResponse(Object key) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setData(key);
        customResponse.setCode(SUCCESS_CODE);
        customResponse.setMessage(SUCCESS_MESSAGE);
        log.debug("customResponse : {}", customResponse);
        return customResponse;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomResponse {
        private String code;
        private String message;
        private Object data;
    }


}

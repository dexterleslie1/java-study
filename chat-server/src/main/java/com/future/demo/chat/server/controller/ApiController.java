package com.future.demo.chat.server.controller;

import com.future.demo.chat.server.common.PageResponse;
import com.future.demo.chat.server.exception.BusinessException;
import com.future.demo.chat.server.model.ChatMessageModel;
import com.future.demo.chat.server.service.MessageService;
import com.future.demo.chat.server.common.ObjectResponse;
import com.future.demo.chat.server.value.object.SendMessageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ApiController {
    @Autowired
    MessageService messageService;

    @RequestMapping(value="send", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<SendMessageResultVO>> send(
            @RequestParam(value = "usernameSelf",defaultValue = "") String usernameSelf,
            @RequestParam(value = "usernameTo",defaultValue = "") String usernameTo,
            @RequestParam(value = "content",defaultValue = "") String content,
            @RequestParam(value = "type",defaultValue = "0") int type) throws BusinessException {
        SendMessageResultVO sendMessageRequestVO = this.messageService.send(usernameSelf, usernameTo, type, content);
        ObjectResponse<SendMessageResultVO> response = new ObjectResponse<>();
        response.setData(sendMessageRequestVO);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="pull", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageResponse<ChatMessageModel>> pull(
            @RequestParam(value = "usernameSelf",defaultValue = "") String usernameSelf) throws BusinessException {
        PageResponse<ChatMessageModel> response = this.messageService.pull(usernameSelf);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value="confirm", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ObjectResponse<String>> confirm(
            @RequestParam(value = "usernameSelf",defaultValue = "") String usernameSelf,
            @RequestParam(value = "ids",defaultValue = "") List<Long> ids) throws BusinessException {
        this.messageService.confirm(usernameSelf, ids);
        ObjectResponse<String> response = new ObjectResponse<>();
        return ResponseEntity.ok(response);
    }
}

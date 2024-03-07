package com.stocktalkhub.stocktalkhub.controller;

import com.stocktalkhub.stocktalkhub.dto.PostDTO.PostsDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SwaggerController {
    @GetMapping(value = "/call")
    @ApiOperation(value="테스트", notes="테스트 중")
    public ResponseEntity<List<PostsDTO>> Call(@RequestParam("name") String name) throws Exception {
        List<PostsDTO> data = new ArrayList<>();
        return ResponseEntity.ok(data);
    }

}

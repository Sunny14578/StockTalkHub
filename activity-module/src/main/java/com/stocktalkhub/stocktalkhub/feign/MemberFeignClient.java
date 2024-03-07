package com.stocktalkhub.stocktalkhub.feign;

import com.stocktalkhub.stocktalkhub.dto.MemberDTO.MemberByNameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MemberFeignClient", url = "http://localhost:8080/user-module")
public interface MemberFeignClient {

    @PostMapping("/members/name")
    ResponseEntity<String> getNameMembers(@RequestBody MemberByNameDTO MfBynDTO);
}

package com.run_us.server.domains.user.controller;

import com.run_us.server.domains.user.controller.model.MyPageResult;
import com.run_us.server.domains.user.controller.model.response.UserHttpResponseCode;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/my")
@RequiredArgsConstructor
public class MyPageController {

  private final UserService userService;

  @GetMapping
  public SuccessResponse<MyPageResult> getMyPageData(@RequestAttribute("publicUserId") String publicId) {
    return SuccessResponse.of(UserHttpResponseCode.MY_PAGE_DATA_FETCHED, userService.getMyPageData(publicId));
  }
}

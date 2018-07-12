/*
 * Copyright 2017 Xiaomi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.shepher.controller;

import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.service.UserService;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@link IndexController} is entrance of basic module,
 * such as logouts.
 *
 * Created by weichuyang on 16-8-1.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${cas.logout.url}")
    private String casServerLogoutUrl;

    @Value("${server.url}")
    private String serverUrl;

    @Value("${server.login.type}")
    private String loginType;

    @Autowired
    private UserHolder userHolder;

    @Autowired
    UserService userService;

    /**
     * Logouts.
     *
     * @param request
     * @return
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        String referer = request.getHeader(HttpHeaders.REFERER);
        if (StringUtils.isBlank(referer)) {
            referer = serverUrl;
        }
        if (ShepherConstants.LOGIN_TYPE_CAS.equals(loginType.toUpperCase())) {
            return "redirect:" + casServerLogoutUrl + "?service=" + referer;
        }
        return "redirect:" + referer;
    }

    /**
     * Login.
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        if (userHolder == null || userHolder.getUser() == null) {
            return "login";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String registerGet(HttpServletRequest request) {
        if (userHolder == null || userHolder.getUser() == null) {
            return "register";
        } else {
            return "redirect:/";
        }
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String registerPost(HttpServletRequest request,String username,String password, Model model) throws ShepherException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            model.addAttribute("message", "用户名密码不能为空");
            return "/error";
        }
        User user = userService.get(username);
        if (user!=null){
            model.addAttribute("message", "用户["+username+"]已经存在");
            return "/error";
        }
        userService.create(username, password);
        return "redirect:/";

    }
}

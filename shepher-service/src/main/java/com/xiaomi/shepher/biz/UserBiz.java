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

package com.xiaomi.shepher.biz;

import com.xiaomi.shepher.common.DaoValidator;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.dao.UserMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 16-8-9.
 */
@Service
public class UserBiz {
    private static Logger logger = LoggerFactory.getLogger(UserBiz.class);

    @Autowired
    private UserMapper userMapper;

    public List<User> list(Set<Long> teams, Status status, Role role) throws ShepherException {
        if (teams == null || status == null || role == null) {
            throw ShepherException.createIllegalParameterException();
        }
        if (teams.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.list(teams, status.getValue(), role.getValue());
    }

    public Set<String> listNames(Set<Long> teams, Status status, Role role) throws ShepherException {
        Set<String> userNames = new HashSet<>();
        List<User> users = this.list(teams, status, role);
        for (User user : users) {
            userNames.add(user.getName());
        }
        return userNames;
    }

    public User create(String name,String password) throws ShepherException {
        if (StringUtils.isBlank(name)) {
            throw ShepherException.createIllegalParameterException();
        }
        if (StringUtils.isBlank(password)) {
            throw ShepherException.createIllegalParameterException();
        }
        User user = new User(name);
        user.setPassword(password);
        int count;
        try {
            count = userMapper.create(user);
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage(),e);
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw ShepherException.createDBCreateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return user;
    }

    public User getByName(String name) throws ShepherException {
        if (StringUtils.isBlank(name)) {
            throw ShepherException.createIllegalParameterException();
        }
        return userMapper.getByName(name);
    }

    public User getById(long id) {
        return userMapper.getById(id);
    }

}

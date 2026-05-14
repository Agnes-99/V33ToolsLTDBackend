package com.v33toolsltd.service.users;

import com.v33toolsltd.domain.users.Manager;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface IManagerService extends IService<Manager, Long> {
    List<Manager> getAll();
}
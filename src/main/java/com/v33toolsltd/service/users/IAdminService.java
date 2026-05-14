package com.v33toolsltd.service.users;

import com.v33toolsltd.domain.users.Admin;
import com.v33toolsltd.service.IService;

import java.util.List;

public interface IAdminService extends IService<Admin,Long> {

    List<Admin> getAll();
}
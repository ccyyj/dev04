package com.rx.service;

import com.rx.entity.Userlogin;

public interface UserloginService {
    void save(Userlogin userlogin);

    void removeByName(String toString);
}

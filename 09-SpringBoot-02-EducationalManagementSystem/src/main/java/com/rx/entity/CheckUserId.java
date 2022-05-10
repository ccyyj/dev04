package com.rx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckUserId {
    private Integer userid;
    private boolean flag;
    private String errorMsg;
}

package com.rx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataVO<T> {
    private Long total;
    private List<T> list;
    private Integer page;
    private Integer pageSize;
}

package com.hanghae.justpotluck.global.result;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Success {
    private boolean success;
    private String msg;
}

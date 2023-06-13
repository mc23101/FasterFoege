package com.github.zhangsiyao.FasterForge.NetWork;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.Map;

public class NetworkPack {
    @JSONField(name="Command")
    private String command;
    @JSONField(name = "State")
    private String state;
    @JSONField(name="Data")
    private Map<String,Object> data=new HashMap<>();


    public NetworkPack(String command, String state, Map<String, Object> data) {
        this.command = command;
        this.state = state;
        this.data = data;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}

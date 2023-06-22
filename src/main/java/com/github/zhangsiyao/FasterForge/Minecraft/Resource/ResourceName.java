package com.github.zhangsiyao.FasterForge.Minecraft.Resource;

public class ResourceName {

    private final String location;
    public ResourceName(String mod,String id){
        this.location=mod+":"+id;
    }

    public ResourceName(String location){
        this.location=location;
    }

    public String getLocation() {
        return location;
    }
}

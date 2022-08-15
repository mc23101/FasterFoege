package io.gitee.zhangsisiyao.ForgeAPI.Gui.ex;

import net.minecraft.util.ResourceLocation;

public class TextureNotFoundException extends Exception{
    private ResourceLocation location;
    public TextureNotFoundException(ResourceLocation location){
        this.location=location;
    }

    public ResourceLocation getLocation() {
        return location;
    }
}
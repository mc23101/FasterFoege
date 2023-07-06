package com.github.zhangsiyao.FasterForge.Proxy.Constant;

import com.github.zhangsiyao.FasterForge.Minecraft.Constant.Hand;
import net.minecraft.util.EnumHand;

public class EnumCast {
    public static Hand getHand(EnumHand hand){
        if(hand==EnumHand.MAIN_HAND){
            return Hand.MAIN_HAND;
        }else {
            return Hand.OFF_HAND;
        }
    }
}

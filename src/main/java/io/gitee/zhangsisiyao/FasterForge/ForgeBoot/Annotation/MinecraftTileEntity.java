package io.gitee.zhangsisiyao.FasterForge.ForgeBoot.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * <p>注册TileEntity,建议一个TileEntity绑定一个方块</p>
 * <p>TileEntity 是一种和实体类似，可以存储一个 NBT 标签的特殊对象。 </p>
 * <p>这意味着，借助 TileEntity，你可以在一个方块中存储超过 4 bits 的数据。</p>
 * <p>TileEntity 可以通过实现 ITickable 接口来获得每秒刷新 20 次的能力。</p>
 * <p>可以说，绝大部分看上去功能异常强大的方块都是基于 TileEntity 的。</p>
 * */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
public @interface MinecraftTileEntity {
    /**
     * <p>与TileEntity绑定的Block的注册名</p>
     * <p>例如：ExampleMod:TestBlock</p>
     * */
    String[] Blocks();
}

# 介绍
- FasterForge是一个专注于简化Minecraft Mod开发的API框架
- Minecraft版本:1.12.2
- Forge版本:forge:1.12.2-14.23.5.2859

## FasterForge在整个mod开发中的定位

FasterForge框架是介于Forge和mod开发之间的中间层，集成涵盖Forge的基本功能，并拓展了其他功能。

![](/image/1.png)

设计FasterForge框架的目的是：规范Minecraft模组的开发，并支持Minecraft模组各个版本的兼容，使用FasterForge可以帮助mod开发者在不修改代码的情况下，可以移植到Minecraft的各个版本.因此，mod开发者在使用FasterForge框架开发Minecraft模组时，应尽量规避直接调用Forge框架的内容.

# 自动注册

FasterForge通过注解来自动注册Minecraft的内容，例如：Item(物品)、Block(方块)、Dimension(维度)、Entity(实体)等元素.

使用FasterForge您只需要在类上使用注解，FasterForge就会帮您自动注册Minecraft内容.

## `Item(物品)`自动注册

物品的自动注册通过`@MinecraftItem`来完成，此注解只能在以下几种情况使用：

- 类

- 静态成员

- 静态方法

`@MinecraftItem`注解的参数说明:

| 参数  |   说明   |
| :---: | :------: |
| modId | 模组的id |
| name  | 物品名称 |



### 在类上使用

`@MinecraftItem`在类上使用时，应注意以下几点事项

-  被注解的类应为`net.minecraft.item.Item`的子类
-  被注解的类应拥有`无参构造`

以下代码是在类上使用`@MinecraftItem`注解来注册一个普通物品：

```java
@MinecraftItem(modId = ExampleMod.MODID,name = "testItem")
public class TestItem extends Item {
    public TestItem(){
    }
}
```

### 在静态成员上使用

`@MinecraftItem`在静态成员上使用时，应注意以下几点事项

- 静态成员的类型应为`net.minecraft.item.Item`的子类
- 静态成员应被`static`修饰(静态成员的定义)
- 静态成员的数据应不为`null`

以下代码是在静态成员上使用`@MinecraftItem`注解来注册一个物品：

```java
public class Items {
    @MinecraftItem(modId = ExampleMod.MODID,name = "testItem2")
    public static TestItem testItem=new TestItem();
}
```

### 在静态方法上使用

`@MinecraftItem`在静态方法上使用时，应注意以下几点事项

- 静态方法应有返回值，且返回值类型为`net.minecraft.item.Item`的子类
- 静态方法应被`static`修饰(静态方法的定义)
- 静态方法应为`无参`静态方法

以下代码是在静态方法上使用`@MinecraftItem`注解来注册一个物品：

```java
@MinecraftItem(modId = ExampleMod.MODID,name = "testItem")
public static TestItem testItem(){
	return new TestItem();
}
```

## `Block(方块)`自动注册

## `Enchantment(附魔)`自动注册

## `Entity(实体)`自动注册

## `EntityRender(实体模型渲染)`自动注册

## `Potion(药水效果)`自动注册

## `Resource(文件资源)`自动注册

## `TileEntity`自动注册

## `Dimension(维度)`自动注册

# GUI组件

为了保证Gui组件的稳定性和泛用性，本项目的Gui组件的实现借鉴`javax.swing`的实现方式.

# 游戏事件

# 模型动画机

# 实体状态机




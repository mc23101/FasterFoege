package com.github.zhangsiyao.FasterForge.ForgeBoot.Annotation.Loader;

public interface IAnnotationLoader {
    public void loadFromClass();

    public void loadFromField();

    public void loadFromMethod();
}

package com.douyu.ocean.demo.api;

import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * BuilderProcessor
 *
 * @author weiqi
 * @create 2018-04-21 17:39:00
 */
@SupportedAnnotationTypes({
        "com.douyu.ocean.demo.api.Builder"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BuilderProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        this.filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("Processing " + annotations + roundEnv);

        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(Builder.class);
        elementsAnnotatedWith.forEach(element -> {
            if (element.getKind() != ElementKind.CLASS) {
                System.out.println("not class");
                return;
            }

            TypeMirror elementTypeMirror = element.asType();
            String builderClassName = element.getSimpleName() + "Builder";


        });

        return false;
    }
}

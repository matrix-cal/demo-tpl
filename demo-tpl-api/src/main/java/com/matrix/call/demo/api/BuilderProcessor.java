package com.matrix.call.demo.api;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Set;

/**
 * BuilderProcessor
 *
 * @author weiqi
 * @create 2018-04-21 17:39:00
 */
@SupportedAnnotationTypes({
        "com.matrix.call.demo.api.Builder"
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

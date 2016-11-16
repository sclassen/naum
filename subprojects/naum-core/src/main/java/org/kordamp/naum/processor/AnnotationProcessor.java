/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kordamp.naum.processor;

import org.kordamp.naum.model.AnnotationInfo;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.signature.SignatureReader;

import static org.kordamp.naum.model.AnnotationValue.newEnumValue;
import static org.kordamp.naum.model.AnnotationValue.newSimpleValue;
import static org.objectweb.asm.Opcodes.ACC_PUBLIC;

/**
 * @author Andres Almiray
 * @author Stephan Classen
 * @author Vitaly Tsaplin
 * @author Alexey Dubrovskiy
 */
public class AnnotationProcessor extends AnnotationVisitor {
    private final AnnotationInfo annotation;

    public AnnotationProcessor(AnnotationInfo annotation) {
        super(Opcodes.ASM5);
        this.annotation = annotation;
    }

    public AnnotationInfo getAnnotation() {
        return annotation;
    }

    @Override
    public void visit(String name, Object value) {
        annotation.getValues().put(name, newSimpleValue(value.getClass(), value));
    }

    @Override
    public void visitEnum(String name, String desc, String value) {
        SignatureReader r = new SignatureReader(desc);
        CustomTraceSignatureVisitor sv = new CustomTraceSignatureVisitor(ACC_PUBLIC);
        r.accept(sv);

        annotation.getValues().put(name, newEnumValue(sv.getTypeOrSuperclass(), value));
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String desc) {
        final String annotationName = Type.getType(desc).getClassName();
        final AnnotationInfo innerAnnotation = AnnotationInfo.annotationInfo().name(annotationName).build();
        final AnnotationProcessor processor = new AnnotationProcessor(innerAnnotation);
        annotation.getValues().put(name, innerAnnotation);
        return processor;
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        throw new RuntimeException("Not implemented yet!");
    }
}

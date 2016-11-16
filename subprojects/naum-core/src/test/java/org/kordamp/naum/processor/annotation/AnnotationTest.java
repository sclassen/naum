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
package org.kordamp.naum.processor.annotation;

import org.junit.Test;
import org.kordamp.naum.model.EnumValue;
import org.kordamp.naum.model.AnnotationInfo;
import org.kordamp.naum.processor.AbstractProcessorTest;
import org.kordamp.naum.processor.annotation.WithAnnotationArrayValueAnnotation.AnotherAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationArrayValueAnnotation.CustomAnnotationArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationArrayValueAnnotation.DefaultAnnotationArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationArrayValueAnnotation.EmptyAnnotationArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationArrayValueAnnotation.SingleAnnotationArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationValueAnnotation.CustomAnnotationValueAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationValueAnnotation.DefaultAnnotationValueAnnotation;
import org.kordamp.naum.processor.annotation.WithAnnotationValueAnnotation.InnerAnnotation;
import org.kordamp.naum.processor.annotation.WithClassArrayValueAnnotation.CustomClassArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithClassArrayValueAnnotation.DefaultClassArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithClassArrayValueAnnotation.EmptyClassArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithClassArrayValueAnnotation.SingleClassArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithClassValueAnnotation.CustomArrayClassValueAnnotation;
import org.kordamp.naum.processor.annotation.WithClassValueAnnotation.CustomClassValueAnnotation;
import org.kordamp.naum.processor.annotation.WithClassValueAnnotation.DefaultClassValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.AnotherEnum;
import org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.CustomEnumArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.DefaultEnumArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.EmptyEnumArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.SingleEnumArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumValueAnnotation.CustomEnumValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumValueAnnotation.DefaultEnumValueAnnotation;
import org.kordamp.naum.processor.annotation.WithEnumValueAnnotation.SomeEnum;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.ByteArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.CharArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.DoubleArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.FloatArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.IntArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.LongArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveArrayValueAnnotation.ShortArrayAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.ByteValueAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.CharValueAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.DoubleValueAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.FloatValueAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.IntValueAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.LongValueAnnotation;
import org.kordamp.naum.processor.annotation.WithPrimitiveValueAnnotation.ShortValueAnnotation;
import org.kordamp.naum.processor.annotation.WithRetentionClassAnnotation.PlainClassAnnotation;
import org.kordamp.naum.processor.annotation.WithRetentionRuntimeAnnotation.PlainRuntimeAnnotation;
import org.kordamp.naum.processor.annotation.WithStringArrayValueAnnotation.CustomStringArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithStringArrayValueAnnotation.DefaultStringArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithStringArrayValueAnnotation.EmptyStringArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithStringArrayValueAnnotation.SingleStringArrayValueAnnotation;
import org.kordamp.naum.processor.annotation.WithStringValueAnnotation.CustomStringValueAnnotation;
import org.kordamp.naum.processor.annotation.WithStringValueAnnotation.EmptyDefaultStringValueAnnotation;
import org.kordamp.naum.processor.annotation.WithStringValueAnnotation.NonEmptyDefaultStringValueAnnotation;
import org.objectweb.asm.Type;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.kordamp.naum.NamedInfoMatcher.namedInfo;
import static org.kordamp.naum.model.AnnotationInfo.annotationInfo;
import static org.kordamp.naum.model.AnnotationValue.newEnumValue;
import static org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.AnotherEnum.BAR;
import static org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.AnotherEnum.GARTEN;
import static org.kordamp.naum.processor.annotation.WithEnumArrayValueAnnotation.AnotherEnum.PIZZA;
import static org.kordamp.naum.processor.annotation.WithEnumValueAnnotation.SomeEnum.NAUM;

public class AnnotationTest extends AbstractProcessorTest {

    @Test
    public void loadAndCheckWithRetentionSourceAnnotation() throws Exception {
        loadAndCheckAnnotations(WithRetentionSourceAnnotation.class, (annotations) -> {
            assertThat(annotations.size(), is(0));
        });
    }

    @Test
    public void loadAndCheckWithRetentionClassAnnotation() throws Exception {
        final AnnotationInfo annotation = annotationInfo().name(PlainClassAnnotation.class.getName()).build();

        loadAndCheckAnnotations(WithRetentionClassAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(annotation)));
        });
    }

    @Test
    public void loadAndCheckWithRetentionRuntimeAnnotation() throws Exception {
        final AnnotationInfo annotation = annotationInfo().name(PlainRuntimeAnnotation.class.getName()).build();

        loadAndCheckAnnotations(WithRetentionRuntimeAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(annotation)));
        });
    }

    @Test
    public void loadAndCheckWithStringValueAnnotation() throws Exception {
        final AnnotationInfo emptyDefault = annotationInfo().name(EmptyDefaultStringValueAnnotation.class.getName()).build();
        final AnnotationInfo nonEmptyDefault = annotationInfo().name(NonEmptyDefaultStringValueAnnotation.class.getName()).build();
        final AnnotationInfo custom = annotationInfo().name(CustomStringValueAnnotation.class.getName()).value("value", "Pizza").build();

        loadAndCheckAnnotations(WithStringValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(custom), namedInfo(emptyDefault), namedInfo(nonEmptyDefault)));
        });
    }

    @Test
    public void loadAndCheckWithClassValueAnnotation() throws Exception {
        final Type type = Type.getType(Exception.class);
        final Type arrayType = Type.getType(Exception[].class);

        final AnnotationInfo defaultClass = annotationInfo().name(DefaultClassValueAnnotation.class.getName()).build();
        final AnnotationInfo customClass = annotationInfo().name(CustomClassValueAnnotation.class.getName()).value("value", type).build();
        final AnnotationInfo customArrayClass = annotationInfo().name(CustomArrayClassValueAnnotation.class.getName()).value("value", arrayType).build();

        loadAndCheckAnnotations(WithClassValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(customArrayClass), namedInfo(customClass), namedInfo(defaultClass)));
        });
    }

    @Test
    public void loadAndCheckWithEnumValueAnnotation() throws Exception {
        final AnnotationInfo defaultEnum = annotationInfo().name(DefaultEnumValueAnnotation.class.getName()).build();
        final EnumValue enumValue = newEnumValue(SomeEnum.class.getName(), NAUM.name());
        final AnnotationInfo customEnum = annotationInfo().name(CustomEnumValueAnnotation.class.getName()).annotationValue("value", enumValue).build();

        loadAndCheckAnnotations(WithEnumValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(customEnum), namedInfo(defaultEnum)));
        });
    }

    @Test
    public void loadAndCheckWithAnnotationValueAnnotation() throws Exception {
        final AnnotationInfo defaultAnno = annotationInfo().name(DefaultAnnotationValueAnnotation.class.getName()).build();
        final AnnotationInfo innerAnnotation = annotationInfo().name(InnerAnnotation.class.getName()).value("value", "Pizza").build();
        final AnnotationInfo customAnno = annotationInfo().name(CustomAnnotationValueAnnotation.class.getName()).annotationValue("value", innerAnnotation).build();

        loadAndCheckAnnotations(WithAnnotationValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(customAnno), namedInfo(defaultAnno)));
        });
    }

    @Test
    public void loadAndCheckWithPrimitiveValueAnnotation() throws Exception {
        final AnnotationInfo byteAnnotation = annotationInfo().name(ByteValueAnnotation.class.getName()).value("byteValue", (byte) 1).build();
        final AnnotationInfo charAnnotation = annotationInfo().name(CharValueAnnotation.class.getName()).value("charValue", (char) 2).build();
        final AnnotationInfo shortAnnotation = annotationInfo().name(ShortValueAnnotation.class.getName()).value("shortValue", (short) 3).build();
        final AnnotationInfo intAnnotation = annotationInfo().name(IntValueAnnotation.class.getName()).value("intValue", 4).build();
        final AnnotationInfo longAnnotation = annotationInfo().name(LongValueAnnotation.class.getName()).value("longValue", (long) 5).build();
        final AnnotationInfo floatAnnotation = annotationInfo().name(FloatValueAnnotation.class.getName()).value("floatValue", 6.1f).build();
        final AnnotationInfo doubleAnnotation = annotationInfo().name(DoubleValueAnnotation.class.getName()).value("doubleValue", 7.1).build();

        loadAndCheckAnnotations(WithPrimitiveValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(
                namedInfo(byteAnnotation),
                namedInfo(charAnnotation),
                namedInfo(doubleAnnotation),
                namedInfo(floatAnnotation),
                namedInfo(intAnnotation),
                namedInfo(longAnnotation),
                namedInfo(shortAnnotation)
            ));
        });
    }

    @Test
    public void loadAndCheckWithStringArrayValueAnnotation() throws Exception {
        final AnnotationInfo nonEmptyDefault = annotationInfo().name(DefaultStringArrayValueAnnotation.class.getName()).build();
        final AnnotationInfo empty = annotationInfo().name(EmptyStringArrayValueAnnotation.class.getName()).value("value", new String[0]).build();
        final AnnotationInfo single = annotationInfo().name(SingleStringArrayValueAnnotation.class.getName()).value("value", new String[]{"Garten"}).build();
        final AnnotationInfo custom = annotationInfo().name(CustomStringArrayValueAnnotation.class.getName()).value("value", new String[]{"Pizza", "Bar"}).build();

        loadAndCheckAnnotations(WithStringArrayValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(custom), namedInfo(nonEmptyDefault), namedInfo(empty), namedInfo(single)));
        });
    }

    @Test
    public void loadAndCheckWithClassArrayValueAnnotation() throws Exception {
        final AnnotationInfo nonEmptyDefault = annotationInfo().name(DefaultClassArrayValueAnnotation.class.getName()).build();
        final AnnotationInfo empty = annotationInfo().name(EmptyClassArrayValueAnnotation.class.getName()).value("value", new Class[0]).build();
        final AnnotationInfo single = annotationInfo().name(SingleClassArrayValueAnnotation.class.getName()).value("value", new Class[]{Exception.class}).build();
        final AnnotationInfo custom = annotationInfo().name(CustomClassArrayValueAnnotation.class.getName()).value("value", new Class[]{IllegalStateException.class, IllegalArgumentException.class}).build();

        loadAndCheckAnnotations(WithClassArrayValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(custom), namedInfo(nonEmptyDefault), namedInfo(empty), namedInfo(single)));
        });
    }

    @Test
    public void loadAndCheckWithEnumArrayValueAnnotation() throws Exception {
        final EnumValue garten = newEnumValue(AnotherEnum.class.getName(), GARTEN.name());
        final EnumValue pizza = newEnumValue(AnotherEnum.class.getName(), PIZZA.name());
        final EnumValue bar = newEnumValue(AnotherEnum.class.getName(), BAR.name());

        final AnnotationInfo nonEmptyDefault = annotationInfo().name(DefaultEnumArrayValueAnnotation.class.getName()).build();
        final AnnotationInfo empty = annotationInfo().name(EmptyEnumArrayValueAnnotation.class.getName()).value("value", new EnumValue[0]).build();
        final AnnotationInfo single = annotationInfo().name(SingleEnumArrayValueAnnotation.class.getName()).value("value", new EnumValue[]{garten}).build();
        final AnnotationInfo custom = annotationInfo().name(CustomEnumArrayValueAnnotation.class.getName()).value("value", new EnumValue[]{pizza, bar}).build();

        loadAndCheckAnnotations(WithEnumArrayValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(custom), namedInfo(nonEmptyDefault), namedInfo(empty), namedInfo(single)));
        });
    }

    @Test
    public void loadAndCheckWithAnnotationArrayValueAnnotation() throws Exception {
        final AnnotationInfo garten = annotationInfo().name(AnotherAnnotation.class.getName()).value("value", "garten").build();
        final AnnotationInfo pizza = annotationInfo().name(AnotherAnnotation.class.getName()).value("value", "pizza").build();
        final AnnotationInfo bar = annotationInfo().name(AnotherAnnotation.class.getName()).value("value", "bar").build();

        final AnnotationInfo nonEmptyDefault = annotationInfo().name(DefaultAnnotationArrayValueAnnotation.class.getName()).build();
        final AnnotationInfo empty = annotationInfo().name(EmptyAnnotationArrayValueAnnotation.class.getName()).value("value", new AnnotationInfo[0]).build();
        final AnnotationInfo single = annotationInfo().name(SingleAnnotationArrayValueAnnotation.class.getName()).value("value", new AnnotationInfo[]{garten}).build();
        final AnnotationInfo custom = annotationInfo().name(CustomAnnotationArrayValueAnnotation.class.getName()).value("value", new AnnotationInfo[]{pizza, bar}).build();

        loadAndCheckAnnotations(WithAnnotationArrayValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(namedInfo(custom), namedInfo(nonEmptyDefault), namedInfo(empty), namedInfo(single)));
        });
    }

    @Test
    public void loadAndCheckWithPrimitiveArrayValueAnnotation() throws Exception {
        final AnnotationInfo byteAnnotation = annotationInfo().name(ByteArrayAnnotation.class.getName())
            .value("emptyByteArray", new byte[0])
            .value("singleByteArray", new byte[]{0})
            .value("customByteArray", new byte[]{1, 2})
            .build();
        final AnnotationInfo charAnnotation = annotationInfo().name(CharArrayAnnotation.class.getName())
            .value("emptyCharArray", new char[0])
            .value("singleCharArray", new char[]{1})
            .value("customCharArray", new char[]{2, 3})
            .build();
        final AnnotationInfo shortAnnotation = annotationInfo().name(ShortArrayAnnotation.class.getName())
            .value("emptyShortArray", new short[0])
            .value("singleShortArray", new short[]{2})
            .value("customShortArray", new short[]{3, 4})
            .build();
        final AnnotationInfo intAnnotation = annotationInfo().name(IntArrayAnnotation.class.getName())
            .value("emptyIntArray", new int[0])
            .value("singleIntArray", new int[]{3})
            .value("customIntArray", new int[]{4, 5})
            .build();
        final AnnotationInfo longAnnotation = annotationInfo().name(LongArrayAnnotation.class.getName())
            .value("emptyLongArray", new long[0])
            .value("singleLongArray", new long[]{5})
            .value("customLongArray", new long[]{5, 6})
            .build();
        final AnnotationInfo floatAnnotation = annotationInfo().name(FloatArrayAnnotation.class.getName())
            .value("emptyFloatArray", new float[0])
            .value("singleFloatArray", new float[]{6.1f})
            .value("customFloatArray", new float[]{6.2f, 6.3f})
            .build();
        final AnnotationInfo doubleAnnotation = annotationInfo().name(DoubleArrayAnnotation.class.getName())
            .value("emptyDoubleArray", new double[0])
            .value("singleDoubleArray", new double[]{7.1})
            .value("customDoubleArray", new double[]{7.2, 7.3})
            .build();

        loadAndCheckAnnotations(WithPrimitiveArrayValueAnnotation.class, (annotations) -> {
            assertThat(annotations, contains(
                namedInfo(byteAnnotation),
                namedInfo(charAnnotation),
                namedInfo(doubleAnnotation),
                namedInfo(floatAnnotation),
                namedInfo(intAnnotation),
                namedInfo(longAnnotation),
                namedInfo(shortAnnotation)
            ));
        });
    }

    private void loadAndCheckAnnotations(Class<?> clazz, AnnotationChecks checks) throws Exception {
        final String targetClassPath = clazz.getCanonicalName().replaceAll("\\.", "/");
        loadAndCheck(targetClassPath + ".class", (klass) -> {
            checks.check(klass.getAnnotations());
        });
    }

    public interface AnnotationChecks {
        void check(List<AnnotationInfo> annotations);
    }
}

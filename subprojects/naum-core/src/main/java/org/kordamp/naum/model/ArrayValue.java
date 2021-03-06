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

package org.kordamp.naum.model;

import lombok.Data;

import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

/**
 * @author Stephan Classen
 */
@Data
public class ArrayValue implements AnnotationValue {
    private final List<AnnotationValue> value;

    @Override
    public String getType() {
        if (isNotEmpty(value)) {
            Object head = value.get(0);
            return ((AnnotationValue) head).getType() + "[]";
        }
        throw new RuntimeException("Not implemented yet!");
    }

    @Override
    public String getValueAsString() {
        return value.toString();
    }
}

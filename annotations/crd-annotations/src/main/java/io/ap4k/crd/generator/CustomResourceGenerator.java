/**
 * Copyright 2018 The original authors.
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
 *
 **/
package io.ap4k.crd.generator;

import io.ap4k.Generator;
import io.ap4k.WithSession;
import io.ap4k.config.ConfigurationSupplier;
import io.ap4k.crd.adapter.CustomResourceConfigAdapter;
import io.ap4k.crd.annotation.CustomResource;
import io.ap4k.crd.confg.Keys;
import io.ap4k.crd.config.CustomResourceConfig;
import io.ap4k.crd.config.CustomResourceConfigBuilder;
import io.ap4k.crd.configurator.AddClassNameConfigurator;
import io.ap4k.crd.handler.CustomResourceHandler;
import io.sundr.codegen.functions.ElementTo;
import io.sundr.codegen.model.TypeDef;
import io.sundr.codegen.utils.ModelUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Map;

public interface CustomResourceGenerator extends Generator, WithSession {

  @Override
  default void add(Map map) {
    on(new ConfigurationSupplier<>(CustomResourceConfigAdapter.newBuilder(propertiesMap(map, CustomResource.class))));
  }

  @Override
  default void add(Element element) {
    CustomResource customResource = element.getAnnotation(CustomResource.class);
    if (element instanceof TypeElement) {
      TypeDef definition = ElementTo.TYPEDEF.apply((TypeElement) element);
      String className = ModelUtils.getClassName(element);

      on(customResource != null
        ? new ConfigurationSupplier<CustomResourceConfig>(CustomResourceConfigAdapter.newBuilder(customResource).addToAttributes(Keys.TYPE_DEFINITION, definition).accept(new AddClassNameConfigurator(className)))
        : new ConfigurationSupplier<CustomResourceConfig>(new CustomResourceConfigBuilder().addToAttributes(Keys.TYPE_DEFINITION, definition).accept(new AddClassNameConfigurator(className))));
    }
  }

  default void on(ConfigurationSupplier<CustomResourceConfig> config) {
    session.configurators().add(config);
    session.handlers().add(new CustomResourceHandler(session.resources()));
  }
}

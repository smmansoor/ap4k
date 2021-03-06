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

package io.ap4k.kubernetes.decorator;

import io.ap4k.kubernetes.config.Port;
import io.ap4k.kubernetes.annotation.Protocol;
import io.ap4k.deps.kubernetes.api.model.ContainerBuilder;
import io.ap4k.doc.Description;

import java.util.Objects;

/**
 * A decorator that adds a port to all containers.
 */
@Description("Add port to to the specified container(s).")
public class AddPortDecorator extends ApplicationContainerDecorator<ContainerBuilder> {

  private final Port port;

  public AddPortDecorator(Port port) {
    this.port = port;
  }

  @Override
  public void andThenVisit(ContainerBuilder container) {
    container.addNewPort()
      .withName(port.getName())
      .withHostPort(port.getHostPort() > 0 ? port.getHostPort() : null)
      .withContainerPort(port.getContainerPort())
      .withProtocol(port.getProtocol() != null ? port.getProtocol().name() : Protocol.TCP.name())
      .endPort();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AddPortDecorator addPortDecorator = (AddPortDecorator) o;
    return Objects.equals(port, addPortDecorator.port);
  }

  @Override
  public int hashCode() {

    return Objects.hash(port);
  }
}

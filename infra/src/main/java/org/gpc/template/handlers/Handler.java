package org.gpc.template.handlers;

@FunctionalInterface
public interface Handler<T,R> {
  R handle(T command);
}

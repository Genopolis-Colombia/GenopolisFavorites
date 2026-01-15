package org.gpc.template.usecase;


@FunctionalInterface
public interface UseCase<T,R> {
    R execute(T command);
}

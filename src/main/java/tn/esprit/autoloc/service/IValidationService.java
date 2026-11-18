package tn.esprit.autoloc.service;

public interface IValidationService<T> {
    boolean check(T entity, boolean isNew);
}

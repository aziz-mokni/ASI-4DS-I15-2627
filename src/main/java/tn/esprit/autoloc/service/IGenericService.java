package tn.esprit.autoloc.service;

public interface IGenericService<T, K> extends IReadService<T, K>, IWriteService<T, K>, IValidationService<T> {
}

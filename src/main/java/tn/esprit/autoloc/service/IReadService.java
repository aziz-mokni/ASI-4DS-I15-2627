package tn.esprit.autoloc.service;

import java.util.List;

public interface IReadService<T, K> {
    T findById(K id);

    List<T> findAll();
}

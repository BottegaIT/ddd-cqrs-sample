/*
 * Copyright 2011-2012 the original author or authors.
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
package pl.com.bottega.ddd.infrastructure.repo.jpa;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author Slawek
 * 
 * @param <E>
 *            JPA Entity Type (DDD: Aggregate, Entity)
 * @param <K>
 *            key type
 */
public class GenericJpaRepository<E, K> {

    @PersistenceContext
    protected EntityManager entityManager;

    private Class<E> clazz;

    @SuppressWarnings("unchecked")
    public GenericJpaRepository() {
        this.clazz = ((Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    public E load(K id) {
        return entityManager.find(clazz, id);
    }

    public void delete(K id) {
        entityManager.remove(load(id));
    }

    public void persist(E entity) {
        entityManager.persist(entity);
    }

    public E save(E entity) {
        return entityManager.merge(entity);
    }
}

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
package pl.com.bottega.ddd.domain.sharedkernel.specification;

/**
 * 
 * @author Slawek
 *
 * @param <T>
 */
public class OrSpecification<T> extends CompositeSpecification<T>{
    private Specification<T> a;
    private Specification<T> b;

    public OrSpecification(Specification<T> a, Specification<T> b){
        this.a = a;
        this.b = b;
    }

    public boolean isSatisfiedBy(T candidate){
        return a.isSatisfiedBy(candidate) || b.isSatisfiedBy(candidate);
    }
}

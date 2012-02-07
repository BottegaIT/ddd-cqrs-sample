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
package pl.com.bottega.erp.shipping.intrastructure;

import javax.inject.Inject;

import pl.com.bottega.ddd.domain.annotations.DomainRepositoryImpl;
import pl.com.bottega.ddd.domain.support.InjectorHelper;
import pl.com.bottega.ddd.infrastructure.repo.jpa.GenericJpaRepositoryForBaseEntity;
import pl.com.bottega.erp.shipping.domain.Shipment;
import pl.com.bottega.erp.shipping.domain.ShipmentRepository;

@DomainRepositoryImpl
public class JpaShipmentRepository extends GenericJpaRepositoryForBaseEntity<Shipment> implements ShipmentRepository {

    @Inject
    private InjectorHelper injectorHelper;

    // TODO move to GenericJpaRepositoryForBaseEntity
    @Override
    public Shipment load(Long id) {
        Shipment shipping = super.load(id);
        injectorHelper.injectDependencies(shipping);
        return shipping;
    }
}

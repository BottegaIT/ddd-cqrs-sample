package pl.com.bottega.erp.sales.domain;

import javax.persistence.Entity;

import pl.com.bottega.ddd.domain.BaseEntity;
import pl.com.bottega.ddd.domain.annotations.DomainAggregateRoot;

@Entity
@DomainAggregateRoot
public class Client extends BaseEntity {

}

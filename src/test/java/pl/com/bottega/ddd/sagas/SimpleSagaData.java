package pl.com.bottega.ddd.sagas;

import javax.persistence.Entity;

@Entity
public class SimpleSagaData {
    private Long createdOrderId;
    private Long submittedOrderId;

    public Long getCreatedOrderId() {
        return createdOrderId;
    }

    public void setCreatedOrderId(Long createdOrderId) {
        this.createdOrderId = createdOrderId;
    }

    public Long getSubmittedOrderId() {
        return submittedOrderId;
    }

    public void setSubmittedOrderId(Long submittedOrderId) {
        this.submittedOrderId = submittedOrderId;
    }
}

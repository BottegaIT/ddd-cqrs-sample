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

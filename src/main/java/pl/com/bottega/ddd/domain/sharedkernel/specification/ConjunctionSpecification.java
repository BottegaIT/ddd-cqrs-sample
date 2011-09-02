package pl.com.bottega.ddd.domain.sharedkernel.specification;

/**
 * 
 * @author Slawek
 *
 * @param <T>
 */
public class ConjunctionSpecification<T> extends CompositeSpecification<T>{
    private Specification<T>[] conjunction;

    public ConjunctionSpecification(Specification<T>... conjunction){
        this.conjunction = conjunction;
    }

    public boolean isSatisfiedBy(T candidate){
        for (Specification<T> spec : conjunction){
        	if (!spec.isSatisfiedBy(candidate))
        		return false;
        }
    	
    	return true;
    }
}

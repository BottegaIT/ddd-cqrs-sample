package pl.com.bottega.ddd.domain.sharedkernel;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import pl.com.bottega.ddd.domain.annotations.ValueObject;

@ValueObject
@Embeddable
public class Probability {

	private BigDecimal value;
	
	@Transient
	private MathContext mathContext = MathContext.DECIMAL32;
	
	private Probability(BigDecimal value){
		this.value = value;
	}
	
	public static Probability fromDecimal(BigDecimal value) {
		if (value == null || value.compareTo(BigDecimal.ZERO) == -1 || value.compareTo(BigDecimal.ONE) == 1)
			throw new IllegalArgumentException("value must be in <0,1>");
		return new Probability(value);
	}
	

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof Probability) {
			Probability percentage = (Probability) obj;
			return percentage.value.round(mathContext).compareTo(value.round(mathContext)) == 0;
		}
		
		return false;
	}

	public Probability combinedWith(Probability percentage) {
		return new Probability(value.multiply(percentage.value));
	}

	public Probability not() {
		return new Probability(BigDecimal.ONE.subtract(value));
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}

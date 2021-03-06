package org.libex.test.theories.suppliers;

import static com.google.common.collect.Lists.newArrayList;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import org.junit.experimental.theories.ParameterSignature;
import org.junit.experimental.theories.ParameterSupplier;
import org.junit.experimental.theories.ParametersSuppliedBy;
import org.junit.experimental.theories.PotentialAssignment;
import org.libex.test.theories.suppliers.TestOn.TestOnSupplier;

/**
 * Allows for testing multiple values using Theories. Theories may be set up as
 * follows:
 * 
 * {@code @Thoery public void test(@TestOn(ints= 2,3,4} int value1,
 * 
 * @TestOn(booleans={true, false} boolean bool)}
 *                         <p/>
 *                         The following example will return "blah" and
 *                         {@code null}: {@code @Thoery public void
 *                         test(@TestOn(strings= "blah", TestOn.NULL} boolean
 *                         bool)}
 * 
 * @author John Butler
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@ParametersSuppliedBy(TestOnSupplier.class)
@Target(ElementType.PARAMETER)
public @interface TestOn {

	public static final String NULL = "null";

	int[] ints() default {};

	long[] longs() default {};

	short[] shorts() default {};

	byte[] bytes() default {};

	float[] floats() default {};

	double[] doubles() default {};

	boolean[] booleans() default {};

	/**
	 * A value of {@code "null"} or {@link NULL} will result in a {@code null}
	 * value being returned
	 */
	String[] strings() default {};

	public static class TestOnSupplier extends ParameterSupplier {

		@Override
		public List<PotentialAssignment> getValueSources(ParameterSignature sig) {
			List<PotentialAssignment> result = newArrayList();

			TestOn testOn = sig.getAnnotation(TestOn.class);
			if (testOn != null) {
				for (int i : testOn.ints()) {
					result.add(PotentialAssignment.forValue(
							Integer.toString(i), i));
				}
				for (long i : testOn.longs()) {
					result.add(PotentialAssignment.forValue(Long.toString(i), i));
				}
				for (short i : testOn.shorts()) {
					result.add(PotentialAssignment.forValue(Short.toString(i),
							i));
				}
				for (byte i : testOn.bytes()) {
					result.add(PotentialAssignment.forValue(Byte.toString(i), i));
				}
				for (float i : testOn.floats()) {
					result.add(PotentialAssignment.forValue(Float.toString(i),
							i));
				}
				for (double i : testOn.doubles()) {
					result.add(PotentialAssignment.forValue(Double.toString(i),
							i));
				}
				for (boolean i : testOn.booleans()) {
					result.add(PotentialAssignment.forValue(
							Boolean.toString(i), i));
				}
				for (String i : testOn.strings()) {
					result.add(PotentialAssignment.forValue(i, ("null".equals(i) || NULL.equals(i)) ? null : i));
				}
			}

			return result;
		}

	}
}

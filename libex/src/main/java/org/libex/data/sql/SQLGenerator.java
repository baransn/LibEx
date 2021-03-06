package org.libex.data.sql;

import static com.google.common.base.Functions.*;
import static com.google.common.base.Preconditions.*;
import static com.google.common.collect.Iterables.*;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Joiner;

@ThreadSafe
@ParametersAreNonnullByDefault
public class SQLGenerator {

	private static final Joiner commaJoiner = Joiner.on(",");

	public static String generateInsertStatement(String tableName, Iterable<String> columnNames) {
		checkArguments(tableName, columnNames);

		String columns = commaJoiner.join(columnNames);
		String values = commaJoiner.join(transform(columnNames, constant("?")));

		return String.format("insert into %s (%s) values (%s)", tableName, columns, values);
	}

	public static String generateSelectStatement(String tableName, Iterable<String> columnNames) {
		checkArguments(tableName, columnNames);

		return String.format("select %s from %s", commaJoiner.join(columnNames), tableName);
	}

	private static void checkArguments(String tableName, Iterable<String> columnNames) {
		checkNotNull(tableName);
		checkArgument(!tableName.isEmpty());
		checkNotNull(columnNames);
		checkArgument(columnNames.iterator().hasNext());
	}

	private SQLGenerator() {
	}
}

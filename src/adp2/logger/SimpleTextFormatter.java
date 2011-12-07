package adp2.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * (level) methodName: message\n
 * @author Benjamin Kahlau
 */
public class SimpleTextFormatter extends Formatter {
	private static long starttime = 0;
	
	@Override
	public String format(LogRecord record) {
		if (starttime == 0) {
			starttime = record.getMillis();
		}
		return (record.getMillis()-starttime) + "ms (" + record.getLevel() + ") " + record.getSourceMethodName() + ": " + record.getMessage() + "\n";
	}
}
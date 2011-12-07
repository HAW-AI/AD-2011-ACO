package adp2.logger;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * (level) methodName: message\n
 * @author Benjamin Kahlau
 */
public class SimpleHtmlFormatter extends Formatter {
	private static long starttime = 0;
	
	@Override
	public String format(LogRecord record) {
		if (starttime == 0) {
			starttime = record.getMillis();
		}
		return "   <tr class=\"" + record.getLevel() + "\">\n"
				+ "    <td>" + (record.getMillis()-starttime) + "ms</td>\n"
				+ "    <td>" + record.getLevel() + "</td>\n"
				+ "    <td>" + record.getSourceMethodName() + "</td>\n"
				+ "    <td>" + record.getMessage() + "</td>\n"
				+ "   </tr>\n";
	}
	
		// This method is called just after the handler using this
	// formatter is created
	public String getHead(Handler h)
	{
		return "<html>\n"
				+ " <head>"
				+ " <title>ACO Logging</title>\n"
				+ " <style type=\"text/css\">\n"
				+ " td, th { border: 1px solid lightgray; }\n"
				+ " table { width: 100%; }\n"
				+ " th { color: grey; }\n"
				+ " tr.info { color: severe; }\n"
				+ " tr.warn { color: black; }\n"
				+ " </style>\n"
				+ " </head>\n"
				+ " <body>\n"
				+ "  <table>\n"
				+ "   <tr>\n"
				+ "    <th>Time</th>\n"
				+ "    <th>Level</th>\n"
				+ "    <th>Method</th>\n"
				+ "    <th>Message</th>\n"
				+ "   </tr>\n";
	}

	// This method is called just after the handler using this
	// formatter is closed
	public String getTail(Handler h)
	{
		return " </table>\n </body>\n</html>\n";
	}
}
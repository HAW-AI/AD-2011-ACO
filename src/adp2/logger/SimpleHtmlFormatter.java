package adp2.logger;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
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
	
	/*
	 * This method is called just after the handler using this formatter is created
	 */
	@Override
	public String getHead(Handler h)
	{
		return "<html>\n"
				+ " <head>"
				+ " <title>ACO Logging</title>\n"
				+ " <style type=\"text/css\">\n"
				+ " td, th { border: 1px solid lightgray; }\n"
				+ " table { width: 100%; }\n"
				+ " th { color: grey; }\n"
				+ " tr.info { color: #800000; }\n"
				+ " tr.fine { color: #404040; }\n"
				+ " tr.finer { color: #606060; }\n"
				+ " tr.warning { color: #FF0000; }\n"
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

	/*
	 * This method is called just after the handler using this formatter is closed
	 * @param
	 */
	@Override
	public String getTail(Handler h)
	{
		return " </table>\n </body>\n</html>\n";
	}
}
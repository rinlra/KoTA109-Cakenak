package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class EnableTable {
	public static void main(String args[]) throws MasterNotRunningException, IOException {

		// Instantiating configuration class
		Configuration conf = HBaseConfiguration.create();

		// Instantiating HBaseAdmin class
		HBaseAdmin admin = new HBaseAdmin(conf);

		// Verifying whether the table is disabled
		Boolean bool = admin.isTableEnabled("employee");
		System.out.println(bool);

		// Enabling the table using HBaseAdmin object
		if (!bool) {
			admin.enableTable("employee");
			System.out.println("Table Enabled");
		}
	}
}

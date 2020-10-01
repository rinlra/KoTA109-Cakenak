package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Filter {
	public static void main(String args[]) throws IOException {
		
		Configuration conf = HBaseConfiguration.create();
		HTable table = new HTable(conf, "kue");
		Scan scan = new Scan();
		scan.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("salary"));
		ResultScanner result = table.getScanner(scan);
		for (Result res : result) {
			byte[] valSalary = res.getValue(Bytes.toBytes("professional"), Bytes.toBytes("salary"));
			System.out.println("Salary:"+Bytes.toString(valSalary));
		}
		table.close();
		
	}
}

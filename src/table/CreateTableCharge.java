package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateTableCharge {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Instantiating configuration class
		Configuration con = HBaseConfiguration.create();

		// Instantiating HbaseAdmin class
		HBaseAdmin charge = new HBaseAdmin(con);

		// Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("charge"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("market"));

		// Execute the table through admin
		charge.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		charge.close();
	}
}

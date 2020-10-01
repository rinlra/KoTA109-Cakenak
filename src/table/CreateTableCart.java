package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateTableCart {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Instantiating configuration class
		Configuration con = HBaseConfiguration.create();

		// Instantiating HbaseAdmin class
		HBaseAdmin cart = new HBaseAdmin(con);

		// Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("cart"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("item"));
		tableDescriptor.addFamily(new HColumnDescriptor("market"));
		tableDescriptor.addFamily(new HColumnDescriptor("details"));

		// Execute the table through admin
		cart.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		cart.close();
	}
	
}

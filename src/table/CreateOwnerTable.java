package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateOwnerTable {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		// Instantiating configuration class
		Configuration con = HBaseConfiguration.create();

		// Instantiating HbaseAdmin class
		HBaseAdmin admin = new HBaseAdmin(con);

		// Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("penjual"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("info"));

		// Execute the table through admin
		admin.createTable(tableDescriptor);
		System.out.println(" Table created ");
	}

}

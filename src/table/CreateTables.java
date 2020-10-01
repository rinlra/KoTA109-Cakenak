package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

public class CreateTables {

	public static void main(String[] args) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {

		Configuration con = HBaseConfiguration.create();

		// Instantiating HbaseAdmin class
		HBaseAdmin admin = new HBaseAdmin(con);

		// Instantiating table descriptor class
		HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("admin"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("personal"));
		tableDescriptor.addFamily(new HColumnDescriptor("market"));
		tableDescriptor.addFamily(new HColumnDescriptor("info"));

		// Execute the table through admin
		admin.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		admin.close();
		
		HBaseAdmin ads = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("ads"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("market"));

		// Execute the table through admin
		ads.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		ads.close();
		
		HBaseAdmin cart = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("cart"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("item"));
		tableDescriptor.addFamily(new HColumnDescriptor("market"));
		tableDescriptor.addFamily(new HColumnDescriptor("details"));

		// Execute the table through admin
		cart.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		cart.close();
		
		HBaseAdmin charge = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("charge"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("market"));

		// Execute the table through admin
		charge.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		charge.close();
		
		HBaseAdmin kategori = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("kategori"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("market"));

		// Execute the table through admin
		kategori.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		kategori.close();
		
		HBaseAdmin kue = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("kue"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("item"));
		tableDescriptor.addFamily(new HColumnDescriptor("market"));
		tableDescriptor.addFamily(new HColumnDescriptor("details"));

		// Execute the table through admin
		kue.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		kue.close();
		
		HBaseAdmin pembeli = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("pembeli"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("personal"));
		tableDescriptor.addFamily(new HColumnDescriptor("market"));
		tableDescriptor.addFamily(new HColumnDescriptor("details"));

		// Execute the table through admin
		pembeli.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		pembeli.close();
		
		HBaseAdmin penjual = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("penjual"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("personal"));
		tableDescriptor.addFamily(new HColumnDescriptor("market"));
		tableDescriptor.addFamily(new HColumnDescriptor("info"));

		// Execute the table through admin
		penjual.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		penjual.close();
		
		HBaseAdmin slider = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("slider"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("market"));

		// Execute the table through admin
		slider.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		slider.close();
		
		HBaseAdmin transaksi = new HBaseAdmin(con);

		// Instantiating table descriptor class
		tableDescriptor = new HTableDescriptor(TableName.valueOf("transaksi"));

		// Adding column families to table descriptor
		tableDescriptor.addFamily(new HColumnDescriptor("details"));

		// Execute the table through admin
		transaksi.createTable(tableDescriptor);
		System.out.println(" Table created ");
		
		transaksi.close();
		
	}

}

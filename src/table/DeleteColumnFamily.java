package table;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;

public class DeleteColumnFamily {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Configuration conf = HBaseConfiguration.create();

		HBaseAdmin admin = new HBaseAdmin(conf);
		admin.deleteColumn(TableName.valueOf("penjual"), 
		          Bytes.toBytes("NewColumnFamily"));
		System.out.println("cf deleted");
	}

}

package dbrepo;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import model.Kue;

public class WriteSync {

	Configuration config = HBaseConfiguration.create();

	public WriteSync() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}
	
	public synchronized void write(Kue kue) {	
		
		try {
			HTable table = new HTable(config,"kue");

			Put p = new Put(Bytes.toBytes(kue.getIdkue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"), Bytes.toBytes(kue.getAvailablestock()));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			System.out.println("data updated.....");
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

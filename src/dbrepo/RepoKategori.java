package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.util.Bytes;

import model.Kategori;

/**
 *
 * @KoTA109
 */
public class RepoKategori {

    Kategori bean = new Kategori();
    
    Configuration config = HBaseConfiguration.create();

	public RepoKategori() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}
    
    public boolean addKategori(Kategori cat) {
    	boolean b = false;
    	
		try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "kategori");
				Put p = null;
	
				Scan scan = new Scan();
				String Row = null;
				ResultScanner scanner = hTable.getScanner(scan);
				for (Result result = scanner.next(); result != null; result = scanner.next()) {
					Row = Bytes.toString(result.getRow());
				}
				if (Row == null) {
					p = new Put(Bytes.toBytes("0"));
				} else {
					int data = 0;
					// System.out.println(data + 1);
	
					// Instantiating Put class
					// accepts a row name.
					p = new Put(Bytes.toBytes(data + 1));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("nama"), Bytes.toBytes(bean.getNama()));
			
			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
			b = true;
		} catch (IOException e) {
			// TODO: handle exception
			return b;
		}
		
		return b;
    }

    public void updateKategori(Kategori updateCat) {

    	boolean b = false;
		
		try {
			// Instantiating HTable class
			HTable hTable = new HTable(config, "kategori");
			Put p = new Put(Bytes.toBytes(updateCat.getIdkategori()));

		// adding values using add() method
		// accepts column family name, qualifier/row name ,value
		p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("nama"), Bytes.toBytes(updateCat.getNama()));
 
		
		hTable.put(p);
		System.out.println("data inserted");

		// closing HTable
		hTable.close();
		b = true;
	} catch (IOException e) {
		// TODO: handle exception
		Logger.getLogger(RepoKategori.class.getName()).log(Level.SEVERE, null, e);
	}
    	
    	
    }

    public ArrayList<Kategori> getAllKategori() throws IOException {

        ArrayList<Kategori> AllCategory = new ArrayList<>();
        HTable table = new HTable(config, "kategori");
        
        Kategori cat = null;
        
		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("nama"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			cat = new Kategori();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("nama"));

			cat.setIdkategori(Bytes.toInt(result.getRow()));
			cat.setNama(Bytes.toString(value1));
			
		}
		scanner.close();
        return AllCategory;
    }
    
    public Kategori getKategori(int id) throws IOException {

    	Kategori cat = null;

		HTable table = new HTable(config, "kategori");

		List<Filter> filters = new ArrayList<Filter>();

		Get get = new Get(Bytes.toBytes(id));
		
		
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("nama"));
		
		Result result = table.get(get);
		
		if (result != null) {
			cat = new Kategori();
			
			// Get values from the row
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("nama"));
		
			cat.setIdkategori(Bytes.toInt(result.getRow()));
			cat.setNama(Bytes.toString(value1));
			//kue.setKomentar(komentar);
		}
		
		return cat;
    }

}

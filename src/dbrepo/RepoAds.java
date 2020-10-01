package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import model.Advertisement;

/**
 *
 * @KoTA109
 */
public class RepoAds {


    Configuration config = HBaseConfiguration.create();

	public RepoAds() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}


    public boolean addAdvertisment(String img ,String url) throws IOException {
    	
    	boolean b = false;
    	HTable hTable = new HTable(config, "ads");
    	
        try {
            
				// Instantiating HTable class
				
				Put p = null;
	
				Scan scan = new Scan();
				String Row = null;
				ResultScanner scanner = hTable.getScanner(scan);
				for (Result result = scanner.next(); result != null; result = scanner.next()) {
					Row = Bytes.toString(result.getRow());
				}
				if (Row == null) {
					p = new Put(Bytes.toBytes("rowAds0"));
				} else {
					String[] parts = Row.split("rowAds");
					int data = Integer.parseInt(parts[1]);
					// System.out.println(data + 1);
	
					// Instantiating Put class
					// accepts a row name.
					p = new Put(Bytes.toBytes("rowAds" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("image"), Bytes.toBytes(img));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("url"), Bytes.toBytes(url));

			
			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
			b = true;
		}catch (IOException ex) {
			hTable.close();
            ex.printStackTrace();
            return b;
        }
		return b;
    	
    }

    public boolean deleteAdvertisment(int id) {

        try {
        	HTable table = new HTable(config, "ads");
			// Instantiating Delete class
			Delete delete = new Delete(Bytes.toBytes(id));
			delete.deleteFamily(Bytes.toBytes("market"));
			// deleting the data
			table.delete(delete);

			// closing the HTable object
			table.close();
			
			System.out.println("data deleted.....");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }

    public Advertisement getRandomAds() throws IOException{
        ArrayList<Advertisement> allAds = getAllAdvertisements();
        if(allAds.size() == 0 )
            return null ; 
        else return  allAds.get(new Random().nextInt(allAds.size()));
    }

    public ArrayList<Advertisement> getAllAdvertisements() throws IOException {
        
    	ArrayList<Advertisement> resultList = new ArrayList<Advertisement>();
    	
    	Advertisement ads = null;
    	
		HTable table = new HTable(config, "ads");
		try {
		
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("image"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("url"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			
			ads = new Advertisement();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("image"));     
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("url"));
			
			ads.setImage(Bytes.toString(value1));
			ads.setUrl(Bytes.toString(value2));
			ads.setId(Bytes.toInt(result.getRow()));

			resultList.add(ads);
		}
		scanner.close();
		}catch (IOException ex) {
			table.close();
            ex.printStackTrace();
            return null;
        } finally {
        	table.close();
        }
		

		return resultList;
    	
    }
}

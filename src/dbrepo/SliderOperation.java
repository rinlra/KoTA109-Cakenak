package dbrepo;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import model.Slider;

/**
 *
 * @KoTA109
 */
public class SliderOperation {
	
	Configuration config = HBaseConfiguration.create();

	public SliderOperation() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

    public boolean addSlider(Slider slider) {
    
    	boolean b = false;
		
			try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "slider");
				Put p = null;
	
				Scan scan = new Scan();
				String Row = null;
				ResultScanner scanner = hTable.getScanner(scan);
				for (Result result = scanner.next(); result != null; result = scanner.next()) {
					Row = Bytes.toString(result.getRow());
				}
				if (Row == null) {
					p = new Put(Bytes.toBytes("rowSlider0"));
				} else {
					String[] parts = Row.split("rowSlider");
					int data = Integer.parseInt(parts[1]);
					// System.out.println(data + 1);
	
					// Instantiating Put class
					// accepts a row name.
					p = new Put(Bytes.toBytes("rowSlider" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("gambar"), Bytes.toBytes(slider.getGambar()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("title"), Bytes.toBytes(slider.getTitle()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("subtitle"),  Bytes.toBytes(slider.getSubtitle()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("deskripsi"), Bytes.toBytes(slider.getDeskripsi()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"), Bytes.toBytes(slider.getIdkue()));  
			
			hTable.put(p);
			System.out.println("data slider inserted");

			// closing HTable
			hTable.close();
			b = true;
		} catch (IOException e) {
			// TODO: handle exception
			return b;
		}
		
		return b;
    
    }

    public boolean deleteSlider(int id) {

    	try {
			
			HTable table = new HTable(config, "slider");
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

    public ArrayList<Slider> getAllSliders() throws IOException {
    	
    	ArrayList<Slider> slist = new ArrayList<Slider>();
    	
    	Slider slide = null;
    	
    	HTable table = new HTable(config, "slider");

		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("gambar"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("title"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("subtitle"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			slide = new Slider();
			
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("gambar"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("title"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("subtitle"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("deskripsi"));
			
			slide.setGambar(Bytes.toString(value1));
			slide.setTitle(Bytes.toString(value2));
			slide.setSubtitle(Bytes.toString(value3));
			slide.setDeskripsi(Bytes.toString(value4));
			slide.setId(Bytes.toString(result.getRow()));
			
			slist.add(slide);
		}
		scanner.close();
		
		return slist;
		
    }
    
}

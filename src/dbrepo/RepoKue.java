package dbrepo;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import Utils.UploadIMG;
import model.Kue;

/**
 *
 * @author KoTA109
 */
public class RepoKue {

	DecimalFormat df = new DecimalFormat("0.#");
	
	Configuration config = HBaseConfiguration.create();
	
	private int noOfRecords ; 
	
	public RepoKue() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}
	
	public boolean addKue(Kue kue) {
		try {
			int lastrow = 0;
			// Instantiating HTable class
			HTable hTable = new HTable(config, "kue");
			Put p = null;

			Scan scan = new Scan();
			String Row = null;
			ResultScanner scanner = hTable.getScanner(scan);
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				Row = Bytes.toString(result.getRow());
			}
			if (Row == null) {
				p = new Put(Bytes.toBytes("rowKue0"));
			} else {
				String[] parts = Row.split("rowKue");
				int data = Integer.parseInt(parts[1]);
				// System.out.println(data + 1);

				// Instantiating Put class
				// accepts a row name.
				p = new Put(Bytes.toBytes("rowKue" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value

			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"), Bytes.toBytes(kue.getIdpenjual()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"), Bytes.toBytes(kue.getNamakue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"), Bytes.toBytes(kue.getJenis()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"), Bytes.toBytes(kue.getHarga()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"), Bytes.toBytes(kue.getGambarkue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"), Bytes.toBytes(kue.getPhysicalstock()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"), Bytes.toBytes(kue.getAvailablestock()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"), Bytes.toBytes(kue.getBerat()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"), Bytes.toBytes(kue.getDeskripsi()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"), Bytes.toBytes(kue.getTglproduksi()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"), Bytes.toBytes(kue.getTglbaiksblm()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"), Bytes.toBytes(kue.getWaktudiinput()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"), Bytes.toBytes(kue.getLayakjual()));
			// p.aBytes.toBytes("market"), Bytes.toBytes("komentar"));    
			// Bytes.toBytes(floornumber));
			// p.addColumn(Bytes.toBytes("private"), Bytes.toBytes("totalKueArea"),
			// Bytes.toBytes(totalkuearea));
			// p.addColumn(Bytes.toBytes("private"), Bytes.toBytes("totalJenisArea"),
			// Bytes.toBytes(totaljenisarea));
			// Saving the put Instance to the HTable.
			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
			
			//Menambah Jumlah Kue
			HTable table = new HTable(config, "penjual");
			
			Get get = new Get(Bytes.toBytes(kue.getIdpenjual()));
			get.addFamily(Bytes.toBytes("market"));
			
			Result result = table.get(get);
			byte [] value = result.getValue(Bytes.toBytes("market"),Bytes.toBytes("totalkue"));
			
			int kuetotal = Bytes.toInt(value);
			kuetotal += 1;
			
			p = new Put(Bytes.toBytes(kue.getIdpenjual()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(kuetotal));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
		} catch (IOException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public ArrayList<Kue> getKueByIdPenjual(String id) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("idpenjual")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(id)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println(id);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			
			kue = new Kue();
			
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			resultList.add(kue);
		}
		scanner.close();

		return resultList;
	}
	
	public ArrayList<Kue> getKueByIdPenjualRange(String id, int start, int limit) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("idpenjual")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(id)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println(id);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes("rowKue"+start));
		scan.withStopRow(Bytes.toBytes("rowKue"+limit));
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			
			kue = new Kue();
			
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			resultList.add(kue);
		}
		scanner.close();

		return resultList;
	}
	
	public ArrayList<Kue> getAllKue() throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			
			kue = new Kue();
			
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));      
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));        
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));          
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));          
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));      
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));  
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock")); 
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			resultList.add(kue);
		}
		scanner.close();
		table.close();
		return resultList;
	}
	
	public ArrayList<Kue> getAllKue(int start , int limit) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");
		noOfRecords = 0;
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		scan.withStartRow(Bytes.toBytes("rowKue"+start));
		scan.withStopRow(Bytes.toBytes("rowKue"+limit));
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			
			kue = new Kue();
			
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));      
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));        
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));          
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));          
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));      
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));  
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock")); 
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));  
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			//Scan komentar
			//List<Komentar> komentar = new ArrayList<>(); //getKomentarKue(idkue);

			
			resultList.add(kue);
		}
		scanner.close();
		table.close();
		noOfRecords = resultList.size();
		
		return resultList;
	}
	
	public Kue getKueById(String idkue) throws IOException {
		Kue kue = null;

		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		Get get = new Get(Bytes.toBytes(idkue));
		
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));     
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));       
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));         
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));         
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));     
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock")); 
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));         
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		get.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		
		Result result = table.get(get);
		
		if (result != null) {
			kue = new Kue();
			
			// Get values from the row
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			
			System.out.println("nama kue : " + kue.getNamakue() + " Jenis : " + kue.getJenis());
		}
		table.close();
		return kue;
	}
	
	public ArrayList<Kue> getKueByNama(String namakue) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("namakue")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(namakue)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("mencari kue dengan nama : " + namakue);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			kue = new Kue();
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			//Scan komentar
			//List<Komentar> Komentar = getKomentarKue(idkue);
			//List<Komentar> Komentar = new ArrayList<Komentar>();

			
			resultList.add(kue);
		}
		scanner.close();
		table.close();
		return resultList;
	}
	
	public ArrayList<Kue> getKueByKategori(int kategori) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("jenis")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(kategori)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("mencari kue dengan kategori : " + kategori);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			kue = new Kue();
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			//Scan komentar
			//List<Komentar> Komentar = getKomentarKue(idkue);
			//List<Komentar> Komentar = new ArrayList<Komentar>();

			
			resultList.add(kue);
		}
		scanner.close();
		table.close();
		return resultList;
	}
	
	public ArrayList<Kue> getKueByKategori(int kategori, int start, int limit) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		
		noOfRecords = 0;
		
		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("jenis")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(kategori)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("mencari kue dengan kategori : " + kategori);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		scan.withStartRow(Bytes.toBytes("rowKue"+start));
		scan.withStopRow(Bytes.toBytes("rowKue"+limit));
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			kue = new Kue();
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar")); 
			System.out.println("penjualId : " + Bytes.toString(value1) + " nama kue : " + Bytes.toString(value2) + " harga : "
					+ Bytes.toString(value4) + " berat : " + Bytes.toInt(value8));
			//Printing the values
			
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			
			//Scan komentar
			//List<Komentar> Komentar = getKomentarKue(idkue);
			//List<Komentar> Komentar = new ArrayList<Komentar>();

			
			resultList.add(kue);
		}
		scanner.close();
		
		noOfRecords = resultList.size();
		table.close();
		return resultList;
	}
	
	public synchronized boolean updateQuantityKue(Kue product) throws IOException {
		//WriteSync writeSync = new WriteSync();
        int i = 0;
        try {
			HTable table = new HTable(config,"kue");

			Put p = new Put(Bytes.toBytes(product.getIdkue()));
			
			//Put p = writeSync.write(Bytes.toBytes(product.getIdkue()));
			
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"), Bytes.toBytes(product.getPhysicalstock()));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			System.out.println("data updated.....");
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public synchronized boolean updateQuantityKueAvailable(Kue product) throws IOException {
		
        int i = 0;
        try {
			HTable table = new HTable(config,"kue");

			Put p = new Put(Bytes.toBytes(product.getIdkue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"), Bytes.toBytes(product.getAvailablestock()));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			System.out.println("data updated.....");
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ArrayList<Kue> getAllKueByHarga(double priceStart, double priceEnd) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<Kue>();

		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("harga")
	            , CompareFilter.CompareOp.GREATER_OR_EQUAL, new BinaryComparator(Bytes.toBytes(priceStart)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
	    SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("harga")
	            , CompareFilter.CompareOp.LESS_OR_EQUAL, new BinaryComparator(Bytes.toBytes(priceEnd)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter2);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			kue = new Kue();
			
			byte[] value1 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("namakue"));
			byte[] value3 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("jenis"));
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   
			byte[] value5 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"));
			byte[] value6 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"));
			byte[] value7 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("availablestock"));
			byte[] value8 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("berat"));
			byte[] value9 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"));
			byte[] value10 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"));
			byte[] value11 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"));
			byte[] value12 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"));
			byte[] value13 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("layakjual"));
			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));
			kue.setIdpenjual(Bytes.toString(value1));
			kue.setNamakue(Bytes.toString(value2));
			kue.setJenis(Bytes.toInt(value3));
			kue.setHarga(Bytes.toDouble(value4));
			kue.setGambarkue(Bytes.toString(value5));
			kue.setPhysicalstock(Bytes.toInt(value6));
			kue.setAvailablestock(Bytes.toInt(value7));
			kue.setBerat(Bytes.toFloat(value8));
			kue.setDeskripsi(Bytes.toString(value9));
			kue.setTglproduksi(Bytes.toString(value10));
			kue.setTglbaiksblm(Bytes.toString(value11));
			kue.setWaktudiinput(Bytes.toString(value12));
			kue.setLayakjual(Bytes.toInt(value13));
			//Scan komentar
			//List<Komentar> vKomentar = getKomentarKue(idkue);

			resultList.add(kue);
		}
		scanner.close();
		table.close();
		return resultList;
	}
	
	public double getHargaKueMaksimum() throws IOException {
		double highestprice=0;

		Kue kue = null;
		
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"));

		//scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
		

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			kue = new Kue();
			
			byte[] value4 = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("harga"));   

			//byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));   
			
			kue.setIdkue(Bytes.toString(result.getRow()));

			kue.setHarga(Bytes.toDouble(value4));

			
			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("harga")
		            , CompareFilter.CompareOp.GREATER_OR_EQUAL, new BinaryComparator(Bytes.toBytes(kue.getHarga())));
		    colValFilter.setFilterIfMissing(false);
		    filters.add(colValFilter);
			
		    scan.setFilter(fl);
			//Scan komentar
			//List<Komentar> vKomentar = getKomentarKue(idkue);
			
		}
		
		scanner.close();
		
		highestprice = kue.getHarga();
		
		return highestprice;
	}
	
	public int getNoOfRecords() {
        return noOfRecords;
    }
	
	public boolean editKue(Kue kue, int oldphysicalstock) {
		try {
			HTable table = new HTable(config,"kue");

			Put p = new Put(Bytes.toBytes(kue.getIdkue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"), Bytes.toBytes(kue.getIdpenjual()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("namakue"), Bytes.toBytes(kue.getNamakue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("jenis"), Bytes.toBytes(kue.getJenis()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("harga"), Bytes.toBytes(kue.getHarga()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"), Bytes.toBytes(kue.getGambarkue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"), Bytes.toBytes(kue.getPhysicalstock()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("availablestock"), Bytes.toBytes(kue.getPhysicalstock() - (oldphysicalstock - kue.getAvailablestock())));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("berat"), Bytes.toBytes(kue.getBerat()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"), Bytes.toBytes(kue.getDeskripsi()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"), Bytes.toBytes(kue.getTglproduksi()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"), Bytes.toBytes(kue.getTglbaiksblm()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"), Bytes.toBytes(kue.getWaktudiinput()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"), Bytes.toBytes(kue.getLayakjual()));
			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			System.out.println("data updated.....");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean deleteKue(String idkue) {
		RepoCart repoCart = new RepoCart();
		if (idkue != null) {
		try {
			/* ----------- Get Kue ID by Kue ID ----------------- */
			Kue kue = getKueById(idkue);
			boolean deleteFile = UploadIMG.deleteGambarKue(kue.getGambarkue());
			HTable table = new HTable(config, "kue");
			
			Get get = new Get(Bytes.toBytes(idkue));
			get.addFamily(Bytes.toBytes("item"));
			//get.addFamily(Bytes.toBytes("market"));
			
			Result result = table.get(get);
			byte [] value = result.getValue(Bytes.toBytes("item"),Bytes.toBytes("idpenjual"));
			repoCart.deleteCartKue(idkue);
			
			String penjual = Bytes.toString(value);

			// closing the HTable object
			table.close();
			
			/* ----------- Mengurangi Jumlah Kue ----------------- */
			table = new HTable(config, "penjual");
			Put p = null;
			
			Get getPenjual = new Get(Bytes.toBytes(penjual));
			get.addFamily(Bytes.toBytes("market"));
			
			Result KueResult = table.get(getPenjual);
			byte [] value2 = KueResult.getValue(Bytes.toBytes("market"),Bytes.toBytes("totalkue"));
			
			int totalkue = Bytes.toInt(value2);
			totalkue -= 1;
			
			p = new Put(Bytes.toBytes(penjual));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(totalkue));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			
			/* ----------- Delete Kue ----------------- */
			table = new HTable(config, "kue");
			// Instantiating Delete class
			Delete delete = new Delete(Bytes.toBytes(idkue));
			// delete.deleteColumn(Bytes.toBytes("public"), Bytes.toBytes("name"));
			delete.deleteFamily(Bytes.toBytes("item"));
			delete.deleteFamily(Bytes.toBytes("market"));
			delete.deleteFamily(Bytes.toBytes("details"));
			// deleting the data
			table.delete(delete);

			// closing the HTable object
			table.close();
			System.out.println("data deleted.....");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		}
		return false;
	}

	public synchronized boolean kueMelewatiBestUse(Kue kue) {
		try {
			HTable table = new HTable(config,"kue");

			Put p = new Put(Bytes.toBytes(kue.getIdkue()));
			p.addColumn(Bytes.toBytes("item"), Bytes.toBytes("layakjual"), Bytes.toBytes(kue.getLayakjual()));
			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			System.out.println("data updated.....");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}

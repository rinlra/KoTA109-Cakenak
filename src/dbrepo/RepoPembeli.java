package dbrepo;

import java.io.IOException;
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

import Utils.SHA;
import model.Pembeli;

public class RepoPembeli {

	Configuration config = HBaseConfiguration.create();

	public RepoPembeli() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

	public ArrayList<Pembeli> getPembeli() throws IOException {

		ArrayList<Pembeli> resultList = new ArrayList<>();
		HTable table = new HTable(config, "pembeli");

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value8 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value9 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value10 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value11 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value12 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
			byte[] value13 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
			byte[] value14 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value15 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value16 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("cash"));
			byte[] value17 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));

			String idpembeli = Bytes.toString(result.getRow());
			String nama = Bytes.toString(value1);
			String alamat = Bytes.toString(value2);
			String kota = Bytes.toString(value3);
			String provinsi = Bytes.toString(value4);
			String kodepos = Bytes.toString(value5);
			String notelp = Bytes.toString(value6);
			String username = Bytes.toString(value7);
			String password = Bytes.toString(value8);
			String email = Bytes.toString(value9);
			String filegbrakun = Bytes.toString(value10);
			String tglakundibuat = Bytes.toString(value11);
			String idcart = Bytes.toString(value12);
			String idtransaksi = Bytes.toString(value13);
			String tipeakun = Bytes.toString(value14);
			String norekening = Bytes.toString(value15);
			double cash = Bytes.toDouble(value16);
			int totalkuedibeli = Bytes.toInt(value17);
			
//			Printing the values
			
			Pembeli pembeli = new Pembeli(nama, alamat, kota, provinsi, kodepos, notelp, username, password, email, 
					filegbrakun, tglakundibuat, idcart, idtransaksi, tipeakun, norekening, cash, totalkuedibeli);
			pembeli.setIdpembeli(idpembeli);
			resultList.add(pembeli);
		}
		scanner.close();

		return resultList;
	}

	public Pembeli getPembeliById(String id) throws IOException {
		Pembeli pembeli = null;

		HTable table = new HTable(config, "pembeli");

		Get get = new Get(Bytes.toBytes(id));
		
		
		
		Result result = table.get(get);
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));

		// Reading values from scan result
		if (result != null) {
			pembeli = new Pembeli();
			
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value8 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value9 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value10 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value11 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value12 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
			byte[] value13 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
			byte[] value14 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value15 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value16 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("cash"));
			byte[] value17 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
			System.out.println("pembeliId: " + Bytes.toString(value1) + " nama pembeli: " + Bytes.toString(value2));
			//Printing the values
			
			pembeli.setNama(Bytes.toString(value1));
			pembeli.setAlamat(Bytes.toString(value2));
			pembeli.setKota(Bytes.toString(value3));
			pembeli.setProvinsi(Bytes.toString(value4));
			pembeli.setKodepos(Bytes.toString(value5));
			pembeli.setNotelp(Bytes.toString(value6));
			pembeli.setUsername(Bytes.toString(value7));
			pembeli.setPassword(Bytes.toString(value8));
			pembeli.setEmail(Bytes.toString(value9));
			pembeli.setFilegbrakun(Bytes.toString(value10));
			pembeli.setTglakundibuat(Bytes.toString(value11));
			pembeli.setIdcart(Bytes.toString(value12));
			pembeli.setIdtransaksi(Bytes.toString(value13));
			pembeli.setTipeakun(Bytes.toString(value14));
			pembeli.setNorekening(Bytes.toString(value15));
			pembeli.setCash(Bytes.toDouble(value16));
			pembeli.setTotalkuedibeli(Bytes.toInt(value17));
			pembeli.setIdpembeli(Bytes.toString(result.getRow()));
			
		}
		table.close();

		return pembeli;
	}

	public boolean insertDataPembeli(Pembeli pembeli) throws IOException {
		
		boolean b = false;
		
		if (!cekPembeliByEmail(pembeli.getEmail()) && !cekPembeliByUsername(pembeli.getUsername())) {
			try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "pembeli");
				Put p = null;
	
				Scan scan = new Scan();
				String Row = null;
				ResultScanner scanner = hTable.getScanner(scan);
				for (Result result = scanner.next(); result != null; result = scanner.next()) {
					Row = Bytes.toString(result.getRow());
				}
				if (Row == null) {
					p = new Put(Bytes.toBytes("rowPembeli0"));
				} else {
					String[] parts = Row.split("rowPembeli");
					int data = Integer.parseInt(parts[1]);
					// System.out.println(data + 1);
	
					// Instantiating Put class
					// accepts a row name.
					p = new Put(Bytes.toBytes("rowPembeli" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"), Bytes.toBytes(pembeli.getNama()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(pembeli.getAlamat()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"), Bytes.toBytes(pembeli.getKota()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"), Bytes.toBytes(pembeli.getProvinsi()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"), Bytes.toBytes(pembeli.getKodepos()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(pembeli.getNotelp()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(pembeli.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"), Bytes.toBytes(SHA.encrypt(pembeli.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"), Bytes.toBytes(pembeli.getEmail()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"), Bytes.toBytes(pembeli.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"), Bytes.toBytes(pembeli.getTglakundibuat()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"), Bytes.toBytes(pembeli.getIdcart()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"), Bytes.toBytes(pembeli.getIdtransaksi()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"),  Bytes.toBytes(pembeli.getTipeakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(pembeli.getNorekening()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"), Bytes.toBytes(pembeli.getCash()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"), Bytes.toBytes(pembeli.getTotalkuedibeli()));   
			
			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
			b = true;
		} catch (IOException e) {
			// TODO: handle exception
			return b;
		}
		}
		return b;
	}

	public boolean deletepembeli(String idpembeli) {

		try {
			//untuk delete transaksi jika perlu
//			//tambah delete all transaksi
//			HTable table = new HTable(config, "transaksi");
//
//			List<Filter> filters = new ArrayList<Filter>();
//
//			//Filter
//			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("idpembeli")
//		            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(idpembeli)));
//		    colValFilter.setFilterIfMissing(false);
//		    filters.add(colValFilter);
//			System.out.println(idpembeli);
//
//			FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
//			
//			// Instantiating the Scan class
//			Scan scan = new Scan();
//			
//			// Scanning the required columns
//			scan.addFamily(Bytes.toBytes("item"));
//			scan.addFamily(Bytes.toBytes("market"));
//			scan.addFamily(Bytes.toBytes("details"));
//			scan.setFilter(fl);
//
//			// Getting the scan result
//			ResultScanner scanner = table.getScanner(scan);
//			// Reading values from scan result
//			for (Result result = scanner.next(); result != null; result = scanner.next()) {
//				String idtransaksi = Bytes.toString(result.getRow());
//				deletetransaksi(idtransaksi);
//			}
//			scanner.close();
			
			HTable table = new HTable(config, "pembeli");
			// Instantiating Delete class
			Delete delete = new Delete(Bytes.toBytes(idpembeli));
			delete.deleteFamily(Bytes.toBytes("personal"));
			delete.deleteFamily(Bytes.toBytes("market"));
			delete.deleteFamily(Bytes.toBytes("details"));
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

	public boolean updatePembeli(Pembeli pembeli) throws IOException {
		
		boolean b = false;
		
			try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "pembeli");
				Put p = new Put(Bytes.toBytes(pembeli.getIdpembeli()));
	
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"), Bytes.toBytes(pembeli.getNama()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(pembeli.getAlamat()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"), Bytes.toBytes(pembeli.getKota()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"), Bytes.toBytes(pembeli.getProvinsi()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"), Bytes.toBytes(pembeli.getKodepos()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(pembeli.getNotelp()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(pembeli.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"), Bytes.toBytes(SHA.encrypt(pembeli.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"), Bytes.toBytes(pembeli.getEmail()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"), Bytes.toBytes(pembeli.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"), Bytes.toBytes(pembeli.getTglakundibuat()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"), Bytes.toBytes(pembeli.getIdcart()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"), Bytes.toBytes(pembeli.getIdtransaksi()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"),  Bytes.toBytes(pembeli.getTipeakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(pembeli.getNorekening()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"), Bytes.toBytes(pembeli.getCash()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"), Bytes.toBytes(pembeli.getTotalkuedibeli()));   
			
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

	
	public Pembeli signInPembeli(String username, String password) throws IOException {
		Pembeli pembeli = null;

		HTable table = new HTable(config, "pembeli");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("username")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(username)));
	    colValFilter.setFilterIfMissing(false);
	    SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("password")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(SHA.encrypt(password))));
	    colValFilter2.setFilterIfMissing(false);
	    filters.add(colValFilter);
	    filters.add(colValFilter2);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			pembeli = new Pembeli();
			
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value8 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value9 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value10 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value11 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value12 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
			byte[] value13 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
			byte[] value14 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value15 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value16 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("cash"));
			byte[] value17 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
			System.out.println("pembeliId: " + Bytes.toString(value1) + " nama pembeli: " + Bytes.toString(value2));
			//Printing the values
			
			pembeli.setNama(Bytes.toString(value1));
			pembeli.setAlamat(Bytes.toString(value2));
			pembeli.setKota(Bytes.toString(value3));
			pembeli.setProvinsi(Bytes.toString(value4));
			pembeli.setKodepos(Bytes.toString(value5));
			pembeli.setNotelp(Bytes.toString(value6));
			pembeli.setUsername(Bytes.toString(value7));
			pembeli.setPassword(Bytes.toString(value8));
			pembeli.setEmail(Bytes.toString(value9));
			pembeli.setFilegbrakun(Bytes.toString(value10));
			pembeli.setTglakundibuat(Bytes.toString(value11));
			pembeli.setIdcart(Bytes.toString(value12));
			pembeli.setIdtransaksi(Bytes.toString(value13));
			pembeli.setTipeakun(Bytes.toString(value14));
			pembeli.setNorekening(Bytes.toString(value15));
			pembeli.setCash(Bytes.toDouble(value16));
			pembeli.setTotalkuedibeli(Bytes.toInt(value17));
			pembeli.setIdpembeli(Bytes.toString(result.getRow()));
			
			
		}
		scanner.close();

		return pembeli;
	}

	
	public Pembeli getPembeliByUsername(String username) throws IOException {
		Pembeli pembeli = null;

		HTable table = new HTable(config, "pembeli");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("username")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(username)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("username pembeli: " + username);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			pembeli = new Pembeli();
			
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value8 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value9 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value10 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value11 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value12 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
			byte[] value13 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
			byte[] value14 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value15 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value16 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("cash"));
			byte[] value17 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
			System.out.println("pembeliId: " + Bytes.toString(value1) + " nama pembeli: " + Bytes.toString(value2));
			//Printing the values
			
			pembeli.setNama(Bytes.toString(value1));
			pembeli.setAlamat(Bytes.toString(value2));
			pembeli.setKota(Bytes.toString(value3));
			pembeli.setProvinsi(Bytes.toString(value4));
			pembeli.setKodepos(Bytes.toString(value5));
			pembeli.setNotelp(Bytes.toString(value6));
			pembeli.setUsername(Bytes.toString(value7));
			pembeli.setPassword(Bytes.toString(value8));
			pembeli.setEmail(Bytes.toString(value9));
			pembeli.setFilegbrakun(Bytes.toString(value10));
			pembeli.setTglakundibuat(Bytes.toString(value11));
			pembeli.setIdcart(Bytes.toString(value12));
			pembeli.setIdtransaksi(Bytes.toString(value13));
			pembeli.setTipeakun(Bytes.toString(value14));
			pembeli.setNorekening(Bytes.toString(value15));
			pembeli.setCash(Bytes.toDouble(value16));
			pembeli.setTotalkuedibeli(Bytes.toInt(value17));
			pembeli.setIdpembeli(Bytes.toString(result.getRow()));

			
		}
		scanner.close();

		return pembeli;
	}
	
	public Pembeli getPembeliByEmail(String email) throws IOException {
		Pembeli pembeli = null;

		HTable table = new HTable(config, "pembeli");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("email")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(email)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("email pembeli: " + email);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			pembeli = new Pembeli();
			
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value8 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value9 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value10 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value11 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value12 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
			byte[] value13 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
			byte[] value14 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value15 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value16 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("cash"));
			byte[] value17 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
			System.out.println("pembeliId: " + Bytes.toString(result.getRow()) + " nama pembeli: " + Bytes.toString(value2));
			//Printing the values
			
			pembeli.setNama(Bytes.toString(value1));
			pembeli.setAlamat(Bytes.toString(value2));
			pembeli.setKota(Bytes.toString(value3));
			pembeli.setProvinsi(Bytes.toString(value4));
			pembeli.setKodepos(Bytes.toString(value5));
			pembeli.setNotelp(Bytes.toString(value6));
			pembeli.setUsername(Bytes.toString(value7));
			pembeli.setPassword(Bytes.toString(value8));
			pembeli.setEmail(Bytes.toString(value9));
			pembeli.setFilegbrakun(Bytes.toString(value10));
			pembeli.setTglakundibuat(Bytes.toString(value11));
			pembeli.setIdcart(Bytes.toString(value12));
			pembeli.setIdtransaksi(Bytes.toString(value13));
			pembeli.setTipeakun(Bytes.toString(value14));
			pembeli.setNorekening(Bytes.toString(value15));
			pembeli.setCash(Bytes.toDouble(value16));
			pembeli.setTotalkuedibeli(Bytes.toInt(value17));
			pembeli.setIdpembeli(Bytes.toString(result.getRow()));

			
		}
		scanner.close();

		return pembeli;
	}
	
	public boolean cekPembeliByEmail(String email) throws IOException {
		boolean ada = false;

		HTable table = new HTable(config, "pembeli");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("email")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(email)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("email pembeli: " + email);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		if (scanner.next() != null) {
			ada = true;
		} else {
			ada = false;
		}
		scanner.close();
		
		return ada;
	}
	
	public boolean cekPembeliByUsername(String username) throws IOException {
		boolean ada = false;

		HTable table = new HTable(config, "pembeli");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("username")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(username)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("email pembeli: " + username);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kota"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("provinsi"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("kodepos"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idcart"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idtransaksi"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkuedibeli"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		if (scanner.next() != null) {
			ada = true;
		} else {
			ada = false;
		}
		scanner.close();
		
		return ada;
	}
	
	public boolean updateBalancePembeli(Pembeli pembeli) throws IOException {
		
		boolean b = false;
		
		if (cekPembeliByUsername(pembeli.getUsername())) {
			try {
				
				
				// Instantiating HTable class
				HTable hTable = new HTable(config, "pembeli");
				Put p = new Put(Bytes.toBytes(pembeli.getIdpembeli()));
	
				// adding values using add() method
				// accepts column family name, qualifier/row name ,value
				
				p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"), Bytes.toBytes(pembeli.getCash()));
				
				
				hTable.put(p);
				System.out.println("cash updated");
	
				// closing HTable
				hTable.close();
				b = true;
			} catch (IOException e) {
				// TODO: handle exception
				return b;
			}
		}
		return b;
	}

	public boolean updateUser(Pembeli user, String path) {
		// TODO Auto-generated method stub
		return false;
	}
}
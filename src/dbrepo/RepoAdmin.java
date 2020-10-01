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
import model.Admin;

public class RepoAdmin {

	Configuration config = HBaseConfiguration.create();

	public RepoAdmin() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}
	
	public ArrayList<Admin> getAdmin() throws IOException {
		
		Admin admin = null;
		
		ArrayList<Admin> resultList = new ArrayList<>();
		HTable table = new HTable(config, "admin");

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			admin = new Admin();
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value5 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
			byte[] value6 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

			admin.setId(Bytes.toString(result.getRow()));
			admin.setUsername(Bytes.toString(value1));
			admin.setPassword(Bytes.toString(value2));
			admin.setNama(Bytes.toString(value3));
			admin.setEmail(Bytes.toString(value4));
			admin.setTipeakun(Bytes.toString(value5));
			admin.setRevenue(Bytes.toDouble(value6));
			admin.setFilegbrakun(Bytes.toString(value7));
			
			resultList.add(admin);
		}
		scanner.close();

		return resultList;
	}
	
public ArrayList<Admin> pembeliGetAdmin() throws IOException {
		
		Admin admin = null;
		
		ArrayList<Admin> resultList = new ArrayList<>();
		HTable table = new HTable(config, "admin");

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			admin = new Admin();
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value5 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
			byte[] value6 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

			admin.setId(Bytes.toString(result.getRow()));
			admin.setUsername(Bytes.toString(value1));
			admin.setNama(Bytes.toString(value3));
			admin.setEmail(Bytes.toString(value4));
			admin.setTipeakun(Bytes.toString(value5));
			admin.setRevenue(Bytes.toDouble(value6));
			admin.setFilegbrakun(Bytes.toString(value7));
			
			resultList.add(admin);
		}
		scanner.close();

		return resultList;
	}

	public Admin getAdminById(String id) throws IOException {
		Admin admin = null;

		HTable table = new HTable(config, "admin");

		Get get = new Get(Bytes.toBytes(id));
		
		Result result = table.get(get);
		
		// Scanning the required columns
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		get.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

		// Reading values from scan result
		if (result != null) {
			admin = new Admin();
			
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value5 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
			byte[] value6 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

			admin.setId(Bytes.toString(result.getRow()));
			admin.setUsername(Bytes.toString(value1));
			admin.setPassword(Bytes.toString(value2));
			admin.setNama(Bytes.toString(value3));
			admin.setEmail(Bytes.toString(value4));
			admin.setTipeakun(Bytes.toString(value5));
			admin.setRevenue(Bytes.toDouble(value6));
			admin.setFilegbrakun(Bytes.toString(value7));
			
		}
		table.close();

		return admin;
	}

	public boolean insertDataAdmin(Admin admin) throws IOException {
		
		boolean b = false;
		
		if (!cekAdminByEmail(admin.getEmail()) && !cekAdminByUsername(admin.getUsername())) {
			try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "admin");
				Put p = null;
	
				Scan scan = new Scan();
				String Row = null;
				ResultScanner scanner = hTable.getScanner(scan);
				for (Result result = scanner.next(); result != null; result = scanner.next()) {
					Row = Bytes.toString(result.getRow());
				}
				if (Row == null) {
					p = new Put(Bytes.toBytes("rowAdmin0"));
				} else {
					String[] parts = Row.split("rowAdmin");
					int data = Integer.parseInt(parts[1]);
					// System.out.println(data + 1);
	
					// Instantiating Put class
					// accepts a row name.
					p = new Put(Bytes.toBytes("rowAdmin" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"), Bytes.toBytes(admin.getNama()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(admin.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"), Bytes.toBytes(SHA.encrypt(admin.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"), Bytes.toBytes(admin.getEmail()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"), Bytes.toBytes(admin.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"),  Bytes.toBytes(admin.getTipeakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"), Bytes.toBytes(admin.getRevenue()));  
			
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
	
	public boolean deleteadmin(String idadmin) {

		try {
			//untuk delete transaksi jika perlu
//			//tambah delete all transaksi
//			HTable table = new HTable(config, "transaksi");
//
//			List<Filter> filters = new ArrayList<Filter>();
//
//			//Filter
//			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"), Bytes.toBytes("idadmin")
//		            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(idadmin)));
//		    colValFilter.setFilterIfMissing(false);
//		    filters.add(colValFilter);
//			System.out.println(idadmin);
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
			
			HTable table = new HTable(config, "admin");
			// Instantiating Delete class
			Delete delete = new Delete(Bytes.toBytes(idadmin));
			delete.deleteFamily(Bytes.toBytes("personal"));
			delete.deleteFamily(Bytes.toBytes("market"));
			delete.deleteFamily(Bytes.toBytes("info"));
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

	public boolean updateAdmin(Admin admin) throws IOException {
		
		boolean b = false;
		
			try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "admin");
				Put p = new Put(Bytes.toBytes(admin.getId()));
	
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"), Bytes.toBytes(admin.getNama()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(admin.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"), Bytes.toBytes(SHA.encrypt(admin.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"), Bytes.toBytes(admin.getEmail()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"), Bytes.toBytes(admin.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"),  Bytes.toBytes(admin.getTipeakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"), Bytes.toBytes(admin.getRevenue()));
			
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

	
	public Admin signInAdmin(String username, String password) throws IOException {
		Admin admin = null;

		HTable table = new HTable(config, "admin");

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
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			admin = new Admin();
			
			admin = new Admin();
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value5 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
			byte[] value6 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

			admin.setId(Bytes.toString(result.getRow()));
			admin.setUsername(Bytes.toString(value1));
			admin.setPassword(Bytes.toString(value2));
			admin.setNama(Bytes.toString(value3));
			admin.setEmail(Bytes.toString(value4));
			admin.setTipeakun(Bytes.toString(value5));
			admin.setRevenue(Bytes.toDouble(value6));
			admin.setFilegbrakun(Bytes.toString(value7));
			
			
		}
		scanner.close();

		return admin;
	}

	
	public Admin getAdminByUsername(String username) throws IOException {
		Admin admin = null;

		HTable table = new HTable(config, "admin");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("username")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(username)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("username admin: " + username);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			admin = new Admin();
			
			admin = new Admin();
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value5 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
			byte[] value6 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

			admin.setId(Bytes.toString(result.getRow()));
			admin.setUsername(Bytes.toString(value1));
			admin.setPassword(Bytes.toString(value2));
			admin.setNama(Bytes.toString(value3));
			admin.setEmail(Bytes.toString(value4));
			admin.setTipeakun(Bytes.toString(value5));
			admin.setRevenue(Bytes.toDouble(value6));
			admin.setFilegbrakun(Bytes.toString(value7));

			
		}
		scanner.close();

		return admin;
	}
	
	public Admin getAdminByEmail(String email) throws IOException {
		Admin admin = null;

		HTable table = new HTable(config, "admin");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("email")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(email)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("email admin: " + email);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			admin = new Admin();
			
			admin = new Admin();
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));     
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("email"));
			byte[] value5 = result.getValue(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
			byte[] value6 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));

			admin.setId(Bytes.toString(result.getRow()));
			admin.setUsername(Bytes.toString(value1));
			admin.setPassword(Bytes.toString(value2));
			admin.setNama(Bytes.toString(value3));
			admin.setEmail(Bytes.toString(value4));
			admin.setTipeakun(Bytes.toString(value5));
			admin.setRevenue(Bytes.toDouble(value6));
			admin.setFilegbrakun(Bytes.toString(value7));

			
		}
		scanner.close();

		return admin;
	}
	
	public boolean cekAdminByEmail(String email) throws IOException {
		boolean ada = false;

		HTable table = new HTable(config, "admin");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("email")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(email)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("email admin: " + email);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
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
	
	public boolean cekAdminByUsername(String username) throws IOException {
		boolean ada = false;

		HTable table = new HTable(config, "admin");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"), Bytes.toBytes("username")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(username)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);
		System.out.println("username admin: " + username);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("nama"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("email"));
		scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
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
	
	public boolean updateRevenueAdmin(Admin admin) throws IOException {
		
		boolean b = false;
		
			try {
				// Instantiating HTable class
				HTable hTable = new HTable(config, "admin");
				Put p = new Put(Bytes.toBytes(admin.getId()));
	
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("revenue"), Bytes.toBytes(admin.getRevenue()));
			
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
}

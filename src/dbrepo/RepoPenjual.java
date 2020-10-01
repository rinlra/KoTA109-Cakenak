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
import model.Penjual;

public class RepoPenjual {

	Configuration config = HBaseConfiguration.create();

	public RepoPenjual() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

	public ArrayList<Penjual> getAllPenjual() throws IOException {

		ArrayList<Penjual> resultList = new ArrayList<>();
		HTable table = new HTable(config, "penjual");

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value9 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
			byte[] value11 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
			byte[] value12 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"));

			String idpenjual = Bytes.toString(result.getRow());
			String name = Bytes.toString(value1);
			String contact = Bytes.toString(value2);
			String address = Bytes.toString(value3);
			String username = Bytes.toString(value4);
			String password = Bytes.toString(value5);
			String filegbrakun = Bytes.toString(value6);
			String tglakundibuat = Bytes.toString(value7);
			String tipeakun = Bytes.toString(value8);
			String norekening = Bytes.toString(value9);
			int kueTotal = Bytes.toInt(value10);
			double pendapatan = Bytes.toDouble(value11);
			int statusverifikasi = Bytes.toInt(value12);

//			Printing the values
			System.out.println("id:" + idpenjual + " namapenjual:" + Bytes.toString(value1) + " Contact:"
					+ Bytes.toString(value2) + " KueTotal:" + Bytes.toInt(value10));

			Penjual penjual = new Penjual(name, contact, address, username, password, filegbrakun, tglakundibuat,
					tipeakun, norekening, kueTotal, pendapatan, statusverifikasi);
			penjual.setIdpenjual(idpenjual);
			resultList.add(penjual);
		}
		scanner.close();

		return resultList;
	}

	public ArrayList<Penjual> pembeliGetAllPenjual() throws IOException {

		Penjual penjual = null;

		ArrayList<Penjual> resultList = new ArrayList<>();
		HTable table = new HTable(config, "penjual");

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			penjual = new Penjual();

			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));

			String idpenjual = Bytes.toString(result.getRow());
			String name = Bytes.toString(value1);
			String contact = Bytes.toString(value2);
			String address = Bytes.toString(value3);
			String filegbrakun = Bytes.toString(value6);
			String tglakundibuat = Bytes.toString(value7);
			String tipeakun = Bytes.toString(value8);
			int kueTotal = Bytes.toInt(value10);

			penjual.setIdpenjual(idpenjual);
			penjual.setNamapenjual(name);
			penjual.setNotelp(contact);
			penjual.setAlamat(address);
			penjual.setFilegbrakun(filegbrakun);
			penjual.setTglakundibuat(tglakundibuat);
			penjual.setTipeakun(tipeakun);
			penjual.setTotalkue(kueTotal);
			resultList.add(penjual);
		}
		scanner.close();

		return resultList;
	}

	public ArrayList<Penjual> pembeliGetAllPenjual(int start, int limit) throws IOException {

		Penjual penjual = null;

		ArrayList<Penjual> resultList = new ArrayList<>();
		HTable table = new HTable(config, "penjual");

		// Instantiating the Scan class
		Scan scan = new Scan();

		scan.withStartRow(Bytes.toBytes("rowPenjual" + start));
		scan.withStopRow(Bytes.toBytes("rowPenjual" + limit));

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			penjual = new Penjual();

			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));

			String idpenjual = Bytes.toString(result.getRow());
			String name = Bytes.toString(value1);
			String contact = Bytes.toString(value2);
			String address = Bytes.toString(value3);
			String filegbrakun = Bytes.toString(value6);
			String tglakundibuat = Bytes.toString(value7);
			String tipeakun = Bytes.toString(value8);
			int kueTotal = Bytes.toInt(value10);

			penjual.setIdpenjual(idpenjual);
			penjual.setNamapenjual(name);
			penjual.setNotelp(contact);
			penjual.setAlamat(address);
			penjual.setFilegbrakun(filegbrakun);
			penjual.setTglakundibuat(tglakundibuat);
			penjual.setTipeakun(tipeakun);
			penjual.setTotalkue(kueTotal);
			resultList.add(penjual);
		}
		scanner.close();

		return resultList;
	}

	public boolean insertDataPenjual(Penjual penjual) {
		try {
			// Instantiating HTable class
			HTable hTable = new HTable(config, "penjual");
			Put p = null;

			Scan scan = new Scan();
			String Row = null;
			ResultScanner scanner = hTable.getScanner(scan);
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				Row = Bytes.toString(result.getRow());
			}
			if (Row == null) {
				p = new Put(Bytes.toBytes("rowPenjual0"));
			} else {
				String[] parts = Row.split("rowPenjual");
				int data = Integer.parseInt(parts[1]);
				// System.out.println(data + 1);

				// Instantiating Put class
				// accepts a row name.
				p = new Put(Bytes.toBytes("rowPenjual" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"),
					Bytes.toBytes(penjual.getNamapenjual()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(penjual.getNotelp()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(penjual.getAlamat()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(penjual.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"),
					Bytes.toBytes(SHA.encrypt(penjual.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"),
					Bytes.toBytes(penjual.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"),
					Bytes.toBytes(penjual.getTglakundibuat()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"), Bytes.toBytes(penjual.getTipeakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(penjual.getNorekening()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(penjual.getTotalkue()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"), Bytes.toBytes(penjual.getPendapatan()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"),
					Bytes.toBytes(penjual.getStatusverifikasi()));

			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
		} catch (IOException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public boolean deletepenjual(String idpenjual) {
		RepoKue repoKue = new RepoKue();

		try {
			// tambah delete all kue
			HTable table = new HTable(config, "kue");

			List<Filter> filters = new ArrayList<Filter>();

			// Filter
			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"),
					Bytes.toBytes("idpenjual"), CompareFilter.CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(idpenjual)));
			colValFilter.setFilterIfMissing(false);
			filters.add(colValFilter);
			System.out.println(idpenjual);

			FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

			// Instantiating the Scan class
			Scan scan = new Scan();

			// Scanning the required columns
			scan.addFamily(Bytes.toBytes("item"));
			scan.addFamily(Bytes.toBytes("market"));
			scan.addFamily(Bytes.toBytes("details"));
			scan.setFilter(fl);

			// Getting the scan result
			ResultScanner scanner = table.getScanner(scan);
			// Reading values from scan result
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				String idkue = Bytes.toString(result.getRow());

				deletekue(idkue);
			}
			scanner.close();

			table = new HTable(config, "penjual");
			// Instantiating Delete class
			Delete delete = new Delete(Bytes.toBytes(idpenjual));
			delete.deleteFamily(Bytes.toBytes("info"));
			delete.deleteFamily(Bytes.toBytes("personal"));
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

	public boolean deletekue(String idkue) {

		try {
			/* ----------- Get Kue ID by Kue ID ----------------- */
			HTable table = new HTable(config, "kue");

			Get get = new Get(Bytes.toBytes(idkue));
			get.addFamily(Bytes.toBytes("item"));
			// get.addFamily(Bytes.toBytes("market"));

			Result result = table.get(get);
			byte[] value = result.getValue(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"));

			String penjual = Bytes.toString(value);

			// closing the HTable object
			table.close();

			/* ----------- Mengurangi Jumlah Kue ----------------- */
			table = new HTable(config, "penjual");
			Put p = null;

			Get getPenjual = new Get(Bytes.toBytes(penjual));
			get.addFamily(Bytes.toBytes("market"));

			Result KueResult = table.get(getPenjual);
			byte[] value2 = KueResult.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));

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
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean updatePenjual(Penjual penjual) {
		try {
			HTable table = new HTable(config, "penjual");

			Put p = new Put(Bytes.toBytes(penjual.getIdpenjual()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"),
					Bytes.toBytes(penjual.getNamapenjual()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(penjual.getNotelp()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(penjual.getAlamat()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(penjual.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"),
					Bytes.toBytes(SHA.encrypt(penjual.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"),
					Bytes.toBytes(penjual.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"),
					Bytes.toBytes(penjual.getTglakundibuat()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"), Bytes.toBytes(penjual.getTipeakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(penjual.getNorekening()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(penjual.getTotalkue()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"), Bytes.toBytes(penjual.getPendapatan()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"),
					Bytes.toBytes(penjual.getStatusverifikasi()));

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

	public boolean adminUpdatePenjual(Penjual penjual) {
		try {
			HTable table = new HTable(config, "penjual");

			Put p = new Put(Bytes.toBytes(penjual.getIdpenjual()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"),
					Bytes.toBytes(penjual.getNamapenjual()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(penjual.getNotelp()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(penjual.getAlamat()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(penjual.getUsername()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"),
					Bytes.toBytes(SHA.encrypt(penjual.getPassword())));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"),
					Bytes.toBytes(penjual.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"),
					Bytes.toBytes(penjual.getTglakundibuat()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"), Bytes.toBytes(penjual.getFilegbrakun()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(penjual.getNorekening()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"),
					Bytes.toBytes(penjual.getStatusverifikasi()));

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

	public Penjual getPenjualById(String id) throws IOException {
		Penjual penjual = null;

		HTable table = new HTable(config, "penjual");

		Get get = new Get(Bytes.toBytes(id));

		Result result = table.get(get);
		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		get.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));

		// Reading values from scan result
		if (result != null) {
			penjual = new Penjual();

			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value9 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
			byte[] value11 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
			System.out.println("Id penjual: " + id + " nama penjual: " + Bytes.toString(value2));
			// Printing the values

			penjual.setNamapenjual(Bytes.toString(value1));
			penjual.setNotelp(Bytes.toString(value2));
			penjual.setAlamat(Bytes.toString(value3));
			penjual.setUsername(Bytes.toString(value4));
			penjual.setPassword(Bytes.toString(value5));
			penjual.setFilegbrakun(Bytes.toString(value6));
			penjual.setTglakundibuat(Bytes.toString(value7));
			penjual.setTipeakun(Bytes.toString(value8));
			penjual.setNorekening(Bytes.toString(value9));
			penjual.setTotalkue(Bytes.toInt(value10));
			penjual.setPendapatan(Bytes.toDouble(value11));
			penjual.setIdpenjual(id);
		}
		table.close();

		return penjual;
	}

	public Penjual signInPenjual(String username, String password) throws IOException {
		Penjual penjual = null;

		HTable table = new HTable(config, "penjual");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"),
				Bytes.toBytes("username"), CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(username)));
		colValFilter.setFilterIfMissing(false);
		SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("personal"),
				Bytes.toBytes("password"), CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(SHA.encrypt(password))));
		colValFilter2.setFilterIfMissing(false);
		filters.add(colValFilter);
		filters.add(colValFilter2);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			penjual = new Penjual();

			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value9 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
			byte[] value11 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));

			// Printing the values

			penjual.setNamapenjual(Bytes.toString(value1));
			penjual.setNotelp(Bytes.toString(value2));
			penjual.setAlamat(Bytes.toString(value3));
			penjual.setUsername(Bytes.toString(value4));
			penjual.setPassword(Bytes.toString(value5));
			penjual.setFilegbrakun(Bytes.toString(value6));
			penjual.setTglakundibuat(Bytes.toString(value7));
			penjual.setTipeakun(Bytes.toString(value8));
			penjual.setNorekening(Bytes.toString(value9));
			penjual.setTotalkue(Bytes.toInt(value10));
			penjual.setPendapatan(Bytes.toDouble(value11));
			penjual.setIdpenjual(Bytes.toString(result.getRow()));

		}
		scanner.close();

		return penjual;
	}

	public Penjual getPenjualByUsername(String username) throws IOException {
		Penjual penjual = null;

		HTable table = new HTable(config, "penjual");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"),
				Bytes.toBytes("username"), CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(username)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println("username penjual: " + username);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			penjual = new Penjual();

			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value9 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
			byte[] value11 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));

			// Printing the values

			penjual.setNamapenjual(Bytes.toString(value1));
			penjual.setNotelp(Bytes.toString(value2));
			penjual.setAlamat(Bytes.toString(value3));
			penjual.setUsername(Bytes.toString(value4));
			penjual.setPassword(Bytes.toString(value5));
			penjual.setFilegbrakun(Bytes.toString(value6));
			penjual.setTglakundibuat(Bytes.toString(value7));
			penjual.setTipeakun(Bytes.toString(value8));
			penjual.setNorekening(Bytes.toString(value9));
			penjual.setTotalkue(Bytes.toInt(value10));
			penjual.setPendapatan(Bytes.toDouble(value11));
			penjual.setIdpenjual(Bytes.toString(result.getRow()));

		}
		scanner.close();

		return penjual;
	}

	public Penjual getPenjualByEmail(String email) throws IOException {
		Penjual penjual = null;

		HTable table = new HTable(config, "penjual");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"),
				Bytes.toBytes("email"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(email)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println("email penjual: " + email);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			penjual = new Penjual();

			byte[] value1 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
			byte[] value2 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
			byte[] value3 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
			byte[] value4 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("username"));
			byte[] value5 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("password"));
			byte[] value6 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
			byte[] value7 = result.getValue(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
			byte[] value8 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
			byte[] value9 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
			byte[] value10 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
			byte[] value11 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));

			// Printing the values

			penjual.setNamapenjual(Bytes.toString(value1));
			penjual.setNotelp(Bytes.toString(value2));
			penjual.setAlamat(Bytes.toString(value3));
			penjual.setUsername(Bytes.toString(value4));
			penjual.setPassword(Bytes.toString(value5));
			penjual.setFilegbrakun(Bytes.toString(value6));
			penjual.setTglakundibuat(Bytes.toString(value7));
			penjual.setTipeakun(Bytes.toString(value8));
			penjual.setNorekening(Bytes.toString(value9));
			penjual.setTotalkue(Bytes.toInt(value10));
			penjual.setPendapatan(Bytes.toDouble(value11));
			penjual.setIdpenjual(Bytes.toString(result.getRow()));

		}
		scanner.close();

		return penjual;
	}

	public boolean cekPenjualByEmail(String email) throws IOException {
		boolean ada = false;

		HTable table = new HTable(config, "penjual");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"),
				Bytes.toBytes("email"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(email)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println("email penjual: " + email);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
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

	public boolean cekPenjualByUsername(String username) throws IOException {
		boolean ada = false;

		HTable table = new HTable(config, "penjual");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("personal"),
				Bytes.toBytes("username"), CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(username)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println("email penjual: " + username);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("notelp"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("alamat"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("username"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("password"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"));
		scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"));
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

	public boolean updatePendapatanPenjual(Penjual penjual) throws IOException {

		boolean b = false;

		if (cekPenjualByUsername(penjual.getUsername())) {
			try {

				// Instantiating HTable class
				HTable hTable = new HTable(config, "penjual");
				Put p = new Put(Bytes.toBytes(penjual.getIdpenjual()));

				// adding values using add() method
				// accepts column family name, qualifier/row name ,value

				p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("cash"), Bytes.toBytes(penjual.getPendapatan()));

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

}

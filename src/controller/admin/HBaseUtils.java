package controller.admin;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
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
import dbrepo.RepoCart;
import model.Komentar;
import model.Kue;
import model.Penjual;

public class HBaseUtils {

	DecimalFormat df = new DecimalFormat("0.#");

	Configuration config = HBaseConfiguration.create();

	public HBaseUtils() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

	public ArrayList<Penjual> getPenjual() throws IOException {

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
			// Double pendapatan = Bytes.toDouble(value11);
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

	public ArrayList<Kue> getKueById(String id) throws IOException {
		ArrayList<Kue> resultList = new ArrayList<>();
		Kue kue = null;
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"),
				Bytes.toBytes("idpenjual"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(id)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println(id);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

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
		// scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
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
			// byte[] value10 = result.getValue(Bytes.toBytes("market"),
			// Bytes.toBytes("komentar"));

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

	public List<Komentar> getKomentarKue(String idkue) throws IOException {
		List<Komentar> vKelengkapan = new ArrayList<Komentar>();
		HTable table = new HTable(config, "kue");

		Get get = new Get(Bytes.toBytes(idkue));
		get.addFamily(Bytes.toBytes("market"));

		Result result = table.get(get);
		byte[] value = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("komentar"));

		String daftarKomentar = Bytes.toString(value);

		if (daftarKomentar == null || daftarKomentar == "") {
			return null;
		}

		String[] columns = daftarKomentar.split(",");

		for (int i = 0; i < columns.length; i++) {
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes(columns[i]));
			String komentar = columns[i];
			int jumlah = Bytes.toInt(value1);

			Komentar newKomentar = new Komentar(komentar, jumlah);
			vKelengkapan.add(newKomentar);
		}

		// closing the HTable object
		table.close();
		return vKelengkapan;
	}

	public ArrayList<Kue> getKue() throws IOException {
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
		// scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));

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
			// byte[] value10 = result.getValue(Bytes.toBytes("market"),
			// Bytes.toBytes("komentar"));

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

	public boolean insertDataPenjual(String namapenjual, String notelp, String alamat, String username, String password,
			String filegbrakun, String tglakundibuat, String tipeakun, String norekening, int kuetotal,
			double pendapatan, int statusverifikasi, String newcf, String newc, String newv) {
		try {
			// Instantiating HTable class
			HTable hTable = new HTable(config, "penjual");
			TableName table = TableName.valueOf("penjual");
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
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"), Bytes.toBytes(namapenjual));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(notelp));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(alamat));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(username));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("password"), Bytes.toBytes(SHA.encrypt(password)));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"), Bytes.toBytes(filegbrakun));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"), Bytes.toBytes(tglakundibuat));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"), Bytes.toBytes(tipeakun));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(norekening));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(kuetotal));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("pendapatan"), Bytes.toBytes(pendapatan));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"), Bytes.toBytes(statusverifikasi));
			if (newcf != null && newc != null && newv != null) {

				System.out.println(newcf + " " + newc + " " + newv);
				HBaseAdmin admin = new HBaseAdmin(config);

				// Instantiating columnDescriptor class
				HColumnDescriptor columnDescriptor = new HColumnDescriptor(newcf);

				// Adding column family
				admin.disableTable(table);
				admin.addColumn(table, columnDescriptor);
				admin.enableTable(table);
				admin.close();
				System.out.println("coloumn added");
				p.add(Bytes.toBytes(newcf), Bytes.toBytes(newc), Bytes.toBytes(newv));

			}

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

	public boolean insertDataKue(Kue kue) {
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

			p.add(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"), Bytes.toBytes(kue.getIdpenjual()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("namakue"), Bytes.toBytes(kue.getNamakue()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("jenis"), Bytes.toBytes(kue.getJenis()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("harga"), Bytes.toBytes(kue.getHarga()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"), Bytes.toBytes(kue.getGambarkue()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"), Bytes.toBytes(kue.getPhysicalstock()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("availablestock"), Bytes.toBytes(kue.getAvailablestock()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("berat"), Bytes.toBytes(kue.getBerat()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"), Bytes.toBytes(kue.getDeskripsi()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"), Bytes.toBytes(kue.getTglproduksi()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"), Bytes.toBytes(kue.getTglbaiksblm()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"), Bytes.toBytes(kue.getWaktudiinput()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("layakjual"), Bytes.toBytes(kue.getLayakjual()));
			// p.aBytes.toBytes("market"), Bytes.toBytes("komentar"));
			// Bytes.toBytes(floornumber));
			// p.add(Bytes.toBytes("private"), Bytes.toBytes("totalKueArea"),
			// Bytes.toBytes(totalkuearea));
			// p.add(Bytes.toBytes("private"), Bytes.toBytes("totalJenisArea"),
			// Bytes.toBytes(totaljenisarea));
			// Saving the put Instance to the HTable.
			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();

			// Menambah Jumlah Kue
			HTable table = new HTable(config, "penjual");

			Get get = new Get(Bytes.toBytes(kue.getIdpenjual()));
			get.addFamily(Bytes.toBytes("market"));

			Result result = table.get(get);
			byte[] value = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("totalkue"));

			int kuetotal = Bytes.toInt(value);
			kuetotal += 1;

			p = new Put(Bytes.toBytes(kue.getIdpenjual()));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(kuetotal));

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

	public boolean deletepenjual(String idpenjual) {

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
		RepoCart repoCart = new RepoCart();
		if (idkue != null) {
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
				repoCart.deleteCartKue(idkue);
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
				p.add(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(totalkue));

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

	public boolean updatePenjual(String idpenjual, String namapenjual, String notelp, String alamat, String username,
			String password, String filegbrakun, String tglakundibuat, String tipeakun, String norekening,
			int kuetotal) {
		try {
			HTable table = new HTable(config, "penjual");

			Put p = new Put(Bytes.toBytes(idpenjual));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("namapenjual"), Bytes.toBytes(namapenjual));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("notelp"), Bytes.toBytes(notelp));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("alamat"), Bytes.toBytes(alamat));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("username"), Bytes.toBytes(username));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("password"), Bytes.toBytes(password));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("filegbrakun"), Bytes.toBytes(filegbrakun));
			p.add(Bytes.toBytes("personal"), Bytes.toBytes("tglakundibuat"), Bytes.toBytes(tglakundibuat));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("tipeakun"), Bytes.toBytes(tipeakun));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("norekening"), Bytes.toBytes(norekening));
			p.add(Bytes.toBytes("market"), Bytes.toBytes("totalkue"), Bytes.toBytes(kuetotal));

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

	public boolean updatekue(Kue kue, int oldphysicalstock) {
		try {
			HTable table = new HTable(config, "kue");

			Put p = new Put(Bytes.toBytes(kue.getIdkue()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("idpenjual"), Bytes.toBytes(kue.getIdpenjual()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("namakue"), Bytes.toBytes(kue.getNamakue()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("jenis"), Bytes.toBytes(kue.getJenis()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("harga"), Bytes.toBytes(kue.getHarga()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("gambarkue"), Bytes.toBytes(kue.getGambarkue()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("physicalstock"), Bytes.toBytes(kue.getPhysicalstock()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("availablestock"),
					Bytes.toBytes(kue.getPhysicalstock() - (oldphysicalstock - kue.getAvailablestock())));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("berat"), Bytes.toBytes(kue.getBerat()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("deskripsi"), Bytes.toBytes(kue.getDeskripsi()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("tglproduksi"), Bytes.toBytes(kue.getTglproduksi()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("tglbaiksblm"), Bytes.toBytes(kue.getTglbaiksblm()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("waktudiinput"), Bytes.toBytes(kue.getWaktudiinput()));
			p.add(Bytes.toBytes("item"), Bytes.toBytes("layakjual"), Bytes.toBytes(kue.getLayakjual()));
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

	public boolean insertKomentar(String kueid, String namaKomentar, int jumlah) {
		try {
			HTable table = new HTable(config, "kue");
			namaKomentar = namaKomentar.toLowerCase();

			Get get = new Get(Bytes.toBytes(kueid));
			get.addFamily(Bytes.toBytes("private"));

			Result result = table.get(get);
			byte[] value = result.getValue(Bytes.toBytes("private"), Bytes.toBytes("daftarKomentar"));

			String daftarKomentar = Bytes.toString(value);

			if (daftarKomentar == null) {
				daftarKomentar = namaKomentar.concat(",");
			} else {
				daftarKomentar = daftarKomentar.concat(namaKomentar + ",");
			}

			Put p = new Put(Bytes.toBytes(kueid));
			p.add(Bytes.toBytes("private"), Bytes.toBytes("daftarKomentar"), Bytes.toBytes(daftarKomentar));
			p.add(Bytes.toBytes("private"), Bytes.toBytes(namaKomentar), Bytes.toBytes(jumlah));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			System.out.println("komentar inserted.....");
		} catch (IOException e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public ArrayList<Penjual> getPenjualByJumlah(int jumlah) throws IOException {
		ArrayList<Penjual> resultList = new ArrayList<>();
		HTable table = new HTable(config, "penjual");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"),
				Bytes.toBytes("totalkue"), CompareFilter.CompareOp.GREATER_OR_EQUAL,
				new BinaryComparator(Bytes.toBytes(jumlah)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);

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
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("statusverifikasi"));
		scan.setFilter(fl);

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
//			System.out.println("Name:" + Bytes.toString(value1) + " Address:" + Bytes.toString(value2) + " Contact:"
//					+ Bytes.toString(value3) + " KueTotal:" + Bytes.toInt(value4));
//			// Printing the values
			String vpenjualid = Bytes.toString(result.getRow());
			String vname = Bytes.toString(value1);
			String vcontact = Bytes.toString(value2);
			String vaddress = Bytes.toString(value3);
			String vusername = Bytes.toString(value4);
			String vpassword = Bytes.toString(value5);
			String vfilegbrakun = Bytes.toString(value6);
			String vtglakundibuat = Bytes.toString(value7);
			String vtipeakun = Bytes.toString(value8);
			String vnorekening = Bytes.toString(value9);
			int vkueTotal = Bytes.toInt(value10);
			double pendapatan = Bytes.toDouble(value11);
			int statusverifikasi = Bytes.toInt(value12);

			Penjual penjual = new Penjual(vname, vcontact, vaddress, vusername, vpassword, vfilegbrakun, vtglakundibuat,
					vtipeakun, vnorekening, vkueTotal, pendapatan, statusverifikasi);
			penjual.setIdpenjual(vpenjualid);
			resultList.add(penjual);
		}
		scanner.close();

		return resultList;
	}

	public List<Kue> getKueByJenis(int jenisType) throws IOException {
		List<Kue> resultList = new ArrayList<Kue>();
		Kue kue = null;
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"),
				Bytes.toBytes("jenis"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(jenisType)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println(jenisType);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

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
		// scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
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
			// byte[] value10 = result.getValue(Bytes.toBytes("market"),
			// Bytes.toBytes("komentar"));

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

	public List<Kue> getKueByBerat(float berat) throws IOException {
		List<Kue> resultList = new ArrayList<Kue>();
		Kue kue = null;
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"),
				Bytes.toBytes("berat"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(berat)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		System.out.println(berat);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

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
		// scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
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
			// byte[] value10 = result.getValue(Bytes.toBytes("market"),
			// Bytes.toBytes("komentar"));

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

	public List<Kue> getKueByHarga(float min, float max) throws IOException {
		List<Kue> resultList = new ArrayList<Kue>();
		Kue kue = null;

		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("item"),
				Bytes.toBytes("harga"), CompareFilter.CompareOp.GREATER_OR_EQUAL,
				new BinaryComparator(Bytes.toBytes(min)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);
		SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("item"),
				Bytes.toBytes("harga"), CompareFilter.CompareOp.LESS_OR_EQUAL,
				new BinaryComparator(Bytes.toBytes(max)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter2);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

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
		// scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
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
			// byte[] value10 = result.getValue(Bytes.toBytes("market"),
			// Bytes.toBytes("komentar"));

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

	public List<Kue> getKueByKomentar(String[] komentar) throws IOException {
		List<Kue> resultList = new ArrayList<Kue>();
		Kue kue = null;
		HTable table = new HTable(config, "kue");

		List<Filter> filters = new ArrayList<Filter>();

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Filter
		SingleColumnValueFilter colValFilter;
		for (int i = 0; i < komentar.length; i++) {
			System.out.println(komentar[i]);
			colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes(komentar[i]),
					CompareFilter.CompareOp.GREATER, new BinaryComparator(Bytes.toBytes(0)));
			colValFilter.setFilterIfMissing(true);
			filters.add(colValFilter);
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes(komentar[i]));
		}

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

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
		// scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("komentar"));
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
			// byte[] value10 = result.getValue(Bytes.toBytes("market"),
			// Bytes.toBytes("komentar"));

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
}
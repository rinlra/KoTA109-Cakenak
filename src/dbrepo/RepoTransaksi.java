package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import Utils.TimestampCreator;
import model.Pembeli;
import model.Transaksi;

/**
 *
 * @KoTA109
 */
public class RepoTransaksi {

	Configuration config = HBaseConfiguration.create();

	TimestampCreator tc = new TimestampCreator();

	public RepoTransaksi() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

	public ArrayList<Transaksi> getUserHistory(String idpembeli) throws IOException {
		Transaksi transaksi = null;

		ArrayList<Transaksi> daftartx = new ArrayList<Transaksi>();

		HTable table = new HTable(config, "transaksi");

		List<Filter> filters = new ArrayList<Filter>();

		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("details"),
				Bytes.toBytes("idpembeli"), CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(idpembeli)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idkue"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("quantity"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {

			transaksi = new Transaksi();

			byte[] value1 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("norekening"));
			byte[] value2 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"));
			byte[] value3 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idkue"));
			byte[] value4 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"));
			byte[] value5 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"));
			byte[] value6 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"));
			byte[] value7 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("quantity"));
			byte[] value8 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"));
			byte[] value9 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"));

			transaksi.setIdtransaksi(Bytes.toString(result.getRow()));
			transaksi.setNorekening(Bytes.toString(value1));
			transaksi.setIdpembeli(Bytes.toString(value2));
			transaksi.setIdkue(Bytes.toString(value3));
			transaksi.setIdpengiriman(Bytes.toString(value4));
			transaksi.setTglpembelian(Bytes.toString(value5));
			transaksi.setTotaltransaksi(Bytes.toDouble(value6));
			transaksi.setQuantity(Bytes.toInt(value7));
			transaksi.setStatussellerpaid(Bytes.toInt(value8));
			transaksi.setStatustransaksi(Bytes.toInt(value9));

			daftartx.add(transaksi);
		}
		scanner.close();

		table.close();

		return daftartx;
	}

	public Transaksi getSellerHistory(String idkue) throws IOException {
		Transaksi transaksi = null;

		ArrayList<Transaksi> daftartx = new ArrayList<Transaksi>();

		HTable table = new HTable(config, "transaksi");

		List<Filter> filters = new ArrayList<Filter>();

		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("details"),
				Bytes.toBytes("idkue"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(idkue)));
		colValFilter.setFilterIfMissing(false);
		filters.add(colValFilter);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idkue"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("quantity"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {

			transaksi = new Transaksi();

			byte[] value2 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"));
			byte[] value3 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idkue"));
			byte[] value4 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"));
			byte[] value5 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"));
			byte[] value6 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"));
			byte[] value7 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("quantity"));
			byte[] value8 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"));
			byte[] value9 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"));

			transaksi.setIdtransaksi(Bytes.toString(result.getRow()));
			transaksi.setIdpembeli(Bytes.toString(value2));
			transaksi.setIdkue(Bytes.toString(value3));
			transaksi.setIdpengiriman(Bytes.toString(value4));
			transaksi.setTglpembelian(Bytes.toString(value5));
			transaksi.setTotaltransaksi(Bytes.toDouble(value6));
			transaksi.setQuantity(Bytes.toInt(value7));
			transaksi.setStatussellerpaid(Bytes.toInt(value8));
			transaksi.setStatustransaksi(Bytes.toInt(value9));

		}
		scanner.close();

		table.close();

		return transaksi;
	}

	public ArrayList<Transaksi> getAllHistory() throws IOException {

		ArrayList<Transaksi> resultList = new ArrayList<>();

		Transaksi transaksi = null;

		HTable table = new HTable(config, "transaksi");

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("norekening"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idkue"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("quantity"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"));
		scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"));

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);

		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {

			transaksi = new Transaksi();

			byte[] value1 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("norekening"));
			byte[] value2 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"));
			byte[] value3 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idkue"));
			byte[] value4 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"));
			byte[] value5 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"));
			byte[] value6 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"));
			byte[] value7 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("quantity"));
			byte[] value8 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"));
			byte[] value9 = result.getValue(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"));

			transaksi.setIdtransaksi(Bytes.toString(result.getRow()));
			transaksi.setNorekening(Bytes.toString(value1));
			transaksi.setIdpembeli(Bytes.toString(value2));
			transaksi.setIdkue(Bytes.toString(value3));
			transaksi.setIdpengiriman(Bytes.toString(value4));
			transaksi.setTglpembelian(Bytes.toString(value5));
			transaksi.setTotaltransaksi(Bytes.toDouble(value6));
			transaksi.setQuantity(Bytes.toInt(value7));
			transaksi.setStatussellerpaid(Bytes.toInt(value8));
			transaksi.setStatustransaksi(Bytes.toInt(value9));

			resultList.add(transaksi);
		}
		scanner.close();

		table.close();

		return resultList;
	}

	public boolean addUserHistory(Transaksi s) throws IOException {
		String ts = Objects.toString(tc.getCurTmstmp());
		boolean b = false;
		int rowcount = 0;
		Connection connection = ConnectionFactory.createConnection(config);
		Table hTable = connection.getTable(TableName.valueOf("transaksi"));
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
				p = new Put(Bytes.toBytes("rowTransaksi0"));
			} else {
				String[] parts = Row.split("rowTransaksi");
				int data = Integer.parseInt(parts[1]);
				// System.out.println(data + 1);

				// Instantiating Put class
				// accepts a row name.
				// System.out.println("data : " + data);
				p = new Put(Bytes.toBytes("rowTransaksi" + (data + 1)));
			}

			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("norekening"), Bytes.toBytes(s.getNorekening()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpembeli"), Bytes.toBytes(s.getIdpembeli()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idkue"), Bytes.toBytes(s.getIdkue()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("idpengiriman"), Bytes.toBytes(s.getIdpengiriman()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("tglpembelian"), Bytes.toBytes(s.getTglpembelian()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("totaltransaksi"),
					Bytes.toBytes(s.getTotaltransaksi()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("quantity"), Bytes.toBytes(s.getQuantity()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"),
					Bytes.toBytes(s.getStatussellerpaid()));
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statustransaksi"),
					Bytes.toBytes(s.getStatustransaksi()));

			hTable.put(p);
			System.out.println("data transaksi inserted");

			// closing HTable
			connection.close();
			hTable.close();
			b = true;
		} catch (IOException e) {
			// TODO: handle exception
			connection.close();
			hTable.close();
			return b;
		} finally {
			connection.close();
			hTable.close();
		}

		return b;

	}

	public boolean updateTransaksiPembeli(Transaksi transaksi) throws IOException {

		boolean b = false;
		HTable hTable = new HTable(config, "transaksi");
		try {
			// Instantiating HTable class

			Put p = new Put(Bytes.toBytes(transaksi.getIdtransaksi()));

			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("details"), Bytes.toBytes("statussellerpaid"),
					Bytes.toBytes(transaksi.getStatussellerpaid()));

			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
			b = true;
		} catch (IOException e) {
			// TODO: handle exception
			hTable.close();
			return b;
		} finally {
			hTable.close();
		}
		return b;
	}

}

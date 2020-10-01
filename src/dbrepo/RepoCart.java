package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
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

import model.Cart;
import model.CartItem;
import model.Kue;

public class RepoCart {

	Configuration config = HBaseConfiguration.create();

	public RepoCart() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

	public ArrayList<Cart> getUserCart(String idpembeli) throws IOException {
		ArrayList<Cart> resultList = new ArrayList<>();

		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));
		try {

			List<Filter> filters = new ArrayList<Filter>();

			// Filter
			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"),
					Bytes.toBytes("idpembeli"), CompareFilter.CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(idpembeli)));
			colValFilter.setFilterIfMissing(false);
			filters.add(colValFilter);
			// System.out.println("id pembeli: " + idpembeli);

			FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

			// Instantiating the Scan class
			Scan scan = new Scan();

			// Scanning the required columns
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

			scan.setFilter(fl);

			// Getting the scan result
			ResultScanner scanner = table.getScanner(scan);
			// Reading values from scan result
			for (Result result = scanner.next(); result != null; result = scanner.next()) {

				Cart cart = new Cart();

				byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
				byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
				byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

				// System.out.println("pembeliId: " + Bytes.toString(value1) + " nama pembeli: "
				// + Bytes.toString(value2));
				// Printing the values

				cart.setIdpembeli(Bytes.toString(value1));
				cart.setIdkue(Bytes.toString(value2));
				cart.setQuantity(Bytes.toInt(value3));
				cart.setIdcart(Bytes.toString(result.getRow()));

				cart.setIdpembeli(idpembeli);
				resultList.add(cart);
			}
			scanner.close();
		} catch (IOException ex) {
			table.close();
			connection.close();
			ex.printStackTrace();
			return null;
		} finally {
			table.close();
			connection.close();
		}

		return resultList;
	}

	public boolean addCart(Cart cart) throws IOException {

		boolean b = false;
		Connection connection = ConnectionFactory.createConnection(config);
		Table hTable = connection.getTable(TableName.valueOf("cart"));
		try {
			// System.out.println("search untuk kue id : " + cart.getIdkue());
			int search = search(cart.getIdkue(), cart.getIdpembeli());
			// System.out.println("Search -- " + search);
			if (search != 0) {
				// System.out.println("In edit");
				hTable.close();
				return editQuantity(search + cart.getQuantity(), cart.getIdpembeli(), cart.getIdkue());
			}
			// System.out.println("Not Edit");

			// Instantiating HTable class

			Put p = null;

			Scan scan = new Scan();

			String Row = null;
			ResultScanner scanner = hTable.getScanner(scan);
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				Row = Bytes.toString(result.getRow());
			}
			if (Row == null) {
				p = new Put(Bytes.toBytes("rowCart0"));
			} else {
				String[] parts = Row.split("rowCart");
				int data = Integer.parseInt(parts[1]);
				// System.out.println(data + 1);

				// Instantiating Put class
				// accepts a row name.
				// System.out.println("data : " + data);
				p = new Put(Bytes.toBytes("rowCart" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"), Bytes.toBytes(cart.getIdpembeli()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"), Bytes.toBytes(cart.getIdkue()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"), Bytes.toBytes(cart.getQuantity()));

			hTable.put(p);
			System.out.println("data inserted");

			// closing HTable
			hTable.close();
			b = true;
		} catch (IOException ex) {
			hTable.close();
			connection.close();
			ex.printStackTrace();
			return b;
		} finally {
			hTable.close();
			connection.close();
		}
		return b;
	}

	private int search(String idkue, String idpembeli) throws IOException {

		ArrayList<Cart> resultList = new ArrayList<Cart>();
		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));

		int qu = 0;
		try {
			List<Filter> filters = new ArrayList<Filter>();

			// Filter
			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"),
					Bytes.toBytes("idpembeli"), CompareFilter.CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(idpembeli)));
			colValFilter.setFilterIfMissing(false);
			SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("market"),
					Bytes.toBytes("idkue"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(idkue)));
			colValFilter2.setFilterIfMissing(false);
			filters.add(colValFilter);
			filters.add(colValFilter2);

			FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

			// Instantiating the Scan class
			Scan scan = new Scan();

			// Scanning the required columns
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
			scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"));
			scan.setFilter(fl);

			// Getting the scan result
			ResultScanner scanner = table.getScanner(scan);
			// Reading values from scan result
			for (Result result = scanner.next(); result != null; result = scanner.next()) {

				byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
				byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
				byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

				// System.out.println("idcart: " + Bytes.toString(result.getRow()) + " id kue: "
				// + Bytes.toString(value2));
				// Printing the values

				qu = Bytes.toInt(value3);

			}

			scanner.close();
		} catch (IOException ex) {
			table.close();
			connection.close();
			ex.printStackTrace();
			return qu;
		} finally {
			table.close();
			connection.close();
		}

		return qu;

	}

	private boolean editQuantity(int quantity, String idpembeli, String idkue) throws IOException {
		Cart cart = null;

		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));

		List<Filter> filters = new ArrayList<Filter>();

		// Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"),
				Bytes.toBytes("idpembeli"), CompareFilter.CompareOp.EQUAL,
				new BinaryComparator(Bytes.toBytes(idpembeli)));
		colValFilter.setFilterIfMissing(false);
		SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("market"),
				Bytes.toBytes("idkue"), CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(idkue)));
		colValFilter2.setFilterIfMissing(false);
		filters.add(colValFilter);
		filters.add(colValFilter2);

		FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

		// Instantiating the Scan class
		Scan scan = new Scan();

		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			cart = new Cart();

			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

			// System.out.println("cartId: " + Bytes.toString(result.getRow()) + " id kue: "
			// + Bytes.toString(value2));
			// Printing the values

			cart.setIdpembeli(Bytes.toString(value1));
			cart.setIdkue(Bytes.toString(value2));
			cart.setQuantity(Bytes.toInt(value3));
			cart.setIdcart(Bytes.toString(result.getRow()));

		}
		scanner.close();

		try {

			Put p = new Put(Bytes.toBytes(cart.getIdcart()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"), Bytes.toBytes(idpembeli));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"), Bytes.toBytes(idkue));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"), Bytes.toBytes(quantity));

			// Saving the put Instance to the HTable.
			table.put(p);
			// closing the HTable object
			table.close();
			// System.out.println("data updated.....");
		} catch (IOException e) {
			e.printStackTrace();
			table.close();
			return false;
		}
		table.close();
		connection.close();
		return true;
	}

	public boolean deleteCart(String idcart) throws IOException {

		
		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));
		
		try {
			
			// Instantiating Delete class
			Delete delete = new Delete(Bytes.toBytes(idcart));
			delete.deleteFamily(Bytes.toBytes("market"));
			// deleting the data
			table.delete(delete);

			// closing the HTable object
			table.close();

			// System.out.println("data deleted.....");
		} catch (IOException ex) {
			table.close();
			connection.close();
			ex.printStackTrace();
			return false;
		} finally {
			table.close();
			connection.close();
		}
		return true;

	}

	public boolean deleteUserCart(String idpembeli) throws IOException {

		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));
		try {
			// tambah delete all kue

			List<Filter> filters = new ArrayList<Filter>();

			// Filter
			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"),
					Bytes.toBytes("idpembeli"), CompareFilter.CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(idpembeli)));
			colValFilter.setFilterIfMissing(false);
			filters.add(colValFilter);
			// System.out.println(idpembeli);

			FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

			// Instantiating the Scan class
			Scan scan = new Scan();

			// Scanning the required columns
			scan.addFamily(Bytes.toBytes("market"));
			scan.setFilter(fl);

			// Getting the scan result
			ResultScanner scanner = table.getScanner(scan);
			// Reading values from scan result
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				String idcart = Bytes.toString(result.getRow());
				deleteCart(idcart);
			}
			scanner.close();

			table.close();

			// System.out.println("data deleted.....");
		} catch (IOException ex) {
			table.close();
			connection.close();
			ex.printStackTrace();
			return false;
		} finally {
			table.close();
			connection.close();
		}
		return true;
	}
	
	public boolean deleteCartKue(String idkue) throws IOException {

		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));
		try {
			// tambah delete all kue

			List<Filter> filters = new ArrayList<Filter>();

			// Filter
			SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"),
					Bytes.toBytes("idkue"), CompareFilter.CompareOp.EQUAL,
					new BinaryComparator(Bytes.toBytes(idkue)));
			colValFilter.setFilterIfMissing(false);
			filters.add(colValFilter);
			// System.out.println(idpembeli);

			FilterList fl = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);

			// Instantiating the Scan class
			Scan scan = new Scan();

			// Scanning the required columns
			scan.addFamily(Bytes.toBytes("market"));
			scan.setFilter(fl);

			// Getting the scan result
			ResultScanner scanner = table.getScanner(scan);
			// Reading values from scan result
			for (Result result = scanner.next(); result != null; result = scanner.next()) {
				String idcart = Bytes.toString(result.getRow());
				deleteCart(idcart);
			}
			scanner.close();

			table.close();

			// System.out.println("data deleted.....");
		} catch (IOException ex) {
			table.close();
			connection.close();
			ex.printStackTrace();
			return false;
		} finally {
			table.close();
			connection.close();
		}
		return true;
	}

	public int getNumberOfCartsForUser(String idpembeli) throws IOException {

		int count = 0;
		ArrayList<Cart> userCart = getUserCart(idpembeli);
		for (int i = 0; i < userCart.size(); i++) {
			count += userCart.get(i).getQuantity();
			System.out.println("size ==" + userCart.size());
		}
		// System.out.println("Quantity == " + count);
		return count;
	}

	public ArrayList<CartItem> getProductFromCart(String idpembeli) {

		ArrayList<CartItem> carts = new ArrayList<CartItem>();
//    	 return carts;
		Kue kue = null;
		RepoKue repoKue = new RepoKue();
		CartItem cartProduct = null;
		ArrayList<Kue> listkue = new ArrayList<Kue>();

		List<Filter> filters = new ArrayList<Filter>();

		try {
			ArrayList<Cart> resultList = getUserCart(idpembeli);
			if (resultList != null) {
				kue = new Kue();

				for (Cart cart : resultList) {

					cartProduct = new CartItem();
					kue = repoKue.getKueById(cart.getIdkue());
					cartProduct.setIdcart(cart.getIdcart());
					cartProduct.setIdpembeli(cart.getIdpembeli());
					cartProduct.setQuantity(cart.getQuantity());
					cartProduct.setQuantity_kue(kue.getPhysicalstock());
					cartProduct.setNama(kue.getNamakue());
					cartProduct.setDeskripsi(kue.getDeskripsi());
					cartProduct.setHarga(kue.getHarga());
					cartProduct.setGambar(kue.getGambarkue());
					cartProduct.setIdkue(kue.getIdkue());
					cartProduct.setKategori(kue.getJenis());
					carts.add(cartProduct);

				}
			}
			System.out.println(carts.toString());
			return carts;

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Cart getCart(String idcart) throws IOException {
		Cart cart = null;
		ArrayList<Cart> resultList = new ArrayList<>();
		HTable table = new HTable(config, "cart");

		try {
			Get get = new Get(Bytes.toBytes(idcart));

			// Scanning the required columns
			get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
			get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
			get.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

			Result result = table.get(get);

			// Getting the scan result
			if (result != null) {

				cart = new Cart();

				byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"));
				byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("idkue"));
				byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("quantity"));

				System.out.println("idcart: " + Bytes.toString(result.getRow()) + " id kue: " + Bytes.toString(value2));
				// Printing the values

				cart.setIdpembeli(Bytes.toString(value1));
				cart.setIdkue(Bytes.toString(value2));
				cart.setQuantity(Bytes.toInt(value3));
				cart.setIdcart(Bytes.toString(result.getRow()));

			}

		} catch (IOException ex) {

			ex.printStackTrace();
		}

		return cart;
	}

	public boolean reduceQuantity(String idcart) throws IOException {
		Cart cart = getCart(idcart);
		
		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));
		if (cart != null) {
			int quantity = cart.getQuantity();
			if (quantity < 2) {
				return deleteCart(idcart);
			} else {

				try {

					Put p = new Put(Bytes.toBytes(idcart));
					p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"),
							Bytes.toBytes(cart.getIdpembeli()));
					p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"), Bytes.toBytes(cart.getIdkue()));
					p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"),
							Bytes.toBytes((cart.getQuantity() - 1)));

					// Saving the put Instance to the HTable.
					table.put(p);
					// closing the HTable object
					table.close();
					System.out.println("data updated.....");
				} catch (IOException ex) {
					table.close();
					connection.close();
					ex.printStackTrace();
					return false;
				} finally {
					table.close();
					connection.close();
				}
				return true;
			}
		}
		return false;
	}

	public boolean increaseQuantity(String idcart) throws IOException {
		Cart cart = getCart(idcart);
		RepoKue repoKue = new RepoKue();
		Kue kue = repoKue.getKueById(cart.getIdkue());
		Connection connection = ConnectionFactory.createConnection(config);
		Table table = connection.getTable(TableName.valueOf("cart"));
		if (cart != null && kue.getAvailablestock() > 0) {
			int quantity = cart.getQuantity();

			try {

				Put p = new Put(Bytes.toBytes(idcart));
				p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idpembeli"), Bytes.toBytes(cart.getIdpembeli()));
				p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("idkue"), Bytes.toBytes(cart.getIdkue()));
				p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("quantity"),
						Bytes.toBytes((cart.getQuantity() + 1)));

				// Saving the put Instance to the HTable.
				table.put(p);
				// closing the HTable object
				table.close();
				System.out.println("data updated.....");
			} catch (IOException ex) {
				table.close();
				connection.close();
				ex.printStackTrace();
				return false;
			} finally {
				table.close();
				connection.close();
			}
			return true;
		}

		return false;

	}

}

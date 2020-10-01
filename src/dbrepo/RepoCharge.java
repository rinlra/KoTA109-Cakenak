package dbrepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
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

import model.Charge;

/**
 *
 * @author Nesmaa
 */
public class RepoCharge {

	Configuration config = HBaseConfiguration.create();

	public RepoCharge() {
		config.set("hbase.zookeeper.quorum", "127.0.0.1");
	}

    public boolean checkCardValidation(String number) throws IOException {
    	
    	Charge charge = null;
    	
    	int com = 0;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("card_number")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(number)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			charge = new Charge();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("value"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("used"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("taken"));
			
			charge.setNomorkartu(Bytes.toString(value1));
			charge.setValue(Bytes.toInt(value2));
			charge.setUsed(Bytes.toInt(value3));
			charge.setTaken(Bytes.toInt(value4));
			charge.setIdcharge(Bytes.toString(result.getRow()));

			com = charge.getUsed();
		}
		scanner.close();
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
		if (com == 0) {
			return true;
		}
    	

        return false;

    }

    public boolean addCard(Charge obj) throws IOException {

        int i = 0;
        
        boolean b = false;
    	HTable hTable = new HTable(config, "charge");
    	
        try {
            System.out.println("Not Edit");
            
				// Instantiating HTable class
				
				Put p = null;
	
				Scan scan = new Scan();
				String Row = null;
				ResultScanner scanner = hTable.getScanner(scan);
				for (Result result = scanner.next(); result != null; result = scanner.next()) {
					Row = Bytes.toString(result.getRow());
				}
				if (Row == null) {
					p = new Put(Bytes.toBytes("rowCharge0"));
				} else {
					String[] parts = Row.split("rowCharge");
					int data = Integer.parseInt(parts[1]);
					// System.out.println(data + 1);
	
					// Instantiating Put class
					// accepts a row name.
					p = new Put(Bytes.toBytes("rowCharge" + (data + 1)));
			}
			// adding values using add() method
			// accepts column family name, qualifier/row name ,value
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"), Bytes.toBytes(obj.getNomorkartu()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"), Bytes.toBytes(obj.getValue()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"), Bytes.toBytes(obj.getUsed()));
			p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"), Bytes.toBytes(obj.getTaken()));
			
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

    public int getSumCardNumber(int value) throws IOException {
    	
    	Charge charge = null;
    	
    	int com = 0;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("value")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(value)));
	    colValFilter.setFilterIfMissing(false);
	    SingleColumnValueFilter colValFilter2 = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("taken")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(0)));
	    colValFilter2.setFilterIfMissing(false);
	    SingleColumnValueFilter colValFilter3 = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("used")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(0)));
	    colValFilter3.setFilterIfMissing(false);
	    filters.add(colValFilter);
	    filters.add(colValFilter2);
	    filters.add(colValFilter3);
	 
		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			com = result.size();
		}
		scanner.close();
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
		if (com == 0) {
			return com;
		}
    	
        return com;

    }

    public boolean checkCardExistForUser(String number) throws IOException {
    	
    	Charge charge = null;
    	
    	int com = 0;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("card_number")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(number)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		if (scanner.next() != null) {
			return true;
		}
		scanner.close();
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
        return false;
    }

    public int getValuefromNumber(String card_number) throws IOException {
    	
    	Charge charge = null;
    	
    	int com = 0;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("card_number")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(card_number)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			charge = new Charge();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("value"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("used"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("taken"));
			
			charge.setNomorkartu(Bytes.toString(value1));
			charge.setValue(Bytes.toInt(value2));
			charge.setUsed(Bytes.toInt(value3));
			charge.setTaken(Bytes.toInt(value4));
			charge.setIdcharge(Bytes.toString(result.getRow()));

			com = charge.getValue();
		}
		scanner.close();
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
    	return com;

    }

    public String getCard(int value) throws IOException {
    	
    	Charge charge = null;
    	
    	String com = null;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("value")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(value)));
	    colValFilter.setFilterIfMissing(false);
	    SingleColumnValueFilter colValFilter3 = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("used")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(0)));
	    colValFilter3.setFilterIfMissing(false);
	    filters.add(colValFilter);
	    filters.add(colValFilter3);
	    
		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			charge = new Charge();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("value"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("used"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("taken"));
			
			charge.setNomorkartu(Bytes.toString(value1));
			charge.setValue(Bytes.toInt(value2));
			charge.setUsed(Bytes.toInt(value3));
			charge.setTaken(Bytes.toInt(value4));
			charge.setIdcharge(Bytes.toString(result.getRow()));

			com = charge.getNomorkartu();
		}
		scanner.close();
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
    	return com;

    }

    public boolean setCardUsed(String number) throws IOException {
    	
    	Charge charge = null;
    	
    	boolean com = false;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("card_number")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(number)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			charge = new Charge();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("value"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("used"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("taken"));
			
			charge.setNomorkartu(Bytes.toString(value1));
			charge.setValue(Bytes.toInt(value2));
			charge.setUsed(Bytes.toInt(value3));
			charge.setTaken(Bytes.toInt(value4));
			charge.setIdcharge(Bytes.toString(result.getRow()));
			com = true;
		}
		scanner.close();
		
		Put p = new Put(Bytes.toBytes(charge.getIdcharge()));
		
		p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"), Bytes.toBytes(1));
		
		table.put(p);
		
		com = true;
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
    	return com;
    	

    }

    public boolean setCardTaken(String number) throws IOException {
    	
Charge charge = null;
    	
    	boolean com = false;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("card_number")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(number)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			charge = new Charge();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("value"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("used"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("taken"));
			
			charge.setNomorkartu(Bytes.toString(value1));
			charge.setValue(Bytes.toInt(value2));
			charge.setUsed(Bytes.toInt(value3));
			charge.setTaken(Bytes.toInt(value4));
			charge.setIdcharge(Bytes.toString(result.getRow()));
			com = true;
		}
		scanner.close();
		
		Put p = new Put(Bytes.toBytes(charge.getIdcharge()));
		
		p.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"), Bytes.toBytes(1));
		
		table.put(p);
		
		com = true;
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
    	return com;
    	
    }

    public boolean numberOfCardIsFound(String numbercheck) throws IOException {
    	
    	boolean com = false;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("card_number")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(numbercheck)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			
			com = true;
		}
		scanner.close();
		
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
    	return com;
    	
        
    }
    
    public int getProfit() throws IOException {
    	int profit=0;
        
    	Charge charge = null;
    	
    	int com = 0;
    	HTable table = null;
    	try {
		table = new HTable(config, "charge");

		List<Filter> filters = new ArrayList<Filter>();

		//Filter
		SingleColumnValueFilter colValFilter = new SingleColumnValueFilter(Bytes.toBytes("market"), Bytes.toBytes("taken")
	            , CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes(1)));
	    colValFilter.setFilterIfMissing(false);
	    filters.add(colValFilter);

		FilterList fl = new FilterList( FilterList.Operator.MUST_PASS_ALL, filters);
		
		// Instantiating the Scan class
		Scan scan = new Scan();
		
		// Scanning the required columns
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("value"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("used"));
		scan.addColumn(Bytes.toBytes("market"), Bytes.toBytes("taken"));
		scan.setFilter(fl);

		// Getting the scan result
		ResultScanner scanner = table.getScanner(scan);
		// Reading values from scan result
		for (Result result = scanner.next(); result != null; result = scanner.next()) {
			charge = new Charge();
			
			byte[] value1 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("card_number"));
			byte[] value2 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("value"));
			byte[] value3 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("used"));
			byte[] value4 = result.getValue(Bytes.toBytes("market"), Bytes.toBytes("taken"));
			
			charge.setNomorkartu(Bytes.toString(value1));
			charge.setValue(Bytes.toInt(value2));
			charge.setUsed(Bytes.toInt(value3));
			charge.setTaken(Bytes.toInt(value4));
			charge.setIdcharge(Bytes.toString(result.getRow()));

			com = com + charge.getValue();
		}
		scanner.close();
		table.close();
	    } catch (IOException ex) {
	    	table.close();
	        ex.printStackTrace();
	    }
    	return com;
   
     }
}

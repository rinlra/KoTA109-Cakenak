package Scheduler;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dbrepo.RepoKue;
import model.Kue;

public class DailyJob implements Runnable {

    @Override
    public void run() {
    	
    	ZoneId asiaJakarta = ZoneId.of("Asia/Jakarta");
    	
    	Instant instant = Instant.now();
    	
    	ZonedDateTime datetime = ZonedDateTime.ofInstant(instant, asiaJakarta);
    	
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    	
    	String formattedDate = datetime.format(formatter);
    	
    	LocalDate datenow = LocalDate.parse(formattedDate, formatter);
    	
        RepoKue repoKue = new RepoKue();
        ArrayList<Kue> listKue = new ArrayList<Kue>();
        int tdklayak = 0;
        try {
			listKue = repoKue.getAllKue();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        if (!listKue.isEmpty()) {
        	listKue.forEach(produk -> {
        		LocalDate bestuse = LocalDate.parse(produk.getTglbaiksblm(), formatter);
        		System.out.println("bestuse : " + bestuse + " tanggal hari ini : " + datenow );
        		if (produk.getLayakjual() == 0) {
        			System.out.println("Sudah tidak layak");
        			return;
        		}
        		else if (bestuse.equals(datenow) || bestuse.isBefore(datenow)) {
        			System.out.println("Mengubah data kue menjadi tidak layak..");
        			
        			produk.setLayakjual(tdklayak);
        			repoKue.kueMelewatiBestUse(produk);
        		}
        	});
        }
        
    }

}

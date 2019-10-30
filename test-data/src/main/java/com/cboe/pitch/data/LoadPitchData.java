package com.cboe.pitch.data;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cboe.pitch.data.util.StringParser;
import com.cboe.pitch.data.vo.PitchData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadPitchData {
	
	
	private static Map<String, List<PitchData>>  pitchMap = new HashMap<String, List<PitchData>>();	

	public static  void loadPitchData() {
		Stream<String> pitchDataStream = null;
		List<PitchData> pitchDataList= new ArrayList<PitchData>();
		try {
			pitchDataStream = Files.lines(Paths
					.get(Thread.currentThread().getContextClassLoader().getResource("pitch_example_data.txt").toURI()));
		} catch (IOException e) {			
			log.error("File IO Error"+e.getMessage());
		}
		catch (URISyntaxException e1) {
			log.error("URISyntaxException "+e1.getMessage());
		}
		
		pitchDataStream.forEach(pitchDataStr -> {
			PitchData pitchData =null;
			try {				
				pitchData =StringParser.parseString(pitchDataStr);
				if(null !=pitchData) {
				  pitchDataList.add(pitchData);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});		
	  Map<String, Long> counting = pitchDataList.stream().filter(p -> p.getStockSymbol() != null)
			  .collect(
                Collectors.groupingBy(PitchData::getStockSymbol, Collectors.counting()));   	        
        // print the sorted map
        counting.entrySet().stream().sorted((Map.Entry.<String, Long>comparingByValue().reversed())).forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));     
        pitchMap = pitchDataList
                .stream()
                .collect
                    (Collectors.groupingBy(PitchData::getOrderId));     
       
	}
	
	public static void addOrder() {		
		//read the order for orderid 5K27GA000089		
		List<PitchData> orderList = pitchMap.get("5K27GA00006A");		
		PitchData pitchData = new PitchData();
		pitchData.setTimestamp(new Date());
		pitchData.setMessageType("A");
		pitchData.setOrderId("5K27GA000089");
		pitchData.setSideIndicator("B");
		pitchData.setStockSymbol("CCE");
		pitchData.setShares("4");
		pitchData.setPrice(new BigDecimal("3454535"));		
		addToList(orderList, Stream.of(pitchData));	
		orderList.forEach(item->System.out.println(item.getMessageType()));
		modifyOrder(orderList);		
	}
	
	public static void modifyOrder(List<PitchData> orderList) {	
		orderList.stream().filter(i -> i.getMessageType().equals("A")).forEach(i -> {
			i.setMessageType("E");            
        });

	  
	}
	
	public static void main(String arg[]) throws ParseException {			
		loadPitchData();
		addOrder() ;
		
	}	
	public static<T> void addToList(List<T> target, Stream<T> source)
	{
		source.collect(Collectors.toCollection(() -> target));
	}
	
	


}

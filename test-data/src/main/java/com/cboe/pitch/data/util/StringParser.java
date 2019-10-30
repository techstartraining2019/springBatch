package com.cboe.pitch.data.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.cboe.pitch.data.vo.PitchData;

public class StringParser {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
	
	public static PitchData parseString(String input) throws ParseException
    {
		PitchData pitchData = null;	
			pitchData=new PitchData();
	         
			String timeStamp=getString(input,1, 8);			
			if(null !=timeStamp)
	        pitchData.setTimestamp(sdf.parse(timeStamp));
	        pitchData.setMessageType(getString(input,9,1));
	        pitchData.setOrderId(getString(input,10,12));	     
	        pitchData.setSideIndicator(getString(input,22,1));
	        pitchData.setShares(getString(input,23,6));
	        pitchData.setStockSymbol(getString(input,29,6));	        
	        String price=getString(input,35,10);
	        if(null != price && isNumeric(price)) {	        	
	        	pitchData.setPrice(new BigDecimal(price));
	        }
	        pitchData.setDisplay(getString(input,45,1));  
		
        return pitchData;

    }
	
	
	private static String getString(String record,int offset,int length) {
		String result;
	if (record.length() >= offset + length) {
	      result = record.substring(offset, offset + length);
	    } else if (record.length() > offset) {
	      //the field does contain data, but is not as long as the instructions tells.
	      result = record.substring(offset, record.length());			
	    } else {
	      result = null;	   
	    }		
	    return result;
	}
	
	private static boolean isNumeric(String str) { 
		for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;

		}


}

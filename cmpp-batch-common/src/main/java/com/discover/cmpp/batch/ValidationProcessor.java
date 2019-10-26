package com.discover.cmpp.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import com.discover.cmpp.batch.domain.Partner;
import com.discover.cmpp.batch.exception.UnknownGenderException;



/**
 * {@link ItemProcessor} which validates data.
 * 
 * @author vthota
 */
public class ValidationProcessor implements ItemProcessor<Partner, Partner> {

  private static final Log log = LogFactory.getLog(ValidationProcessor.class);

  @Override
  public Partner process(Partner partner) throws Exception {
    log.info(partner);
    if (!("m".equals(partner.getGender()) || ("w".equals(partner.getGender())))) {
      throw new UnknownGenderException("Gender " + partner.getGender() + " is unknown!");
    }
    return partner;
  }

}

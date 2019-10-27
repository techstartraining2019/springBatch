package com.tvc.example.ex;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.discover.cmpp.batch.model.Invoice;

@Configuration
public class InvoiceLoader extends AbstractFileLoader<Invoice> {

  private class InvoiceFieldSetMapper implements FieldSetMapper<Invoice> {
    @Override
    public Invoice mapFieldSet(FieldSet f) {
      Invoice invoice = new Invoice();
      invoice.setNo(f.readString("INVOICE_NO"));
      return invoice;
    }
  }

  @Override
  @Bean(name = "invoiceJob")
  public Job loaderJob(JobExecutionListener listener) {
    return createJob(listener);
  }

  @Override
  public FieldSetMapper<Invoice> getFieldSetMapper() {
    return new InvoiceFieldSetMapper();
  }

  @Override
  public String getFilesPath() {
    return "test";
  }

  @Override
  public String[] getColumnNames() {
    return new String[] {"INVOICE_NO", "DATE"};
  }

  @Override
  public int getChunkSize() {
    return 0;
  }

  @Override
  public int getThrottleLimit() {
    return 0;
  }
}

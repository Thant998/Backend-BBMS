package com.test.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.test.entity.Task;
import com.test.repo.TaskRepository;

import io.jsonwebtoken.io.IOException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;


@Service
public class TaskReport {
	
	@Autowired
	TaskRepository repo;
	
	public String exportPdfReport (String reportFormat,int id) throws FileNotFoundException, JRException {
	    String path="C:\\Users\\USER\\Desktop";
	    List<Task> stu=(List<Task>) repo.getAllDeleteTask(id);
	    //Load file and complie it
	    File file=ResourceUtils.getFile("classpath:bbms.jrxml");
	    JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	    JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(stu);
	    Map<String,Object> parameters=new HashMap<>();
	    parameters.put("createdBy ", "Team 404");
	    JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport, parameters,dataSource);
	    
	    
//	    export pdf
	    if(reportFormat.equalsIgnoreCase("pdf")) {
	      JasperExportManager.exportReportToPdfFile(jasperPrint, path+"\\report.pdf");
	    }
	    
	    return "report generated in path :"+path;
	  }

	  public String exportExcelReport(HttpServletResponse response,int id  ) throws JRException, IOException, FileNotFoundException{
	    String path="C:\\Users\\USER\\Desktop";
	    List<Task> stu=(List<Task>) repo.getAllDeleteTask(id);
	      System.out.println(stu.toString());
	      File file=ResourceUtils.getFile("classpath:bbms.jrxml");
	      JasperReport jasperReport=JasperCompileManager.compileReport(file.getAbsolutePath());
	      JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(stu);
	      Map<String,Object>  parameters=new HashMap<>();
	      parameters.put("createdBy ", "Team 404");
	      JasperPrint jasperPrint=JasperFillManager.fillReport(jasperReport,parameters, dataSource);
	      
	      
//	      export excel
	      JRXlsxExporter exporter = new JRXlsxExporter();
	      exporter.setExporterInput( new SimpleExporterInput(jasperPrint));
	      exporter.setExporterOutput( new SimpleOutputStreamExporterOutput(path + "\\report.xlsx" ));

	      SimpleXlsxReportConfiguration config = new SimpleXlsxReportConfiguration();
	      config.setOnePagePerSheet( true );
	      config.setDetectCellType( true );
	      exporter.setConfiguration( config );
	      exporter.exportReport();
	      
	      return "report generated in path :"+path;
	  }

}

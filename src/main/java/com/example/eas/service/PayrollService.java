package com.example.eas.service; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import com.example.eas.exception.NotEligibleException;
import com.example.eas.exception.SalaryNotCreditedException;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

@Service 
public class PayrollService {

	private static final String EXPORT_PATH = "D:/Employee Automation System/Project/Exports/"; 
	
	/* public ByteArrayResource generatePayrollPdf(String employeeCode, int month, int year) throws IOException { 
		String filename = String.format("payroll_%d_%02d.xlsx", year, month); 
		
		Path excelPath = Paths.get(EXPORT_PATH + filename); 
		
		if(!Files.exists(excelPath)) { 
			throw new FileNotFoundException("Excel file not found for given month an year."); 
		} 
		
		try(Workbook workbook = WorkbookFactory.create(excelPath.toFile())){ 
			Sheet sheet = workbook.getSheetAt(0); 
			
			Row headerRow = sheet.getRow(0); 
			
			
			Row matchedRow = null; 
			Pattern pattern = Pattern.compile("employee_salary_(\\d{4}-\\d{2}-\\d{2}\\.xlsx"); 
			
			
			
			for(Row row : sheet) { 
				Cell idCell = row.getCell(0); 
				if(idCell != null && idCell.getStringCellValue().equalsIgnoreCase(employeeCode)) { 
					matchedRow = row; 
					break; 
				} 
			} 
			
			if(matchedRow == null) { 
				throw new RuntimeException("employee not found in payroll sheet."); 
			} 
			
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			PdfWriter writer = new PdfWriter(out); 
			PdfDocument pdfDoc = new PdfDocument(writer); 
			Document document = new Document(pdfDoc); 
			
			document.add(new Paragraph("Employee Payroll Details")
					.setBold()
					.setFontSize(16)
					.setTextAlignment(TextAlignment.CENTER)); 
			
			for(int i = 0; i < headerRow.getLastCellNum(); i++) { 
				String label = headerRow.getCell(i).getStringCellValue(); 
				String value = getCellValue(matchedRow.getCell(i)); 
				document.add(new Paragraph(label + " : " + value)); 
			} 
			
			
			document.close(); 
			return new ByteArrayResource(out.toByteArray()); 
			
		} 
	} */ 
	
	public ByteArrayResource generatePayrollPdf(String employeeCode, int month, int year) throws IOException {
        File folder = new File(EXPORT_PATH);
        File[] files = folder.listFiles();

        if (files == null) {
            throw new FileNotFoundException("Export directory not found.");
        }

        File matchedFile = null;
        Pattern pattern = Pattern.compile("employee_salary_(\\d{4}-\\d{2}-\\d{2})\\.xlsx");

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();

            Matcher matcher = pattern.matcher(fileName);
            if (matcher.matches()) {
                String datePart = matcher.group(1);
                LocalDate fileDate = LocalDate.parse(datePart);

                if (fileDate.getYear() == year && fileDate.getMonthValue() == month) {
                    matchedFile = files[i];
                    break;
                }
            }
        }

        if (matchedFile == null) {
            // throw new FileNotFoundException("No Excel file found for given month and year."); 
        	throw new SalaryNotCreditedException("Salary is not credited for the selected month and year."); 
        } 

        try (Workbook workbook = WorkbookFactory.create(matchedFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);
            Row matchedRow = null;

            for (int r = 0; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row != null && row.getCell(0) != null) {
                    String cellValue = getCellValue(row.getCell(0));
                    if (employeeCode.equalsIgnoreCase(cellValue)) {
                        matchedRow = row;
                        break;
                    }
                }
            }

            if (matchedRow == null) {
                // throw new RuntimeException("Employee not found in payroll sheet.");
            	throw new NotEligibleException("Not eligible."); 
            } 

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Employee Payroll Details")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            for (int i = 0; i < headerRow.getLastCellNum(); i++) {
                String label = getCellValue(headerRow.getCell(i));
                String value = getCellValue(matchedRow.getCell(i));
                document.add(new Paragraph(label + " : " + value));
            }

            document.close();
            return new ByteArrayResource(out.toByteArray());
        }
    } 
	
	/* public ByteArrayResource managerGeneratePayrollPdf(String managerName, int month, int year) throws IOException { 
        File folder = new File(EXPORT_PATH);
        File[] files = folder.listFiles();

        if (files == null) {
            throw new FileNotFoundException("Export directory not found.");
        }

        File matchedFile = null;
        Pattern pattern = Pattern.compile("employee_salary_(\\d{4}-\\d{2}-\\d{2})\\.xlsx");

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();

            Matcher matcher = pattern.matcher(fileName);
            if (matcher.matches()) {
                String datePart = matcher.group(1);
                LocalDate fileDate = LocalDate.parse(datePart);

                if (fileDate.getYear() == year && fileDate.getMonthValue() == month) {
                    matchedFile = files[i];
                    break;
                }
            }
        }

        if (matchedFile == null) {
            // throw new FileNotFoundException("No Excel file found for given month and year."); 
        	throw new SalaryNotCreditedException("Salary is not credited for the selected month and year."); 
        } 

        try (Workbook workbook = WorkbookFactory.create(matchedFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(1);
            Row matchedRow = null;

            for (int r = 1; r <= sheet.getLastRowNum(); r++) {
                Row row = sheet.getRow(r);
                if (row != null && row.getCell(1) != null) {
                    String cellValue = getCellValue(row.getCell(0));
                    if (managerName.equalsIgnoreCase(cellValue)) {
                        matchedRow = row;
                        break;
                    }
                }
            }

            if (matchedRow == null) {
                // throw new RuntimeException("Employee not found in payroll sheet.");
            	throw new NotEligibleException("Not eligible."); 
            } 

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Employee Payroll Details")
                    .setBold()
                    .setFontSize(16)
                    .setTextAlignment(TextAlignment.CENTER));

            for (int i = 1; i < headerRow.getLastCellNum(); i++) {
                String label = getCellValue(headerRow.getCell(i));
                String value = getCellValue(matchedRow.getCell(i));
                document.add(new Paragraph(label + " : " + value));
            }

            document.close();
            return new ByteArrayResource(out.toByteArray());
        }
    } */ 
	
	public ByteArrayResource managerGeneratePayrollPdf(String managerName, int month, int year) throws IOException {
	    File folder = new File(EXPORT_PATH);
	    File[] files = folder.listFiles();

	    if (files == null) {
	        throw new FileNotFoundException("Export directory not found.");
	    }

	    File matchedFile = null;
	    Pattern pattern = Pattern.compile("employee_salary_(\\d{4}-\\d{2}-\\d{2})\\.xlsx");

	    for (File file : files) {
	        String fileName = file.getName();
	        Matcher matcher = pattern.matcher(fileName);
	        if (matcher.matches()) {
	            String datePart = matcher.group(1);
	            LocalDate fileDate = LocalDate.parse(datePart);
	            if (fileDate.getYear() == year && fileDate.getMonthValue() == month) {
	                matchedFile = file;
	                break;
	            }
	        }
	    }

	    if (matchedFile == null) {
	        throw new SalaryNotCreditedException("Salary is not credited for the selected month and year.");
	    }

	    try (Workbook workbook = WorkbookFactory.create(matchedFile)) {
	        Sheet sheet = workbook.getSheetAt(0);
	        Row headerRow = sheet.getRow(0);
	        Row matchedRow = null;

	        for (int r = 1; r <= sheet.getLastRowNum(); r++) {
	            Row row = sheet.getRow(r);
	            if (row != null && row.getCell(0) != null) {
	                String cellValue = getCellValue(row.getCell(1));
	                if (managerName.equalsIgnoreCase(cellValue)) {
	                    matchedRow = row;
	                    break;
	                }
	            }
	        }

	        if (matchedRow == null) {
	            throw new NotEligibleException("Not eligible.");
	        }

	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        PdfWriter writer = new PdfWriter(out);
	        PdfDocument pdfDoc = new PdfDocument(writer);
	        Document document = new Document(pdfDoc);

	        document.add(new Paragraph("Manager Payroll Details")
	                .setBold()
	                .setFontSize(16)
	                .setTextAlignment(TextAlignment.CENTER));

	        // Skip column index 0
	        for (int i = 1; i < headerRow.getLastCellNum(); i++) {
	            String label = getCellValue(headerRow.getCell(i));
	            String value = getCellValue(matchedRow.getCell(i));
	            document.add(new Paragraph(label + " : " + value));
	        }

	        document.close();
	        return new ByteArrayResource(out.toByteArray());
	    }
	}  

	
	private String getCellValue(Cell cell) { 
		if(cell == null) { 
			return ""; 
		} 
		switch(cell.getCellType()) { 
		case STRING : 
			return cell.getStringCellValue(); 
		case NUMERIC : 
			if(DateUtil.isCellDateFormatted(cell)) { 
				return cell.getDateCellValue().toString(); 
			} 
			else { 
				return Double.toString(cell.getNumericCellValue()); 
			} 
		case BOOLEAN : 
			return Boolean.toString(cell.getBooleanCellValue()); 
		default : 
			return ""; 
		} 
	} 
} 


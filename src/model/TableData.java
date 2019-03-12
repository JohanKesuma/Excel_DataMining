/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.FileUtil;

/**
 *
 * @author johan
 */
public class TableData {

    private DefaultTableModel tableModel;
    private Workbook workbook;

    public TableData(File file) {
        this.tableModel = new DefaultTableModel();
        initWorkbook(file);
        initTableModel();
    }

    public Sheet getSheet(int i) {
        return workbook.getSheetAt(i);
    }

    private void initWorkbook(File file) {
        FileInputStream fis;
        String fileExtension = FileUtil.getFileExtension(file);
        try {
            fis = new FileInputStream(file);
            if (fileExtension.equalsIgnoreCase(".xls")) { // untuk file excel 2003 ke bawah (.xls)
                workbook = new HSSFWorkbook(fis);
            } else if(fileExtension.equalsIgnoreCase(".xlsx")) { // untuk file excel 2007 ke atas (.xlsx)
                workbook = new XSSFWorkbook(fis);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(TableData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TableData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initTableModel() {
        Iterator<Row> rowIterator = getSheet(0).iterator();
        
        // init header
        Row headerRow = rowIterator.next();
        Object[] headers = new Object[headerRow.getPhysicalNumberOfCells()];
        for (int i = 0; i < headers.length; i++) {
            headers[i] = headerRow.getCell(i).getStringCellValue();
        }
        tableModel.setColumnIdentifiers(headers);
        //
        
        while (rowIterator.hasNext()) {
            // dapat setiap baris
            Row rowData = rowIterator.next();

            
            Object[] data = new Object[rowData.getPhysicalNumberOfCells()];
            for (int i = 0; i < rowData.getPhysicalNumberOfCells(); i++) {
                Cell currentCell = rowData.getCell(i);
                if (currentCell.getCellType() == CellType.BOOLEAN) {
                    data[i] = rowData.getCell(i).getBooleanCellValue();
                } else if (currentCell.getCellType() == CellType.STRING) {
                    data[i] = rowData.getCell(i).getStringCellValue();
                } else if (currentCell.getCellType() == CellType.NUMERIC) {
                    data[i] = rowData.getCell(i).getNumericCellValue();
                } else {
                    data[i] = rowData.getCell(i).getStringCellValue();
                }
            }
            tableModel.addRow(data);
            
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

}

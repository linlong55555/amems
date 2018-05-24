package com.eray.util.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil
{
	public static String getCellValue(Cell cell)
	{
		if (cell == null)
		{
			return null;
		}
		String value = null;
		try
		{
			switch (cell.getCellType())
			{
			case Cell.CELL_TYPE_BLANK:
				value = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				value = String.valueOf(cell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = String.valueOf(cell.getNumericCellValue());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				DecimalFormat df = new DecimalFormat("0.00");
				value = String.valueOf(df.format(cell.getNumericCellValue()));
				if (value.endsWith(".00"))
				{
					value = value.substring(0, value.length() - 3);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				value = cell.getStringCellValue();
				break;
			default:
				value = null;
				break;
			}
		} catch (Exception e)
		{
			return null;
		}
		if (value != null)
		{
			return value.trim();
		}
		return value;
	}

	public static Map<Integer, String> setObject(Row row, int number)
	{
		Map<Integer, String> contact = new HashMap<Integer, String>();
		for (int i = 0; i < number; i++)
		{
			if (row.getCell(i) != null)
			{
				contact.put(i, getCellValue(row.getCell(i)));
			} else
			{
				contact.put(i, null);
			}
		}
		return contact;
	}

	/**
	 * 
	 * @param file
	 *            要导入的Excel表格
	 * @param number
	 *            要导入的字段个数
	 * @return
	 */
	public static Map<Integer, Map<Integer, String>> getObjects(File file, int number)
	{
		Map<Integer, Map<Integer, String>> contacts = new HashMap<Integer, Map<Integer, String>>();
		FileInputStream in = null;
		try
		{
			in = new FileInputStream(file);
			Workbook wb = WorkbookFactory.create(in);
			Sheet sheet = wb.getSheetAt(0);
			System.out.println("lastrow:" + sheet.getLastRowNum());
			for (int i = 1; i <= sheet.getLastRowNum(); i++)
			{
				if (sheet.getRow(i).getCell(0) == null)
				{
					continue;
				}
				Map<Integer, String> object = setObject(sheet.getRow(i), number);
				contacts.put(i, object);
			}

		} catch (InvalidFormatException e)
		{
			System.out.println("文件格式错误！");
			e.printStackTrace();
		} catch (IOException e)
		{
			System.out.println("文件读取错误！");
			e.printStackTrace();
		} finally
		{
			try
			{
				if (null != in)
				{
					in.close();
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return contacts;
	}
}

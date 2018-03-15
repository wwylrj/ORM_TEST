/**   
* @(#) ExportExcelBean.java  Nov 25, 2017 8:38:40 AM 
* @Package com.xQuant.base.entitybase 
*/ 
package orm.export;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * @param <T>
 * @Author Administrator
 * @Date Nov 25, 2017 8:38:40 AM
 * @Version V2.1 
 */
public class ExportExcelBean<T> {
	/**
	 * String title, ArrayList columnsName,
			ArrayList columnsValue, Collection<T> dataset, boolean hasLastRow,
			String[] lastRowsValue, Collection<T> lastDataSet
	 */
	private String title;
	private ArrayList columnsName;
	private ArrayList columnsValue;
	private List<T> dataSet;
	private boolean hasLastRow;
	private String[] lastRowsValue;
	private Collection<T> lastDataSet;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList getColumnsName() {
		return columnsName;
	}
	public void setColumnsName(ArrayList columnsName) {
		this.columnsName = columnsName;
	}
	public ArrayList getColumnsValue() {
		return columnsValue;
	}
	public void setColumnsValue(ArrayList columnsValue) {
		this.columnsValue = columnsValue;
	}
	
	public List<T> getDataSet() {
		return dataSet;
	}
	public void setDataSet(List<T> dataSet) {
		this.dataSet = dataSet;
	}
	public boolean isHasLastRow() {
		return hasLastRow;
	}
	public void setHasLastRow(boolean hasLastRow) {
		this.hasLastRow = hasLastRow;
	}
	public String[] getLastRowsValue() {
		return lastRowsValue;
	}
	public void setLastRowsValue(String[] lastRowsValue) {
		this.lastRowsValue = lastRowsValue;
	}
	public Collection<T> getLastDataSet() {
		return lastDataSet;
	}
	public void setLastDataSet(Collection<T> lastDataSet) {
		this.lastDataSet = lastDataSet;
	}
	
	
	
	

}

package utils;

import java.util.List;

public class DataGridResult {
	private long total;//总数量
	private List<?> rows;//数据
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	DataGridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
	public DataGridResult() {
		super();
	}
	
}

package com.AIS.Modul.MataKuliah.Service;

public class DatatableExtractParams {
	private String where;
	private String order;
	
	public DatatableExtractParams() {
	}
	
	public DatatableExtractParams(String search, String[] column, Boolean[] searchable, int iSortCol_0, String sSortDir_0){
		where = "";
		for(int i =0;i<column.length;i++)
		{
			if(searchable[i]==true)
			{
				if(where.equals("")) where += "lower( CAST( "+column[i]+" as string) ) LIKE '%"+search.toLowerCase()+"%'";
				else where += " OR lower( CAST( "+column[i]+" as string) ) LIKE '%"+search.toLowerCase()+"%'";
			}
		}
		order = " "+column[iSortCol_0]+" "+sSortDir_0;
	}
	
	public String getWhere() {
		return where;
	}
	
	public void setWhere(String where) {
		this.where = where;
	}
	
	public String getOrder() {
		return order;
	}
	
	public void setOrder(String order) {
		this.order = order;
	}
}

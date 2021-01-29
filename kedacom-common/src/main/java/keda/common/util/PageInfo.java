package keda.common.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 *  分页信息封装类
 * @author root
 * @referenceBy 基本所有的工程都使用了此类
 */
public class PageInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 升序
	 */
	public static final String ASC = "asc";
	/**
	 * 降序
	 */
	public static final String DESC = "desc";
	
	/**页码，首页的值是1.*/
	private int pageNumber = 1;
	/**每页数量*/
	private int pageSize = 20;
	/**排序关键字段*/
	private String sortName;
	/**排序类型*/
	private String sortOrder = "asc";
	/**总数*/
	private int total = 0;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * 返回页码，首页的值是1.
	 * @return
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * 设置页码，首页的值是1.
	 * @param pageNumber
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * 设备每页数量
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public static PageInfo loadFromJson(JSONObject obj) throws JSONException {
		if(obj == null)
			return null;
		PageInfo info = new PageInfo();
		if(!obj.isNull("pageNumber"))	
			info.setPageNumber(obj.getInt("pageNumber"));
		if(!obj.isNull("pageSize"))
			info.setPageSize(obj.getInt("pageSize"));
		if(!obj.isNull("sortName"))
			info.setSortName(obj.getString("sortName"));
		if(!obj.isNull("sortOrder"))
			info.setSortOrder(obj.getString("sortOrder"));
		if(!obj.isNull("total"))
			info.setTotal(obj.getInt("total"));
	
		return info;
	}
	
	/**
	 * 返回当前分页状态是否有下一页
	 * @return
	 */
	public boolean hasNextPage(){

		int total = this.total;
		int pageSize = this.pageSize;
		int pageCount =(int) Math.ceil( total / (float)pageSize);//总页数
		return this.pageNumber < pageCount;
	}
	
	/**
	 * 当前页的开始索引号（含）。
	 * @see #getEnd()
	 * @return 索引号。第一条记录的索引号为0.
	 */
	public int getStart(){

		return (pageNumber-1) * pageSize;
	}
	
	/**
	 * 当前页的结束索引号（含）。
	 * @see #getStart()
	 * @return 索引号。.
	 */
	public int getEnd(){
		int start = getStart();
		return start + pageSize - 1;
	}
	
	/**
	 * 移动页码，指向当面页的下一页。
	 */
	public void nextPage(){
		this.pageNumber ++;
	}
	
}

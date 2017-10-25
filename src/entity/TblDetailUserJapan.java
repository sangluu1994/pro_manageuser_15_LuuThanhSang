/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * TblDetailUserJapan.java, 2017-10-25 luuthanhsang
 *
 */
package entity;

/**
 * Class mô phỏng bảng TblDetailUserJapan
 * 
 * @author luuthanhsang
 *
 */
public class TblDetailUserJapan {
	private int detailUserJapanId;
	private int userId;
	private String codeLevel;
	private String startDate;
	private String endDate;
	private int total;
	/**
	 * @return the detailUserJapanId
	 */
	public int getDetailUserJapanId() {
		return detailUserJapanId;
	}
	/**
	 * @param detailUserJapanId the detailUserJapanId to set
	 */
	public void setDetailUserJapanId(int detailUserJapanId) {
		this.detailUserJapanId = detailUserJapanId;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}
	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	
}

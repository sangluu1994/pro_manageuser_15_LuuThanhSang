/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroup.java, 2017-10-25 luuthanhsang
 *
 */
package entity;

/**
 * Class mô phỏng bảng mst_group
 * 
 * @author luuthanhsang
 *
 */
public class MstGroup {
	private int groupId;
	private String groupName;
	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}

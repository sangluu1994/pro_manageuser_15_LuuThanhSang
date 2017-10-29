/**
 * Copyright(C) 2017 Luvina Software Company
 * 
 * MstGroupLogic.java, 2017-10-26 luuthanhsang
 */
package logic;

import java.util.List;
import entity.MstGroup;

/**
 * Interface chứa các phương thức thao tác với bảng mst_group
 *
 * @author luuthanhsang
 */
public interface MstGroupLogic {
	public List<MstGroup> getAllGroups();
}

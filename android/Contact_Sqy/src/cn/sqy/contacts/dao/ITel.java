package cn.sqy.contacts.dao;

import java.util.List;

import cn.sqy.contacts.model.Tel;

public interface ITel {

	public void insert(Tel tel);

	public void delete(int telId);

	public void update(Tel tel);

	public List<Tel> getTelsByCondition(String condition);
}

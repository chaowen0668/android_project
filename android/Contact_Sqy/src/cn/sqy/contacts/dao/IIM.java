package cn.sqy.contacts.dao;

import java.util.List;

import cn.sqy.contacts.model.IM;

public interface IIM {
	public void insert(IM im);

	public void delete(int imId);

	public void update(IM im);

	public List<IM> getIMsByCondition(String condition);
}

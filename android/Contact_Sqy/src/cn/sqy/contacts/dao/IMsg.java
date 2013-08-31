package cn.sqy.contacts.dao;

import java.util.List;

import cn.sqy.contacts.model.Msg;

public interface IMsg {
	public void insert(Msg msg);

	public void deleteAll();

	public List<Msg> getMsgsByCondition(String condition);
}

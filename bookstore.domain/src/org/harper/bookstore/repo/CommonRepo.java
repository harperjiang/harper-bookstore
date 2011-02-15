package org.harper.bookstore.repo;

import java.util.Collection;

public interface CommonRepo extends Repo{

	public String NUMBER_TYPE_PO = "PO";
	
	public String NUMBER_TYPE_SO = "SO";
	
	public String NUMBER_TYPE_TR = "TR";
	
	public String NUMBER_TYPE_BR = "BR";
	
	public String getNumber(String type);
	
	public void store(Collection<?> cols);
	
	public <T> T store(T object);
}

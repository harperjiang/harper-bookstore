package org.harper.bookstore.repo.mem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harper.bookstore.domain.Entity;
import org.harper.bookstore.repo.CommonRepo;

public class MemoryCommonRepo implements CommonRepo {

	private Map<Class, List<Entity>> stores;

	private Map<String, Integer> numbers;

	public Map<Class, List<Entity>> getStores() {
		return stores;
	}

	public void setStores(Map<Class, List<Entity>> stores) {
		this.stores = stores;
	}

	public Map<String, Integer> getNumbers() {
		return numbers;
	}

	public void setNumbers(Map<String, Integer> numbers) {
		this.numbers = numbers;
	}

	public MemoryCommonRepo() {
		stores = new HashMap<Class, List<Entity>>();
		numbers = new HashMap<String, Integer>();
	}

	@Override
	public Object readObject(Class clazz, int oid) {
		if (stores.containsKey(clazz)) {
			List<Entity> result = stores.get(clazz);
			for (Entity obj : result)
				if (oid == obj.getOid())
					return obj;
		}
		return null;
	}

	@Override
	public String getNumber(String type) {
		if (!numbers.containsKey(type)) {
			numbers.put(type, 0);
		}
		numbers.put(type, numbers.get(type) + 1);
		return type + numbers.get(type);
	}

	@Override
	public void store(Collection<?> cols) {
		for (Object object : cols)
			store(object);
	}

	@Override
	public <T> T store(T object) {
		if (!stores.containsKey(object.getClass()))
			stores.put(object.getClass(), new ArrayList<Entity>());
		stores.get(object.getClass()).add((Entity) object);
		return object;
	}

	@Override
	public <T> T remove(T object) {
		if (stores.containsKey(object.getClass()))
			stores.get(object.getClass()).remove((Entity) object);
		return object;
	}

	public List get(Class class1) {
		if (getStores().containsKey(class1))
			return getStores().get(class1);
		return new ArrayList();
	}

}

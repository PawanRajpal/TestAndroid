package com.ma.bean;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("serial")
public class NaviationParentItems implements Serializable {
	private String name;
	private int id;
	private List<NavigationChildItems> entities;
	
	public NaviationParentItems(){
		
	}
	 

	/**
	 * @param name
	 * @param entities
	 */
	public NaviationParentItems(String name, int id, List<NavigationChildItems> entities) {
		this.name = name;
		this.id = id;
		this.entities = entities;
	}
	

	/**
	 * @param name
	 * @param id
	 */
	public NaviationParentItems(String name, int id) {
		this.name = name;
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the entities
	 */
	public List<NavigationChildItems> getEntities() {
		return entities;
	}

	/**
	 * @param entities the entities to set
	 */
	public void setEntities(List<NavigationChildItems> entities) {
		this.entities = entities;
	}
	
	/**
	 * @param id
	 * @param name
	 */
	public void addEntity(int id, String name){
		entities.add(new NavigationChildItems(name, id));
	}
	
	public int getCount(){
		return entities.size();
	}
	
	
	//Name by Ascending order
		public static class OrderByName implements Comparator<NaviationParentItems>{

			@Override
			public int compare(NaviationParentItems o1, NaviationParentItems o2) {
				return o1.name.compareTo(o2.name);
			}

		}


		//Name by Descending order
		public static class OrderByNameDesc implements Comparator<NaviationParentItems>{

			@Override
			public int compare(NaviationParentItems o1, NaviationParentItems o2) {
				return o2.name.compareTo(o1.name);
			}

		}

		//Count by Ascending order
		public static class OrderByCount implements Comparator<NaviationParentItems>{

			@Override
			public int compare(NaviationParentItems o1, NaviationParentItems o2) {
				return o1.entities.size() > o2.entities.size() ? 1 : (o1.entities.size() < o2.entities.size() ? -1 : 0);
			}

		}

		//Count by Descending order
		public static class OrderByCountDesc implements Comparator<NaviationParentItems>{

			@Override
			public int compare(NaviationParentItems o1, NaviationParentItems o2) {
				return o2.entities.size()> o1.entities.size() ? 1 : (o2.entities.size() < o1.entities.size() ? -1 : 0);
			}

		}

}

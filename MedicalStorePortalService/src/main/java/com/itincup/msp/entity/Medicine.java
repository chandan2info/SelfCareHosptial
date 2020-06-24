package com.itincup.msp.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MEDICINE_INVENTORY")
public class Medicine {
	@Id
	private Long id;
	private String medicine_name;
	private Integer quantity;
	private Double price;
	private Integer sell_med_qantity;
	private Integer avail_med_quantity;
	
	

	public Medicine() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedicine_name() {
		return medicine_name;
	}

	public void setMedicine_name(String medicine_name) {
		this.medicine_name = medicine_name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getSell_med_qantity() {
		return sell_med_qantity;
	}

	public void setSell_med_qantity(Integer sell_med_qantity) {
		this.sell_med_qantity = sell_med_qantity;
	}

	public Integer getAvail_med_quantity() {
		return avail_med_quantity;
	}

	public void setAvail_med_quantity(Integer avail_med_quantity) {
		this.avail_med_quantity = avail_med_quantity;
	}

	@Override
	public String toString() {
		return "Medicine [id=" + id + ", medicine_name=" + medicine_name + ", quantity=" + quantity + ", price=" + price
				+ ", sell_med_qantity=" + sell_med_qantity + ", avail_med_quantity=" + avail_med_quantity + "]";
	}

}

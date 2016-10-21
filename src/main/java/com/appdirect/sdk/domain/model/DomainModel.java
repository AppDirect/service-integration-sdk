package com.appdirect.sdk.domain.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DomainModel implements Serializable {
	private static final long serialVersionUID = 6542116431323964928L;

	private String name;
	private String currency;
	private BigDecimal priceFirstYear;
	private BigDecimal priceRecurringPerYear;
	private boolean available = true;

	public DomainModel() {
	}

	public String getName() {
		return this.name;
	}

	public String getCurrency() {
		return this.currency;
	}

	public BigDecimal getPriceFirstYear() {
		return this.priceFirstYear;
	}

	public BigDecimal getPriceRecurringPerYear() {
		return this.priceRecurringPerYear;
	}

	public boolean isAvailable() {
		return this.available;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setPriceFirstYear(BigDecimal priceFirstYear) {
		this.priceFirstYear = priceFirstYear;
	}

	public void setPriceRecurringPerYear(BigDecimal priceRecurringPerYear) {
		this.priceRecurringPerYear = priceRecurringPerYear;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof DomainModel)) return false;
		final DomainModel other = (DomainModel) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final Object this$currency = this.getCurrency();
		final Object other$currency = other.getCurrency();
		if (this$currency == null ? other$currency != null : !this$currency.equals(other$currency)) return false;
		final Object this$priceFirstYear = this.getPriceFirstYear();
		final Object other$priceFirstYear = other.getPriceFirstYear();
		if (this$priceFirstYear == null ? other$priceFirstYear != null : !this$priceFirstYear.equals(other$priceFirstYear))
			return false;
		final Object this$priceRecurringPerYear = this.getPriceRecurringPerYear();
		final Object other$priceRecurringPerYear = other.getPriceRecurringPerYear();
		if (this$priceRecurringPerYear == null ? other$priceRecurringPerYear != null : !this$priceRecurringPerYear.equals(other$priceRecurringPerYear))
			return false;
		if (this.isAvailable() != other.isAvailable()) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $currency = this.getCurrency();
		result = result * PRIME + ($currency == null ? 43 : $currency.hashCode());
		final Object $priceFirstYear = this.getPriceFirstYear();
		result = result * PRIME + ($priceFirstYear == null ? 43 : $priceFirstYear.hashCode());
		final Object $priceRecurringPerYear = this.getPriceRecurringPerYear();
		result = result * PRIME + ($priceRecurringPerYear == null ? 43 : $priceRecurringPerYear.hashCode());
		result = result * PRIME + (this.isAvailable() ? 79 : 97);
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof DomainModel;
	}

	public String toString() {
		return "com.appdirect.domain.model.DomainModel(name=" + this.getName() + ", currency=" + this.getCurrency() + ", priceFirstYear=" + this.getPriceFirstYear() + ", priceRecurringPerYear=" + this.getPriceRecurringPerYear() + ", available=" + this.isAvailable() + ")";
	}
}

package com.appdirect.sdk.domain.model;

import java.io.Serializable;

public class DnsRecordModel implements Serializable {
	private static final long serialVersionUID = 7149783858521513203L;

	private String type;
	private String name;
	private String data;
	private Long ttl;

	public DnsRecordModel() {
	}

	public String getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}

	public String getData() {
		return this.data;
	}

	public Long getTtl() {
		return this.ttl;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setTtl(Long ttl) {
		this.ttl = ttl;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof DnsRecordModel)) return false;
		final DnsRecordModel other = (DnsRecordModel) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$type = this.getType();
		final Object other$type = other.getType();
		if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
		final Object this$name = this.getName();
		final Object other$name = other.getName();
		if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
		final Object this$data = this.getData();
		final Object other$data = other.getData();
		if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
		final Object this$ttl = this.getTtl();
		final Object other$ttl = other.getTtl();
		if (this$ttl == null ? other$ttl != null : !this$ttl.equals(other$ttl)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $type = this.getType();
		result = result * PRIME + ($type == null ? 43 : $type.hashCode());
		final Object $name = this.getName();
		result = result * PRIME + ($name == null ? 43 : $name.hashCode());
		final Object $data = this.getData();
		result = result * PRIME + ($data == null ? 43 : $data.hashCode());
		final Object $ttl = this.getTtl();
		result = result * PRIME + ($ttl == null ? 43 : $ttl.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof DnsRecordModel;
	}

	public String toString() {
		return "com.appdirect.domain.model.DnsRecordModel(type=" + this.getType() + ", name=" + this.getName() + ", data=" + this.getData() + ", ttl=" + this.getTtl() + ")";
	}
}

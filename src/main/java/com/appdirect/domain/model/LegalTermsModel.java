package com.appdirect.domain.model;

import java.io.Serializable;

public class LegalTermsModel implements Serializable {
	private static final long serialVersionUID = 6542111336323952928L;

	private String agreementKey;
	private String title;
	private String url;
	private String content;

	public LegalTermsModel() {
	}

	public String getAgreementKey() {
		return this.agreementKey;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUrl() {
		return this.url;
	}

	public String getContent() {
		return this.content;
	}

	public void setAgreementKey(String agreementKey) {
		this.agreementKey = agreementKey;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof LegalTermsModel)) return false;
		final LegalTermsModel other = (LegalTermsModel) o;
		if (!other.canEqual((Object) this)) return false;
		final Object this$agreementKey = this.getAgreementKey();
		final Object other$agreementKey = other.getAgreementKey();
		if (this$agreementKey == null ? other$agreementKey != null : !this$agreementKey.equals(other$agreementKey))
			return false;
		final Object this$title = this.getTitle();
		final Object other$title = other.getTitle();
		if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
		final Object this$url = this.getUrl();
		final Object other$url = other.getUrl();
		if (this$url == null ? other$url != null : !this$url.equals(other$url)) return false;
		final Object this$content = this.getContent();
		final Object other$content = other.getContent();
		if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
		return true;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $agreementKey = this.getAgreementKey();
		result = result * PRIME + ($agreementKey == null ? 43 : $agreementKey.hashCode());
		final Object $title = this.getTitle();
		result = result * PRIME + ($title == null ? 43 : $title.hashCode());
		final Object $url = this.getUrl();
		result = result * PRIME + ($url == null ? 43 : $url.hashCode());
		final Object $content = this.getContent();
		result = result * PRIME + ($content == null ? 43 : $content.hashCode());
		return result;
	}

	protected boolean canEqual(Object other) {
		return other instanceof LegalTermsModel;
	}

	public String toString() {
		return "com.appdirect.domain.model.LegalTermsModel(agreementKey=" + this.getAgreementKey() + ", title=" + this.getTitle() + ", url=" + this.getUrl() + ", content=" + this.getContent() + ")";
	}
}

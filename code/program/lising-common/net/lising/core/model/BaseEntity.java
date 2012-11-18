package net.lising.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.lising.lib.encrypt.SuperEncript;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@MappedSuperclass
public abstract class BaseEntity implements Serializable,Cloneable{

	private static final long serialVersionUID = 3562266036196989835L;

	/**
	 * 主键ID
	 */
	private Long id;
	
	/**
	 * <pre>
	 * 
	 * 加密ID<不同步到数据库>：意为-secure source id,目的防止别人猜测ID
	 * 
	 * 使用方法：
	 * 例如:<a href='${web_unpass}plan/view.go?ssid=${ssid}'>查看</a>
	 * 在抽象action中，必须有取ssid的方法
	 * 
	 * <pre>
	 */
	private String ssid;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String tableName() {
		Class<?> c = this.getClass();
		Table a = (Table) c.getAnnotation(Table.class);
		if (null != a)
			return a.name().toLowerCase();
		return null;
	}

	public int compareTo(Object obj) {
		int compare = -1;
		if (obj == null)
			compare = -1;
		else if (this == obj)
			compare = 0;
		else if (!(obj instanceof BaseEntity))
			compare = -1;
		else if (!this.getClass().equals(obj.getClass()))
			compare = -1;
		else {
			BaseEntity castObj = (BaseEntity) obj;
			CompareToBuilder builder = new CompareToBuilder();
			builder.append(this.getId(), castObj.getId());
			compare = builder.toComparison();
		}
		return compare;
	}

	public boolean equals(Object obj) {
		boolean isEqual = false;
		if (obj == null) {
			isEqual = false;
		} else if (this == obj) {
			isEqual = true;
		} else if (!(obj instanceof BaseEntity)) {
			isEqual = false;
		} else if (!this.getClass().equals(obj.getClass())) {
			isEqual = false;
		} else {
			BaseEntity castObj = (BaseEntity) obj;
			EqualsBuilder builder = new EqualsBuilder();
			builder.append(this.getId(), castObj.getId());
			isEqual = builder.isEquals();
		}
		return isEqual;
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	@Transient
	public String getSsid() {
		ssid = SuperEncript.encryptEveryThing(this.getId());
		return ssid;
	}
	
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
	}
}
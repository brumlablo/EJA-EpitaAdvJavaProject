package fr.epita.iam.datamodel;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Identity object
 * @author bb
 *
 */
@Entity
@Table(name="ADRRESS")
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ADDRESS_UID")
	private Long uid;
	
	@Column(name="ADDRESS_REAL")
	private String addr;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ADDRESS_OWNER") // foreign database
	private Identity identity;

	/**
	 * @param uid unique id
	 * @param addr address
	 * @param Identity owner
	 */
	public Address(Long uid, String addr, Identity identity) {
		super();
		this.uid = uid;
		this.addr = addr;
		this.identity = identity;
	}

	/**
	 * Get uid
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}

	/**
	 * Set uid
	 * @param uid
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}

	/**
	 * Get address
	 * @return address identity address
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * Set address
	 * @param addr new address
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * Get identity connected to this address
	 * @return identity
	 */
	public Identity getIdentity() {
		return identity;
	}

	/**
	 * Set identity
	 * @param identity
	 */
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
}

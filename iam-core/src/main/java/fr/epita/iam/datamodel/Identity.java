/**
 * 
 */
package fr.epita.iam.datamodel;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Identity object
 * @author bb
 *
 */
@Entity
@Table(name="IDENTITIES")
public class Identity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="IDENTITY_UID")
	private Long uid;
	
	
	@Column(name="IDENTITY_DISPLAYNAME")
	private String displayName;
	@Column(name="IDENTITY_PASSWORD")
	private String password;
	@Column(name="IDENTITY_EMAIL")
	private String email;
	@Column(name="IDENTITY_BIRTHDATE")
	private Date dob;
	@Column(name="IDENTITY_ROLE")
	private String role;
	
	/**
	 * @param uid unique id
	 * @param displayName name
	 * @param password password
	 * @param email email
	 * @param dob date of birth
	 */
	public Identity(Long uid, String displayName, String password, String email,Date dob) {
		
		this.uid = uid;
		this.displayName = displayName;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.role = "user"; // default = user
	}
	
	/**
	 * @param uid unique id
	 * @param displayName name
	 * @param password password
	 * @param email email
	 * @param dob date of birth
	 * @param isAdmin is identity the admin?
	 */
	public Identity(Long uid, String displayName, String password, String email,Date dob, String role) {
		
		this.uid = uid;
		this.displayName = displayName;
		this.password = password;
		this.email = email;
		this.dob = dob;
		this.role = role;
	}
	
	/**
	 * @param default constructor
	 */
	public Identity() {
		
	}
	
	/**
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get password
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Get Date
	 * @return date of birth
	 */
	public Date getDOB() {
		return this.dob;
	}

	/**
	 * Get role - if is the identity admin
	 * @return 
	 */
	public String getRole() {
		return this.role;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayName=" + displayName + ", email=" + email + "]";
	}
}

/**
 * 
 */
package fr.epita.iam.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import fr.epita.iam.services.PasswordEndecryptor;

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
	private String dob;
	@Column(name="IDENTITY_ROLE")
	private String role;
	
	
	/**
	 * @param uid unique id
	 * @param displayName name
	 * @param password password
	 * @param email email
	 * @param dob date of birth
	 */
	public Identity(Long uid, String displayName, String password, String email, String dob) {
		
		this.uid = uid;
		this.displayName = displayName;
		PasswordEndecryptor.getInst();
		this.password = PasswordEndecryptor.hashPwd(password);
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
	 * @param role is identity the admin?
	 */
	public Identity(Long uid, String displayName, String password, String email,String dob, String role) {
		
		this.uid = uid;
		this.displayName = displayName;
		PasswordEndecryptor.getInst();
		this.password = PasswordEndecryptor.hashPwd(password);
		this.email = email;
		this.dob = dob;
		this.role = role;
	}
	
	/**
	 * Default constructor
	 */
	public Identity() {
		
	}
	
	/**
	 * Get uid
	 * @return the uid
	 */
	public Long getUid() {
		return uid;
	}
	/**
	 * Set uid, done implicitely by hibernate
	 * @param uid the uid to set
	 */
	public void setUid(Long uid) {
		this.uid = uid;
	}
	/**
	 * Get displayName
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}
	/**
	 * Set displayName
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	/**
	 * Get e-mail
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Set e-mail
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get password
	 * @return password password in bcrypt hash
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * Set password
	 * @param password password in plain text
	 */
	public void setPassword(String password) {
		PasswordEndecryptor.getInst();
		this.password = PasswordEndecryptor.hashPwd(password);
	}
	
	/**
	 * Get Date
	 * @return dob date of birth
	 */
	public String getDOB() {
		return this.dob;
	}
	
	/**
	 * Set Date of birth
	 * @param dob date of birth
	 */
	public void setDOB(String dob) {
		this.dob = dob;
	}

	/**
	 * Get role - if the identity is admin or user
	 * @return role
	 */
	public String getRole() {
		return this.role;
	}
	
	/**
	 * Set role
	 * @param role admin or user
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayName=" + displayName + ", email=" + email + "]";
	}
}

/**
 * 
 */
package fr.epita.iam.datamodel;

import java.sql.Date;

/**
 * Identity object
 * @author bb
 *
 */
public class Identity {
	
	private String uid;
	private String displayName;
	private String password;
	private String email;
	private Date dob;
	
	
	/**
	 * @param uid unique id
	 * @param displayName name
	 * @param password password
	 * @param email email
	 * @param dob date of birth
	 */
	public Identity(String uid, String displayName, String password, String email,Date dob) {
		this.uid = uid;
		this.displayName = displayName;
		this.password = password;
		this.email = email;
		this.dob = dob;
	}
	
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayName=" + displayName + ", email=" + email + "]";
	}
}

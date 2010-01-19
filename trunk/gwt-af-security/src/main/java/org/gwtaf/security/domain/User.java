/*
 * Copyright 2008. Mount Sinai Hospital, Toronto, Canada.
 * 
 * Licensed under the Apache License, Version 2.0. You
 * can find a copy of the license at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * IN NO EVENT SHALL MOUNT SINAI HOSPITAL BE LIABLE TO ANY PARTY FOR DIRECT, 
 * INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST 
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, 
 * EVEN IF MOUNT SINAI HOSPITAL HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH 
 * DAMAGE.
 * 
 * MOUNT SINAI HOSPITAL SPECIFICALLY DISCLAIMS ANY IMPLIED WARRANTIES OF 
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE AND 
 * ACCOMPANYING DOCUMENTATION, IF ANY, PROVIDED HEREUNDER IS PROVIDED "AS IS". 
 * MOUNT SINAI HOSPITAL HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, 
 * UPDATES, ENHANCEMENTS, OR MODIFICATIONS. 
 */

package org.gwtaf.security.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The client side representation of a user in the system. This client side user
 * mirrors the server side Spring Security based user.
 * 
 * @author Arthur Kalmenson
 */
@SuppressWarnings("serial")
public class User implements Serializable {

	/**
	 * The unique identifier for the user object.
	 */
	private Integer id;

	/**
	 * The username of the user, we insure it's not null and unique.
	 */
	private String username;

	/**
	 * The password of the user.
	 */
	private String password;

	/**
	 * Boolean representing if the account has expired or not.
	 */
	private Boolean accountNonExpired;

	/**
	 * Boolean representing if the account has been locked or not.
	 */
	private Boolean accountNonLocked;

	/**
	 * Boolean representing if the credentials have expired or not.
	 */
	private Boolean credentialsNonExpired;

	/**
	 * Boolean representing if the account is enabled or not.
	 */
	private Boolean enabled;

	/**
	 * The set of authorities that this user has.
	 */
	private Set<Authority> authority = new HashSet<Authority>();

	/**
	 * Creates a new <code>User</code>.
	 */
	public User() {
	}

	/**
	 * Creates a new <code>User</code> assuming the new user is enabled, the
	 * account has not expired, account is not locked and the credentials have
	 * not expired.
	 * 
	 * @param username
	 *            the username of the user.
	 * @param password
	 *            the plain text password of the user.
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.enabled = true;
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
	}

	/**
	 * Creates a full new <code>User</code>.
	 * 
	 * @param username
	 *            username of the user.
	 * @param password
	 *            plain text password of the user.
	 * @param enabled
	 *            true if the account is enabled, false otherwise.
	 * @param accountNonExpired
	 *            true if the account is not expired, false otherwise.
	 * @param accountNonLocked
	 *            true if the account is not locked, false otherwise.
	 * @param credentialsNonExpired
	 *            true if the credentials have expired, false otherwise.
	 */
	public User(String username, String password, Boolean enabled,
			Boolean accountNonExpired, Boolean accountNonLocked,
			Boolean credentialsNonExpired) {
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean isAccountNonExpired) {
		this.accountNonExpired = isAccountNonExpired;
	}

	public Boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean isAccountNonLocked) {
		this.accountNonLocked = isAccountNonLocked;
	}

	public Boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.credentialsNonExpired = isCredentialsNonExpired;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean isEnabled) {
		this.enabled = isEnabled;
	}

	public Set<Authority> getAuthority() {
		return authority;
	}

	public void setAuthority(Set<Authority> authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<User: accountNonExpired=");
		builder.append(accountNonExpired);
		builder.append(" / accountNonLocked=");
		builder.append(accountNonLocked);
		builder.append(" / authority=");
		builder.append(authority);
		builder.append(" / credentialsNonExpired=");
		builder.append(credentialsNonExpired);
		builder.append(" / enabled=");
		builder.append(enabled);
		builder.append(" / id=");
		builder.append(id);
		builder.append(" / password=");
		builder.append(password);
		builder.append(" / username=");
		builder.append(username);
		builder.append(">");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accountNonExpired == null) ? 0 : accountNonExpired
						.hashCode());
		result = prime
				* result
				+ ((accountNonLocked == null) ? 0 : accountNonLocked.hashCode());
		result = prime * result
				+ ((authority == null) ? 0 : authority.hashCode());
		result = prime
				* result
				+ ((credentialsNonExpired == null) ? 0 : credentialsNonExpired
						.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accountNonExpired == null) {
			if (other.accountNonExpired != null)
				return false;
		} else if (!accountNonExpired.equals(other.accountNonExpired))
			return false;
		if (accountNonLocked == null) {
			if (other.accountNonLocked != null)
				return false;
		} else if (!accountNonLocked.equals(other.accountNonLocked))
			return false;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (credentialsNonExpired == null) {
			if (other.credentialsNonExpired != null)
				return false;
		} else if (!credentialsNonExpired.equals(other.credentialsNonExpired))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}

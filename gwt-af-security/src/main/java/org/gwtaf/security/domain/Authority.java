/*
 * Copyright 2010. Mount Sinai Hospital, Toronto, Canada.
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

/**
 * The client side representation of an Authority in the system. This client
 * side user mirrors the server side Spring Security based authority.
 * 
 * @author Arthur Kalmenson
 */
@SuppressWarnings("serial")
public class Authority implements Serializable {

	/**
	 * The unique identifier for this <code>Authority</code> object.
	 */
	private Integer id;

	/**
	 * The user that this <code>Authority</code> belongs to.
	 */
	private User user;

	/**
	 * The name of the authority.
	 */
	private String authority;

	/**
	 * Creates an empty <code>Authority</code> object.
	 */
	public Authority() {
	}

	/**
	 * Creates a new <code>Authority</code> object with the granted authority,
	 * <code>authority</code>.
	 * 
	 * @param authority
	 *            the granted authority.
	 */
	public Authority(String authority) {
		this.authority = authority;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("<Authority: authority=");
		builder.append(authority);
		builder.append(" / id=");
		builder.append(id);
		builder.append(" / user=");
		builder.append(user);
		builder.append(">");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result =
				prime * result
						+ ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Authority other = (Authority) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

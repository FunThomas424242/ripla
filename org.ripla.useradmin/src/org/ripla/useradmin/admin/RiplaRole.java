/*******************************************************************************
* Copyright (c) 2012 RelationWare, Benno Luthiger
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
* RelationWare, Benno Luthiger
******************************************************************************/

package org.ripla.useradmin.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;

import org.osgi.service.useradmin.Role;
import org.ripla.useradmin.internal.PropertiesHashtable;

/**
 * RiplaRole class.
 * 
 * <p>
 * The base interface for <tt>Role</tt> objects managed by the User Admin service.
 * 
 * </p><p>This interface exposes the characteristics shared by all <tt>Role</tt> classes: a name,
 * a type, and a set of properties.
 * </p><p>
 * Properties represent public information about the <tt>Role</tt> object that can be read by
 * anyone. Specific <a href="../../../../org/osgi/service/useradmin/UserAdminPermission.html" title="class in org.osgi.service.useradmin"><code>UserAdminPermission</code></a>objects are required to
 * change a <tt>Role</tt> object's properties.
 * </p><p>
 * <tt>Role</tt> object properties are <tt>Dictionary</tt> objects. Changes to
 * these objects are propagated to the User Admin service and
 * made persistent.
 * </p><p>
 * Every User Admin service contains a set of predefined <tt>Role</tt> objects that are always present
 * and cannot be removed. All predefined <tt>Role</tt> objects are of type <tt>ROLE</tt>.
 * This version of the <tt>org.osgi.service.useradmin</tt> package defines a
 * single predefined role named "user.anyone", which is inherited
 * by any other role. Other predefined roles may be added in the future.
 * Since "user.anyone" is a <tt>Role</tt> object that has properties associated with
 * it that can be read and modified.  Access to these properties and their
 * use is application specific and is controlled using 
 * <tt>UserAdminPermission</tt> in the same way that properties for other
 * <tt>Role</tt> objects are.
 * </p>
 * 
 * @author Luthiger
 */
public class RiplaRole implements Role {
	
	private String name;
	private PropertiesHashtable properties;
	private RiplaUserAdmin userAdmin;
	private Collection<RiplaGroup> impliedRoles;
	
	protected boolean exists = true;

	/**
	 * RiplaRole constructor.
	 * 
	 * @param inName String
	 * @param inUserAdmin {@link RiplaUserAdmin}
	 */
	public RiplaRole(String inName, RiplaUserAdmin inUserAdmin) {
		name = inName;
		userAdmin = inUserAdmin;
		properties = new PropertiesHashtable(this, inUserAdmin);
		
		// we want to track the groups this role is directly a member of
		impliedRoles = new ArrayList<RiplaGroup>();
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.useradmin.Role#getName()
	 */
	@Override
	public String getName() {
		userAdmin.checkAlive();
		return name;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.useradmin.Role#getType()
	 */
	@Override
	public int getType() {
		userAdmin.checkAlive();
		return ROLE;
	}

	/* (non-Javadoc)
	 * @see org.osgi.service.useradmin.Role#getProperties()
	 */
	@Override
	public Dictionary<String, Object> getProperties() {
		userAdmin.checkAlive();
		return properties;
	}
	
	protected void addImpliedRole(RiplaGroup inGroup) {
		impliedRoles.add(inGroup);
	}
	
	protected void removeImpliedRole(RiplaGroup inGroup) {
		if (exists) {
			//this prevents a loop when destroy is called
			impliedRoles.remove(inGroup);
		}
	}
	
	protected synchronized void destroy() {
		exists = false;
		Iterator<RiplaGroup> lGroups = impliedRoles.iterator();
		while (lGroups.hasNext()) {
			RiplaGroup lGroup = (RiplaGroup) lGroups.next();
			if (lGroup.exists) {
				lGroup.removeMember(this);
			}
		}
		properties = null;
		impliedRoles = null;
	}

	protected boolean isImpliedBy(Role inRole, ArrayList<String> inCheckLoop) {
		if (inCheckLoop.contains(getName())) {
			//we have a circular dependency
			return false;
		}
		inCheckLoop.add(getName());
		return getName().equals(Role.USER_ANYONE);
	}

}
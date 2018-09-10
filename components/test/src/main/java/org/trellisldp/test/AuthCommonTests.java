/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trellisldp.test;

/**
 * Common Authorization tests.
 */
public interface AuthCommonTests extends CommonTests {

    String OK_RESPONSE = "Verify an OK response";
    String FORBIDDEN_RESPONSE = "Verify a Forbidden response";
    String NOT_FOUND_RESPONSE = "Verify a Not Found response";
    String UNAUTHORIZED_RESPONSE = "Verify an Unauthorized response";
    String INSERT_PROP_FOO = "INSERT { <> <http://example.com/prop> \"Foo\" } WHERE {}";
    String INSERT_PROP_BAR = "INSERT { <> <http://example.com/prop> \"Bar\" } WHERE {}";
    String EXT_ACL = "?ext=acl";
    String PATCH = "PATCH";

    /**
     * Get the authorization header.
     * @return the authorization header
     */
    String getAuthorizationHeader();

    /**
     * Get the public container location.
     * @return the location of the public container
     */
    String getPublicContainer();

    /**
     * Set the public container location.
     * @param location the location
     */
    void setPublicContainer(String location);

    /**
     * Get the public container child location.
     * @return the location of the public container child
     */
    String getPublicContainerChild();

    /**
     * Set the public container child location.
     * @param location the location
     */
    void setPublicContainerChild(String location);

    /**
     * Get the protected container location.
     * @return the location of the protected container
     */
    String getProtectedContainer();

    /**
     * Set the protected container location.
     * @param location the location
     */
    void setProtectedContainer(String location);

    /**
     * Get the protected container child location.
     * @return the location of the protected container child
     */
    String getProtectedContainerChild();

    /**
     * Set the protected container child location.
     * @param location the location
     */
    void setProtectedContainerChild(String location);

    /**
     * Get the private container location.
     * @return the location of the private container
     */
    String getPrivateContainer();

    /**
     * Set the private container location.
     * @param location the location
     */
    void setPrivateContainer(String location);

    /**
     * Get the private container child location.
     * @return the location of the private container child
     */
    String getPrivateContainerChild();

    /**
     * Set the private container child location.
     * @param location the location
     */
    void setPrivateContainerChild(String location);

    /**
     * Get the default container location.
     * @return the location of the default container
     */
    String getDefaultContainer();

    /**
     * Set the default container location.
     * @param location the location
     */
    void setDefaultContainer(String location);

    /**
     * Get the default container child location.
     * @return the location of the default container child
     */
    String getDefaultContainerChild();

    /**
     * Set the default container child location.
     * @param location the location
     */
    void setDefaultContainerChild(String location);

    /**
     * Get the group container location.
     * @return the location of the group container
     */
    String getGroupContainer();

    /**
     * Set the group container location.
     * @param location the location
     */
    void setGroupContainer(String location);

    /**
     * Get the group container child location.
     * @return the location of the group container child
     */
    String getGroupContainerChild();

    /**
     * Set the group container child location.
     * @param location the location
     */
    void setGroupContainerChild(String location);
}

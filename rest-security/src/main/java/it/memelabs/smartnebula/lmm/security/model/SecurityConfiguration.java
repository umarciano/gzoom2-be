package it.memelabs.smartnebula.lmm.security.model;

import java.text.MessageFormat;

public interface SecurityConfiguration {

    /**
     * Retrieves the number of minutes after which the token is considered expired.
     * <p>
     * If 0 then token does not expires. Default is 0.
     * </p>
     *
     * @return the number of minutes after which the token is considered expired.
     */
    int getTokenExpiryMinutes();

    /**
     * Tells whether the AD module is enabled or not.
     *
     * @return True if AD is used, false otherwise.
     */
    boolean isADEnabled();

    /**
     * Retrieves the host of the the remote AD server.
     *
     * @return the host of the the remote AD server.
     */
    String getADHost();

    /**
     * Retrieves the port of the the remote AD server.
     *
     * @return the port of the the remote AD server.
     */
    int getADPort();

    /**
     * Returns the AD domain.
     *
     * @return The AD domain.
     */
    String getADDomain();

    /**
     * Returns the AD user login format.
     *
     * @return The AD user login format.
     */
    MessageFormat getADUser();

    /**
     * Returns the AD format of the user search filter.
     *
     * @return The AD format of the user search filter.
     */
    MessageFormat getADUserFilter();

    /**
     * Returns the AD root directory where users are looked up into.
     *
     * @return Returns the AD root directory where users are looked up into.
     */
    String getADUserRoot();

    /**
     * User attributes to be retrieved for an existing AD user.
     *
     * @return attributes to be retrieved for an existing AD user.
     */
    String[] getADUserAttributes();

    /**
     * Timeout in milliseconds when attempting to connect to AD.
     *
     * @return Timeout in milliseconds when attempting to connect to AD.
     */
    int getADTimeout();
}

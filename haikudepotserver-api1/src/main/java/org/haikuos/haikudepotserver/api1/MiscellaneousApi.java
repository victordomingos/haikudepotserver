/*
 * Copyright 2013-2014, Andrew Lindesay
 * Distributed under the terms of the MIT License.
 */

package org.haikuos.haikudepotserver.api1;

import com.googlecode.jsonrpc4j.JsonRpcService;
import org.haikuos.haikudepotserver.api1.model.miscellaneous.*;

@JsonRpcService("/api/v1/miscellaneous")
public interface MiscellaneousApi {

    /**
     * <p>This method will raise a runtime exception to test the behaviour of the server and client in this
     * situation.</p>
     */

    RaiseExceptionResult raiseException(RaiseExceptionRequest raiseExceptionRequest);

    /**
     * <p>This method will return information about the running application server.</p>
     */

    GetRuntimeInformationResult getRuntimeInformation(GetRuntimeInformationRequest getRuntimeInformationRequest);

    /**
     * <p>This method will return all of the localization messages that might be able to be displayed
     * to the user from the result of validation problems and so on.</p>
     */

    GetAllMessagesResult getAllMessages(GetAllMessagesRequest getAllMessagesRequest);

    /**
     * <P>This method will return a list of all of the possible architectures in the system such as x86 or arm.
     * Note that this will explicitly exclude the pseudo-architectures of "source" and "any".</p>
     */

    GetAllArchitecturesResult getAllArchitectures(GetAllArchitecturesRequest getAllArchitecturesRequest);

}

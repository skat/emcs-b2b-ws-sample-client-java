/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package dk.skat.emcs.b2b.sample;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.wss4j.common.ext.WSPasswordCallback;

/**
 * UTPasswordCallback
 *
 * @author SKAT
 * @since 1.2
 */
public class UTPasswordCallback implements CallbackHandler {

    private Map<String, String> passwords =
            new HashMap<String, String>();

    public UTPasswordCallback() {
        String P12_ALIAS = getConfig().getString("dk.skat.emcs.b2b.sample.ClientCertAlias");
        String P12_PASSPHRASE = getConfig().getString("dk.skat.emcs.b2b.sample.P12_PASSPHRASE");
        passwords.put(P12_ALIAS, P12_PASSPHRASE);
    }

    /**
     * Here, we attempt to get the password from the private 
     * alias/passwords map.
     */
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];

            String pass = passwords.get(pc.getIdentifier());
            if (pass != null) {
                pc.setPassword(pass);
                return;
            }
        }
    }

    /**
     * Add an alias/password pair to the callback mechanism.
     */
    public void setAliasPassword(String alias, String password) {
        passwords.put(alias, password);
    }

    private static Config getConfig() {
        return ConfigFactory.parseFile(new File("app.conf")).withFallback(ConfigFactory.load());
    }

}

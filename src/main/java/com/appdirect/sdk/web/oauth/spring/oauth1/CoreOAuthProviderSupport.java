package com.appdirect.sdk.web.oauth.spring.oauth1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.DecoderException;
import org.springframework.security.oauth.common.OAuthCodec;
import org.springframework.security.oauth.common.OAuthConsumerParameter;
import org.springframework.security.oauth.common.StringSplitUtils;

/** @deprecated */
@Deprecated
public class CoreOAuthProviderSupport implements OAuthProviderSupport {
    private final Set<String> supportedOAuthParameters;
    private String baseUrl = null;

    public CoreOAuthProviderSupport() {
        Set<String> supportedOAuthParameters = new TreeSet();
        OAuthConsumerParameter[] var2 = OAuthConsumerParameter.values();
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            OAuthConsumerParameter supportedParameter = var2[var4];
            supportedOAuthParameters.add(supportedParameter.toString());
        }

        this.supportedOAuthParameters = supportedOAuthParameters;
    }

    public Map<String, String> parseParameters(HttpServletRequest request) {
        Map<String, String> parameters = this.parseHeaderParameters(request);
        if (parameters == null) {
            parameters = new HashMap();
            Iterator var3 = this.getSupportedOAuthParameters().iterator();

            while(var3.hasNext()) {
                String supportedOAuthParameter = (String)var3.next();
                String param = request.getParameter(supportedOAuthParameter);
                if (param != null) {
                    ((Map)parameters).put(supportedOAuthParameter, param);
                }
            }
        }

        return (Map)parameters;
    }

    protected Map<String, String> parseHeaderParameters(HttpServletRequest request) {
        String header = null;
        Enumeration<String> headers = request.getHeaders("Authorization");

        while(headers.hasMoreElements()) {
            String value = (String)headers.nextElement();
            if (value.toLowerCase().startsWith("oauth ")) {
                header = value;
                break;
            }
        }

        Map<String, String> parameters = null;
        if (header != null) {
            parameters = new HashMap();
            String authHeaderValue = header.substring(6);
            String[] headerEntries = StringSplitUtils.splitIgnoringQuotes(authHeaderValue, ',');
            Iterator var7 = StringSplitUtils.splitEachArrayElementAndCreateMap(headerEntries, "=", "\"").entrySet().iterator();

            while(var7.hasNext()) {
                Object o = var7.next();
                Map.Entry entry = (Map.Entry)o;

                try {
                    String key = OAuthCodec.oauthDecode((String)entry.getKey());
                    String value = OAuthCodec.oauthDecode((String)entry.getValue());
                    parameters.put(key, value);
                } catch (DecoderException var12) {
                    throw new IllegalStateException(var12);
                }
            }
        }

        return parameters;
    }

    protected Set<String> getSupportedOAuthParameters() {
        return this.supportedOAuthParameters;
    }

    public String getSignatureBaseString(HttpServletRequest request) {
        SortedMap<String, SortedSet<String>> significantParameters = this.loadSignificantParametersForSignatureBaseString(request);
        StringBuilder queryString = new StringBuilder();
        Iterator<Map.Entry<String, SortedSet<String>>> paramIt = significantParameters.entrySet().iterator();

        label31:
        while(paramIt.hasNext()) {
            Map.Entry<String, SortedSet<String>> sortedParameter = (Map.Entry)paramIt.next();
            Iterator<String> valueIt = ((SortedSet)sortedParameter.getValue()).iterator();

            while(true) {
                do {
                    if (!valueIt.hasNext()) {
                        continue label31;
                    }

                    String parameterValue = (String)valueIt.next();
                    queryString.append((String)sortedParameter.getKey()).append('=').append(parameterValue);
                } while(!paramIt.hasNext() && !valueIt.hasNext());

                queryString.append('&');
            }
        }

        String url = this.getBaseUrl(request);
        if (url == null) {
            url = request.getRequestURL().toString();
        }

        url = this.normalizeUrl(url);
        url = OAuthCodec.oauthEncode(url);
        String method = request.getMethod().toUpperCase();
        return method + '&' + url + '&' + OAuthCodec.oauthEncode(queryString.toString());
    }

    protected String normalizeUrl(String url) {
        try {
            URL requestURL = new URL(url);
            StringBuilder normalized = (new StringBuilder(requestURL.getProtocol().toLowerCase())).append("://").append(requestURL.getHost().toLowerCase());
            if (requestURL.getPort() >= 0 && requestURL.getPort() != requestURL.getDefaultPort()) {
                normalized.append(":").append(requestURL.getPort());
            }

            normalized.append(requestURL.getPath());
            return normalized.toString();
        } catch (MalformedURLException var4) {
            throw new IllegalStateException("Illegal URL for calculating the OAuth signature.", var4);
        }
    }

    protected SortedMap<String, SortedSet<String>> loadSignificantParametersForSignatureBaseString(HttpServletRequest request) {
        SortedMap<String, SortedSet<String>> significantParameters = new TreeMap();
        Enumeration parameterNames = request.getParameterNames();

        while(parameterNames.hasMoreElements()) {
            String parameterName = (String)parameterNames.nextElement();
            String[] values = request.getParameterValues(parameterName);
            if (values == null) {
                values = new String[]{""};
            }

            parameterName = OAuthCodec.oauthEncode(parameterName);
            String[] var6 = values;
            int var7 = values.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String parameterValue = var6[var8];
                if (parameterValue == null) {
                    parameterValue = "";
                }

                parameterValue = OAuthCodec.oauthEncode(parameterValue);
                SortedSet<String> significantValues = (SortedSet)significantParameters.get(parameterName);
                if (significantValues == null) {
                    significantValues = new TreeSet();
                    significantParameters.put(parameterName, significantValues);
                }

                ((SortedSet)significantValues).add(parameterValue);
            }
        }

        Map<String, String> oauthParams = this.parseParameters(request);
        oauthParams.remove("realm");
        Set<String> parsedParams = oauthParams.keySet();

        String parameterValue;
        SortedSet<String> significantValues;
        for(Iterator var13 = parsedParams.iterator(); var13.hasNext(); ((SortedSet)significantValues).add(parameterValue)) {
            String parameterName = (String)var13.next();
            parameterValue = (String)oauthParams.get(parameterName);
            if (parameterValue == null) {
                parameterValue = "";
            }

            parameterName = OAuthCodec.oauthEncode(parameterName);
            parameterValue = OAuthCodec.oauthEncode(parameterValue);
            significantValues = significantParameters.get(parameterName);
            if (significantValues == null) {
                significantValues = new TreeSet();
                significantParameters.put(parameterName, significantValues);
            }
        }

        significantParameters.remove(OAuthConsumerParameter.oauth_signature.toString());
        return significantParameters;
    }

    protected String getBaseUrl(HttpServletRequest request) {
        String baseUrl = this.getBaseUrl();
        if (baseUrl != null) {
            StringBuilder builder = new StringBuilder(baseUrl);
            String path = request.getRequestURI();
            if (path != null && !"".equals(path)) {
                if (!baseUrl.endsWith("/") && !path.startsWith("/")) {
                    builder.append('/');
                }

                builder.append(path);
            }

            baseUrl = builder.toString();
        }

        return baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}

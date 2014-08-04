package smartRecruiter;

import java.io.InputStream;
import java.util.Map;

public interface Connector {
    String AUTHORIZE_URL = "https://api.linkedin.com/uas/oauth/authorize?oauth_token=";
    String CONSUMER_KEY = "770tqx99p4dr1p";
    String CONSUMER_SECRET = "0eZGM1FUvG6y2EO4";
    String SIGN_OUT_URL = "https://api.linkedin.com/uas/oauth/invalidateToken";

    String authenticate();

    String[] authorize(String code) throws Exception;

    SiteResponse get(String url);

    void init(String authToken, String authSecret);

    int signOut();

    public interface SiteResponse {
        public abstract String getBody();

        public abstract int getCode();

        public abstract String getHeader(final String name);

        public abstract Map getHeaders();

        public abstract InputStream getStream();
    }
}
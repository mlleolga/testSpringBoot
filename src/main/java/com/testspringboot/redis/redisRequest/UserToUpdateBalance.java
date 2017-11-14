package com.testspringboot.redis.redisRequest;


public class UserToUpdateBalance {
    private Long userId;
    private String integrationApiKey;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIntegrationApiKey() {
        return integrationApiKey;
    }

    public void setIntegrationApiKey(String integrationApiKey) {
        this.integrationApiKey = integrationApiKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserToUpdateBalance)) return false;

        UserToUpdateBalance that = (UserToUpdateBalance) o;

        if (!userId.equals(that.userId)) return false;
        return integrationApiKey.equals(that.integrationApiKey);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + integrationApiKey.hashCode();
        return result;
    }
}

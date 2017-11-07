package com.testspringboot.persistance;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="sent_mes")
public class SentMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "integration_api_key")
    private String integrationApiKey;
    private String content;


    @Column(name="created_date", nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "rate_message")
    private BigDecimal rateMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntegrationApiKey() {
        return integrationApiKey;
    }

    public void setIntegrationApiKey(String integrationApiKey) {
        this.integrationApiKey = integrationApiKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getRateMessage() {
        return rateMessage;
    }

    public void setRateMessage(BigDecimal rateMessage) {
        this.rateMessage = rateMessage;
    }
}

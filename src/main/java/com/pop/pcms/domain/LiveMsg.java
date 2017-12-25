package com.pop.pcms.domain;

import com.pop.pcms.domain.enumeration.MsgType;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 聊天消息
 */
@ApiModel(description = "聊天消息")
@Entity
@Table(name = "live_msg")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LiveMsg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fm")
    private String fm;

    @Column(name = "tos")
    private String tos;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private MsgType type;

    @Column(name = "msg")
    private String msg;

    @Column(name = "send_time")
    private LocalDateTime send_time;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    //对应的活动
    @ManyToOne
    private PopCompetition competition;

    @Column(name = "msg_id")
    private Long msg_id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getTos() {
        return tos;
    }

    public void setTos(String tos) {
        this.tos = tos;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public LocalDateTime getSend_time() {
        return send_time;
    }

    public void setSend_time(LocalDateTime send_time) {
        this.send_time = send_time;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public PopCompetition getCompetition() {
        return competition;
    }

    public void setCompetition(PopCompetition competition) {
        this.competition = competition;
    }

    public Long getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(Long msg_id) {
        this.msg_id = msg_id;
    }
}

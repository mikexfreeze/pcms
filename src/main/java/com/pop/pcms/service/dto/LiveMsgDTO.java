package com.pop.pcms.service.dto;

import com.pop.pcms.domain.PopCompetition;
import com.pop.pcms.domain.enumeration.MsgType;
import net.sf.oval.constraint.NotEmpty;
import net.sf.oval.constraint.NotNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 聊天消息
 */
public class LiveMsgDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotNull(message = "[from]不能为空!",profiles = "pf1")
    @NotEmpty(message = "[from]不能为空!",profiles = "pf1")
    private String from;

    private String to;

    private MsgType type;

    @NotNull(message = "[msg]不能为空!",profiles = "pf2")
    @NotEmpty(message = "[msg]不能为空!",profiles = "pf2")
    private String msg;

    private Date send_time;

    private Date create_time;

    @NotNull(message = "[msg]不能为空!",profiles = "pf5")
    @NotEmpty(message = "[msg]不能为空!",profiles = "pf5")
    private Long competition_id;

    //消息ID
    @NotNull(message = "[msgId]不能为空!",profiles = "pf3")
    @NotEmpty(message = "[msgId]不能为空!",profiles = "pf3")
    private Long msgId;

    //请求的方法
    @NotNull(message = "[method]不能为空!",profiles = "pf4")
    @NotEmpty(message = "[method]不能为空!",profiles = "pf4")
    private String method;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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

    public Date getSend_time() {
        return send_time;
    }

    public void setSend_time(Date send_time) {
        this.send_time = send_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getCompetition_id() {
        return competition_id;
    }

    public void setCompetition_id(Long competition_id) {
        this.competition_id = competition_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
}

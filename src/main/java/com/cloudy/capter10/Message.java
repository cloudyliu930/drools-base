package com.cloudy.capter10;
 
import java.io.Serializable;
 
/**
 * bean
 * @author caicongyang1
 * @version id: Message, v 0.1 16/10/26 下午2:49 caicongyang1 Exp $$
 */
public class Message implements Serializable {
 
    private static final long serialVersionUID = 1L;
 
    private String            status;
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
}

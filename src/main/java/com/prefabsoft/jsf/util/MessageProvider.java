package com.prefabsoft.jsf.util;

import java.util.HashMap;

public class MessageProvider extends HashMap{

    private MessageManager msgMgr;
    public MessageProvider() {
    }


    @Override
    public Object get(Object key) {
        return msgMgr.getMessage((String)key);
    }

    public void setMsgMgr(MessageManager msgMgr) {
        this.msgMgr = msgMgr;
    }

    public MessageManager getMsgMgr() {
        return msgMgr;
    }
}

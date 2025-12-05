package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.vampire_talent_show_req)
public class HandlerVampireTalentShowReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Encode and send
        return session.encodeMsg(NetMsgId.vampire_talent_show_succeed_ack);
    }

}

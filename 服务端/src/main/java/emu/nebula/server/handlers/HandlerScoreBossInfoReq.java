package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.ScoreBossInfoOuterClass.ScoreBossInfo;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.score_boss_info_req)
public class HandlerScoreBossInfoReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Build response
        var rsp = ScoreBossInfo.newInstance()
                .setControlId(session.getPlayer().getScoreBossManager().getControlId());
        
        // Encode and send
        return session.encodeMsg(NetMsgId.score_boss_info_succeed_ack, rsp);
    }

}

package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.ScoreBossApply.ScoreBossApplyReq;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.score_boss_apply_req)
public class HandlerScoreBossApplyReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = ScoreBossApplyReq.parseFrom(message);
        
        // Apply
        boolean success = session.getPlayer().getScoreBossManager().apply(req.getLevelId(), req.getBuildId());
        
        // Encode and send
        return session.encodeMsg(success ? NetMsgId.score_boss_apply_succeed_ack : NetMsgId.score_boss_apply_failed_ack);
    }

}

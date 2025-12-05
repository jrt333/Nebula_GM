package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.Public.UI32;
import emu.nebula.net.HandlerId;
import emu.nebula.Nebula;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.tutorial_level_settle_req)
public class HandlerTutorialLevelSettleReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = UI32.parseFrom(message);
        
        // Settle
        boolean success = Nebula.getGameContext().getTutorialModule().settle(session.getPlayer(), req.getValue());
        
        if (success == false) {
            return session.encodeMsg(NetMsgId.tutorial_level_settle_failed_ack);
        }
        
        // Encode and send
        return session.encodeMsg(NetMsgId.tutorial_level_settle_succeed_ack);
    }

}

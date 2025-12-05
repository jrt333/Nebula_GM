package emu.nebula.server.handlers;

import emu.nebula.net.NetHandler;
import emu.nebula.net.NetMsgId;
import emu.nebula.proto.AgentGiveUp.AgentGiveUpReq;
import emu.nebula.proto.AgentGiveUp.AgentGiveUpResp;
import emu.nebula.net.HandlerId;
import emu.nebula.net.GameSession;

@HandlerId(NetMsgId.agent_give_up_req)
public class HandlerAgentGiveUpReq extends NetHandler {

    @Override
    public byte[] handle(GameSession session, byte[] message) throws Exception {
        // Parse request
        var req = AgentGiveUpReq.parseFrom(message);
        
        // Give up
        var agent = session.getPlayer().getAgentManager().giveUp(req.getId());
        
        if (agent == null) {
            return session.encodeMsg(NetMsgId.agent_give_up_failed_ack);
        }
        
        // Build response
        var rsp = AgentGiveUpResp.newInstance()
                .addAllCharIds(agent.getCharIds());
        
        // Encode and send
        return session.encodeMsg(NetMsgId.agent_give_up_succeed_ack, rsp);
    }

}
